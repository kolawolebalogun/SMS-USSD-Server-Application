package com.kolawolebalogun.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by KolawoleBalogun on 7/11/17.
 */
public class Producer extends BrokerConnection {
    public Producer(String endPointName) throws IOException, TimeoutException {
        super(endPointName);
    }

    public Producer(String endPointName, Map<String, Object> args) throws IOException, TimeoutException {
        super(endPointName, args);
    }

    public Producer(String endPointName, Boolean priority) throws IOException, TimeoutException {
        super(endPointName, priority);
    }

    public Producer(String endPointName, Map<String, Object> args, int prefetchCount) throws IOException, TimeoutException {
        super(endPointName, args, prefetchCount);
    }

    public Producer(String endPointName, Map<String, Object> args, Boolean durable, int prefetchCount) throws IOException, TimeoutException {
        super(endPointName, args, durable, prefetchCount);
    }

    public Producer(String endPointName, Map<String, Object> args, Boolean durable, int prefetchCount, Boolean autoAck) throws IOException, TimeoutException {
        super(endPointName, args, durable, prefetchCount);
    }

    public void sendMessage(String body) throws IOException {
        AMQP.BasicProperties.Builder basicProps = new AMQP.BasicProperties.Builder();
        basicProps.contentType("text/plain").priority(0);
        channel.basicPublish("", endPointName, basicProps.build(), body.getBytes());
    }

    public void sendMessage(String body, int priority) throws IOException {
        AMQP.BasicProperties.Builder basicProps = new AMQP.BasicProperties.Builder();
        basicProps.contentType("text/plain").priority(priority);
        channel.basicPublish("", endPointName, basicProps.build(), body.getBytes());
    }
}
