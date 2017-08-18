package com.kolawolebalogun.rabbitmq;

/**
 * Created by KolawoleBalogun on 7/10/17.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.kolawolebalogun.constants.AppConstants;
import com.rabbitmq.client.*;

public class BrokerConnection {
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;
    protected Map<String, Object> args = new HashMap<String, Object>();
    protected int prefetchCount = 1;
    protected Boolean durable = true;
    protected Boolean autoAck = false;

    public BrokerConnection(String endpointName) throws java.io.IOException, TimeoutException {
        this.endPointName = endpointName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setRequestedHeartbeat(60);
        factory.setHost(AppConstants.MESSAGE_BROKER_HOST);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        channel.queueDeclare(endpointName, durable, false, false, null);
    }

    public BrokerConnection(String endpointName, Map<String, Object> args) throws java.io.IOException, TimeoutException {
        this.endPointName = endpointName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(AppConstants.MESSAGE_BROKER_HOST);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
        if(args != null) {
            this.args = args;
        }

        channel.queueDeclare(endpointName, durable, false, false, args);
    }

    public BrokerConnection(String endpointName, Boolean priority) throws java.io.IOException, TimeoutException {
        this.endPointName = endpointName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(AppConstants.MESSAGE_BROKER_HOST);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
        if(priority) {
            this.args.put("x-max-priority", 10);
        }

        channel.queueDeclare(endpointName, durable, false, false, args);
    }

    public BrokerConnection(String endpointName, Map<String, Object> args, int prefetchCount) throws java.io.IOException, TimeoutException {
        this.endPointName = endpointName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(AppConstants.MESSAGE_BROKER_HOST);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
        if(args != null) {
            this.args = args;
        }
        this.prefetchCount = prefetchCount;

        channel.queueDeclare(endpointName, durable, false, false, args);
    }

    public BrokerConnection(String endpointName, Map<String, Object> args, Boolean durable, int prefetchCount) throws java.io.IOException, TimeoutException {
        this.endPointName = endpointName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(AppConstants.MESSAGE_BROKER_HOST);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
        if(args != null) {
            this.args = args;
        }
        this.prefetchCount = prefetchCount;
        this.durable = durable;

        channel.queueDeclare(endpointName, durable, false, false, args);
    }


    public BrokerConnection(String endpointName, Map<String, Object> args, Boolean durable, int prefetchCount, Boolean autoAck) throws java.io.IOException, TimeoutException {
        this.endPointName = endpointName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(AppConstants.MESSAGE_BROKER_HOST);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
        if(args != null) {
            this.args = args;
        }
        this.prefetchCount = prefetchCount;
        this.durable = durable;
        this.autoAck = autoAck;

        channel.queueDeclare(endpointName, durable, false, false, args);
    }

    public void close() throws java.io.IOException, TimeoutException {
        this.channel.close();
        this.connection.close();
    }

    public Channel getChannel(){
        return this.channel;
    }
}
