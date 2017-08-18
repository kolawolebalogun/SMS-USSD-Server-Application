package com.kolawolebalogun.pojo;

/**
 * Created by KolawoleBalogun on 7/31/17.
 */
public class ShortCode {
    private int ID;
    private String shortCode;
    private float tariff;
    private int telco;
    private int smppBind;
    private Boolean delete;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public float getTariff() {
        return tariff;
    }

    public void setTariff(float tariff) {
        this.tariff = tariff;
    }

    public int getTelco() {
        return telco;
    }

    public void setTelco(int telco) {
        this.telco = telco;
    }

    public int getSmppBind() {
        return smppBind;
    }

    public void setSmppBind(int smppBind) {
        this.smppBind = smppBind;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}
