package com.ipb.rental;

import java.io.Serializable;

public class AdminUser implements Serializable {
    private String id;
    private String name;
    private String email;
    private String nim;
    private String role;
    private String status;
    private int avatarColor;

    public AdminUser(String id, String name, String email, String nim, String role, String status, int avatarColor) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nim = nim;
        this.role = role;
        this.status = status;
        this.avatarColor = avatarColor;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getNim() { return nim; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public int getAvatarColor() { return avatarColor; }

    public void setStatus(String status) { this.status = status; }
}
