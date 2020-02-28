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


//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link StudyMaterialFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link StudyMaterialFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class StudyMaterialFragment extends Fragment {

    private RecyclerView studyMaterialRecyclerView;
    private List<SaleItems> saleItemsList;
    private StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter;
    private View view;
    private int NUM_COLUMNS = 2;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("SaleItems");

    public StudyMaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_books, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        saleItemsList = new ArrayList<>();
        studyMaterialRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter= new StaggeredRecyclerViewAdapter(getContext(),bookList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        //bookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        studyMaterialRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        studyMaterialRecyclerView.setAdapter(staggeredRecyclerViewAdapter);

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
                if (staggeredRecyclerViewAdapter != null) {
                    staggeredRecyclerViewAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onStart() {
        super.onStart();
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot books : task.getResult()) {
                        SaleItems items = books.toObject(SaleItems.class);
                        if (items.getItem().equals("Tool") && items.getStatus().equals("Available")) {
                            saleItemsList.add(items);
                        }
//                                items.setBookId(books.getId());
                    }
                    Log.d("Restart", "onComplete: Books Restarted");
                    staggeredRecyclerViewAdapter = new StaggeredRecyclerViewAdapter(getContext(), saleItemsList);
                    studyMaterialRecyclerView.setAdapter(staggeredRecyclerViewAdapter);
                    staggeredRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
