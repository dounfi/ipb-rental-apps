package com.ipb.rental;

public class Product {
    private String name;
    private String price;
    private String rating;
    private String category;

    public Product(String name, String price, String rating, String category) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.category = category;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getRating() { return rating; }
    public String getCategory() { return category; }
}