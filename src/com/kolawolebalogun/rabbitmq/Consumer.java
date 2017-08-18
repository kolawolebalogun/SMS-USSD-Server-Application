package com.kolawolebalogun.rabbitmq;

import com.kolawolebalogun.constants.AppConstants;
import com.kolawolebalogun.processors.Processors;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by KolawoleBalogun on 7/11/17.
 */
public class Consumer extends BrokerConnection implements Runnable, com.rabbitmq.client.Consumer {

    public Consumer(String endPointName) throws IOException, TimeoutException {
        super(endPointName);
    }

    public Consumer(String endPointName, Map<String, Object> args) throws IOException, TimeoutException {
        super(endPointName, args);
    }

    public Consumer(String endPointName, Boolean priority) throws IOException, TimeoutException {
        super(endPointName, priority);
    }

    public Consumer(String endPointName, Map<String, Object> args, int prefetchCount) throws IOException, TimeoutException {
        super(endPointName, args, prefetchCount);
    }

    public Consumer(String endPointName, Map<String, Object> args, Boolean durable, int prefetchCount) throws IOException, TimeoutException {
        super(endPointName, args, durable, prefetchCount);
    }

    public Consumer(String endPointName, Map<String, Object> args, Boolean durable, int prefetchCount, Boolean autoAck) throws IOException, TimeoutException {
        super(endPointName, args, durable, prefetchCount, autoAck);
    }

    public void run() {
        try {
            channel.basicQos(prefetchCount);
            //start consuming messages. Auto acknowledge messages.
            channel.basicConsume(endPointName, autoAck, this);
        } catch (IOException e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called when consumer is registered.
     */
    public void handleConsumeOk(String consumerTag) {
        System.out.print(String.format("[x] Consumer %s registered\r", consumerTag));
    }

    /**
     * Called when new message is available.
     */
    @Override
    public void handleDelivery(String consumerTag, Envelope env, AMQP.BasicProperties basicProperties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        try {
            if (endPointName.equalsIgnoreCase(AppConstants.MESSAGE_BROKER_QUEUE_DND_ASSEMBLY)) {
                Processors.processorDNDAssembly(message);
            } else if (endPointName.equalsIgnoreCase(AppConstants.MESSAGE_BROKER_QUEUE_DND_UPLOAD)) {
                Processors.processorDNDUpload(message);
            } else if (endPointName.startsWith(AppConstants.PREFIX_OUTGOING_BIND)) {
                Processors.processorSubmitMessages(endPointName, message);
            } else if (endPointName.equalsIgnoreCase(AppConstants.MESSAGE_BROKER_QUEUE_INCOMING_MESSAGES)) {
                Processors.processorIncomingMessages(message);
            } else if (endPointName.equalsIgnoreCase(AppConstants.MESSAGE_BROKER_QUEUE_CONTENT_UPLOAD)) {
                Processors.processorContentUpload(message);
            } else if (endPointName.equalsIgnoreCase(AppConstants.MESSAGE_BROKER_QUEUE_AWAITING_ASYNC)) {
                Processors.processorAwaitingAsync(message);
            } else if (endPointName.startsWith(AppConstants.PREFIX_TELCO_API_REQUEST)) {
                Processors.processorTelcoAPIRequest(message);
            } else if (endPointName.equalsIgnoreCase(AppConstants.MESSAGE_BROKER_QUEUE_CONTENT_PROVIDER_RECEIPT)) {
                Processors.processorContentProviderReceipt(message);
            }
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        } finally {
            if (!autoAck) {
                channel.basicAck(env.getDeliveryTag(), false);
            }
        }
    }

    public void handleCancel(String consumerTag) {
    }

    public void handleCancelOk(String consumerTag) {
    }

    public void handleRecoverOk(String consumerTag) {
    }

    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {
    }

}
