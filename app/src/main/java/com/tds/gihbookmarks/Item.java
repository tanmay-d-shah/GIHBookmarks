package com.tds.gihbookmarks;

import android.content.Context;

public class Item {
    private String itemName;
    private String itemImage;

    public Item(String itemName, String itemImage) {
        this.itemName = itemName;
        this.itemImage = itemImage;
    }

    public int getImageResourceId(Context context){
        return context.getResources().getIdentifier(this.itemImage,"drawable",context.getPackageName());
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}

