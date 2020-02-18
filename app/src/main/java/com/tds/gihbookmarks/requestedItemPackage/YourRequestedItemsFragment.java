package com.tds.gihbookmarks.requestedItemPackage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

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
import com.tds.gihbookmarks.YourRequestedItem_RecyclerViewAdapter;
import com.tds.gihbookmarks.model.SaleItems;

import java.util.ArrayList;
import java.util.List;

public class YourRequestedItemsFragment extends Fragment {

    private RecyclerView requestedRecyclerView;
//    private ArrayList<String> mName;

    private List<SaleItems> saleItemsList;
    //    private ArrayList<String> mImageUrls;
    private YourRequestedItem_RecyclerViewAdapter yourRequestedItem_recyclerViewAdapter;
    private View view;
//    private int NUM_COLUMNS=2;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private StorageReference storageReference;
    private CollectionReference collectionReference=db.collection("SaleItems");

    public YourRequestedItemsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_books, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        saleItemsList=new ArrayList<>();
        requestedRecyclerView= (RecyclerView)view.findViewById(R.id.recyclerView);
        //StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter= new StaggeredRecyclerViewAdapter(getContext(),bookList);
//        StaggeredGridLayoutManager staggeredGridLayoutManager= new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        //bookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        bookRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        requestedRecyclerView.setAdapter(yourRequestedItem_recyclerViewAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
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
                                if(items.getItem().equals("Book") && items.getStatus().equals("Available")){
                                    saleItemsList.add(items);
                                }
//                                items.setBookId(books.getId());


                            }
                            Log.d("Restart", "onComplete: Books Restarted");
                            yourRequestedItem_recyclerViewAdapter=new YourRequestedItem_RecyclerViewAdapter(saleItemsList, getContext());
                            requestedRecyclerView.setAdapter(yourRequestedItem_recyclerViewAdapter);
                            yourRequestedItem_recyclerViewAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
