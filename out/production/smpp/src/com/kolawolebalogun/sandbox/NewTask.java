package com.kolawolebalogun.sandbox;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.concurrent.TimeoutException;

/**
 * Created by KolawoleBalogun on 7/10/17.
 */
public class NewTask {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        String message = "Kolawole Balogun . . .";

        channel.basicPublish( "", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println("[x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
