package com.kolawolebalogun.sandbox;

/**
 * Created by KolawoleBalogun on 7/10/17.
 */

import com.kolawolebalogun.constants.AppConstants;
import com.rabbitmq.client.*;

import java.util.concurrent.TimeoutException;


public class Emit {
    private Connection connection;
    private Channel channel;

    public Emit() throws java.io.IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(AppConstants.MESSAGE_BROKER_HOST);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.exchangeDeclare(AppConstants.MESSAGE_BROKER_EXCHANGE_NAME, "direct");
    }

    private void publish(String routingKey, String body) throws java.io.IOException {
        this.channel.basicPublish(AppConstants.MESSAGE_BROKER_EXCHANGE_NAME, routingKey, null, body.getBytes());
        System.out.println("[x] Sent '" + routingKey + "':'" + body + "'");
    }

    private void close() throws java.io.IOException, TimeoutException {
        this.channel.close();
        this.connection.close();
    }
}
