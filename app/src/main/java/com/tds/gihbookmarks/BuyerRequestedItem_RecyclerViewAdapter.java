package com.tds.gihbookmarks;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BuyerRequestedItem_RecyclerViewAdapter extends RecyclerView.Adapter<BuyerRequestedItem_RecyclerViewAdapter.ViewHolder> {


    @NonNull
    @Override
    public BuyerRequestedItem_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BuyerRequestedItem_RecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
