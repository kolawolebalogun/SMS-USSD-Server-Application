package com.kolawolebalogun;

import com.kolawolebalogun.app.Variables;
import com.kolawolebalogun.constants.AppConstants;
import com.kolawolebalogun.database.MongoDatabaseConnection;
import com.kolawolebalogun.jsmpp.SMPP;
import com.kolawolebalogun.pojo.CustomSMPPSession;
import com.kolawolebalogun.pojo.SMPPBind;
import com.kolawolebalogun.pojo.TelcoAPI;
import com.kolawolebalogun.rabbitmq.Consumer;
import com.kolawolebalogun.util.Util;
import com.mongodb.client.MongoDatabase;
import org.jsmpp.session.SMPPSession;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {

            List<SMPPBind> smppBinds = Util.getSMPPAccounts();
            for (SMPPBind sm : smppBinds) {
                for (int i = 0; i < sm.getMaxConnections(); i++) {
                    SMPP smpp = new SMPP(sm);
                    SMPPSession session = smpp.newSession();
                    CustomSMPPSession smppSession = new CustomSMPPSession();
                    smppSession.setSession(session);
                    smppSession.setSmppBind(sm);

                    ConcurrentLinkedQueue<CustomSMPPSession> smppSessions = Variables.smppSessions.get(String.valueOf(sm.getID()));
                    if (smppSessions != null) {
                        ConcurrentLinkedQueue<CustomSMPPSession> smppSessionQueue = Variables.smppSessions.get(String.valueOf(sm.getID()));
                        smppSessionQueue.add(smppSession);
                    } else {
                        ConcurrentLinkedQueue<CustomSMPPSession> smppSessionQueue = new ConcurrentLinkedQueue<>();
                        smppSessionQueue.add(smppSession);
                        Variables.smppSessions.put(String.valueOf(sm.getID()), smppSessionQueue);
                    }
                }
            }
            Util.systemOut((char) 27 + String.format("[%s;%sm[x] Waiting for SMSC connection to stabilize ...", AppConstants.OUTPUT_BRIGHT, AppConstants.OUTPUT_COLOR_YELLOW));
            Thread.sleep(5000);

            Util.systemOut((char) 27 + String.format("[%s;%sm[x] SMSC connection stable ...", AppConstants.OUTPUT_BRIGHT, AppConstants.OUTPUT_COLOR_GREEN));


            // Start DND Upload Consumer
            for (int i = 0; i < AppConstants.MESSAGE_BROKER_QUEUE_DND_UPLOAD_WORKER; i++) {
                Consumer consumer = new Consumer(AppConstants.MESSAGE_BROKER_QUEUE_DND_UPLOAD);
                Thread consumerThread = new Thread(consumer);
                consumerThread.start();
            }

            // Start DND Assembly Consumer
            for (int i = 0; i < AppConstants.MESSAGE_BROKER_QUEUE_DND_ASSEMBLY_WORKER; i++) {
                Consumer consumer = new Consumer(AppConstants.MESSAGE_BROKER_QUEUE_DND_ASSEMBLY);
                Thread consumerThread = new Thread(consumer);
                consumerThread.start();
            }

            // Start Content Upload Consumer
            for (int i = 0; i < AppConstants.MESSAGE_BROKER_QUEUE_CONTENT_UPLOAD_WORKER; i++) {
                Consumer consumer = new Consumer(AppConstants.MESSAGE_BROKER_QUEUE_CONTENT_UPLOAD, true);
                Thread consumerThread = new Thread(consumer);
                consumerThread.start();
            }

            // Start Incoming Messages Consumer
            for (int i = 0; i < AppConstants.MESSAGE_BROKER_QUEUE_INCOMING_MESSAGES_WORKER; i++) {
                Consumer consumer = new Consumer(AppConstants.MESSAGE_BROKER_QUEUE_INCOMING_MESSAGES, true);
                Thread consumerThread = new Thread(consumer);
                consumerThread.start();
            }


            // Start Awaiting Async Consumer
            for (int i = 0; i < AppConstants.MESSAGE_BROKER_QUEUE_AWAITING_ASYNC_WORKER; i++) {
                Consumer consumer = new Consumer(AppConstants.MESSAGE_BROKER_QUEUE_AWAITING_ASYNC, true);
                Thread consumerThread = new Thread(consumer);
                consumerThread.start();
            }

            // Start Telco API Consumer
            List<TelcoAPI> telcoAPIs = Util.getTelcoAPIs();
            for (TelcoAPI telcoAPI : telcoAPIs) {
                for (int i = 0; i < telcoAPI.getMaxConnection(); i++) {
                    Consumer consumer = new Consumer(String.format("%s%s_%s", AppConstants.PREFIX_TELCO_API_REQUEST, telcoAPI.getApiCategory(), telcoAPI.getID()), true);
                    Thread consumerThread = new Thread(consumer);
                    consumerThread.start();
                }
            }

            // Start Content Provider Receipt Consumer
            for (int i = 0; i < AppConstants.MESSAGE_BROKER_QUEUE_CONTENT_PROVIDER_RECEIPT_WORKER; i++) {
                Consumer consumer = new Consumer(AppConstants.MESSAGE_BROKER_QUEUE_CONTENT_PROVIDER_RECEIPT, true);
                Thread consumerThread = new Thread(consumer);
                consumerThread.start();
            }

            // Start Outgoing Message Consumer
            for (Map.Entry<String, ConcurrentLinkedQueue<CustomSMPPSession>> entry : Variables.smppSessions.entrySet()) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    Consumer consumer = new Consumer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, entry.getKey()), true);
                    Thread consumerThread = new Thread(consumer);
                    consumerThread.start();
                }
            }

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Map.Entry<String, ConcurrentLinkedQueue<CustomSMPPSession>> entry : Variables.smppSessions.entrySet()) {
                        try {
                            SMPPBind smppBind = Util.getSMPPAccount(Integer.parseInt(entry.getKey()));
                            System.out.println(String.format("[x] %s %s bind(%s:%s) has %s Connections ", smppBind.getTelcoName(), smppBind.getAccountName().toLowerCase(), smppBind.getIp(), smppBind.getPort(), entry.getValue().size()));
                        } catch (SQLException e) {
                            if (AppConstants.showError) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }, 2000, 20000);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
