package com.tds.gihbookmarks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.tds.gihbookmarks.model.SaleItems;

import java.util.List;

public class AcceptedRequestedItem_RecyclerViewAdapter extends RecyclerView.Adapter<AcceptedRequestedItem_RecyclerViewAdapter.ViewHolder> {

    private List<SaleItems> saleItemsList;
    private Context mContext;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;


    private YourRequestedItem_RecyclerViewAdapter yourRequestedItem_recyclerViewAdapter;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private CollectionReference buyerCollectionReference = db.collection("Users");
    private CollectionReference requestedItemsCollectionReference = db.collection("RequestedItems");
    private CollectionReference saleItemsCollectionReference=db.collection("SaleItems");
    private CollectionReference acceptedItemCollectionReference=db.collection("AcceptedItems");

    public AcceptedRequestedItem_RecyclerViewAdapter(List<SaleItems> saleItemsList, Context mContext) {
        this.saleItemsList = saleItemsList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public AcceptedRequestedItem_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accepted_items, parent, false);
        return new AcceptedRequestedItem_RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AcceptedRequestedItem_RecyclerViewAdapter.ViewHolder holder, int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        final AcceptedRequestedItem_RecyclerViewAdapter.ViewHolder newHolder = holder;
        final SaleItems saleItems = saleItemsList.get(position);

        String imageUrl;
        imageUrl = saleItems.getImageUrl();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(holder.image);

        holder.desc.setText(saleItems.getDesc());
        holder.requestedDate.setText(saleItems.getDateAdded().toString());

        requestedItemsCollectionReference
                .whereEqualTo("status","accepted")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot item:queryDocumentSnapshots){
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

        holder.deliveredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delivered();
            }

            private void delivered() {



            }
        });
        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
            private void cancel(){

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
        Button deliveredButton;
        Button cancelButton;
        CardView itemCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.deliveredButton= itemView.findViewById(R.id.delivered_button);
            this.cancelButton=itemView.findViewById(R.id.cancel_button);
            this.buyerName=itemView.findViewById(R.id.buyer_request_item_buyer_name);
            this.desc=itemView.findViewById(R.id.buyer_request_item_desc);
            this.image=itemView.findViewById(R.id.imageView);
            this.requestedDate=itemView.findViewById(R.id.buyer_request_item_date);
            this.itemCard=itemView.findViewById(R.id.buyer_request_item_card);
        }
    }
}
