package com.kolawolebalogun.pojo;

/**
 * Created by KolawoleBalogun on 7/17/17.
 */
public class Subscriber {
    private int ID;
    private String msisdn;
    private int serviceID;
    private int status;
    private String lastBilled;
    private String subscriptionExpiry;
    private String lastContentSent;
    private String optInAt;
    private String optOutAt;
    private String createdAt;
    private String updatedAt;
    private Boolean delete;
    private String deletedAt;
    private Boolean alreadySubscribed = false;
    private String content;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLastBilled() {
        return lastBilled;
    }

    public void setLastBilled(String lastBilled) {
        this.lastBilled = lastBilled;
    }

    public String getSubscriptionExpiry() {
        return subscriptionExpiry;
    }

    public void setSubscriptionExpiry(String subscriptionExpiry) {
        this.subscriptionExpiry = subscriptionExpiry;
    }

    public String getLastContentSent() {
        return lastContentSent;
    }

    public void setLastContentSent(String lastContentSent) {
        this.lastContentSent = lastContentSent;
    }

    public String getOptInAt() {
        return optInAt;
    }

    public void setOptInAt(String optInAt) {
        this.optInAt = optInAt;
    }

    public String getOptOutAt() {
        return optOutAt;
    }

    public void setOptOutAt(String optOutAt) {
        this.optOutAt = optOutAt;
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

    public Boolean getAlreadySubscribed() {
        return alreadySubscribed;
    }

    public void setAlreadySubscribed(Boolean alreadySubscribed) {
        this.alreadySubscribed = alreadySubscribed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
