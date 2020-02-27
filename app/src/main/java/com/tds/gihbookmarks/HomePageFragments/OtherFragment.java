package com.tds.gihbookmarks.HomePageFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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
import com.tds.gihbookmarks.model.SaleItems;

import java.util.ArrayList;
import java.util.List;


public class OtherFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    private RecyclerView bookRecyclerView;
//    private ArrayList<String> mName;

    private List<SaleItems> saleItemsList;
    //    private ArrayList<String> mImageUrls;
    private StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter;
    private int NUM_COLUMNS = 2;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("SaleItems");


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public OtherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtherFragment newInstance(String param1, String param2) {
        OtherFragment fragment = new OtherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot books : task.getResult()) {
                        SaleItems items = books.toObject(SaleItems.class);
                        if (items.getItem().equals("Book") && items.getStatus().equals("Available")) {
                            saleItemsList.add(items);
                        }
//                                items.setBookId(books.getId());
                    }
                    Log.d("Restart", "onComplete: Books Restarted");
                    Log.d("hi", "onComplete: Hi");
                    staggeredRecyclerViewAdapter = new StaggeredRecyclerViewAdapter(getContext(), saleItemsList);

                    bookRecyclerView.setAdapter(staggeredRecyclerViewAdapter);
                    staggeredRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_other, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        saleItemsList = new ArrayList<>();
        bookRecyclerView = (RecyclerView) view.findViewById(R.id.rentrecyclerView);
        //StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter= new StaggeredRecyclerViewAdapter(getContext(),bookList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        //bookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        bookRecyclerView.setAdapter(staggeredRecyclerViewAdapter);

        setHasOptionsMenu(true);

        return view;
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(null);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                queryFor="books";
                staggeredRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }

        });

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
