package com.kolawolebalogun;

import com.google.gson.Gson;
import com.kolawolebalogun.app.Variables;
import com.kolawolebalogun.constants.AppConstants;
import com.kolawolebalogun.jsmpp.SMPP;
import com.kolawolebalogun.pojo.CustomSMPPSession;
import com.kolawolebalogun.pojo.SMPPBind;
import com.kolawolebalogun.pojo.TelcoAPI;
import com.kolawolebalogun.rabbitmq.Consumer;
import com.kolawolebalogun.util.Util;
import com.kolawolebalogun.websocket.application.Client;
import com.kolawolebalogun.websocket.application.ClientEndpoint;
import com.kolawolebalogun.websocket.utils.Messages;
import org.glassfish.tyrus.client.ClientManager;
import org.jsmpp.session.SMPPSession;

import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    static Session session = null;

    public static void main(String[] args) {
        // write your code here
        try {

            List<SMPPBind> smppBinds = Util.getSMPPAccounts();
            for (SMPPBind sm : smppBinds) {
                for (int i = 0; i < sm.getMaxConnections(); i++) {
                    try {
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
                    } catch (Exception e) {

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

            // Sleep before connecting to web socket
            Thread.sleep(5000);

            Client client = new Client();
            session = client.connect();
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }
    }
}
