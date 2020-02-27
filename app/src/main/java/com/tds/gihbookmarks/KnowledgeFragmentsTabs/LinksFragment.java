package com.tds.gihbookmarks.KnowledgeFragmentsTabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tds.gihbookmarks.PageAdapter_Link;
import com.tds.gihbookmarks.R;
import com.tds.gihbookmarks.RecyclerViewAdapterInfo;
import com.tds.gihbookmarks.model.InformativePost;
import com.tds.gihbookmarks.model.LinkPost;

import java.util.ArrayList;
import java.util.List;

public class LinksFragment extends Fragment {

    private PageAdapter_Link pageAdapter_link;
    private View view;
    private RecyclerView recyclerView;
    private List<LinkPost> links;


    public LinksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        links=new ArrayList<>();
        links.add(new LinkPost("Google is big search engine all around the world","www.google.com"));

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_links, container, false);
        recyclerView = view.findViewById(R.id.link_recycler);
        pageAdapter_link = new PageAdapter_Link(getContext(),links);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(pageAdapter_link);
        return view;
    }

}
