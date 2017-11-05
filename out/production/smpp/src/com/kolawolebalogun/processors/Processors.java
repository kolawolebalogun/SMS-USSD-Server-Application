package com.kolawolebalogun.processors;

import com.google.gson.Gson;
import com.kolawolebalogun.app.Variables;
import com.kolawolebalogun.constants.AppConstants;
import com.kolawolebalogun.jsmpp.SMPP;
import com.kolawolebalogun.pojo.*;
import com.kolawolebalogun.rabbitmq.Producer;
import com.kolawolebalogun.util.Util;
import org.jsmpp.session.SMPPSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KolawoleBalogun on 7/11/17.
 */
public class Processors {
    public static void processorDNDUpload(String task) {
        try {
            Gson g = new Gson();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(task);
            System.out.println(g.toJson(json));

            try (BufferedReader br = new BufferedReader(new FileReader(json.get("file_path").toString()))) {
                String line;
                int counter = 0;
                Producer producer = new Producer(AppConstants.MESSAGE_BROKER_QUEUE_DND_ASSEMBLY);
                while ((line = br.readLine()) != null) {
                    counter++;
                    if ((counter % AppConstants.MESSAGE_BROKER_PRODUCER_TIMEOUT_COUNT) == 0) {
                        // Start a fresh producer
                        producer.close();
                        Util.systemOut((char) 27 + String.format("[%s;%sm[x] Closing current producer to start a fresh one ", AppConstants.OUTPUT_BRIGHT, AppConstants.OUTPUT_COLOR_WHITE));
                        Thread.sleep(2000);
                        producer = new Producer(AppConstants.MESSAGE_BROKER_QUEUE_DND_ASSEMBLY);
                        producer.sendMessage(line);
                    } else {
                        producer.sendMessage(line);
                    }
                }
                producer.close();
            }
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }
    }

