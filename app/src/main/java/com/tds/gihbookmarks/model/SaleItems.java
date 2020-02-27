package com.tds.gihbookmarks.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class SaleItems implements Parcelable {
    private String Item;
    private String ItemCode;
    private String Price;
    private String SellerId;
    private String Status;
    private Timestamp DateAdded;
    private String ImageUrl;
    private String Desc;
    private String City;

    public SaleItems(Parcel in) {
        Item = in.readString();
        ItemCode = in.readString();
        Price = in.readString();
        SellerId = in.readString();
        Status = in.readString();
        DateAdded = in.readParcelable(Timestamp.class.getClassLoader());
        ImageUrl = in.readString();
        Desc = in.readString();
        City = in.readString();
    }

    public SaleItems(String item, String itemCode, String price, String sellerId, String status, Timestamp dateAdded, String imageUrl, String Desc, String city) {
        this.Item = item;
        this.ItemCode = itemCode;
        this.Price = price;
        this.SellerId = sellerId;
        this.Status = status;
        this.DateAdded = dateAdded;
        this.ImageUrl = imageUrl;
        this.Desc = Desc;
        this.City = city;
    }

    public static final Parcelable.Creator<SaleItems> CREATOR = new Parcelable.Creator<SaleItems>() {
        @Override
        public SaleItems createFromParcel(Parcel in) {
            return new SaleItems(in);
        }

        @Override
        public SaleItems[] newArray(int size) {
            return new SaleItems[size];
        }
    };

    public SaleItems() {
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Item);
        dest.writeString(ItemCode);
        dest.writeString(Price);
        dest.writeString(SellerId);
        dest.writeString(Status);
        dest.writeParcelable(DateAdded, flags);

        dest.writeString(ImageUrl);
        dest.writeString(Desc);
        dest.writeString(City);


    }
}



