package com.tds.gihbookmarks;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.tds.gihbookmarks.model.Book;
import com.tds.gihbookmarks.model.SaleItems;
import com.tds.gihbookmarks.model.Tools;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ViewHolder> {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private StorageReference storageReference;
    private CollectionReference collectionReference=db.collection("SaleItems");
    private CollectionReference bookcollectionReference=db.collection("Books");

    private static final String TAG = "StaggerRecyclerViewAdapter";
//    private final ArrayList<String> mImageUrls;
    private List<SaleItems> saleItemsList;

//    private ArrayList<String> mName= new ArrayList<>();
//    private ArrayList<String> mImage= new ArrayList<>();
    private Context mContext;
    private RecyclerView.ViewHolder holder;
    //private int position;


    public StaggeredRecyclerViewAdapter(Context mContext, List<SaleItems> saleItemsList) {
        this.saleItemsList=saleItemsList;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item,parent,false);

        return new ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        final SaleItems saleItem=saleItemsList.get(position);
        Log.d(TAG, "onBindViewHolder: called.");

        RequestOptions requestOptions= new RequestOptions().placeholder(R.drawable.ic_launcher_background);



        String imageUrl;
        imageUrl=saleItem.getImageUrl();
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

                if(saleItem.getItem().equals("Book")) {
                    bookcollectionReference
                            .document(saleItem.getItemCode()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Book book = documentSnapshot.toObject(Book.class);
                                    Log.d("Book Fetched", "onSuccess: " + book.getTitle());
                                    Intent intent=new Intent(mContext,SaleItemDetailActivity.class);
                                    intent.putExtra("parcel_data",book);
                                    mContext.startActivity(intent);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
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
    public int getItemCount() {

        return saleItemsList.size();
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
