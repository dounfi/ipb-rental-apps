package com.ipb.rental;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String id;
    private String message;
    private String time;
    private boolean isSentByMe;

    public ChatMessage(String id, String message, String time, boolean isSentByMe) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.isSentByMe = isSentByMe;
    }

    public String getId() { return id; }
    public String getMessage() { return message; }
    public String getTime() { return time; }
    public boolean isSentByMe() { return isSentByMe; }
}