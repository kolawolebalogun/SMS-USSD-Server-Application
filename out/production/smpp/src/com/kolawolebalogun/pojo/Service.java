package com.kolawolebalogun.pojo;

/**
 * Created by KolawoleBalogun on 7/14/17.
 */
public class Service {
    private int ID;
    private String serviceName;
    private int stage;
    private int contentProviderID;
    private int telco;
    private String bearer;
    private String optInKeyword;
    private String optOutKeyword;
    private String helpKeyword;
    private String confirmKeyword;
    private int telcoAPISubscriptionID;
    private String telcoAPISubscriptionName;
    private String telcoAPISubscriptionURL;
    private int telcoAPISubscriptionType;
    private String telcoAPISubscriptionMethod;
    private String telcoAPISubscriptionBodyType;
    private String telcoAPISubscriptionHeader;
    private String telcoAPISubscriptionBody;
    private String telcoAPISubscriptionBodyRaw;
    private String telcoAPISubscriptionSuccessCode;
    private String telcoAPISubscriptionInsufficientAirtimeCode;
    private String telcoAPISubscriptionResponseCodeMatchType;
    private String telcoAPISubscriptionResponseCodeMatchValue;
    private String telcoAPISubscriptionResponseTransactionIDMatchType;
    private String telcoAPISubscriptionResponseTransactionIDMatchValue;
    private String telcoAPISubscriptionResponseDescriptionMatchType;
    private String telcoAPISubscriptionResponseDescriptionMatchValue;
    private Boolean telcoAPISubscriptionBills;
    private int telcoAPIUnsubscriptionID;
    private String telcoAPIUnsubscriptionName;
    private String telcoAPIUnsubscriptionURL;
    private int telcoAPIUnsubscriptionType;
    private String telcoAPIUnsubscriptionMethod;
    private String telcoAPIUnsubscriptionBodyType;
    private String telcoAPIUnsubscriptionHeader;
    private String telcoAPIUnsubscriptionBody;
    private String telcoAPIUnsubscriptionBodyRaw;
    private String telcoAPIUnsubscriptionSuccessCode;
    private String telcoAPIUnsubscriptionInsufficientAirtimeCode;
    private String telcoAPIUnsubscriptionResponseCodeMatchType;
    private String telcoAPIUnsubscriptionResponseCodeMatchValue;
    private String telcoAPIUnsubscriptionResponseTransactionIDMatchType;
    private String telcoAPIUnsubscriptionResponseTransactionIDMatchValue;
    private String telcoAPIUnsubscriptionResponseDescriptionMatchType;
    private String telcoAPIUnsubscriptionResponseDescriptionMatchValue;
    private Boolean telcoAPIUnsubscriptionBills;
    private int categoryID;
    private int parentID;
    private Float tariff;
    private int optInShortCodeID;
    private String optInShortCode;
    private int optInShortCodeBind;
    private Float optInShortCodeTariff;
    private int optOutShortCodeID;
    private String optOutShortCode;
    private int optOutShortCodeBind;
    private Float optOutShortCodeTariff;
    private int billingShortCodeID;
    private String billingShortCode;
    private int billingShortCodeBind;
    private Float billingShortCodeTariff;
    private int telcoAPIBillingID;
    private String telcoAPIBillingName;
    private String telcoAPIBillingURL;
    private int telcoAPIBillingType;
    private String telcoAPIBillingMethod;
    private String telcoAPIBillingBodyType;
    private String telcoAPIBillingHeader;
    private String telcoAPIBillingBody;
    private String telcoAPIBillingBodyRaw;
    private String telcoAPIBillingSuccessCode;
    private String telcoAPIBillingInsufficientAirtimeCode;
    private String telcoAPIBillingResponseCodeMatchType;
    private String telcoAPIBillingResponseCodeMatchValue;
    private String telcoAPIBillingResponseTransactionIDMatchType;
    private String telcoAPIBillingResponseTransactionIDMatchValue;
    private String telcoAPIBillingResponseDescriptionMatchType;
    private String telcoAPIBillingResponseDescriptionMatchValue;
    private Boolean telcoAPIBillingBills = false;
    private int confirmationShortCodeID;
    private String confirmationShortCode;
    private int confirmationShortCodeBind;
    private Float confirmationShortCodeTariff;
    private int helpShortCodeID;
    private String helpShortCode;
    private int helpShortCodeBind;
    private Float helpShortCodeTariff;
    private int contentShortCodeID;
    private String contentShortCode;
    private int contentShortCodeBind;
    private Float contentShortCodeTariff;
    private int telcoAPIContentID;
    private String telcoAPIContentName;
    private String telcoAPIContentURL;
    private int telcoAPIContentType;
    private String telcoAPIContentMethod;
    private String telcoAPIContentBodyType;
    private String telcoAPIContentHeader;
    private String telcoAPIContentBody;
    private String telcoAPIContentBodyRaw;
    private String telcoAPIContentSuccessCode;
    private String telcoAPIContentInsufficientAirtimeCode;
    private String telcoAPIContentResponseCodeMatchType;
    private String telcoAPIContentResponseCodeMatchValue;
    private String telcoAPIContentResponseDescriptionMatchType;
    private String telcoAPIContentResponseDescriptionMatchValue;
    private String telcoAPIContentResponseTransactionIDMatchType;
    private String telcoAPIContentResponseTransactionIDMatchValue;
    private Boolean telcoAPIContentBills = false;
    private int preRenewalNotificationShortCodeID;
    private String preRenewalNotificationShortCode;
    private int preRenewalNotificationShortCodeBind;
    private Float preRenewalNotificationTariff;
    private String optInMessage;
    private String confirmationMessage;
    private String optOutMessage;
    private String helpMessage;
    private String preRenewalMessage;
    private String renewalMessage;
    private String insufficientBalanceMessage;
    private String alreadySubscribedMessage;
    private String stopMessage;
    private String chargingNotificationMessage;
    private int billingCycle;
    private int preRenewalCycle;
    private String telcoParams;
    private String externalAPI;
    private String externalAPIMethod;
    private String externalAPISourceAddressParam;
    private String externalAPIDestinationAddressParam;
    private String externalAPIMessageParam;
    private int externalAPIProcessing;
    private Boolean contentShouldBill = false;
    private Boolean renewalDoneByTelco;
    private Boolean includeServiceInGeneralHelp;
    private Boolean sendBillingNotification = false;
    private String createdAt;
    private String updatedAt;
    private Boolean delete;
    private String deletedAt;
    private int telcoID;
    private TelcoAPI telcoAPI;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getContentProviderID() {
        return contentProviderID;
    }

