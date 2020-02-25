package com.tds.gihbookmarks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.tds.gihbookmarks.model.SaleItems;

import java.util.List;

public class YourRequestedItem_RecyclerViewAdapter extends RecyclerView.Adapter<YourRequestedItem_RecyclerViewAdapter.ViewHolder>{

    private List<SaleItems> saleItemsList;
    private Context mContext;
    private RecyclerView.ViewHolder holder;
    private int position;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    public YourRequestedItem_RecyclerViewAdapter(List<SaleItems> saleItemsList, Context mContext) {

        this.saleItemsList = saleItemsList;
        this.mContext = mContext;
        Log.d("Tanmay", "YourRequestedItem_RecyclerViewAdapter: "+saleItemsList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
//        firebaseAuth= FirebaseAuth.getInstance();
//        user=firebaseAuth.getCurrentUser();

        SaleItems saleItems=saleItemsList.get(position);
        Log.d("Yo", "hi how are you.");

        //RequestOptions requestOptions= new RequestOptions().placeholder(R.drawable.ic_launcher_background);


        String imageUrl;
        imageUrl=saleItems.getImageUrl();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(holder.image);
        holder.itemDesc.setText(saleItems.getDesc());
        holder.itemName.setText(saleItems.getItem());
        holder.itemStatus.setText(saleItems.getStatus());
        Log.d("check1", "onBindViewHolder: "+holder.itemName.getText());
        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePopUpDialog();
            }
        });
    }



    private void CreatePopUpDialog() {
        builder=new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.requested_item_popup,null);



        Button cancelRequest=view.findViewById(R.id.pop_item_cancel_request);
        cancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,"Request Cancelled",Toast.LENGTH_LONG).show();
            }
        });
        builder.setView(view);
        alertDialog=builder.create();
        alertDialog.show();

    }

    @Override
    public int getItemCount() {
        return saleItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView itemName;
        TextView itemDesc;
        TextView itemStatus;
        CardView itemCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemCard=itemView.findViewById(R.id.request_item_card);
            this.image=itemView.findViewById(R.id.imageView);
            this.itemName= itemView.findViewById(R.id.request_item_item);
            this.itemDesc=itemView.findViewById(R.id.request_item_desc);
            this.itemStatus=itemView.findViewById(R.id.request_item_status);
            

        }
    }
}
