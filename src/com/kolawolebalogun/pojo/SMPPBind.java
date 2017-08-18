package com.kolawolebalogun.pojo;

/**
 * Created by KolawoleBalogun on 7/13/17.
 */
public class SMPPBind {
    private int ID;
    private int port;
    private int tps;
    private int maxConnections;
    private int type;
    private int addressTON;
    private int addressNPI;
    private int sourceAddressTON;
    private int sourceAddressNPI;
    private int destinationAddressTON;
    private int destinationAddressNPI;
    private String ip;
    private String accountName;
    private String accountPassword;
    private String systemType;
    private String telcoName;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public int getTps() {
        return tps;
    }

    public void setTps(int tps) {
        this.tps = tps;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAddressTON() {
        return addressTON;
    }

    public void setAddressTON(int addressTON) {
        this.addressTON = addressTON;
    }

    public int getAddressNPI() {
        return addressNPI;
    }

    public void setAddressNPI(int addressNPI) {
        this.addressNPI = addressNPI;
    }

    public int getSourceAddressTON() {
        return sourceAddressTON;
    }

    public void setSourceAddressTON(int sourceAddressTON) {
        this.sourceAddressTON = sourceAddressTON;
    }

    public int getSourceAddressNPI() {
        return sourceAddressNPI;
    }

    public void setSourceAddressNPI(int sourceAddressNPI) {
        this.sourceAddressNPI = sourceAddressNPI;
    }

    public int getDestinationAddressTON() {
        return destinationAddressTON;
    }

    public void setDestinationAddressTON(int destinationAddressTON) {
        this.destinationAddressTON = destinationAddressTON;
    }

    public int getDestinationAddressNPI() {
        return destinationAddressNPI;
    }

    public void setDestinationAddressNPI(int destinationAddressNPI) {
        this.destinationAddressNPI = destinationAddressNPI;
    }

    public String getTelcoName() {
        return telcoName;
    }

    public void setTelcoName(String telcoName) {
        this.telcoName = telcoName;
    }
}