    public void setContentProviderID(int contentProviderID) {
        this.contentProviderID = contentProviderID;
    }

    public int getTelco() {
        return telco;
    }

    public void setTelco(int telco) {
        this.telco = telco;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getOptInKeyword() {
        return optInKeyword;
    }

    public void setOptInKeyword(String optInKeyword) {
        this.optInKeyword = optInKeyword;
    }

    public String getOptOutKeyword() {
        return optOutKeyword;
    }

    public void setOptOutKeyword(String optOutKeyword) {
        this.optOutKeyword = optOutKeyword;
    }

    public String getHelpKeyword() {
        return helpKeyword;
    }

    public void setHelpKeyword(String helpKeyword) {
        this.helpKeyword = helpKeyword;
    }

    public String getConfirmKeyword() {
        return confirmKeyword;
    }

    public void setConfirmKeyword(String confirmKeyword) {
        this.confirmKeyword = confirmKeyword;
    }

    public int getTelcoAPISubscriptionID() {
        return telcoAPISubscriptionID;
    }

    public void setTelcoAPISubscriptionID(int telcoAPISubscriptionID) {
        this.telcoAPISubscriptionID = telcoAPISubscriptionID;
    }

    public String getTelcoAPISubscriptionName() {
        return telcoAPISubscriptionName;
    }

    public void setTelcoAPISubscriptionName(String telcoAPISubscriptionName) {
        this.telcoAPISubscriptionName = telcoAPISubscriptionName;
    }

    public String getTelcoAPISubscriptionURL() {
        return telcoAPISubscriptionURL;
    }

    public void setTelcoAPISubscriptionURL(String telcoAPISubscriptionURL) {
        this.telcoAPISubscriptionURL = telcoAPISubscriptionURL;
    }

    public int getTelcoAPISubscriptionType() {
        return telcoAPISubscriptionType;
    }

    public void setTelcoAPISubscriptionType(int telcoAPISubscriptionType) {
        this.telcoAPISubscriptionType = telcoAPISubscriptionType;
    }

    public String getTelcoAPISubscriptionMethod() {
        return telcoAPISubscriptionMethod;
    }

    public void setTelcoAPISubscriptionMethod(String telcoAPISubscriptionMethod) {
        this.telcoAPISubscriptionMethod = telcoAPISubscriptionMethod;
    }

    public String getTelcoAPISubscriptionBodyType() {
        return telcoAPISubscriptionBodyType;
    }

    public void setTelcoAPISubscriptionBodyType(String telcoAPISubscriptionBodyType) {
        this.telcoAPISubscriptionBodyType = telcoAPISubscriptionBodyType;
    }

    public String getTelcoAPISubscriptionHeader() {
        return telcoAPISubscriptionHeader;
    }

    public void setTelcoAPISubscriptionHeader(String telcoAPISubscriptionHeader) {
        this.telcoAPISubscriptionHeader = telcoAPISubscriptionHeader;
    }

    public String getTelcoAPISubscriptionBody() {
        return telcoAPISubscriptionBody;
    }

    public void setTelcoAPISubscriptionBody(String telcoAPISubscriptionBody) {
        this.telcoAPISubscriptionBody = telcoAPISubscriptionBody;
    }

    public String getTelcoAPISubscriptionBodyRaw() {
        return telcoAPISubscriptionBodyRaw;
    }

    public void setTelcoAPISubscriptionBodyRaw(String telcoAPISubscriptionBodyRaw) {
        this.telcoAPISubscriptionBodyRaw = telcoAPISubscriptionBodyRaw;
    }

    public String getTelcoAPISubscriptionSuccessCode() {
        return telcoAPISubscriptionSuccessCode;
    }

    public void setTelcoAPISubscriptionSuccessCode(String telcoAPISubscriptionSuccessCode) {
        this.telcoAPISubscriptionSuccessCode = telcoAPISubscriptionSuccessCode;
    }

    public String getTelcoAPISubscriptionInsufficientAirtimeCode() {
        return telcoAPISubscriptionInsufficientAirtimeCode;
    }

    public void setTelcoAPISubscriptionInsufficientAirtimeCode(String telcoAPISubscriptionInsufficientAirtimeCode) {
        this.telcoAPISubscriptionInsufficientAirtimeCode = telcoAPISubscriptionInsufficientAirtimeCode;
    }

    public String getTelcoAPISubscriptionResponseCodeMatchType() {
        return telcoAPISubscriptionResponseCodeMatchType;
    }

    public void setTelcoAPISubscriptionResponseCodeMatchType(String telcoAPISubscriptionResponseCodeMatchType) {
        this.telcoAPISubscriptionResponseCodeMatchType = telcoAPISubscriptionResponseCodeMatchType;
    }

    public String getTelcoAPISubscriptionResponseCodeMatchValue() {
        return telcoAPISubscriptionResponseCodeMatchValue;
    }

    public void setTelcoAPISubscriptionResponseCodeMatchValue(String telcoAPISubscriptionResponseCodeMatchValue) {
        this.telcoAPISubscriptionResponseCodeMatchValue = telcoAPISubscriptionResponseCodeMatchValue;
    }

    public String getTelcoAPISubscriptionResponseDescriptionMatchType() {
        return telcoAPISubscriptionResponseDescriptionMatchType;
    }

    public void setTelcoAPISubscriptionResponseDescriptionMatchType(String telcoAPISubscriptionResponseDescriptionMatchType) {
        this.telcoAPISubscriptionResponseDescriptionMatchType = telcoAPISubscriptionResponseDescriptionMatchType;
    }

    public String getTelcoAPISubscriptionResponseDescriptionMatchValue() {
        return telcoAPISubscriptionResponseDescriptionMatchValue;
    }

    public void setTelcoAPISubscriptionResponseDescriptionMatchValue(String telcoAPISubscriptionResponseDescriptionMatchValue) {
        this.telcoAPISubscriptionResponseDescriptionMatchValue = telcoAPISubscriptionResponseDescriptionMatchValue;
    }


    public int getTelcoAPIUnsubscriptionID() {
        return telcoAPIUnsubscriptionID;
    }

    public void setTelcoAPIUnsubscriptionID(int telcoAPIUnsubscriptionID) {
        this.telcoAPIUnsubscriptionID = telcoAPIUnsubscriptionID;
    }

    public String getTelcoAPIUnsubscriptionName() {
        return telcoAPIUnsubscriptionName;
    }

    public void setTelcoAPIUnsubscriptionName(String telcoAPIUnsubscriptionName) {
        this.telcoAPIUnsubscriptionName = telcoAPIUnsubscriptionName;
    }

    public String getTelcoAPIUnsubscriptionURL() {
        return telcoAPIUnsubscriptionURL;
    }

    public void setTelcoAPIUnsubscriptionURL(String telcoAPIUnsubscriptionURL) {
        this.telcoAPIUnsubscriptionURL = telcoAPIUnsubscriptionURL;
    }

    public int getTelcoAPIUnsubscriptionType() {
        return telcoAPIUnsubscriptionType;
    }

    public void setTelcoAPIUnsubscriptionType(int telcoAPIUnsubscriptionType) {
        this.telcoAPIUnsubscriptionType = telcoAPIUnsubscriptionType;
    }

    public String getTelcoAPIUnsubscriptionMethod() {
        return telcoAPIUnsubscriptionMethod;
    }

    public void setTelcoAPIUnsubscriptionMethod(String telcoAPIUnsubscriptionMethod) {
        this.telcoAPIUnsubscriptionMethod = telcoAPIUnsubscriptionMethod;
    }

    public String getTelcoAPIUnsubscriptionBodyType() {
        return telcoAPIUnsubscriptionBodyType;
    }

    public void setTelcoAPIUnsubscriptionBodyType(String telcoAPIUnsubscriptionBodyType) {
        this.telcoAPIUnsubscriptionBodyType = telcoAPIUnsubscriptionBodyType;
    }

    public String getTelcoAPIUnsubscriptionHeader() {
        return telcoAPIUnsubscriptionHeader;
    }

    public void setTelcoAPIUnsubscriptionHeader(String telcoAPIUnsubscriptionHeader) {
        this.telcoAPIUnsubscriptionHeader = telcoAPIUnsubscriptionHeader;
    }

    public String getTelcoAPIUnsubscriptionBody() {
        return telcoAPIUnsubscriptionBody;
    }

    public void setTelcoAPIUnsubscriptionBody(String telcoAPIUnsubscriptionBody) {
        this.telcoAPIUnsubscriptionBody = telcoAPIUnsubscriptionBody;
    }

    public String getTelcoAPIUnsubscriptionBodyRaw() {
        return telcoAPIUnsubscriptionBodyRaw;
    }

    public void setTelcoAPIUnsubscriptionBodyRaw(String telcoAPIUnsubscriptionBodyRaw) {
        this.telcoAPIUnsubscriptionBodyRaw = telcoAPIUnsubscriptionBodyRaw;
    }

    public String getTelcoAPIUnsubscriptionSuccessCode() {
        return telcoAPIUnsubscriptionSuccessCode;
    }

    public void setTelcoAPIUnsubscriptionSuccessCode(String telcoAPIUnsubscriptionSuccessCode) {
        this.telcoAPIUnsubscriptionSuccessCode = telcoAPIUnsubscriptionSuccessCode;
    }

    public String getTelcoAPIUnsubscriptionInsufficientAirtimeCode() {
        return telcoAPIUnsubscriptionInsufficientAirtimeCode;
    }

    public void setTelcoAPIUnsubscriptionInsufficientAirtimeCode(String telcoAPIUnsubscriptionInsufficientAirtimeCode) {
        this.telcoAPIUnsubscriptionInsufficientAirtimeCode = telcoAPIUnsubscriptionInsufficientAirtimeCode;
    }

    public String getTelcoAPIUnsubscriptionResponseCodeMatchType() {
        return telcoAPIUnsubscriptionResponseCodeMatchType;
    }

    public void setTelcoAPIUnsubscriptionResponseCodeMatchType(String telcoAPIUnsubscriptionResponseCodeMatchType) {
        this.telcoAPIUnsubscriptionResponseCodeMatchType = telcoAPIUnsubscriptionResponseCodeMatchType;
    }

    public String getTelcoAPIUnsubscriptionResponseCodeMatchValue() {
        return telcoAPIUnsubscriptionResponseCodeMatchValue;
    }

    public void setTelcoAPIUnsubscriptionResponseCodeMatchValue(String telcoAPIUnsubscriptionResponseCodeMatchValue) {
        this.telcoAPIUnsubscriptionResponseCodeMatchValue = telcoAPIUnsubscriptionResponseCodeMatchValue;
    }

    public String getTelcoAPIUnsubscriptionResponseTransactionIDMatchType() {
        return telcoAPIUnsubscriptionResponseTransactionIDMatchType;
    }

    public void setTelcoAPIUnsubscriptionResponseTransactionIDMatchType(String telcoAPIUnsubscriptionResponseTransactionIDMatchType) {
        this.telcoAPIUnsubscriptionResponseTransactionIDMatchType = telcoAPIUnsubscriptionResponseTransactionIDMatchType;
    }

    public String getTelcoAPIUnsubscriptionResponseTransactionIDMatchValue() {
        return telcoAPIUnsubscriptionResponseTransactionIDMatchValue;
    }

    public void setTelcoAPIUnsubscriptionResponseTransactionIDMatchValue(String telcoAPIUnsubscriptionResponseTransactionIDMatchValue) {
        this.telcoAPIUnsubscriptionResponseTransactionIDMatchValue = telcoAPIUnsubscriptionResponseTransactionIDMatchValue;
    }

    public String getTelcoAPIUnsubscriptionResponseDescriptionMatchType() {
        return telcoAPIUnsubscriptionResponseDescriptionMatchType;
    }

    public void setTelcoAPIUnsubscriptionResponseDescriptionMatchType(String telcoAPIUnsubscriptionResponseDescriptionMatchType) {
        this.telcoAPIUnsubscriptionResponseDescriptionMatchType = telcoAPIUnsubscriptionResponseDescriptionMatchType;
    }

    public String getTelcoAPIUnsubscriptionResponseDescriptionMatchValue() {
        return telcoAPIUnsubscriptionResponseDescriptionMatchValue;
    }

    public void setTelcoAPIUnsubscriptionResponseDescriptionMatchValue(String telcoAPIUnsubscriptionResponseDescriptionMatchValue) {
        this.telcoAPIUnsubscriptionResponseDescriptionMatchValue = telcoAPIUnsubscriptionResponseDescriptionMatchValue;
    }

    public Boolean getTelcoAPIUnsubscriptionBills() {
        return telcoAPIUnsubscriptionBills;
    }

    public void setTelcoAPIUnsubscriptionBills(Boolean telcoAPIUnsubscriptionBills) {
        this.telcoAPIUnsubscriptionBills = telcoAPIUnsubscriptionBills;
    }

    public Float getTariff() {
        return tariff;
    }

    public void setTariff(Float tariff) {
        this.tariff = tariff;
    }

    public int getOptInShortCodeID() {
        return optInShortCodeID;
    }

    public void setOptInShortCodeID(int optInShortCodeID) {
        this.optInShortCodeID = optInShortCodeID;
    }

    public String getOptInShortCode() {
        return optInShortCode;
    }

    public void setOptInShortCode(String optInShortCode) {
        this.optInShortCode = optInShortCode;
    }

    public int getOptInShortCodeBind() {
        return optInShortCodeBind;
    }

    public void setOptInShortCodeBind(int optInShortCodeBind) {
        this.optInShortCodeBind = optInShortCodeBind;
    }

    public Float getOptInShortCodeTariff() {
        return optInShortCodeTariff;
    }

    public void setOptInShortCodeTariff(Float optInShortCodeTariff) {
        this.optInShortCodeTariff = optInShortCodeTariff;
    }

    public int getOptOutShortCodeID() {
        return optOutShortCodeID;
    }

    public void setOptOutShortCodeID(int optOutShortCodeID) {
        this.optOutShortCodeID = optOutShortCodeID;
    }

    public String getOptOutShortCode() {
        return optOutShortCode;
    }

    public void setOptOutShortCode(String optOutShortCode) {
        this.optOutShortCode = optOutShortCode;
    }

    public int getOptOutShortCodeBind() {
        return optOutShortCodeBind;
    }

    public void setOptOutShortCodeBind(int optOutShortCodeBind) {
        this.optOutShortCodeBind = optOutShortCodeBind;
    }

    public Float getOptOutShortCodeTariff() {
        return optOutShortCodeTariff;
    }

    public void setOptOutShortCodeTariff(Float optOutShortCodeTariff) {
        this.optOutShortCodeTariff = optOutShortCodeTariff;
    }

    public int getBillingShortCodeID() {
        return billingShortCodeID;
    }

    public void setBillingShortCodeID(int billingShortCodeID) {
        this.billingShortCodeID = billingShortCodeID;
    }

    public String getBillingShortCode() {
        return billingShortCode;
    }

    public void setBillingShortCode(String billingShortCode) {
        this.billingShortCode = billingShortCode;
    }

    public int getTelcoAPIBillingID() {
        return telcoAPIBillingID;
    }

    public int getBillingShortCodeBind() {
        return billingShortCodeBind;
    }

    public void setBillingShortCodeBind(int billingShortCodeBind) {
        this.billingShortCodeBind = billingShortCodeBind;
    }

    public Float getBillingShortCodeTariff() {
        return billingShortCodeTariff;
    }

    public void setBillingShortCodeTariff(Float billingShortCodeTariff) {
        this.billingShortCodeTariff = billingShortCodeTariff;
    }

    public void setTelcoAPIBillingID(int telcoAPIBillingID) {
        this.telcoAPIBillingID = telcoAPIBillingID;
    }

    public String getTelcoAPIBillingName() {
        return telcoAPIBillingName;
    }

    public void setTelcoAPIBillingName(String telcoAPIBillingName) {
        this.telcoAPIBillingName = telcoAPIBillingName;
    }

    public String getTelcoAPIBillingURL() {
        return telcoAPIBillingURL;
    }

    public void setTelcoAPIBillingURL(String telcoAPIBillingURL) {
        this.telcoAPIBillingURL = telcoAPIBillingURL;
    }

    public int getTelcoAPIBillingType() {
        return telcoAPIBillingType;
    }

    public void setTelcoAPIBillingType(int telcoAPIBillingType) {
        this.telcoAPIBillingType = telcoAPIBillingType;
    }

    public String getTelcoAPIBillingMethod() {
        return telcoAPIBillingMethod;
    }

    public void setTelcoAPIBillingMethod(String telcoAPIBillingMethod) {
        this.telcoAPIBillingMethod = telcoAPIBillingMethod;
    }

    public String getTelcoAPIBillingBodyType() {
        return telcoAPIBillingBodyType;
    }

    public void setTelcoAPIBillingBodyType(String telcoAPIBillingBodyType) {
        this.telcoAPIBillingBodyType = telcoAPIBillingBodyType;
    }

    public String getTelcoAPIBillingHeader() {
        return telcoAPIBillingHeader;
    }

    public void setTelcoAPIBillingHeader(String telcoAPIBillingHeader) {
        this.telcoAPIBillingHeader = telcoAPIBillingHeader;
    }

    public String getTelcoAPIBillingBody() {
        return telcoAPIBillingBody;
    }

    public void setTelcoAPIBillingBody(String telcoAPIBillingBody) {
        this.telcoAPIBillingBody = telcoAPIBillingBody;
    }

    public String getTelcoAPIBillingBodyRaw() {
        return telcoAPIBillingBodyRaw;
    }

    public void setTelcoAPIBillingBodyRaw(String telcoAPIBillingBodyRaw) {
        this.telcoAPIBillingBodyRaw = telcoAPIBillingBodyRaw;
    }

    public String getTelcoAPIBillingSuccessCode() {
        return telcoAPIBillingSuccessCode;
    }

    public void setTelcoAPIBillingSuccessCode(String telcoAPIBillingSuccessCode) {
        this.telcoAPIBillingSuccessCode = telcoAPIBillingSuccessCode;
    }

    public String getTelcoAPIBillingInsufficientAirtimeCode() {
        return telcoAPIBillingInsufficientAirtimeCode;
    }

    public void setTelcoAPIBillingInsufficientAirtimeCode(String telcoAPIBillingInsufficientAirtimeCode) {
        this.telcoAPIBillingInsufficientAirtimeCode = telcoAPIBillingInsufficientAirtimeCode;
    }

    public String getTelcoAPIBillingResponseCodeMatchType() {
        return telcoAPIBillingResponseCodeMatchType;
    }

    public void setTelcoAPIBillingResponseCodeMatchType(String telcoAPIBillingResponseCodeMatchType) {
        this.telcoAPIBillingResponseCodeMatchType = telcoAPIBillingResponseCodeMatchType;
    }

    public String getTelcoAPIBillingResponseCodeMatchValue() {
        return telcoAPIBillingResponseCodeMatchValue;
    }

    public void setTelcoAPIBillingResponseCodeMatchValue(String telcoAPIBillingResponseCodeMatchValue) {
        this.telcoAPIBillingResponseCodeMatchValue = telcoAPIBillingResponseCodeMatchValue;
    }

    public String getTelcoAPIBillingResponseDescriptionMatchType() {
        return telcoAPIBillingResponseDescriptionMatchType;
    }

    public void setTelcoAPIBillingResponseDescriptionMatchType(String telcoAPIBillingResponseDescriptionMatchType) {
        this.telcoAPIBillingResponseDescriptionMatchType = telcoAPIBillingResponseDescriptionMatchType;
    }

    public String getTelcoAPIBillingResponseDescriptionMatchValue() {
        return telcoAPIBillingResponseDescriptionMatchValue;
    }

    public void setTelcoAPIBillingResponseDescriptionMatchValue(String telcoAPIBillingResponseDescriptionMatchValue) {
        this.telcoAPIBillingResponseDescriptionMatchValue = telcoAPIBillingResponseDescriptionMatchValue;
    }

    public int getConfirmationShortCodeID() {
        return confirmationShortCodeID;
    }

    public void setConfirmationShortCodeID(int confirmationShortCodeID) {
        this.confirmationShortCodeID = confirmationShortCodeID;
    }

    public String getConfirmationShortCode() {
        return confirmationShortCode;
    }

    public void setConfirmationShortCode(String confirmationShortCode) {
        this.confirmationShortCode = confirmationShortCode;
    }

    public int getConfirmationShortCodeBind() {
        return confirmationShortCodeBind;
    }

    public void setConfirmationShortCodeBind(int confirmationShortCodeBind) {
        this.confirmationShortCodeBind = confirmationShortCodeBind;
    }

    public Float getConfirmationShortCodeTariff() {
        return confirmationShortCodeTariff;
    }

    public void setConfirmationShortCodeTariff(Float confirmationShortCodeTariff) {
        this.confirmationShortCodeTariff = confirmationShortCodeTariff;
    }

    public int getHelpShortCodeID() {
        return helpShortCodeID;
    }

    public void setHelpShortCodeID(int helpShortCodeID) {
        this.helpShortCodeID = helpShortCodeID;
    }

    public String getHelpShortCode() {
        return helpShortCode;
    }

    public void setHelpShortCode(String helpShortCode) {
        this.helpShortCode = helpShortCode;
    }

    public int getHelpShortCodeBind() {
        return helpShortCodeBind;
    }

    public void setHelpShortCodeBind(int helpShortCodeBind) {
        this.helpShortCodeBind = helpShortCodeBind;
    }

    public Float getHelpShortCodeTariff() {
        return helpShortCodeTariff;
    }

    public void setHelpShortCodeTariff(Float helpShortCodeTariff) {
        this.helpShortCodeTariff = helpShortCodeTariff;
    }

    public int getContentShortCodeID() {
        return contentShortCodeID;
    }

    public void setContentShortCodeID(int contentShortCodeID) {
        this.contentShortCodeID = contentShortCodeID;
    }

    public String getContentShortCode() {
        return contentShortCode;
    }

    public void setContentShortCode(String contentShortCode) {
        this.contentShortCode = contentShortCode;
    }

    public int getContentShortCodeBind() {
        return contentShortCodeBind;
    }

    public void setContentShortCodeBind(int contentShortCodeBind) {
        this.contentShortCodeBind = contentShortCodeBind;
    }

    public Float getContentShortCodeTariff() {
        return contentShortCodeTariff;
    }

    public void setContentShortCodeTariff(Float contentShortCodeTariff) {
        this.contentShortCodeTariff = contentShortCodeTariff;
    }

    public int getTelcoAPIContentID() {
        return telcoAPIContentID;
    }

    public void setTelcoAPIContentID(int telcoAPIContentID) {
        this.telcoAPIContentID = telcoAPIContentID;
    }

    public String getTelcoAPIContentName() {
        return telcoAPIContentName;
    }

    public void setTelcoAPIContentName(String telcoAPIContentName) {
        this.telcoAPIContentName = telcoAPIContentName;
    }

    public String getTelcoAPIContentURL() {
        return telcoAPIContentURL;
    }

    public void setTelcoAPIContentURL(String telcoAPIContentURL) {
        this.telcoAPIContentURL = telcoAPIContentURL;
    }

    public int getTelcoAPIContentType() {
        return telcoAPIContentType;
    }

    public void setTelcoAPIContentType(int telcoAPIContentType) {
        this.telcoAPIContentType = telcoAPIContentType;
    }

    public String getTelcoAPIContentMethod() {
        return telcoAPIContentMethod;
    }

    public void setTelcoAPIContentMethod(String telcoAPIContentMethod) {
        this.telcoAPIContentMethod = telcoAPIContentMethod;
    }

    public String getTelcoAPIContentBodyType() {
        return telcoAPIContentBodyType;
    }

    public void setTelcoAPIContentBodyType(String telcoAPIContentBodyType) {
        this.telcoAPIContentBodyType = telcoAPIContentBodyType;
    }

    public String getTelcoAPIContentHeader() {
        return telcoAPIContentHeader;
    }

    public void setTelcoAPIContentHeader(String telcoAPIContentHeader) {
        this.telcoAPIContentHeader = telcoAPIContentHeader;
    }

    public String getTelcoAPIContentBody() {
        return telcoAPIContentBody;
    }

    public void setTelcoAPIContentBody(String telcoAPIContentBody) {
        this.telcoAPIContentBody = telcoAPIContentBody;
    }

    public String getTelcoAPIContentBodyRaw() {
        return telcoAPIContentBodyRaw;
    }

    public void setTelcoAPIContentBodyRaw(String telcoAPIContentBodyRaw) {
        this.telcoAPIContentBodyRaw = telcoAPIContentBodyRaw;
    }

    public String getTelcoAPIContentSuccessCode() {
        return telcoAPIContentSuccessCode;
    }

    public void setTelcoAPIContentSuccessCode(String telcoAPIContentSuccessCode) {
        this.telcoAPIContentSuccessCode = telcoAPIContentSuccessCode;
    }

    public String getTelcoAPIContentInsufficientAirtimeCode() {
        return telcoAPIContentInsufficientAirtimeCode;
    }

    public void setTelcoAPIContentInsufficientAirtimeCode(String telcoAPIContentInsufficientAirtimeCode) {
        this.telcoAPIContentInsufficientAirtimeCode = telcoAPIContentInsufficientAirtimeCode;
    }

    public String getTelcoAPIContentResponseCodeMatchType() {
        return telcoAPIContentResponseCodeMatchType;
    }

    public void setTelcoAPIContentResponseCodeMatchType(String telcoAPIContentResponseCodeMatchType) {
        this.telcoAPIContentResponseCodeMatchType = telcoAPIContentResponseCodeMatchType;
    }

    public String getTelcoAPIContentResponseCodeMatchValue() {
        return telcoAPIContentResponseCodeMatchValue;
    }

    public void setTelcoAPIContentResponseCodeMatchValue(String telcoAPIContentResponseCodeMatchValue) {
        this.telcoAPIContentResponseCodeMatchValue = telcoAPIContentResponseCodeMatchValue;
    }

    public String getTelcoAPIContentResponseDescriptionMatchType() {
        return telcoAPIContentResponseDescriptionMatchType;
    }

    public void setTelcoAPIContentResponseDescriptionMatchType(String telcoAPIContentResponseDescriptionMatchType) {
        this.telcoAPIContentResponseDescriptionMatchType = telcoAPIContentResponseDescriptionMatchType;
    }

    public String getTelcoAPIContentResponseDescriptionMatchValue() {
        return telcoAPIContentResponseDescriptionMatchValue;
    }

    public void setTelcoAPIContentResponseDescriptionMatchValue(String telcoAPIContentResponseDescriptionMatchValue) {
        this.telcoAPIContentResponseDescriptionMatchValue = telcoAPIContentResponseDescriptionMatchValue;
    }

    public int getPreRenewalNotificationShortCodeID() {
        return preRenewalNotificationShortCodeID;
    }

    public void setPreRenewalNotificationShortCodeID(int preRenewalNotificationShortCodeID) {
        this.preRenewalNotificationShortCodeID = preRenewalNotificationShortCodeID;
    }

    public String getPreRenewalNotificationShortCode() {
        return preRenewalNotificationShortCode;
    }

    public void setPreRenewalNotificationShortCode(String preRenewalNotificationShortCode) {
        this.preRenewalNotificationShortCode = preRenewalNotificationShortCode;
    }

    public int getPreRenewalNotificationShortCodeBind() {
        return preRenewalNotificationShortCodeBind;
    }

    public void setPreRenewalNotificationShortCodeBind(int preRenewalNotificationShortCodeBind) {
        this.preRenewalNotificationShortCodeBind = preRenewalNotificationShortCodeBind;
    }

    public Float getPreRenewalNotificationTariff() {
        return preRenewalNotificationTariff;
    }

    public void setPreRenewalNotificationTariff(Float preRenewalNotificationTariff) {
        this.preRenewalNotificationTariff = preRenewalNotificationTariff;
    }

    public String getOptInMessage() {
        return optInMessage;
    }

    public void setOptInMessage(String optInMessage) {
        this.optInMessage = optInMessage;
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }

    public String getOptOutMessage() {
        return optOutMessage;
    }

    public void setOptOutMessage(String optOutMessage) {
        this.optOutMessage = optOutMessage;
    }

    public String getHelpMessage() {
        return helpMessage;
    }

    public void setHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
    }

