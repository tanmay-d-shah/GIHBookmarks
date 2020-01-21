package com.tds.gihbookmarks.HomePageFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tds.gihbookmarks.R;

import com.tds.gihbookmarks.StaggeredRecyclerViewAdapter;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;


public class BooksFragment extends Fragment {
    private RecyclerView bookRecyclerView;
    private ArrayList<String> mName;
    private ArrayList<String> mImageUrls;
    private StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter;
    private View view;
    private int NUM_COLUMNS=2;

    public BooksFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_books, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        bookRecyclerView= (RecyclerView)view.findViewById(R.id.recyclerView);
        StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter= new StaggeredRecyclerViewAdapter(mName,mImageUrls,getContext());
        StaggeredGridLayoutManager staggeredGridLayoutManager= new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        //bookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        bookRecyclerView.setAdapter(staggeredRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mName= new ArrayList<>();
        mImageUrls=new ArrayList<>();

        //static photos & titles
        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mName.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mName.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mName.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mName.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mName.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mName.add("Frozen Lake");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mName.add("White Sands Desert");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mName.add("Australia");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mName.add("Washington");
    }




}
