package com.tds.gihbookmarks.requestedItemPackage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tds.gihbookmarks.R;

public class ReceivedItemsFragment extends Fragment {


    public ReceivedItemsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_received_items,container,false);
        ViewPager viewpager = view.findViewById(R.id.viewpager);
    }


}
