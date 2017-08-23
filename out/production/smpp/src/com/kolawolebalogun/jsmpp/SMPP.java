package com.kolawolebalogun.jsmpp;

/**
 * Created by KolawoleBalogun on 7/12/17.
 */

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import com.kolawolebalogun.app.Variables;
import com.kolawolebalogun.constants.AppConstants;
import com.kolawolebalogun.pojo.CustomSMPPSession;
import com.kolawolebalogun.pojo.SMPPBind;
import com.kolawolebalogun.pojo.SubmitMessage;
import com.kolawolebalogun.pojo.Telco;
import com.kolawolebalogun.processors.Processors;
import com.kolawolebalogun.rabbitmq.Producer;
import com.kolawolebalogun.util.Util;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.*;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.extra.SessionState;
import org.jsmpp.session.*;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.InvalidDeliveryReceiptException;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SMPP implements MessageReceiverListener {
    private static Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    static {
        root.setLevel(Level.ERROR);
    }

    private SMPPSession session;

    private SMPPBind smppBind;

    public SMPP(SMPPBind smppBind) {
        this.smppBind = smppBind;
    }

    public SMPPSession newSession() throws PDUException, IOException, InvalidResponseException, NegativeResponseException, ResponseTimeoutException {
        session = new SMPPSession();
        try {
            System.out.println("[x] Connect and bind to " + smppBind.getIp() + " port " + smppBind.getPort());
            session.connectAndBind(smppBind.getIp(), smppBind.getPort(), new BindParameter(BindType.valueOf(smppBind.getType()), smppBind.getAccountName(), smppBind.getAccountPassword(), smppBind.getSystemType(), TypeOfNumber.valueOf((byte) smppBind.getAddressTON()), NumberingPlanIndicator.valueOf((byte) smppBind.getAddressNPI()), null));
            session.addSessionStateListener(new SessionStateListenerImpl());
            session.setMessageReceiverListener(this);
            session.setTransactionTimer(1800);
            if ((session.getSessionState().isBound()) || (session.getSessionState() == SessionState.OPEN)) {
                System.out.println(String.format("[x] Connection to %s | %s | %s is opened", smppBind.getIp(), smppBind.getPort(), smppBind.getAccountName()));
            }
        } catch (IOException e) {
            // Failed connect and bind to SMSC
            System.err.println("[x] Failed connect and bind to host");
            e.printStackTrace();
        }

        return session;
    }

    private void reconnectAfter(final long timeInMillis) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(timeInMillis);
                } catch (InterruptedException e) {
                    if (AppConstants.showError) {
                        e.printStackTrace();
                    }
                }

                int attempt = 0;
                while (session == null || session.getSessionState().equals(SessionState.CLOSED)) {
                    try {
                        ConcurrentLinkedQueue<CustomSMPPSession> smppSessions = Variables.smppSessions.get(String.valueOf(smppBind.getID()));
                        if (smppSessions != null) {
                            for (CustomSMPPSession s : smppSessions) {
                                if (s.getSession().getSessionId().equalsIgnoreCase(session.getSessionId())) {
                                    smppSessions.remove(s);
                                }
                            }
                            System.out.println("[x] Reconnecting attempt #" + (++attempt) + "...");
                            session = newSession();

                            CustomSMPPSession customSMPPSession = new CustomSMPPSession();
                            customSMPPSession.setSmppBind(smppBind);
                            customSMPPSession.setSession(session);

                            Variables.smppSessions.get(String.valueOf(smppBind.getID())).add(customSMPPSession);
                        }
                    } catch (IOException e) {
                        System.out.println("[x] Failed opening connection and bind to " + smppBind.getIp() + ":" + smppBind.getPort());
                        if (AppConstants.showError) {
                            e.printStackTrace();
                        }
                        // wait for a second
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ee) {
                            if (AppConstants.showError) {
                                ee.printStackTrace();
                            }
                        }
                    } catch (PDUException | NegativeResponseException | InvalidResponseException | ResponseTimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onAcceptDeliverSm(DeliverSm deliverSm) throws ProcessRequestException {
        try {
            if (MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm.getEsmClass())) {
                try {
                    DeliveryReceipt delReceipt = deliverSm.getShortMessageAsDeliveryReceipt();

                    String messageId = delReceipt.getId().toUpperCase();
                    DeliveryReceipt deliveryReceipt = deliverSm.getShortMessageAsDeliveryReceipt();
                    System.out.println("[x] Receiving delivery receipt for message '" + messageId + "' from " + deliverSm.getSourceAddr() + " to " + deliverSm.getDestAddress() + " : " + delReceipt);
                    String[] messageID = {deliveryReceipt.getId(), null};

                    SubmitMessage message = new SubmitMessage();
                    message.setSourceAddress(deliverSm.getSourceAddr());
                    message.setSourceAddress(deliverSm.getDestAddress());
                    message.setSms(new String(deliverSm.getShortMessage(), "UTF-8"));
                    Util.logReport(messageID, deliveryReceipt.getFinalStatus().toString(), message);
                } catch (InvalidDeliveryReceiptException ignored) {
                }

            } else {
                Telco telco = Util.getTelcoBySMPPSession(smppBind.getID());
                String task = String.format("{\"telco_id\": \"%s\", \"source_address\": \"%s\", \"destination_address\": \"%s\", \"keyword\": \"%s\", \"bearer\": \"sms\"}", telco.getID(), Util.internationalNumberFormat(deliverSm.getSourceAddr()), deliverSm.getDestAddress(), new String(deliverSm.getShortMessage(), "UTF-8"));

                String[] messageID = {deliverSm.getId(), null};
                SubmitMessage message = new SubmitMessage();
                message.setSourceAddress(deliverSm.getSourceAddr());
                message.setDestinationAddress(deliverSm.getDestAddress());
                message.setSms(new String(deliverSm.getShortMessage(), "UTF-8"));
                Util.logReport(messageID, AppConstants.SMS_STATUS_RECEIVED, message);

                System.out.println("[x] " + new Gson().toJson(message));

                if (message.getSms().trim().replace("\n", "").replace("\r", "").equalsIgnoreCase(AppConstants.KEYWORD_GENERAL_HELP)) {
                    message.setAction(AppConstants.OUTGOING_MESSAGES_DEFAULT);
                    message.setSourceAddress(deliverSm.getDestAddress());
                    message.setDestinationAddress(deliverSm.getSourceAddr());
                    List<String> helpMessages = Util.getGeneralHelpMessage(telco.getID());
                    if (helpMessages.size() > 0) {
                        Producer producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, smppBind.getID()), true);
                        Util.sendMultipleMessages(helpMessages, producer, message);
                        producer.close();
                    }
                } else if (message.getSms().trim().replace("\n", "").replace("\r", "").equalsIgnoreCase(AppConstants.KEYWORD_GENERAL_STOP)) {
                    message.setAction(AppConstants.OUTGOING_MESSAGES_DEFAULT);
                    message.setSourceAddress(deliverSm.getDestAddress());
                    message.setDestinationAddress(deliverSm.getSourceAddr());
                    List<String> stopMessages = Util.getGeneralStopMessage(message.getDestinationAddress());
                    if (stopMessages.size() > 0) {
                        Producer producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, smppBind.getID()), true);
                        Util.sendMultipleMessages(stopMessages, producer, message);
                        producer.close();
                    } else {
                        Producer producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, smppBind.getID()), true);
                        message.setSms(String.format("You do not have any active subscription. Send %s to %s", AppConstants.KEYWORD_GENERAL_HELP.toUpperCase(), message.getSourceAddress()));
                        producer.sendMessage(new Gson().toJson(message), AppConstants.MO_PRIORITY);
                        producer.close();
                    }
                } else {
                    Producer producer = new Producer(AppConstants.MESSAGE_BROKER_QUEUE_INCOMING_MESSAGES, true);
                    producer.sendMessage(task, AppConstants.MO_PRIORITY);
                    producer.close();
                }
            }
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }
    }

    public static String[] sendSMS(String destinationAddress, String sourceAddress, String sms, int protocolID, CustomSMPPSession messageSMPPSession) {
        Util.systemOut((char) 27 + String.format("[%sm[x] Sending %s with session %s to %s from %s  with protocol %s on bind %s%s ", AppConstants.OUTPUT_COLOR_YELLOW, (sms != null) ? sms.trim().replaceAll("\\n", "").replaceAll("\\r", "") : "null", messageSMPPSession.getSession().getSessionId(), destinationAddress, sourceAddress, protocolID, AppConstants.PREFIX_OUTGOING_BIND, messageSMPPSession.getSmppBind().getID()));
        String[] response = {null, null};

        try {
            if ((messageSMPPSession.getSession().getSessionState() == SessionState.CLOSED) || (destinationAddress == null || destinationAddress.trim().equalsIgnoreCase("")) || (sourceAddress == null || sourceAddress.trim().equalsIgnoreCase("")) || (sms == null || sms.trim().equalsIgnoreCase(""))) {
                return response;
            }

            if (protocolID == AppConstants.SUBMIT_SM_64) {
                if (AppConstants.showError) {
                    System.out.println("[x] Sending Quite Message ");

                }
            }
            String messageID = null;
            String errorCode = null;

            try {
                final RegisteredDelivery registeredDelivery = new RegisteredDelivery();
                registeredDelivery.setSMSCDeliveryReceipt(SMSCDeliveryReceipt.SUCCESS_FAILURE);
                messageID = messageSMPPSession.getSession().submitShortMessage("CMT", TypeOfNumber.valueOf((byte) messageSMPPSession.getSmppBind().getSourceAddressTON()),
                        NumberingPlanIndicator.valueOf((byte) messageSMPPSession.getSmppBind().getSourceAddressNPI()), sourceAddress, TypeOfNumber.valueOf((byte) messageSMPPSession.getSmppBind().getDestinationAddressTON()), NumberingPlanIndicator.valueOf((byte) messageSMPPSession.getSmppBind().getDestinationAddressNPI()),
                        destinationAddress, new ESMClass(), (byte) protocolID, (byte) 1, new AbsoluteTimeFormatter().format(new java.util.Date()), null,
                        registeredDelivery, (byte) 0, new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1,
                                false), (byte) 0, sms.getBytes());
            } catch (PDUException | IOException | InvalidResponseException e) {
                e.printStackTrace();
            } catch (ResponseTimeoutException e) {
                errorCode = AppConstants.ERROR_SEND_MESSAGE_TIMEOUT;
            } catch (NegativeResponseException e) {
                errorCode = Processors.processorSMPPNegativeValueExceptionMessage(e.getMessage());
            }

            response[0] = messageID;
            response[1] = errorCode;

            System.out.println(String.format("[x] Send Message response %s", new Gson().toJson(response)));
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }

        return response;
    }

    @Override
    public void onAcceptAlertNotification(AlertNotification alertNotification) {
    }

    @Override
    public DataSmResult onAcceptDataSm(DataSm dataSm, Session session) throws ProcessRequestException {
        return null;
    }


    private class SessionStateListenerImpl implements SessionStateListener {
        public void onStateChange(SessionState newState, SessionState oldState,
                                  Session source) {
            if (newState.equals(SessionState.CLOSED)) {
                System.out.println("[x] Session closed " + session.getSessionId());
                long reconnectInterval = 5000L;
                reconnectAfter(reconnectInterval);
            }
        }
    }
}

