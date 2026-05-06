package com.ipb.rental;

import java.io.Serializable;

public class AdminItem implements Serializable {
    private String id;
    private String name;
    private String category;
    private String ownerName;
    private String ownerEmail;
    private String status;
    private String pricePerDay;
    private String condition;
    private String description;
    private int imageRes;
    private int rentCount;
    private String rating;

    public AdminItem(String id, String name, String category, String ownerName, String ownerEmail, String status, String pricePerDay, String condition, String description, int imageRes, int rentCount, String rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.status = status;
        this.pricePerDay = pricePerDay;
        this.condition = condition;
        this.description = description;
        this.imageRes = imageRes;
        this.rentCount = rentCount;
        this.rating = rating;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getOwnerName() { return ownerName; }
    public String getOwnerEmail() { return ownerEmail; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPricePerDay() { return pricePerDay; }
    public String getCondition() { return condition; }
    public String getDescription() { return description; }
    public int getImageRes() { return imageRes; }
    public int getRentCount() { return rentCount; }
    public String getRating() { return rating; }

    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPricePerDay(String pricePerDay) { this.pricePerDay = pricePerDay; }
    public void setCondition(String condition) { this.condition = condition; }
    public void setDescription(String description) { this.description = description; }
}
