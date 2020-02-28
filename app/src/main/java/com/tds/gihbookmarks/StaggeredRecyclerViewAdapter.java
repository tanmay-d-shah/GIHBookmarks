package com.tds.gihbookmarks;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.tds.gihbookmarks.model.Book;
import com.tds.gihbookmarks.model.SaleItems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StaggeredRecyclerViewAdapter
        extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ViewHolder>
        implements Filterable {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private StorageReference storageReference;
    private CollectionReference sellerCollectionReference = db.collection("Users");
    private CollectionReference collectionReference = db.collection("SaleItems");
    private CollectionReference bookcollectionReference = db.collection("Books");
    private CollectionReference toolCollectionReference = db.collection("Tools");


    private static final String TAG = "StaggerRecyclerViewAdapter";
    //    private final ArrayList<String> mImageUrls;
    private List<SaleItems> saleItemsList;
    private List<SaleItems> saleItemsListAll;
    private List<String> bookIDB;
    private List<String> userIDB;
    private List<String> temp2;


    private Context mContext;
    private RecyclerView.ViewHolder holder;
    //private int position;

    private String sellerName;
    private String sellerMobile;
    private String sellerRating;
    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<SaleItems> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(saleItemsListAll);
            } else {
                for (SaleItems book : saleItemsListAll) {
                    if (book.getDesc().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(book);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            saleItemsList.clear();
            saleItemsList.addAll((Collection<? extends SaleItems>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public StaggeredRecyclerViewAdapter(Context mContext, List<SaleItems> saleItemsList) {
        this.saleItemsList = saleItemsList;
        saleItemsListAll = new ArrayList<>(saleItemsList);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return saleItemsList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        final SaleItems saleItem = saleItemsList.get(position);
        Log.d(TAG, "onBindViewHolder: called.");

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);

        String imageUrl;
        imageUrl = saleItem.getImageUrl();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .resize(500,0)
                .into(holder.image);
//        Glide.with(mContext)
//                .load(mImageUrls.get(position))
//                .apply(requestOptions)
//                .into(holder.image);
//
        holder.name.setText(saleItemsList.get(holder.getAdapterPosition()).getDesc());
        Log.d(TAG, "check2 " + holder.name.getText());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (saleItem.getItem().equals("Book")) {
                    bookcollectionReference.document(saleItem.getItemCode())
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            final Book book = documentSnapshot.toObject(Book.class);
                            Log.d("Book Fetched", "onSuccess: " + book.getTitle());

                                        sellerCollectionReference
                                                .whereEqualTo("UserId",saleItem.getSellerId())
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                        for(QueryDocumentSnapshot user:queryDocumentSnapshots){
                                                            sellerName=user.get("Name").toString();
                                                            sellerMobile=user.get("Mobile").toString();
                                                            sellerRating=user.get("SellerRating").toString();

                                                Log.d(TAG, "onSuccess: " + sellerName);
//
                                                            Intent intent=new Intent(mContext,SaleItemDetailActivity.class);
                                                            intent.putExtra("book_parcel",book);
                                                            intent.putExtra("sale_item_parcel",saleItem);
                                                            intent.putExtra("seller_mobile",sellerMobile);
                                                            intent.putExtra("seller_name",sellerName);
                                                            intent.putExtra("seller_rating",sellerRating);

                                                mContext.startActivity(intent);
                                            }

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
//
//                                    sellerCollectionReference
//                                            .document(saleItem.getSellerId()).get()
//                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                                @Override
//                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                                    String sellerNumber= (String) documentSnapshot.get("Mobile");
//                                                    String sellerName=(String)documentSnapshot.get("Name");
//                                                    Log.d(TAG, "onSuccess: "+sellerName);
//
//                                                    Intent intent=new Intent(mContext,SaleItemDetailActivity.class);
//                                                    intent.putExtra("book_parcel",book);
//                                                    intent.putExtra("sale_item_parcel",saleItem);
//                                                    intent.putExtra("seller_mobile",sellerNumber);
//                                                    intent.putExtra("seller_name",sellerName);
//
//                                                    mContext.startActivity(intent);
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//
//                                                }
//                                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Failed to fetch book", "onFailure: ");
                        }
                    });
                }
            }
        });
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.image_widget);
            this.name = itemView.findViewById(R.id.name_widget);
        }
    }
}
