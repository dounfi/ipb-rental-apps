package com.ipb.rental;

import java.io.Serializable;

public class Item implements Serializable {
    private String id, name, category, status, rating, rentCount;
    private String pricePerDay, shortPrice, condition, description;
    private int imageRes;

    public Item(String id, String name, String category, int imageRes, String status, String rating, String rentCount, String pricePerDay, String shortPrice, String condition, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.imageRes = imageRes;
        this.status = status;
        this.rating = rating;
        this.rentCount = rentCount;
        this.pricePerDay = pricePerDay;
        this.shortPrice = shortPrice;
        this.condition = condition;
        this.description = description;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getImageRes() { return imageRes; }
    public void setImageRes(int imageRes) { this.imageRes = imageRes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getRentCount() { return rentCount; }
    public void setRentCount(String rentCount) { this.rentCount = rentCount; }

    public String getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(String pricePerDay) { this.pricePerDay = pricePerDay; }

    public String getShortPrice() { return shortPrice; }
    public void setShortPrice(String shortPrice) { this.shortPrice = shortPrice; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
