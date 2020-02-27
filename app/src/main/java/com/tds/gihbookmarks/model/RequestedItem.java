package com.tds.gihbookmarks.model;

import com.google.firebase.Timestamp;

public class RequestedItem {
    private String SellerId;
    private String BuyerId;
    private Timestamp DateRequested;
    private String ItemCode;
    private String Item;
    private String status;


    public RequestedItem() {
    }

    public RequestedItem(String sellerId, String buyerId, Timestamp dateRequested, String itemCode, String item,String status) {
        SellerId = sellerId;
        BuyerId = buyerId;
        DateRequested = dateRequested;
        ItemCode = itemCode;
        Item = item;
        status=status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
