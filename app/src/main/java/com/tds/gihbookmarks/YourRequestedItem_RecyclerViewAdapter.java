package com.tds.gihbookmarks;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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

public class  YourRequestedItem_RecyclerViewAdapter extends RecyclerView.Adapter<YourRequestedItem_RecyclerViewAdapter.ViewHolder> {

    private List<SaleItems> saleItemsList;
    private Context mContext;
    private List<RequestedItem>requestedItemList;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

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
    private CollectionReference sellerCollectionReference = db.collection("Users");
    private CollectionReference requestedItemsCollectionReference = db.collection("RequestedItems");

    public YourRequestedItem_RecyclerViewAdapter(List<SaleItems> saleItemsList, List<RequestedItem>requestedItemList,Context mContext) {

        this.saleItemsList = saleItemsList;
        this.mContext = mContext;
        this.requestedItemList=requestedItemList;
        Log.d("Tanmay", "YourRequestedItem_RecyclerViewAdapter: " + saleItemsList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        final ViewHolder newHolder = holder;
        final SaleItems saleItems = saleItemsList.get(position);
        RequestedItem requestedItem=requestedItemList.get(position);
        Log.d("Yo", "hi how are you.");

        //RequestOptions requestOptions= new RequestOptions().placeholder(R.drawable.ic_launcher_background);


        String imageUrl;
        imageUrl = saleItems.getImageUrl();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(holder.image);
        holder.itemDesc.setText(saleItems.getDesc());
        holder.itemName.setText(saleItems.getItem());
        holder.itemStatus.setText(saleItems.getStatus());

//        if(requestedItem.getStatus().equals("requested")){
//            holder.review.setEnabled(false);
//            holder.returnBook.setEnabled(false);


//        }









        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(mContext);
                LayoutInflater inflater =LayoutInflater.from(mContext);
                View diloglayout=inflater.inflate(R.layout.alert_dialog_with_ratingbar,null);
                final RatingBar ratingbar=diloglayout.findViewById(R.id.ratingbar);
                builder.setView(diloglayout);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        float rates =ratingbar.getRating();
                        sellerCollectionReference
                                .whereEqualTo("UserId",saleItems.getSellerId())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for(QueryDocumentSnapshot user:queryDocumentSnapshots){

                                        }
                                    }
                                });
                        Toast.makeText(mContext, "Rating is"+ratingbar.getRating(), Toast.LENGTH_SHORT).show();

                    }
                });
                android.app.AlertDialog alert = builder.create();
                alert.show();

            }


        });



        holder.returnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Log.d("check1", "onBindViewHolder: " + holder.itemName.getText());
        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePopUpDialog(newHolder);
            }
        });
    }


    private void CreatePopUpDialog(final ViewHolder newholder) {
        builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.requested_item_popup, null);
        sellerName = view.findViewById(R.id.pop_item_seller);
        sellerPhone = view.findViewById(R.id.pop_item_seller_contact);
        itemDesc = view.findViewById(R.id.pop_item_desc);
        itemPrice = view.findViewById(R.id.pop_item_price);
        itemStatus = view.findViewById(R.id.pop_item_status);
        cancelRequestButton = view.findViewById(R.id.pop_item_cancel_request);

        final SaleItems saleItem = saleItemsList.get(newholder.getAdapterPosition());
        final int newposition = newholder.getAdapterPosition();

        itemDesc.setText(saleItem.getDesc());
        itemPrice.setText(saleItem.getPrice());
        itemStatus.setText(saleItem.getStatus());

        sellerCollectionReference
                .whereEqualTo("UserId", saleItem.getSellerId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot user : queryDocumentSnapshots) {
                            sellerPhone.setText(user.getString("Mobile"));
                            sellerName.setText(user.getString("Name"));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


        cancelRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestedItemsCollectionReference
                        .whereEqualTo("itemCode", saleItem.getItemCode())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot requestedItem : queryDocumentSnapshots) {
                                    if (requestedItem.get("buyerId").equals(user.getUid())) {


                                        requestedItemsCollectionReference.document(requestedItem.getId()).delete();
                                        Toast.makeText(mContext, "Request Cancelled", Toast.LENGTH_LONG).show();

                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


            }
        });
        builder.setView(view);
        alertDialog = builder.create();
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
        Button review;
        Button returnBook;
        TextView itemStatus;
        CardView itemCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemCard = itemView.findViewById(R.id.request_item_card);
            this.image = itemView.findViewById(R.id.imageView);
            this.itemName = itemView.findViewById(R.id.request_item_item);
            this.itemDesc = itemView.findViewById(R.id.request_item_desc);
            this.itemStatus = itemView.findViewById(R.id.request_item_status);
            this.returnBook=itemView.findViewById(R.id.your_requested_return);
            this.review=itemView.findViewById(R.id.your_requested_review);


        }
    }
}
