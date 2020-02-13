package com.tds.gihbookmarks.model;

import com.google.firebase.Timestamp;

public class SaleItems {
    String Item;
    String ItemCode;
    String Price;
    String SellerId;
    String Status;
    Timestamp DateAdded;
    String ImageUrl;
    String Desc;
    String City;

    public SaleItems(String item, String itemCode, String price, String sellerId, String status, Timestamp dateAdded, String imageUrl, String desc, String city) {
        Item = item;
        ItemCode = itemCode;
        Price = price;
        SellerId = sellerId;
        Status = status;
        DateAdded = dateAdded;
        ImageUrl = imageUrl;
        Desc=Desc;
        City=city;
    }
    public SaleItems(){}

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSellerId() {
        return SellerId;
    }

    public void setSellerId(String sellerId) {
        SellerId = sellerId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Timestamp getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        DateAdded = dateAdded;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}



