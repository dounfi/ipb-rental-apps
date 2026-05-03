package com.ipb.rental;

import java.io.Serializable;

public class RentalItem implements Serializable {
    private String id;
    private String itemName;
    private String personName;
    private String status; // "Aktif", "Pending", "Selesai", "Dibatalkan"
    private String dateRange;
    private String totalPrice;
    private String priceLabel;
    private int imageRes;

    public RentalItem(String id, String itemName, String personName, String status, String dateRange, String totalPrice, String priceLabel, int imageRes) {
        this.id = id;
        this.itemName = itemName;
        this.personName = personName;
        this.status = status;
        this.dateRange = dateRange;
        this.totalPrice = totalPrice;
        this.priceLabel = priceLabel;
        this.imageRes = imageRes;
    }

    public String getId() { return id; }
    public String getItemName() { return itemName; }
    public String getPersonName() { return personName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDateRange() { return dateRange; }
    public String getTotalPrice() { return totalPrice; }
    public String getPriceLabel() { return priceLabel; }
    public int getImageRes() { return imageRes; }
}
