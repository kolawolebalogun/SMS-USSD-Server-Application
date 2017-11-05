package com.kolawolebalogun.websocket.infrastructure;

import com.kolawolebalogun.websocket.domain.Message;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.time.LocalTime;

/**
 * Created by KolawoleBalogun on 9/17/17.
 */
public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(final String textMessage) throws DecodeException {
        Message message = new Message();
        JsonObject jsonObject = Json.createReader(new StringReader(textMessage)).readObject();
        message.setContent(jsonObject.getString("content"));
        message.setSender(jsonObject.getString("sender"));
        message.setReceived(LocalTime.now().toString());
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
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
