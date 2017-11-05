package com.kolawolebalogun.websocket.application;

import com.google.gson.Gson;
import com.kolawolebalogun.constants.AppConstants;
import com.kolawolebalogun.util.Util;
import com.kolawolebalogun.websocket.domain.Message;
import com.kolawolebalogun.websocket.infrastructure.MessageDecoder;
import com.kolawolebalogun.websocket.infrastructure.MessageEncoder;
import com.kolawolebalogun.websocket.utils.Messages;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by KolawoleBalogun on 9/23/17.
 */
@javax.websocket.ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ClientEndpoint {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    @OnOpen
    public void onOpen(final Session session) {
        if (session != null) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (session.isOpen()) {
                            session.getBasicRemote().sendText(Messages.formatMessage(new Gson().toJson(Util.monitoringToolData()), AppConstants.MONITORING_WS_USERNAME));
                        }
                    } catch (Exception e) {
                        if (AppConstants.showError) {
                            e.printStackTrace();
                        }
                    }
                }
            }, 1000, 1000);
        }
    }

    @OnMessage
    public void onMessage(Message message, Session session) {
        session.getUserProperties().putIfAbsent("userName", AppConstants.MONITORING_WS_USERNAME);
        System.out.println(String.format("[%s:%s] %s",
                simpleDateFormat.format(message.getReceived()), message.getSender(), message.getContent()));
    }
}