    public String getPreRenewalMessage() {
        return preRenewalMessage;
    }

    public void setPreRenewalMessage(String preRenewalMessage) {
        this.preRenewalMessage = preRenewalMessage;
    }

    public String getRenewalMessage() {
        return renewalMessage;
    }

    public void setRenewalMessage(String renewalMessage) {
        this.renewalMessage = renewalMessage;
    }

    public String getInsufficientBalanceMessage() {
        return insufficientBalanceMessage;
    }

    public void setInsufficientBalanceMessage(String insufficientBalanceMessage) {
        this.insufficientBalanceMessage = insufficientBalanceMessage;
    }

    public String getAlreadySubscribedMessage() {
        return alreadySubscribedMessage;
    }

    public void setAlreadySubscribedMessage(String alreadySubscribedMessage) {
        this.alreadySubscribedMessage = alreadySubscribedMessage;
    }

    public String getStopMessage() {
        return stopMessage;
    }

    public void setStopMessage(String stopMessage) {
        this.stopMessage = stopMessage;
    }

    public int getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(int billingCycle) {
        this.billingCycle = billingCycle;
    }

    public String getChargingNotificationMessage() {
        return chargingNotificationMessage;
    }

    public void setChargingNotificationMessage(String chargingNotificationMessage) {
        this.chargingNotificationMessage = chargingNotificationMessage;
    }

