package com.kolawolebalogun.websocket.utils;

import com.google.gson.Gson;
import com.kolawolebalogun.websocket.domain.Message;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.time.LocalTime;

/**
 * Created by KolawoleBalogun on 9/19/17.
 */
public class Messages {
    public static Message objectify(String content) {
        return objectify(content, "Duke Bot", LocalTime.now().toString());
    }

    public static Message objectify(String content, String sender) {
        return objectify(content, sender, LocalTime.now().toString());
    }

    public static Message objectify(String content, Session session) {
        return new Message(content, session.getUserProperties().get("userName").toString(), LocalTime.now().toString());
    }

    public static Message objectify(String content, String sender, String received) {
        return new Message(content, sender, received);
    }

    public static String formatMessage(String message, String user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", message);
        jsonObject.put("sender", user);
        jsonObject.put("received", LocalTime.now().toString());

        return new Gson().toJson(jsonObject);
    }

}
