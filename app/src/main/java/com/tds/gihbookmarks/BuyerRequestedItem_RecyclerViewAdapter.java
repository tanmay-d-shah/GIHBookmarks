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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.tds.gihbookmarks.model.RequestedItem;
import com.tds.gihbookmarks.model.SaleItems;

import java.util.List;

public class BuyerRequestedItem_RecyclerViewAdapter extends RecyclerView.Adapter<BuyerRequestedItem_RecyclerViewAdapter.ViewHolder> {

    private List<SaleItems> saleItemsList;
    private List<RequestedItem> requestedItemList;
    private Context mContext;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private String buyerId;

    private TextView itemDesc;
    private TextView itemPrice;
    private TextView sellerName;
    private TextView sellerPhone;
    private TextView itemStatus;
    private Button cancelRequestButton;
    private YourRequestedItem_RecyclerViewAdapter yourRequestedItem_recyclerViewAdapter;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private CollectionReference buyerCollectionReference = db.collection("Users");
    private CollectionReference requestedItemsCollectionReference = db.collection("RequestedItems");
    private CollectionReference saleItemsCollectionReference=db.collection("SaleItems");
    private CollectionReference acceptedItemCollectionReference=db.collection("AcceptedItems");

    public BuyerRequestedItem_RecyclerViewAdapter(List<SaleItems> saleItemsList, List<RequestedItem>requestedItemList, Context mContext) {
        this.saleItemsList = saleItemsList;
        this.requestedItemList=requestedItemList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BuyerRequestedItem_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_requested_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BuyerRequestedItem_RecyclerViewAdapter.ViewHolder holder, int position) {

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        final ViewHolder newHolder = holder;
        final SaleItems saleItems = saleItemsList.get(position);

        String imageUrl;
        imageUrl = saleItems.getImageUrl();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(holder.image);
        RequestedItem requestedItem=requestedItemList.get(position);
        holder.desc.setText(saleItems.getDesc());
        buyerCollectionReference.whereEqualTo("UserId",requestedItem.getBuyerId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot user:queryDocumentSnapshots){
                            holder.requestedDate.setText(user.get("BuyerRating").toString());
                        }
                    }
                });


        requestedItemsCollectionReference
                .whereEqualTo("itemCode",saleItems.getItemCode())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot item:queryDocumentSnapshots){
//                            buyerId=(String)item.get("buyerId");
                            buyerCollectionReference
                                    .whereEqualTo("UserId",item.get("buyerId"))
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for(QueryDocumentSnapshot buyer:queryDocumentSnapshots){
                                                String name= (String) buyer.get("Name");
                                                holder.buyerName.setText(name);
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept();
            }

            private void accept() {


                requestedItemsCollectionReference
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(QueryDocumentSnapshot document:queryDocumentSnapshots){
                                    if(document.get("itemCode").equals(saleItems.getItemCode())){
                                        requestedItemsCollectionReference.document(document.getId()).update("status","accepted");
                                        Log.d("check5", "onSuccess: Request Accepted");
                                        Toast.makeText(mContext,"Request Accepted",Toast.LENGTH_LONG).show();

                                    }
                                }
                            }
                        });





            }
        });
        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reject();
            }
            private void reject(){
                requestedItemsCollectionReference
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(QueryDocumentSnapshot document:queryDocumentSnapshots){
                                    if(document.get("buyerId").equals(saleItems.getItemCode())){
                                        requestedItemsCollectionReference.document(document.getId()).update("status","rejected");
                                        Toast.makeText(mContext,"Request Rejected",Toast.LENGTH_LONG).show();

                                    }
                                }
                            }
                        });

            }
        });


    }

    @Override
    public int getItemCount() {
        return saleItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView desc;
        TextView buyerName;
        TextView requestedDate;
        Button acceptButton;
        Button rejectButton;
        CardView itemCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.acceptButton= itemView.findViewById(R.id.buyer_requested_accept_btn);
            this.rejectButton=itemView.findViewById(R.id.buyer_requested_reject_btn);
            this.buyerName=itemView.findViewById(R.id.buyer_request_item_buyer_name);
            this.desc=itemView.findViewById(R.id.buyer_request_item_desc);
            this.image=itemView.findViewById(R.id.buyer_imageView);
            this.requestedDate=itemView.findViewById(R.id.buyer_request_item_date);
            this.itemCard=itemView.findViewById(R.id.buyer_request_item_card);

        }
    }
}