    public int getPreRenewalCycle() {
        return preRenewalCycle;
    }

    public void setPreRenewalCycle(int preRenewalCycle) {
        this.preRenewalCycle = preRenewalCycle;
    }



    public String getTelcoParams() {
        return telcoParams;
    }

    public void setTelcoParams(String telcoParams) {
        this.telcoParams = telcoParams;
    }

    public String getExternalAPI() {
        return externalAPI;
    }

    public void setExternalAPI(String externalAPI) {
        this.externalAPI = externalAPI;
    }

    public String getExternalAPIMethod() {
        return externalAPIMethod;
    }

    public void setExternalAPIMethod(String externalAPIMethod) {
        this.externalAPIMethod = externalAPIMethod;
    }

    public String getExternalAPISourceAddressParam() {
        return externalAPISourceAddressParam;
    }

    public void setExternalAPISourceAddressParam(String externalAPISourceAddressParam) {
        this.externalAPISourceAddressParam = externalAPISourceAddressParam;
    }

    public String getExternalAPIDestinationAddressParam() {
        return externalAPIDestinationAddressParam;
    }

    public void setExternalAPIDestinationAddressParam(String externalAPIDestinationAddressParam) {
        this.externalAPIDestinationAddressParam = externalAPIDestinationAddressParam;
    }

