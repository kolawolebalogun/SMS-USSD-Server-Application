package com.kolawolebalogun.pojo;

/**
 * Created by KolawoleBalogun on 7/21/17.
 */
public class AwaitingAsync {
    private SubmitMessage submitMessage;
    private String transactionID;
    private String code;
    private String description;

    public SubmitMessage getSubmitMessage() {
        return submitMessage;
    }

    public void setSubmitMessage(SubmitMessage submitMessage) {
        this.submitMessage = submitMessage;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
