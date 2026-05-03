package com.ipb.rental;

import java.io.Serializable;

public class ChatContact implements Serializable {
    private String id;
    private String name;
    private String lastMessage;
    private String time;
    private int unreadCount;
    private int avatarColor;
    private String statusText;
    private String itemName;
    private String itemPrice;
    private String itemDateRange;

    public ChatContact(String id, String name, String lastMessage, String time, int unreadCount, int avatarColor, String statusText, String itemName, String itemPrice, String itemDateRange) {
        this.id = id;
        this.name = name;
        this.lastMessage = lastMessage;
        this.time = time;
        this.unreadCount = unreadCount;
        this.avatarColor = avatarColor;
        this.statusText = statusText;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDateRange = itemDateRange;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getLastMessage() { return lastMessage; }
    public String getTime() { return time; }
    public int getUnreadCount() { return unreadCount; }
    public int getAvatarColor() { return avatarColor; }
    public String getStatusText() { return statusText; }
    public String getItemName() { return itemName; }
    public String getItemPrice() { return itemPrice; }
    public String getItemDateRange() { return itemDateRange; }
}