    public String getExternalAPIMessageParam() {
        return externalAPIMessageParam;
    }

    public void setExternalAPIMessageParam(String externalAPIMessageParam) {
        this.externalAPIMessageParam = externalAPIMessageParam;
    }

    public int getExternalAPIProcessing() {
        return externalAPIProcessing;
    }

    public void setExternalAPIProcessing(int externalAPIProcessing) {
        this.externalAPIProcessing = externalAPIProcessing;
    }

    public Boolean getContentShouldBill() {
        return contentShouldBill;
    }

    public void setContentShouldBill(Boolean contentShouldBill) {
        this.contentShouldBill = contentShouldBill;
    }

    public Boolean getRenewalDoneByTelco() {
        return renewalDoneByTelco;
    }

    public void setRenewalDoneByTelco(Boolean renewalDoneByTelco) {
        this.renewalDoneByTelco = renewalDoneByTelco;
    }

    public Boolean getIncludeServiceInGeneralHelp() {
        return includeServiceInGeneralHelp;
    }

    public void setIncludeServiceInGeneralHelp(Boolean includeServiceInGeneralHelp) {
        this.includeServiceInGeneralHelp = includeServiceInGeneralHelp;
    }

    public Boolean getSendBillingNotification() {
        return sendBillingNotification;
    }

