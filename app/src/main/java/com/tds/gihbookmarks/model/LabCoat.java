package com.tds.gihbookmarks.model;

import com.google.firebase.Timestamp;

public class LabCoat {
    private Timestamp dateAdded;
    private String size;
    private String userId;
    private String price;
    private String city;
    private String imageURL;

    public LabCoat() {
    }

    public LabCoat(Timestamp dateAdded, String size, String userId, String price, String city, String imageURL) {
        this.dateAdded = dateAdded;
        this.size = size;
        this.userId = userId;
        this.price = price;
        this.city = city;
        this.imageURL = imageURL;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
