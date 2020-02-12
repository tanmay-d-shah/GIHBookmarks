package com.tds.gihbookmarks.model;

import com.google.firebase.Timestamp;

public class Stationary {
    String title;
    String stationaryId;
    String price;
    String desc;

    Timestamp dateAdded;
    String city;
    String imageURL;
    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Stationary(){}

    public Stationary(String stationaryId,String title,String price, String desc, Timestamp dateAdded, String city, String imageURL) {
        this.stationaryId = stationaryId;
        this.title = title;
        this.price = price;
        this.desc = desc;
        this.dateAdded = dateAdded;
        this.city = city;
        this.imageURL = imageURL;
        this.userId = userId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStationaryId() {
        return stationaryId;
    }

    public void setStationaryId(String stationaryId) {
        this.stationaryId = stationaryId;
    }
}
