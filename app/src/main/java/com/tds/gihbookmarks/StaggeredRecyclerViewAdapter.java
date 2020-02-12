package com.tds.gihbookmarks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.tds.gihbookmarks.model.Book;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "StaggerRecyclerViewAdapter";
//    private final ArrayList<String> mImageUrls;
    private List<Book> bookList;
    private ArrayList<String> mName= new ArrayList<>();
    private ArrayList<String> mImage= new ArrayList<>();
    private Context mContext;
    private RecyclerView.ViewHolder holder;
    private int position;

    public StaggeredRecyclerViewAdapter(Context mContext, List<Book> bookList) {
        this.bookList=bookList;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item,parent,false);
        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Book book=bookList.get(position);
        Log.d(TAG, "onBindViewHolder: called.");

        RequestOptions requestOptions= new RequestOptions().placeholder(R.drawable.ic_launcher_background);


        String imageUrl;
        imageUrl=book.getImageUrl1();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(holder.image);
//        Glide.with(mContext)
//                .load(mImageUrls.get(position))
//                .apply(requestOptions)
//                .into(holder.image);
//
//        holder.name.setText(mName.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "onClick: clicked on:" +mName.get(position));
//                Toast.makeText(mContext, mName.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image=itemView.findViewById(R.id.image_widget);
            this.name= itemView.findViewById(R.id.name_widget);
        }
    }



}
