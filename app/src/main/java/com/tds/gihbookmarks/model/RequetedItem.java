package com.tds.gihbookmarks.model;

import com.google.firebase.Timestamp;

public class RequetedItem {
    private String SellerId;
    private String BuyerId;
    private Timestamp DateRequested;
    private String ItemCode;
    private String Item;


    public RequetedItem() {
    }

    public RequetedItem(String sellerId, String buyerId, Timestamp dateRequested, String itemCode, String item) {
        SellerId = sellerId;
        BuyerId = buyerId;
        DateRequested = dateRequested;
        ItemCode = itemCode;
        Item = item;
    }

    public String getSellerId() {
        return SellerId;
    }

    public void setSellerId(String sellerId) {
        SellerId = sellerId;
    }

    public String getBuyerId() {
        return BuyerId;
    }

    public void setBuyerId(String buyerId) {
        BuyerId = buyerId;
    }

    public Timestamp getDateRequested() {
        return DateRequested;
    }

    public void setDateRequested(Timestamp dateRequested) {
        DateRequested = dateRequested;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }
}
