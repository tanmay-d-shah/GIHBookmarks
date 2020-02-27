package com.tds.gihbookmarks.KnowledgeFragmentsTabs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tds.gihbookmarks.R;
import com.tds.gihbookmarks.RecyclerViewAdapterInfo;
import com.tds.gihbookmarks.StaggeredRecyclerViewAdapter;
import com.tds.gihbookmarks.model.InformativePost;
import com.tds.gihbookmarks.model.SaleItems;

import java.util.ArrayList;
import java.util.List;


public class InformativePostFragment extends Fragment {

    public InformativePostFragment() {
        // Required empty public constructor
    }

    //private List<SaleItems> saleItemsList;
    //    private ArrayList<String> mImageUrls;
    private RecyclerViewAdapterInfo recyclerViewAdapterInfo;
    private View view;
    private RecyclerView infoRecycler;
    private List<InformativePost> infos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        infos=new ArrayList<>();
        infos.add(new InformativePost("Google Translate Adds Support for 5 New Languages, Including Odia",R.drawable.google));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_informative_post, container, false);
        infoRecycler=view.findViewById(R.id.info_recyclerView);
        recyclerViewAdapterInfo = new RecyclerViewAdapterInfo(getContext(),infos);
        infoRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        infoRecycler.setAdapter(recyclerViewAdapterInfo);



        return view;
    }


}
