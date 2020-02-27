package com.tds.gihbookmarks;

import java.util.ArrayList;

public class ItemData {
    public String[] itemName = {"Books", "Labcoat", "Stationary Items", "Other Items"};
    public int[] picturePath = {R.drawable.books, R.drawable.labcoat, R.drawable.stationaryitems, R.drawable.otheritems};

    public ArrayList<Item> itemList() {
        ArrayList<Item> list = new ArrayList<>();
        for (int i = 0; i < itemName.length; i++) {
            Item item = new Item(itemName[i], itemName[i].replace(" ", "").toLowerCase());
            list.add(item);
        }
        return list;
    }
}
