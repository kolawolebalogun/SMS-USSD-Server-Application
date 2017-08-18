package com.kolawolebalogun.pojo;

/**
 * Created by KolawoleBalogun on 7/15/17.
 */
public class SubmitMessage {
    private String destinationAddress;
    private String sms;
    private String keyword;
    private String sourceAddress;
    private Service service;
    private Subscriber subscriber;
    private Content content;
    private int action;
    private String transactionID;
    public String getDestinationAddress() {
        return destinationAddress;
    }
    public String externalAPIType;
    public float tariff;
    public String bearer;

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getExternalAPIType() {
        return externalAPIType;
    }

    public void setExternalAPIType(String externalAPIType) {
        this.externalAPIType = externalAPIType;
    }

    public float getTariff() {
        return tariff;
    }

    public void setTariff(float tariff) {
        this.tariff = tariff;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }
}
