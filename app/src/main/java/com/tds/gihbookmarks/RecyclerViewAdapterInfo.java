package com.tds.gihbookmarks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapterInfo extends RecyclerView.Adapter<RecyclerViewAdapterInfo.ViewHolder> {

    Context mContext;


    @NonNull
    @Override
    public RecyclerViewAdapterInfo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterInfo.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_desc,tv_link;
        private ImageView img1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_desc=itemView.findViewById(R.id.desc_TV);
            tv_link=itemView.findViewById(R.id.link_TV);
            img1=itemView.findViewById(R.id.info_img);
        }
    }
}