    public void setSendBillingNotification(Boolean sendBillingNotification) {
        this.sendBillingNotification = sendBillingNotification;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getTelcoID() {
        return telcoID;
    }

    public void setTelcoID(int telcoID) {
        this.telcoID = telcoID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getTelcoAPISubscriptionResponseTransactionIDMatchType() {
        return telcoAPISubscriptionResponseTransactionIDMatchType;
    }

    public void setTelcoAPISubscriptionResponseTransactionIDMatchType(String telcoAPISubscriptionResponseTransactionIDMatchType) {
        this.telcoAPISubscriptionResponseTransactionIDMatchType = telcoAPISubscriptionResponseTransactionIDMatchType;
    }

    public String getTelcoAPISubscriptionResponseTransactionIDMatchValue() {
        return telcoAPISubscriptionResponseTransactionIDMatchValue;
    }

    public void setTelcoAPISubscriptionResponseTransactionIDMatchValue(String telcoAPISubscriptionResponseTransactionIDMatchValue) {
        this.telcoAPISubscriptionResponseTransactionIDMatchValue = telcoAPISubscriptionResponseTransactionIDMatchValue;
    }

    public String getTelcoAPIBillingResponseTransactionIDMatchType() {
        return telcoAPIBillingResponseTransactionIDMatchType;
    }

    public void setTelcoAPIBillingResponseTransactionIDMatchType(String telcoAPIBillingResponseTransactionIDMatchType) {
        this.telcoAPIBillingResponseTransactionIDMatchType = telcoAPIBillingResponseTransactionIDMatchType;
    }

    public String getTelcoAPIBillingResponseTransactionIDMatchValue() {
        return telcoAPIBillingResponseTransactionIDMatchValue;
    }

    public void setTelcoAPIBillingResponseTransactionIDMatchValue(String telcoAPIBillingResponseTransactionIDMatchValue) {
        this.telcoAPIBillingResponseTransactionIDMatchValue = telcoAPIBillingResponseTransactionIDMatchValue;
    }

    public String getTelcoAPIContentResponseTransactionIDMatchType() {
        return telcoAPIContentResponseTransactionIDMatchType;
    }

    public void setTelcoAPIContentResponseTransactionIDMatchType(String telcoAPIContentResponseTransactionIDMatchType) {
        this.telcoAPIContentResponseTransactionIDMatchType = telcoAPIContentResponseTransactionIDMatchType;
    }

    public String getTelcoAPIContentResponseTransactionIDMatchValue() {
        return telcoAPIContentResponseTransactionIDMatchValue;
    }

    public void setTelcoAPIContentResponseTransactionIDMatchValue(String telcoAPIContentResponseTransactionIDMatchValue) {
        this.telcoAPIContentResponseTransactionIDMatchValue = telcoAPIContentResponseTransactionIDMatchValue;
    }

    public Boolean getTelcoAPISubscriptionBills() {
        return telcoAPISubscriptionBills;
    }

    public void setTelcoAPISubscriptionBills(Boolean telcoAPISubscriptionBills) {
        this.telcoAPISubscriptionBills = telcoAPISubscriptionBills;
    }

    public Boolean getTelcoAPIBillingBills() {
        return telcoAPIBillingBills;
    }

    public void setTelcoAPIBillingBills(Boolean telcoAPIBillingBills) {
        this.telcoAPIBillingBills = telcoAPIBillingBills;
    }

    public Boolean getTelcoAPIContentBills() {
        return telcoAPIContentBills;
    }

    public void setTelcoAPIContentBills(Boolean telcoAPIContentBills) {
        this.telcoAPIContentBills = telcoAPIContentBills;
    }

    public TelcoAPI getTelcoAPI() {
        return telcoAPI;
    }

    public void setTelcoAPI(TelcoAPI telcoAPI) {
        this.telcoAPI = telcoAPI;
    }
}
