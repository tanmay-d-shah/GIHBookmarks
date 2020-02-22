package com.tds.gihbookmarks.HomePageFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.tds.gihbookmarks.R;
import com.tds.gihbookmarks.StaggeredRecyclerViewAdapter;
import com.tds.gihbookmarks.model.SaleItems;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;


public class ToolsFragment extends Fragment {

    private RecyclerView toolsRecyclerView;
    private List<SaleItems> saleItemsList;
    private StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter;
    private View view;
    private int NUM_COLUMNS=2;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private StorageReference storageReference;
    private CollectionReference collectionReference=db.collection("SaleItems");


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;


    public ToolsFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ToolsFragment.
//     */
    // TODO: Rename and change types and number of parameters
//    public static ToolsFragment newInstance(String param1, String param2) {
//        ToolsFragment fragment = new ToolsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_books, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        saleItemsList=new ArrayList<>();
        toolsRecyclerView= (RecyclerView)view.findViewById(R.id.recyclerView);
        //StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter= new StaggeredRecyclerViewAdapter(getContext(),bookList);
        StaggeredGridLayoutManager staggeredGridLayoutManager= new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        //bookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        toolsRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        toolsRecyclerView.setAdapter(staggeredRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot books:task.getResult()){
                                SaleItems items=books.toObject(SaleItems.class);
                                if(items.getItem().equals("Tool") && items.getStatus().equals("Available")){
                                    saleItemsList.add(items);
                                }
//                                items.setBookId(books.getId());


                            }
                            Log.d("Restart", "onComplete: Books Restarted");
                            staggeredRecyclerViewAdapter=new StaggeredRecyclerViewAdapter(getContext(),saleItemsList);
                            toolsRecyclerView.setAdapter(staggeredRecyclerViewAdapter);
                            staggeredRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}
