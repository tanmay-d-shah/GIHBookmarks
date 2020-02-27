package com.tds.gihbookmarks.requestedItemPackage;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.tds.gihbookmarks.R;
import com.tds.gihbookmarks.YourRequestedItem_RecyclerViewAdapter;
import com.tds.gihbookmarks.model.RequetedItem;
import com.tds.gihbookmarks.model.SaleItems;

import java.util.ArrayList;
import java.util.List;

public class YourRequestedItemsFragment extends Fragment {

    private RecyclerView requestedRecyclerView;
    final Handler handler = new Handler();
//    private ArrayList<String> mName;

    private List<SaleItems> saleItemsList;
    private int flag = 0;
    private List<RequetedItem> requestedItemList;

    //    private ArrayList<String> mImageUrls;
    private YourRequestedItem_RecyclerViewAdapter yourRequestedItem_recyclerViewAdapter;
    private View view;
//    private int NUM_COLUMNS=2;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private StorageReference storageReference;
    private CollectionReference SaleItemsCollectionReference = db.collection("SaleItems");
    private CollectionReference requestedItemCollectionReference = db.collection("RequestedItems");
    private CollectionReference sellerCollectionReference = db.collection("Users");

    public YourRequestedItemsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_your_requested_items, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        saleItemsList = new ArrayList<>();
        requestedItemList = new ArrayList<>();

        requestedRecyclerView = (RecyclerView) view.findViewById(R.id.requested_item_recyclerView);
        //StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter= new StaggeredRecyclerViewAdapter(getContext(),bookList);
//        StaggeredGridLayoutManager staggeredGridLayoutManager= new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        //bookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        bookRecyclerView.setLayoutManager(staggeredGridLayoutManager);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        requestedItemCollectionReference
                .whereEqualTo("buyerId", user.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot requestedItems : queryDocumentSnapshots) {

                            RequetedItem requestedItem = requestedItems.toObject(RequetedItem.class);
                            requestedItemList.add(requestedItem);


                        }


                        for (int i = 0; i < requestedItemList.size(); i++) {


                            RequetedItem listRequestedItem = requestedItemList.get(i);

                            SaleItemsCollectionReference
                                    .whereEqualTo("itemCode", listRequestedItem.getItemCode())
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                            Log.d("Hi", "onSuccess: item returned");
                                            for (QueryDocumentSnapshot saleItems : queryDocumentSnapshots) {
                                                SaleItems item = saleItems.toObject(SaleItems.class);
                                                saleItemsList.add(item);

                                            }


                                        }

                                    });
//


                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                requestedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                yourRequestedItem_recyclerViewAdapter = new YourRequestedItem_RecyclerViewAdapter(saleItemsList, getContext());

                                requestedRecyclerView.setAdapter(yourRequestedItem_recyclerViewAdapter);
                                yourRequestedItem_recyclerViewAdapter.notifyDataSetChanged();


                            }
                        }, 2000);


                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


    }


    @Override
    public void onStart() {
        super.onStart();


//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for(QueryDocumentSnapshot books:task.getResult()){
//                                SaleItems items=books.toObject(SaleItems.class);
//                                if(items.getItem().equals("Book") && items.getStatus().equals("Available")){
//                                    saleItemsList.add(items);
//                                }
////                                items.setBookId(books.getId());
//
//
//                            }
//                            Log.d("Restart", "onComplete: Books Restarted");

//                        }
//                    }
//                });
    }
}
