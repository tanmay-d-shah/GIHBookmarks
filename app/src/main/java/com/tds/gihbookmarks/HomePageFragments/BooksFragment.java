package com.tds.gihbookmarks.HomePageFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.tds.gihbookmarks.MainActivity;
import com.tds.gihbookmarks.R;

import com.tds.gihbookmarks.StaggeredRecyclerViewAdapter;
import com.tds.gihbookmarks.model.Book;
import com.tds.gihbookmarks.model.SaleItems;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;


public class BooksFragment extends Fragment {
    private RecyclerView bookRecyclerView;
//    private ArrayList<String> mName;

    private List<SaleItems> saleItemsList;
//    private ArrayList<String> mImageUrls;
    private StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter;
    private View view;
    private int NUM_COLUMNS=2;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private StorageReference storageReference;
    private CollectionReference collectionReference=db.collection("SaleItems");

    private ProgressBar progressBar;


    public BooksFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_books, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        saleItemsList=new ArrayList<>();
        bookRecyclerView= (RecyclerView)view.findViewById(R.id.recyclerView);


        //StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter= new StaggeredRecyclerViewAdapter(getContext(),bookList);
        StaggeredGridLayoutManager staggeredGridLayoutManager= new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);


        //bookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookRecyclerView.setLayoutManager(staggeredGridLayoutManager);



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();



//        collectionReference
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for(QueryDocumentSnapshot books:task.getResult()){
//                                Book book=books.toObject(Book.class);
//                                book.setBookId(books.getId());
//                                bookList.add(book);
//
//                            }
//                            staggeredRecyclerViewAdapter=new StaggeredRecyclerViewAdapter(getContext(),bookList);
//                            bookRecyclerView.setAdapter(staggeredRecyclerViewAdapter);
//                            staggeredRecyclerViewAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });



//        mName= new ArrayList<>();
//        mImageUrls=new ArrayList<>();
//
//        //static photos & titles
//        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
//        mName.add("Havasu Falls");
//
//        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
//        mName.add("Trondheim");
//
//        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
//        mName.add("Portugal");
//
//        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
//        mName.add("Rocky Mountain National Park");
//
//
//        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
//        mName.add("Mahahual");
//
//        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
//        mName.add("Frozen Lake");
//
//
//        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
//        mName.add("White Sands Desert");
//
//        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
//        mName.add("Australia");
//
//        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
//        mName.add("Washington");
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
                            Log.d("hi", "onComplete: Hi");
                            staggeredRecyclerViewAdapter=new StaggeredRecyclerViewAdapter(getContext(),saleItemsList);

                            bookRecyclerView.setAdapter(staggeredRecyclerViewAdapter);
                            staggeredRecyclerViewAdapter.notifyDataSetChanged();

                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
