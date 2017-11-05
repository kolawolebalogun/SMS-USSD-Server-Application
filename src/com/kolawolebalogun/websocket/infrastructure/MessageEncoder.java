package com.kolawolebalogun.websocket.infrastructure;

import com.kolawolebalogun.websocket.domain.Message;

import javax.json.Json;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.time.LocalTime;

/**
 * Created by KolawoleBalogun on 9/17/17.
 */
public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(final Message message) throws EncodeException {
        return Json.createObjectBuilder()
                .add("content", message.getContent())
                .add("sender", message.getSender())
                .add("received", LocalTime.now().toString())
                .build().toString();
    }

    @Override
    public void init(EndpointConfig config) {
        // Not implemented
    }

    @Override
    public void destroy() {
        // Not implemented
    }
}
