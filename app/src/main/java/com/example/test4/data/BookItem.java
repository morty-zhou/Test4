package com.example.test4.data;

import java.io.Serializable;

public class BookItem implements Serializable {
    public BookItem(String title, String author, double price,  String shop, int resourceId) {
        this.resourceId = resourceId;
        this.title = title;
        this.price = price;
        this.author = author;
        this.shop = shop;
    }

    public BookItem() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getShop() {
        return shop;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author){this.author = author;}
    public void setShop(String shop) {
        this.title = shop;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    private  int resourceId;
    private String title;
    private double price;
    private String author;
    private String shop;
}
