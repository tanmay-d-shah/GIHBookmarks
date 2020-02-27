package com.tds.gihbookmarks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tds.gihbookmarks.model.InformativePost;
import com.tds.gihbookmarks.model.LinkPost;

import java.util.List;

public class PageAdapter_Link extends RecyclerView.Adapter<PageAdapter_Link.ViewHolder> {

    Context context;
    List<LinkPost> linkpost;

    public PageAdapter_Link(Context context, List<LinkPost> linkpost){

        this.context = context;
        this.linkpost = linkpost;
    }

    @NonNull
    @Override
    public PageAdapter_Link.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_layout, parent, false);
        return new PageAdapter_Link.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageAdapter_Link.ViewHolder holder, int position) {
        holder.description.setText(linkpost.get(position).getDescription());
        holder.link.setText(linkpost.get(position).getLink());
    }

    @Override
    public int getItemCount() {
        return linkpost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView description;
        private TextView link;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            description=itemView.findViewById(R.id.link_description);
            link=itemView.findViewById(R.id.article_link);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            LinkPost posts = linkpost.get(position);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://google.com"));
            context.startActivity(intent);
        }

        }
    }
