package com.kolawolebalogun.pojo;

/**
 * Created by KolawoleBalogun on 7/15/17.
 */
public class  TelcoAPI{
    private int ID;
    private String name;
    private String URL;
    private int type;
    private String method;
    private String bodyType;
    private String header;
    private String body;
    private String bodyRaw;
    private String successCode;
    private String insufficientAirtimeCode;
    private String responseCodeMatchType;
    private String responseCodeMatchValue;
    private String responseTransactionIDMatchType;
    private String responseTransactionIDMatchValue;
    private String responseDescriptionMatchType;
    private String responseDescriptionMatchValue;
    private String createdAt;
    private String updatedAt;
    private Boolean delete;
    private String deletedAt;
    private int maxConnection;
    private int apiCategory;
    private Boolean bills;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyRaw() {
        return bodyRaw;
    }

    public void setBodyRaw(String bodyRaw) {
        this.bodyRaw = bodyRaw;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public String getInsufficientAirtimeCode() {
        return insufficientAirtimeCode;
    }

    public void setInsufficientAirtimeCode(String insufficientAirtimeCode) {
        this.insufficientAirtimeCode = insufficientAirtimeCode;
    }

    public String getResponseCodeMatchType() {
        return responseCodeMatchType;
    }

    public void setResponseCodeMatchType(String responseCodeMatchType) {
        this.responseCodeMatchType = responseCodeMatchType;
    }

    public String getResponseCodeMatchValue() {
        return responseCodeMatchValue;
    }

    public void setResponseCodeMatchValue(String responseCodeMatchValue) {
        this.responseCodeMatchValue = responseCodeMatchValue;
    }

    public String getResponseDescriptionMatchType() {
        return responseDescriptionMatchType;
    }

    public void setResponseDescriptionMatchType(String responseDescriptionMatchType) {
        this.responseDescriptionMatchType = responseDescriptionMatchType;
    }

    public String getResponseDescriptionMatchValue() {
        return responseDescriptionMatchValue;
    }

    public void setResponseDescriptionMatchValue(String responseDescriptionMatchValue) {
        this.responseDescriptionMatchValue = responseDescriptionMatchValue;
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

    public int getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }


    public int getApiCategory() {
        return apiCategory;
    }

    public void setApiCategory(int apiCategory) {
        this.apiCategory = apiCategory;
    }

    public Boolean getBills() {
        return bills;
    }

    public void setBills(Boolean bills) {
        this.bills = bills;
    }

    public String getResponseTransactionIDMatchType() {
        return responseTransactionIDMatchType;
    }

    public void setResponseTransactionIDMatchType(String responseTransactionIDMatchType) {
        this.responseTransactionIDMatchType = responseTransactionIDMatchType;
    }

    public String getResponseTransactionIDMatchValue() {
        return responseTransactionIDMatchValue;
    }

    public void setResponseTransactionIDMatchValue(String responseTransactionIDMatchValue) {
        this.responseTransactionIDMatchValue = responseTransactionIDMatchValue;
    }
}
