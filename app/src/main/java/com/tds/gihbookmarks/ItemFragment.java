package com.tds.gihbookmarks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class ItemFragment extends Fragment {
    private RecyclerView itemRecyclerView;
    private ArrayList<Item> itemData;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private ItemListAdapter adapter;
    private View view;

    public ItemFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_items, container, false);

        adapter = new ItemListAdapter();

        itemRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycleview);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        itemRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        itemRecyclerView.setAdapter(adapter);


        return view;
    }

}

