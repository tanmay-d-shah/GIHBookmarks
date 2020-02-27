package com.tds.gihbookmarks.requestedItemPackage;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
import com.tds.gihbookmarks.BuyerRequestedItem_RecyclerViewAdapter;
import com.tds.gihbookmarks.R;
import com.tds.gihbookmarks.model.RequestedItem;
import com.tds.gihbookmarks.model.SaleItems;

import java.util.ArrayList;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link BuyerRequestedItemsFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link BuyerRequestedItemsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class BuyerRequestedItemsFragment extends Fragment {
    private RecyclerView buyerRequestedRecyclerView;
    final Handler handler = new Handler();
//    private ArrayList<String> mName;

    private List<SaleItems> saleItemsList;
    private int flag = 0;
    private List<RequestedItem> buyerRequestedItemList;

    //    private ArrayList<String> mImageUrls;
    private BuyerRequestedItem_RecyclerViewAdapter buyerRequestedItem_recyclerViewAdapter;
    private View view;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private StorageReference storageReference;
    private CollectionReference SaleItemsCollectionReference = db.collection("SaleItems");
    private CollectionReference requestedItemCollectionReference = db.collection("RequestedItems");
    private CollectionReference buyerCollectionReference = db.collection("Users");
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public BuyerRequestedItemsFragment() {
        // Required empty public constructor


    }


//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment BuyerRequestedItemsFragment.
//     */
    // TODO: Rename and change types and number of parameters
//    public static BuyerRequestedItemsFragment newInstance(String param1, String param2) {
//        BuyerRequestedItemsFragment fragment = new BuyerRequestedItemsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                            buyerRequestedItemList.add(requestedItem);
                            Log.d("Buyer", "onSuccess: "+requestedItem.getItem());


                        }
                        for (int i = 0; i < buyerRequestedItemList.size(); i++) {


                            RequestedItem listRequestedItem = buyerRequestedItemList.get(i);

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
                                buyerRequestedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                buyerRequestedItem_recyclerViewAdapter= new BuyerRequestedItem_RecyclerViewAdapter(saleItemsList, getContext());

                                buyerRequestedRecyclerView.setAdapter(buyerRequestedItem_recyclerViewAdapter);
                                buyerRequestedItem_recyclerViewAdapter.notifyDataSetChanged();


                            }
                        }, 2000);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buyer_requested_items, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        saleItemsList = new ArrayList<>();
        buyerRequestedItemList = new ArrayList<>();

        buyerRequestedRecyclerView = (RecyclerView) view.findViewById(R.id.buyer_requested_recyclerView);
        return view;


    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
