package com.tds.gihbookmarks;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter {

    private ItemData itemData = new ItemData();
    private ArrayList<Item> itemList;

    public ItemListAdapter(){

    }

    @NonNull
    @Override
    public ItemListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemGrid= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list,parent,false);
        Log.d("tirth", "onCreateViewHolder: 123");

        return new ItemViewHolder(itemGrid);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ItemViewHolder) holder).bindView(position);
    }


    @Override
    public int getItemCount() {
        return itemData.itemList().size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView itemImage;
        public TextView itemName;

        public ItemViewHolder(View itemView) {
            super(itemView);

            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            itemName = (TextView) itemView.findViewById(R.id.itemText);
            itemView.setOnClickListener((View.OnClickListener) this);
        }
        public void bindView(int position){
            itemName.setText(itemData.itemName[position]);
            itemImage.setImageResource(itemData.picturePath[position]);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
