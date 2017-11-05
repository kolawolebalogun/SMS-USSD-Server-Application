package com.kolawolebalogun.websocket.domain;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by KolawoleBalogun on 9/17/17.
 */
public class Message {

    private String content;
    private String sender;
    private String received;

    public Message() {
    }

    public Message(String content, String sender) {
        this(content, sender, LocalTime.now().toString());
    }

    public Message(String content, String sender, String received) {
        this.content = content;
        this.sender = sender;
        this.received = received;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(content, message.content) &&
                Objects.equals(sender, message.sender) &&
                Objects.equals(received, message.received);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, sender, received);
    }
}