    public static void processorTelcoAPIRequest(String task)
            throws IOException, ParseException, TimeoutException, ClassNotFoundException,
            SQLException, InstantiationException, IllegalAccessException {

        SubmitMessage submitMessage = new Gson().fromJson(task, SubmitMessage.class);

        String responseCode;
        String responseDescription;
        String responseTransactionID;
        boolean billingSuccessful = false;
        boolean insufficientFund = false;
        boolean successful = false;
        TelcoAPI telcoAPI = new TelcoAPI();

        Service service = submitMessage.getService();
        if (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_SUBSCRIBE || submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE) {
            if (submitMessage.getService().getParentID() != 0) {
                service = Util.getServiceByID(submitMessage.getService().getParentID());
            }

            if (service.getTelcoAPISubscriptionID() != 0 && (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_SUBSCRIBE || service.getTelcoAPISubscriptionBills())) {
                telcoAPI.setID(service.getTelcoAPISubscriptionID());
                telcoAPI.setName(service.getTelcoAPISubscriptionName());
                telcoAPI.setURL(service.getTelcoAPISubscriptionURL());
                telcoAPI.setType(service.getTelcoAPISubscriptionType());
                telcoAPI.setMethod(service.getTelcoAPISubscriptionMethod());
                telcoAPI.setBodyType(service.getTelcoAPISubscriptionBodyType());
                telcoAPI.setHeader(service.getTelcoAPISubscriptionHeader());
                telcoAPI.setBody(service.getTelcoAPISubscriptionBody());
                telcoAPI.setBodyRaw(service.getTelcoAPISubscriptionBodyRaw());
                telcoAPI.setSuccessCode(service.getTelcoAPISubscriptionSuccessCode());
                telcoAPI.setInsufficientAirtimeCode(service.getTelcoAPISubscriptionInsufficientAirtimeCode());
                telcoAPI.setResponseCodeMatchType(service.getTelcoAPISubscriptionResponseCodeMatchType());
                telcoAPI.setResponseCodeMatchValue(service.getTelcoAPISubscriptionResponseCodeMatchValue());
                telcoAPI.setResponseDescriptionMatchType(service.getTelcoAPISubscriptionResponseDescriptionMatchType());
                telcoAPI.setResponseDescriptionMatchValue(service.getTelcoAPISubscriptionResponseDescriptionMatchValue());
                telcoAPI.setResponseTransactionIDMatchType(service.getTelcoAPISubscriptionResponseTransactionIDMatchType());
                telcoAPI.setResponseTransactionIDMatchValue(service.getTelcoAPISubscriptionResponseTransactionIDMatchValue());
                telcoAPI.setBills(service.getTelcoAPISubscriptionBills());
            } else if (service.getTelcoAPIBillingID() != 0 && submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE) {
                telcoAPI.setID(service.getTelcoAPIBillingID());
                telcoAPI.setName(service.getTelcoAPIBillingName());
                telcoAPI.setURL(service.getTelcoAPIBillingURL());
                telcoAPI.setType(service.getTelcoAPIBillingType());
                telcoAPI.setMethod(service.getTelcoAPIBillingMethod());
                telcoAPI.setBodyType(service.getTelcoAPIBillingBodyType());
                telcoAPI.setHeader(service.getTelcoAPIBillingHeader());
                telcoAPI.setBody(service.getTelcoAPIBillingBody());
                telcoAPI.setBodyRaw(service.getTelcoAPIBillingBodyRaw());
                telcoAPI.setSuccessCode(service.getTelcoAPIBillingSuccessCode());
                telcoAPI.setInsufficientAirtimeCode(service.getTelcoAPIBillingInsufficientAirtimeCode());
                telcoAPI.setResponseCodeMatchType(service.getTelcoAPIBillingResponseCodeMatchType());
                telcoAPI.setResponseCodeMatchValue(service.getTelcoAPIBillingResponseCodeMatchValue());
                telcoAPI.setResponseDescriptionMatchType(service.getTelcoAPIBillingResponseDescriptionMatchType());
                telcoAPI.setResponseDescriptionMatchValue(service.getTelcoAPIBillingResponseDescriptionMatchValue());
                telcoAPI.setResponseTransactionIDMatchType(service.getTelcoAPIBillingResponseTransactionIDMatchType());
                telcoAPI.setResponseTransactionIDMatchValue(service.getTelcoAPIBillingResponseTransactionIDMatchValue());
                telcoAPI.setBills(true);
            }
        } else if (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_UNSUBSCRIBE) {
            if (submitMessage.getService().getTelcoAPIUnsubscriptionID() != 0) {
                telcoAPI.setID(submitMessage.getService().getTelcoAPIUnsubscriptionID());
                telcoAPI.setName(submitMessage.getService().getTelcoAPIUnsubscriptionName());
                telcoAPI.setURL(submitMessage.getService().getTelcoAPIUnsubscriptionURL());
                telcoAPI.setType(submitMessage.getService().getTelcoAPIUnsubscriptionType());
                telcoAPI.setMethod(submitMessage.getService().getTelcoAPIUnsubscriptionMethod());
                telcoAPI.setBodyType(submitMessage.getService().getTelcoAPIUnsubscriptionBodyType());
                telcoAPI.setHeader(submitMessage.getService().getTelcoAPIUnsubscriptionHeader());
                telcoAPI.setBody(submitMessage.getService().getTelcoAPIUnsubscriptionBody());
                telcoAPI.setBodyRaw(submitMessage.getService().getTelcoAPIUnsubscriptionBodyRaw());
                telcoAPI.setSuccessCode(submitMessage.getService().getTelcoAPIUnsubscriptionSuccessCode());
                telcoAPI.setInsufficientAirtimeCode(submitMessage.getService().getTelcoAPIUnsubscriptionInsufficientAirtimeCode());
                telcoAPI.setResponseCodeMatchType(submitMessage.getService().getTelcoAPIUnsubscriptionResponseCodeMatchType());
                telcoAPI.setResponseCodeMatchValue(submitMessage.getService().getTelcoAPIUnsubscriptionResponseCodeMatchValue());
                telcoAPI.setResponseDescriptionMatchType(submitMessage.getService().getTelcoAPIUnsubscriptionResponseDescriptionMatchType());
                telcoAPI.setResponseDescriptionMatchValue(submitMessage.getService().getTelcoAPIUnsubscriptionResponseDescriptionMatchValue());
                telcoAPI.setResponseTransactionIDMatchType(submitMessage.getService().getTelcoAPIUnsubscriptionResponseTransactionIDMatchType());
                telcoAPI.setResponseTransactionIDMatchValue(submitMessage.getService().getTelcoAPIUnsubscriptionResponseTransactionIDMatchValue());
                telcoAPI.setBills(submitMessage.getService().getTelcoAPIUnsubscriptionBills());
            }
        } else {
            if (submitMessage.getService().getTelcoAPIBillingID() != 0) {
                telcoAPI.setID(submitMessage.getService().getTelcoAPIBillingID());
                telcoAPI.setName(submitMessage.getService().getTelcoAPIBillingName());
                telcoAPI.setURL(submitMessage.getService().getTelcoAPIBillingURL());
                telcoAPI.setType(submitMessage.getService().getTelcoAPIBillingType());
                telcoAPI.setMethod(submitMessage.getService().getTelcoAPIBillingMethod());
                telcoAPI.setBodyType(submitMessage.getService().getTelcoAPIBillingBodyType());
                telcoAPI.setHeader(submitMessage.getService().getTelcoAPIBillingHeader());
                telcoAPI.setBody(submitMessage.getService().getTelcoAPIBillingBody());
                telcoAPI.setBodyRaw(submitMessage.getService().getTelcoAPIBillingBodyRaw());
                telcoAPI.setSuccessCode(submitMessage.getService().getTelcoAPIBillingSuccessCode());
                telcoAPI.setInsufficientAirtimeCode(submitMessage.getService().getTelcoAPIBillingInsufficientAirtimeCode());
                telcoAPI.setResponseCodeMatchType(submitMessage.getService().getTelcoAPIBillingResponseCodeMatchType());
                telcoAPI.setResponseCodeMatchValue(submitMessage.getService().getTelcoAPIBillingResponseCodeMatchValue());
                telcoAPI.setResponseDescriptionMatchType(submitMessage.getService().getTelcoAPIBillingResponseDescriptionMatchType());
                telcoAPI.setResponseDescriptionMatchValue(submitMessage.getService().getTelcoAPIBillingResponseDescriptionMatchValue());
                telcoAPI.setResponseTransactionIDMatchType(submitMessage.getService().getTelcoAPIBillingResponseTransactionIDMatchType());
                telcoAPI.setResponseTransactionIDMatchValue(submitMessage.getService().getTelcoAPIBillingResponseTransactionIDMatchValue());
                telcoAPI.setBills(submitMessage.getService().getTelcoAPIBillingBills());
            }
        }

        if (telcoAPI.getID() != 0) {
            submitMessage.getService().setTelcoAPI(telcoAPI);
            int apiType = telcoAPI.getType();
            String successCode = telcoAPI.getSuccessCode();
            String[] successCodeSplit = (successCode != null) ? successCode.split(",") : null;
            String insufficientAirtimeCode = telcoAPI.getInsufficientAirtimeCode();
            String[] insufficientAirtimeCodeSplit = (insufficientAirtimeCode != null) ? insufficientAirtimeCode.split(",") : null;
            String responseCodeMatchType = telcoAPI.getResponseCodeMatchType();
            String responseCodeMatchTypeValue = telcoAPI.getResponseCodeMatchValue();
            String responseDescriptionMatchType = telcoAPI.getResponseDescriptionMatchType();
            String responseDescriptionMatchTypeValue = telcoAPI.getResponseDescriptionMatchValue();
            String responseTransactionIDMatchType = telcoAPI.getResponseTransactionIDMatchType();
            String responseTransactionIDMatchTypeValue = telcoAPI.getResponseTransactionIDMatchValue();

            String apiResponse = Util.telcoAPIRequest(telcoAPI, service, submitMessage);
            if (apiResponse != null) {
                responseCode = (responseCodeMatchType != null || responseCodeMatchTypeValue != null) ? Util.getValueFromResponse(responseCodeMatchType, responseCodeMatchTypeValue, apiResponse) : null;
                responseDescription = (responseDescriptionMatchType != null && responseDescriptionMatchTypeValue != null) ? Util.getValueFromResponse(responseDescriptionMatchType, responseDescriptionMatchTypeValue, apiResponse) : null;
                responseTransactionID = (responseTransactionIDMatchType != null && responseTransactionIDMatchTypeValue != null) ? Util.getValueFromResponse(responseTransactionIDMatchType, responseTransactionIDMatchTypeValue, apiResponse) : null;

                if (AppConstants.showError) {
                    System.out.println("[x] Response code - " + responseCode);
                    System.out.println("[x] Response description - " + responseDescription);
                    System.out.println("[x] Response transaction - " + responseTransactionID);
                }


                if (apiType == AppConstants.API_ASYNCHRONOUS) {
                    if (responseCode != null && successCodeSplit != null && Arrays.asList(successCodeSplit).contains(responseCode)) {
                        System.out.println("[x] Sending to async awaiting queue ");
                        submitMessage.setTransactionID(responseTransactionID);
                        Util.logAwaitingAsync(submitMessage);
                    }
                } else {
                    if (responseCode != null && successCodeSplit != null && Arrays.asList(successCodeSplit).contains(responseCode)) {
                        System.out.println("[x] Telco API response returns Successful Code ");
                        if (telcoAPI.getBills()) {
                            billingSuccessful = true;
                        } else {
                            successful = true;
                        }
                    } else if (responseCode != null && insufficientAirtimeCodeSplit != null && Arrays.asList(insufficientAirtimeCodeSplit).contains(responseCode)) {
                        insufficientFund = true;
                    }

                    if (insufficientFund) {
                        processInsufficientResponse(submitMessage, new String[]{responseCode, null});
                    } else if (billingSuccessful) {
                        if (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE) {
                            submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE);
                            submitMessage.getService().setTelcoAPISubscriptionID(0);
                        } else if (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL_AND_UNSUBSCRIBE) {
                            submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_BILL_AND_UNSUBSCRIBE);
                            submitMessage.getService().setTelcoAPIUnsubscriptionID(0);
                        }
                        processBillingResponse(submitMessage, new String[]{responseCode, null});
                    } else if (successful) {
                        if (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_SUBSCRIBE) {
                            submitMessage.getService().setTelcoAPISubscriptionID(0);
                            processSubscription(submitMessage);
                        } else if (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_UNSUBSCRIBE) {
                            submitMessage.getService().setTelcoAPIUnsubscriptionID(0);
                            processUnSubscription(submitMessage);
                        }
                    }
                }
            }
        }
    }


    public static void processorAwaitingAsync(String task)
            throws ParseException, IOException, TimeoutException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(task);

        AwaitingAsync awaitingAsync;
        String transactionID = json.get("tranxId").toString();
        String code = json.get("code").toString();
        String description = json.get("description").toString();

        awaitingAsync = Util.getAwaitingAsync(transactionID);
        awaitingAsync.setTransactionID(transactionID);
        awaitingAsync.setCode(code);
        awaitingAsync.setDescription(description);


        String successCode = awaitingAsync.getSubmitMessage().getService().getTelcoAPI().getSuccessCode();
        String[] successCodeSplit = (successCode != null) ? successCode.split(",") : null;
        String insufficientAirtimeCode = awaitingAsync.getSubmitMessage().getService().getTelcoAPIBillingInsufficientAirtimeCode();
        String[] insufficientAirtimeCodeSplit = (insufficientAirtimeCode != null) ? insufficientAirtimeCode.split(",") : null;

        if ((awaitingAsync.getSubmitMessage().getAction() == AppConstants.OUTGOING_MESSAGES_BILL) || (awaitingAsync.getSubmitMessage().getAction() == AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE) || (awaitingAsync.getSubmitMessage().getAction() == AppConstants.OUTGOING_MESSAGES_BILL_AND_CONTENT)) {
            if (awaitingAsync.getSubmitMessage().getService().getTelcoAPI().getType() == AppConstants.API_ASYNCHRONOUS) {
                if (code != null && successCodeSplit != null && Arrays.asList(successCodeSplit).contains(code)) {
                    processBillingResponse(awaitingAsync.getSubmitMessage(), new String[]{code, null});
                } else if (code != null && insufficientAirtimeCodeSplit != null && Arrays.asList(insufficientAirtimeCodeSplit).contains(code)) {
                    processInsufficientResponse(awaitingAsync.getSubmitMessage(), new String[]{code, null});
                }
            }
        }
    }

    public static void processorContentUpload(String task) {
        try {
            Gson g = new Gson();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(task);
            System.out.println(g.toJson(json));

            int serviceID = Integer.parseInt(json.get("service_id").toString());
            JSONObject contentObject = (JSONObject) json.get("content");
            Content content = new Content();
            if (contentObject.get("id") != null) content.setID(Integer.parseInt(contentObject.get("id").toString()));
            if (contentObject.get("service_id") != null)
                content.setServiceID(Integer.parseInt(contentObject.get("service_id").toString()));
            if (contentObject.get("content") != null) content.setContent(contentObject.get("content").toString());
            if (contentObject.get("scheduled_date") != null)
                content.setScheduledDate(contentObject.get("scheduled_date").toString());
            String msisdn = (json.get("msisdn") != null) ? Util.internationalNumberFormat(json.get("msisdn").toString()) : null;

            Service service = Util.getServiceByID(serviceID);

            List<Subscriber> subscribers;

            if (service.getID() != 0) {
                SubmitMessage submitMessage = new SubmitMessage();
                submitMessage.setService(service);
                submitMessage.setContent(content);
                submitMessage.setSourceAddress(service.getContentShortCode());
                submitMessage.setSms(content.getContent());
                submitMessage.setTariff(service.getContentShortCodeTariff());


                if (msisdn == null) {
                    subscribers = Util.getEligibleSubscribersByServiceID(service.getID());
                    Producer producer = null;
                    if (submitMessage.getService().getTelcoAPIBillingID() != 0) {
                        producer = new Producer(String.format("%s%s_%s", AppConstants.PREFIX_TELCO_API_REQUEST, AppConstants.TELCO_API_BILL, submitMessage.getService().getTelcoAPIBillingID()), true);
                    } else if (submitMessage.getService().getContentShouldBill() || submitMessage.getService().getBillingCycle() != AppConstants.DAILY) {
                        producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, submitMessage.getService().getContentShortCodeBind()), true);
                    } else if (submitMessage.getService().getBillingCycle() == AppConstants.DAILY) {
                        producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, submitMessage.getService().getBillingShortCodeBind()), true);
                    }
                    for (Subscriber subscriber : subscribers) {
                        submitMessage.setSubscriber(subscriber);
                        submitMessage.setDestinationAddress(subscriber.getMsisdn());

                        Util.setActionForContent(submitMessage, producer);
                    }
                    if (producer != null) {
                        producer.close();
                    }
                } else {
                    Subscriber subscriber = Util.getActiveServiceSubscriber(service.getID(), msisdn);
                    if (subscriber.getID() != 0) {
                        submitMessage.setSubscriber(subscriber);
                        submitMessage.setDestinationAddress(subscriber.getMsisdn());

                        Util.setActionForContent(submitMessage);
                    } else {
                        submitMessage.setDestinationAddress(msisdn);
                        Util.setActionForContent(submitMessage);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void processorDNDAssembly(String task) throws IOException, TimeoutException {
        String msisdn = Util.internationalNumberFormat(task);
        System.out.println(String.format("[x] Processing %s for DND", msisdn));
        try {
            int affectedRow = Util.updateSubscriberStatusForAllService(AppConstants.SUBSCRIBER_DND, msisdn);
            if (affectedRow > 0) {
                System.out.println(String.format("[x] Successfully put %s on DND", msisdn));
            }
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }
    }

    public static void processorContentProviderReceipt(String task) {
        try {
            SubmitMessage submitMessage = new Gson().fromJson(task, SubmitMessage.class);
            Util.sendToThirdPartyAPI(submitMessage);
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }
    }

    public static void processorSubmitMessages(String endPointName, String task) throws IOException, TimeoutException {
        String bindID = endPointName.substring(endPointName.length() - 1);
        if (!bindID.equalsIgnoreCase("0")) {
            CustomSMPPSession smppSession = Variables.smppSessions.get(bindID).poll();
            try {
                SubmitMessage submitMessage = new Gson().fromJson(task, SubmitMessage.class);

                while (smppSession == null) {
                    smppSession = Variables.smppSessions.get(bindID).poll();
                    System.out.println("[x] Waiting for connection ");
                    Thread.sleep(200);
                }

                if ((submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL) || (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE) || (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL_AND_CONTENT)) {
                    boolean billingSuccessful = false;
                    boolean insufficientFund = false;
                    if (submitMessage.getService() != null) {
                        if (submitMessage.getService().getTelcoAPIBillingID() != 0) {
                            Producer producer = new Producer(String.format("%s%s_%s", AppConstants.PREFIX_TELCO_API_REQUEST, AppConstants.TELCO_API_BILL, submitMessage.getService().getTelcoAPIBillingID()), true);
                            producer.sendMessage(new Gson().toJson(submitMessage));
                            producer.close();
                        } else {
                            int protocolID = (submitMessage.getService().getSendBillingNotification()) ? 0 : 64;
                            String[] response;
                            if (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE) {
                                response = SMPP.sendSMS(submitMessage.getDestinationAddress(), submitMessage.getService().getBillingShortCode(), submitMessage.getService().getChargingNotificationMessage(), protocolID, smppSession);
                            } else {
                                response = SMPP.sendSMS(submitMessage.getDestinationAddress(), submitMessage.getService().getBillingShortCode(), submitMessage.getService().getRenewalMessage(), protocolID, smppSession);
                            }

                            if (Util.getPushSMSState(response) == 2) {
                                insufficientFund = true;
                            } else if (Util.getPushSMSState(response) == 1 || Util.getPushSMSState(response) == 3) {
                                billingSuccessful = true;
                            }


                            if (insufficientFund) {
                                processInsufficientResponse(submitMessage, response);
                            } else if (billingSuccessful) {
                                processBillingResponse(submitMessage, response);
                            }
                        }
                    }
                } else if (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_CONTENT) {
                    int protocolID = AppConstants.SUBMIT_SM_0;
                    if (submitMessage.getService() != null) {
                        String contentShortCode = submitMessage.getSourceAddress();
                        if (!submitMessage.getService().getSendBillingNotification()) {
                            protocolID = AppConstants.SUBMIT_SM_64;
                        }
                        String[] response = SMPP.sendSMS(submitMessage.getDestinationAddress(), contentShortCode, submitMessage.getSms(), protocolID, smppSession);
                        if (submitMessage.getService().getContentShouldBill()) {
                            if (Util.getPushSMSState(response) == 2) {
                                processInsufficientResponse(submitMessage, response);
                            } else if (Util.getPushSMSState(response) == 1 || Util.getPushSMSState(response) == 3) {
                                processBillingResponse(submitMessage, response);
                            }
                        } else {
                            if (Util.getPushSMSStatus(response)) {
                                processSentMessage(submitMessage, response);
                            }
                        }
                    }
                } else if (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_SUBSCRIBE) {
                    if (!(submitMessage.getService().getConfirmationMessage() == null && submitMessage.getService().getConfirmationMessage().trim().equalsIgnoreCase(""))) {
                        processConfirmation(submitMessage);
                    } else {
                        processSubscription(submitMessage);
                    }
                } else {
                    int protocolID = AppConstants.SUBMIT_SM_0;
                    if (submitMessage.getService() != null && !submitMessage.getService().getSendBillingNotification()) {
                        protocolID = AppConstants.SUBMIT_SM_64;
                    }
                    String[] response = SMPP.sendSMS(submitMessage.getDestinationAddress(), submitMessage.getSourceAddress(), submitMessage.getSms(), protocolID, smppSession);
                    if (Util.getPushSMSStatus(response)) {
                        processSentMessage(submitMessage, response);
                    }
                }
            } catch (Exception e) {
                if (AppConstants.showError) {
                    e.printStackTrace();
                }
            } finally {
                if (smppSession != null) {
                    Variables.smppSessions.get(bindID).add(smppSession);
                }
            }
        }
    }

    public static void processorIncomingMessages(String task)
            throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, TimeoutException {
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(task);

            int telcoID = Integer.parseInt(json.get("telco_id").toString());
            String sourceAddress = json.get("source_address").toString();
            String destinationAddress = json.get("destination_address").toString();
            String keyword = json.get("keyword").toString();
            Boolean confirmation = true;
            if (json.get("confirmation") != null) {
                confirmation = Boolean.parseBoolean(json.get("confirmation").toString());
            }
            String bearer = json.get("bearer").toString();


            SubmitMessage submitMessage = new SubmitMessage();
            submitMessage.setDestinationAddress(Util.internationalNumberFormat(sourceAddress));
            submitMessage.setSourceAddress(destinationAddress);
            submitMessage.setKeyword(keyword);
            submitMessage.setExternalAPIType(AppConstants.SMS_STATUS_RECEIVED);
            submitMessage.setBearer(bearer);


            Service confirmationService = Util.getUserFromConfirmation(submitMessage.getDestinationAddress());

            if (confirmationService.getConfirmKeyword() != null && confirmationService.getConfirmKeyword().trim().equalsIgnoreCase(submitMessage.getKeyword().trim())) {
                // Subscribe User Here to service
                System.out.println("[x] User action ::: confirm");
                submitMessage.setService(confirmationService);
                if (confirmationService.getTariff() > 0 && !confirmationService.getTelcoAPISubscriptionBills()) {
                    submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE);
                    processBilling(submitMessage);
                } else {
                    submitMessage.setSourceAddress(confirmationService.getOptInShortCode());
                    processSubscription(submitMessage);
                }
            } else {
                Service service = Util.getServiceByKeyword(submitMessage.getKeyword(), telcoID, submitMessage.getSourceAddress());
                submitMessage.setService(service);
                if (service.getID() != 0) {
                    String getUserAction = Util.getUserInputAction(submitMessage.getKeyword(), service);
                    System.out.println("[x] User action ::: " + getUserAction);
                    if (getUserAction != null) {
                        if (service.getExternalAPI() == null || (service.getExternalAPI() != null && service.getExternalAPIProcessing() == 2)) {
                            if (getUserAction.equalsIgnoreCase(AppConstants.USER_INPUT_ACTION_OPT_IN)) {
                                Subscriber subscriber = Util.getActiveServiceSubscriber(service.getID(), submitMessage.getDestinationAddress());
                                if (subscriber.getID() == 0) {
                                    if (!confirmation || (service.getConfirmKeyword() == null || service.getConfirmKeyword().trim().equalsIgnoreCase(""))) {
                                        if (service.getTariff() > 0 && !service.getTelcoAPISubscriptionBills()) {
                                            submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE);
                                            processBilling(submitMessage);
                                        } else {
                                            processSubscription(submitMessage);
                                        }
                                    } else {
                                        processConfirmation(submitMessage);
                                    }
                                } else {
                                    submitMessage.setSourceAddress(service.getHelpShortCode());
                                    submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_DEFAULT);
                                    submitMessage.setSms(service.getAlreadySubscribedMessage());
                                    submitMessage.setTariff(service.getHelpShortCodeTariff());

                                    Producer producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, service.getHelpShortCodeBind()), true);
                                    producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
                                    producer.close();
                                }
                            } else if (getUserAction.equalsIgnoreCase(AppConstants.USER_INPUT_ACTION_OPT_OUT)) {
                                Subscriber subscriber = Util.getActiveServiceSubscriber(service.getID(), submitMessage.getDestinationAddress());
                                if (subscriber.getID() != 0) {
                                    processUnSubscription(submitMessage);
                                } else {
                                    submitMessage.setSourceAddress(service.getHelpShortCode());
                                    submitMessage.setSms("You don't have an active subscription. " + service.getHelpMessage());
                                    submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_DEFAULT);
                                    submitMessage.setTariff(service.getHelpShortCodeTariff());

                                    Producer producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, service.getHelpShortCodeBind()), true);
                                    producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
                                    producer.close();
                                }

                            } else if (getUserAction.equalsIgnoreCase(AppConstants.USER_INPUT_ACTION_HELP)) {
                                submitMessage.setSourceAddress(service.getHelpShortCode());
                                submitMessage.setSms(service.getHelpMessage());
                                submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_DEFAULT);
                                submitMessage.setTariff(service.getHelpShortCodeTariff());

                                Producer producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, service.getHelpShortCodeBind()), true);
                                producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
                                producer.close();
                            }

                        } else {
                            Util.sendToContentProviderReceiptQueue(submitMessage);
                        }
                    }
                } else {
                    ShortCode shortCode = Util.getShortCodeFromTelcoAndShortCode(telcoID, submitMessage.getSourceAddress());
                    submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_DEFAULT);
                    submitMessage.setTariff(shortCode.getTariff());
                    submitMessage.setService(null);
                    Producer producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, shortCode.getSmppBind()), true);
                    submitMessage.setSms(String.format("Invalid Keyword. Send %s to %s for more info.", AppConstants.KEYWORD_GENERAL_HELP.toUpperCase(), submitMessage.getSourceAddress()));
                    producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
                    producer.close();
                }
            }
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }
    }

    public static String processorSMPPNegativeValueExceptionMessage(String exceptionMessage) {
        String output = null;
        String pattern = "(Negative response )(.*)( found)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(exceptionMessage);
        if (m.find()) {
            output = m.group(2);
        } else {
            System.out.println("NO MATCH");
        }

        return output;
    }

    private static void processUnSubscription(SubmitMessage submitMessage) throws IOException, TimeoutException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Producer subscribeProducer = null;
        Boolean unsubscribe = false;

        submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_UNSUBSCRIBE);
        if (submitMessage.getService().getTelcoAPIUnsubscriptionID() == 0) {
            unsubscribe = Util.updateSubscriberStatusPerService(AppConstants.SUBSCRIBER_INACTIVE, submitMessage.getDestinationAddress(), submitMessage.getService().getID());
        } else {
            submitMessage.getService().setOptOutShortCode(submitMessage.getService().getHelpShortCode());
            submitMessage.getService().setOptOutShortCodeBind(submitMessage.getService().getHelpShortCodeBind());
            subscribeProducer = new Producer(String.format("%s%s_%s", AppConstants.PREFIX_TELCO_API_REQUEST, AppConstants.TELCO_API_SUBSCRIBE, submitMessage.getService().getTelcoAPISubscriptionID()), true);
            subscribeProducer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
        }

        try {
            if (unsubscribe) {
                submitMessage.setSourceAddress(submitMessage.getService().getOptOutShortCode());
                submitMessage.setSms(submitMessage.getService().getOptOutMessage());
                submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_DEFAULT);
                submitMessage.setTariff(submitMessage.getService().getOptOutShortCodeTariff());

                Producer producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, submitMessage.getService().getOptOutShortCodeBind()), true);
                producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
                producer.close();

                if (submitMessage.getService().getExternalAPI() != null && submitMessage.getService().getExternalAPIProcessing() == 2) {
                    Util.sendToContentProviderReceiptQueue(submitMessage);
                }
            }
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        } finally {
            if (subscribeProducer != null) {
                subscribeProducer.close();
            }
        }
    }


    private static void processSubscription(SubmitMessage submitMessage) throws IOException, TimeoutException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Subscriber subscriber = null;
        Producer subscribeProducer = null;
        submitMessage.setSourceAddress(submitMessage.getService().getOptInShortCode());
        submitMessage.setSms(submitMessage.getService().getOptInMessage());
        if (submitMessage.getService().getTariff() > 0) {
            submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE);
        } else {
            submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_SUBSCRIBE);
        }

        if (submitMessage.getService().getTelcoAPISubscriptionID() == 0) {
            subscriber = Util.subscribeUsers(submitMessage.getService().getID(), submitMessage.getDestinationAddress(), true);
        } else {
            submitMessage.getService().setOptInShortCodeBind(submitMessage.getService().getHelpShortCodeBind());
            submitMessage.getService().setOptInShortCode(submitMessage.getService().getHelpShortCode());
            subscribeProducer = new Producer(String.format("%s%s_%s", AppConstants.PREFIX_TELCO_API_REQUEST, AppConstants.TELCO_API_SUBSCRIBE, submitMessage.getService().getTelcoAPISubscriptionID()), true);
            subscribeProducer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
        }
        try {
            if (subscriber != null) {
                submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_DEFAULT);
                if (subscriber.getAlreadySubscribed()) {
                    submitMessage.setTariff(submitMessage.getService().getHelpShortCodeTariff());
                    subscribeProducer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, submitMessage.getService().getHelpShortCodeBind()), true);
                    submitMessage.setSms(submitMessage.getService().getAlreadySubscribedMessage());
                    submitMessage.setSourceAddress(submitMessage.getService().getHelpShortCode());
                } else {
                    if (!submitMessage.getService().getIgnoreMessagesOnSubscription()) {
                        submitMessage.setTariff(submitMessage.getService().getOptInShortCodeTariff());
                        subscribeProducer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, submitMessage.getService().getOptInShortCodeBind()), true);
                        submitMessage.setSms(submitMessage.getService().getOptInMessage());
                        submitMessage.setSourceAddress(submitMessage.getService().getOptInShortCode());
                    }
                }
                if (subscribeProducer != null) {
                    subscribeProducer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
                }

                if (submitMessage.getService().getExternalAPI() != null) {
                    submitMessage.setExternalAPIType(AppConstants.SMS_STATUS_SUBSCRIBED);
                    Util.sendToContentProviderReceiptQueue(submitMessage);
                }
            }
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        } finally {
            if (subscribeProducer != null) {
                subscribeProducer.close();
            }
        }
    }

    public static void processContent(SubmitMessage submitMessage) throws IOException, TimeoutException {
        submitMessage.setTariff(submitMessage.getService().getContentShortCodeTariff());
        Producer producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, submitMessage.getService().getContentShortCodeBind()), true);
        producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
        producer.close();
    }


    public static void processContent(SubmitMessage submitMessage, Producer producer) throws IOException, TimeoutException {
        submitMessage.setTariff(submitMessage.getService().getContentShortCodeTariff());
        producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
    }

    private static void processConfirmation(SubmitMessage submitMessage) throws IOException, TimeoutException {
        Util.insertIntoServiceConfirmation(submitMessage.getDestinationAddress(), submitMessage.getService());
        submitMessage.setSourceAddress(submitMessage.getService().getConfirmationShortCode());
        submitMessage.setSms(submitMessage.getService().getConfirmationMessage());
        submitMessage.setTariff(submitMessage.getService().getConfirmationShortCodeTariff());
        submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_DEFAULT);

        Producer producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, submitMessage.getService().getConfirmationShortCodeBind()), true);
        producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
        producer.close();
    }

    private static void processSentMessage(SubmitMessage submitMessage, String[] response) throws SQLException {
        String service = (submitMessage.getService() != null) ? submitMessage.getService().getServiceName() : "Unknown";
        Util.systemOut((char) 27 + String.format("[%s;%sm[x] Message Sent for %s on %s service", AppConstants.OUTPUT_BRIGHT, AppConstants.OUTPUT_COLOR_WHITE, submitMessage.getDestinationAddress(), service));
        if (submitMessage.getTariff() > 0) {
            Util.logReport(response, AppConstants.SMS_STATUS_BILLED, submitMessage);
        } else {
            Util.logReport(response, AppConstants.SMS_STATUS_SENT, submitMessage);
        }

        Util.updateSubscriberLastContent(submitMessage);
    }

    private static void processInsufficientResponse(SubmitMessage submitMessage, String[] response) throws IOException, TimeoutException {
        Util.systemOut((char) 27 + String.format("[%s;%sm[x] Insufficient Fund for %s on %s service", AppConstants.OUTPUT_BRIGHT, AppConstants.OUTPUT_COLOR_RED, submitMessage.getDestinationAddress(), submitMessage.getService().getServiceName()));
        // Util.logReport(response, AppConstants.SMS_STATUS_INSUFFICIENT_FUND, submitMessage);
        if (submitMessage.getService().getSendBillingNotification()) {
            submitMessage.setSms(submitMessage.getService().getInsufficientBalanceMessage());
            submitMessage.setSourceAddress(submitMessage.getService().getHelpShortCode());
            submitMessage.setTariff(submitMessage.getService().getHelpShortCodeTariff());
            submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_DEFAULT);

            Producer producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, submitMessage.getService().getHelpShortCodeBind()), true);
            producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
            producer.close();
        }

        if (submitMessage.getService().getExternalAPI() != null) {
            submitMessage.setExternalAPIType(AppConstants.SMS_STATUS_INSUFFICIENT_FUND);
            Util.sendToContentProviderReceiptQueue(submitMessage);
        }
    }

    public static void processBilling(SubmitMessage submitMessage) throws IOException, TimeoutException {
        Producer producer;
        if (submitMessage.getService().getTelcoAPIBillingID() != 0) {
            submitMessage.setTariff(submitMessage.getService().getTariff());
            producer = new Producer(String.format("%s%s_%s", AppConstants.PREFIX_TELCO_API_REQUEST, AppConstants.TELCO_API_BILL, submitMessage.getService().getTelcoAPIBillingID()), true);
        } else {
            submitMessage.setTariff(submitMessage.getService().getBillingShortCodeTariff());
            producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, submitMessage.getService().getBillingShortCodeBind()), true);
        }

        producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
        producer.close();
    }

    public static void processBilling(SubmitMessage submitMessage, Producer producer) throws IOException, TimeoutException {
        if (submitMessage.getService().getTelcoAPIBillingID() != 0) {
            submitMessage.setTariff(submitMessage.getService().getTariff());
        } else {
            submitMessage.setTariff(submitMessage.getService().getBillingShortCodeTariff());
        }

        producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
    }

    private static void processBillingResponse(SubmitMessage submitMessage, String[] response) throws IOException, TimeoutException {
        if (submitMessage.getTariff() > 0)
            Util.systemOut((char) 27 + String.format("[%s;%sm[x] Successfully Billed %s for %s ", AppConstants.OUTPUT_BRIGHT, AppConstants.OUTPUT_COLOR_GREEN, submitMessage.getDestinationAddress(), submitMessage.getService().getServiceName()));

        Util.logReport(response, AppConstants.SMS_STATUS_BILLED, submitMessage);
        Producer producer = null;
        Float tariff = submitMessage.getTariff();

        if ((submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL) || (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE) || (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL_AND_CONTENT)) {
            int action = submitMessage.getAction();
            try {
                if ((submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL) || (submitMessage.getAction() == AppConstants.OUTGOING_MESSAGES_BILL_AND_CONTENT)) {
                    submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_DEFAULT);
                    if (action == AppConstants.OUTGOING_MESSAGES_BILL_AND_CONTENT) {
                        producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, submitMessage.getService().getContentShortCodeBind()), true);
                        submitMessage.setTariff(submitMessage.getService().getContentShortCodeTariff());
                        producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
                    }
                } else if (action == AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE) {
                    processSubscription(submitMessage);
                }

                if (submitMessage.getService().getTelcoAPIBillingID() != 0 && submitMessage.getService().getSendBillingNotification()) {
                    submitMessage.setTariff(submitMessage.getService().getHelpShortCodeTariff());
                    submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_DEFAULT);
                    producer = new Producer(String.format("%s%s", AppConstants.PREFIX_OUTGOING_BIND, submitMessage.getService().getHelpShortCodeBind()), true);
                    if (action == AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE) {
                        submitMessage.setSms(submitMessage.getService().getChargingNotificationMessage());
                    } else {
                        submitMessage.setSms(submitMessage.getService().getRenewalMessage());
                    }
                    if (action != AppConstants.OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE && !submitMessage.getService().getIgnoreMessagesOnSubscription()) {
                        producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
                    }
                }

                if (submitMessage.getService().getExternalAPI() != null) {
                    if (tariff > 0) {
                        submitMessage.setExternalAPIType(AppConstants.SMS_STATUS_BILLED);
                    } else {
                        submitMessage.setExternalAPIType(AppConstants.SMS_STATUS_SENT);
                    }

                    Util.sendToContentProviderReceiptQueue(submitMessage);
                }

                if (tariff > 0) {
                    Util.updateSubscriberLastBilled(submitMessage);
                } else {
                    Util.updateSubscriberLastContent(submitMessage);
                }


            } catch (Exception e) {
                if (AppConstants.showError) {
                    e.printStackTrace();
                }
            } finally {
                if (producer != null) producer.close();
            }
        }
    }
}
