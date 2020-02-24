package com.tds.gihbookmarks.SellFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tds.gihbookmarks.R;


public class StudyMaterialSellFragment extends Fragment {

    public StudyMaterialSellFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_study_material_sell, container, false);
        return view;
    }

}
