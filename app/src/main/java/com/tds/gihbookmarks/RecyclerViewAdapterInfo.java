package com.tds.gihbookmarks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tds.gihbookmarks.model.InformativePost;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapterInfo extends RecyclerView.Adapter<RecyclerViewAdapterInfo.ViewHolder> {

    Context mContext;
    List<InformativePost> informativePosts;

    public RecyclerViewAdapterInfo(Context mContext, List<InformativePost> informativePosts) {
        this.mContext = mContext;
        this.informativePosts = informativePosts;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterInfo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterInfo.ViewHolder holder, int position) {
        //holder.tv_link.setText(informativePosts.get(position).getLink());
        holder.tv_desc.setText(informativePosts.get(position).getDesc());
        holder.img1.setImageResource(informativePosts.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return informativePosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_desc,tv_link;
        private ImageView img1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tv_desc=itemView.findViewById(R.id.desc_TV);
            //tv_link=itemView.findViewById(R.id.link_TV);
            img1=itemView.findViewById(R.id.info_img);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            InformativePost posts = informativePosts.get(position);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://google.com"));
            mContext.startActivity(intent);
        }
    }

}
