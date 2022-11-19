package com.example.test4.data;

public class BookItem {
    public BookItem(String title, double price, int resourceId) {
        this.resourceId = resourceId;
        this.title = title;
        this.price = price;
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

    public void setTitle(String title) {
        this.title = title;
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
}
