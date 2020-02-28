package com.tds.gihbookmarks.requestedItemPackage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.tds.gihbookmarks.AcceptedRequestedItem_RecyclerViewAdapter;
import com.tds.gihbookmarks.BuyerRequestedItem_RecyclerViewAdapter;
import com.tds.gihbookmarks.R;
import com.tds.gihbookmarks.model.RequestedItem;
import com.tds.gihbookmarks.model.SaleItems;

import java.util.ArrayList;
import java.util.List;

public class AcceptedItemsFragment extends Fragment {
    private RecyclerView acceptedItemsRequestedRecyclerView;
    final Handler handler = new Handler();
//    private ArrayList<String> mName;

    private List<SaleItems> saleItemsList;
    private int flag = 0;
    private List<RequestedItem> acceptedRequestedItemList;

    //    private ArrayList<String> mImageUrls;
    private AcceptedRequestedItem_RecyclerViewAdapter acceptedRequestedItem_recyclerViewAdapter;
    private View view;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private StorageReference storageReference;
    private CollectionReference SaleItemsCollectionReference = db.collection("SaleItems");
    private CollectionReference requestedItemCollectionReference = db.collection("RequestedItems");
    private CollectionReference buyerCollectionReference = db.collection("Users");


    public AcceptedItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        firebaseAuth = FirebaseAuth.getInstance();
//        user = firebaseAuth.getCurrentUser();
//
//        requestedItemCollectionReference
//                .whereEqualTo("sellerId",user.getUid())
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        Log.d("Buyer", "onSuccess: Seller Returned");
//
//                        for (QueryDocumentSnapshot requestedItems : queryDocumentSnapshots) {
//
//                            RequestedItem requestedItem = requestedItems.toObject(RequestedItem.class);
//                            if(requestedItem.getStatus().equals("accepted")) {
//                                acceptedRequestedItemList.add(requestedItem);
//                            }
//                            Log.d("Buyer", "onSuccess: "+requestedItem.getItem());
//
//
//                        }
//                        for (int i = 0; i < acceptedRequestedItemList.size(); i++) {
//
//
//                            RequestedItem listRequestedItem = acceptedRequestedItemList.get(i);
//
//                            SaleItemsCollectionReference
//                                    .whereEqualTo("itemCode", listRequestedItem.getItemCode())
//                                    .get()
//                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                                        @Override
//                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                                            Log.d("buyer", "onSuccess: item returned");
//                                            for (QueryDocumentSnapshot saleItems : queryDocumentSnapshots) {
//                                                SaleItems item = saleItems.toObject(SaleItems.class);
//                                                saleItemsList.add(item);
//
//                                            }
//
//
//                                        }
//
//                                    });
////
//
//
//                        }
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.d("buyer", "onSuccess: setting adapter");
//                                acceptedItemsRequestedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                                acceptedRequestedItem_recyclerViewAdapter= new AcceptedRequestedItem_RecyclerViewAdapter(saleItemsList, getContext());
//
//                                acceptedItemsRequestedRecyclerView.setAdapter(acceptedRequestedItem_recyclerViewAdapter);
//                                acceptedRequestedItem_recyclerViewAdapter.notifyDataSetChanged();
//
//
//                            }
//                        }, 2000);
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_received_items, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        saleItemsList = new ArrayList<>();
        acceptedRequestedItemList = new ArrayList<>();

        acceptedItemsRequestedRecyclerView = (RecyclerView) view.findViewById(R.id.received_items_recyclerview);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        requestedItemCollectionReference
                .whereEqualTo("sellerId",user.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("Buyer", "onSuccess: Seller Returned");

                        for (QueryDocumentSnapshot requestedItems : queryDocumentSnapshots) {

                            RequestedItem requestedItem = requestedItems.toObject(RequestedItem.class);
                            if(requestedItem.getStatus().equals("accepted")) {
                                acceptedRequestedItemList.add(requestedItem);
                            }
                            Log.d("Buyer", "onSuccess: "+requestedItem.getItem());


                        }
                        for (int i = 0; i < acceptedRequestedItemList.size(); i++) {


                            RequestedItem listRequestedItem = acceptedRequestedItemList.get(i);

                            SaleItemsCollectionReference
                                    .whereEqualTo("itemCode", listRequestedItem.getItemCode())
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                            Log.d("buyer", "onSuccess: item returned");
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
                                Log.d("buyer", "onSuccess: setting adapter");
                                acceptedItemsRequestedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                acceptedRequestedItem_recyclerViewAdapter= new AcceptedRequestedItem_RecyclerViewAdapter(saleItemsList,acceptedRequestedItemList, getContext());

                                acceptedItemsRequestedRecyclerView.setAdapter(acceptedRequestedItem_recyclerViewAdapter);
                                acceptedRequestedItem_recyclerViewAdapter.notifyDataSetChanged();


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
}
