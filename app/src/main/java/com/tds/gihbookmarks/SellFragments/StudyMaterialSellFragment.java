package com.tds.gihbookmarks.SellFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.tds.gihbookmarks.R;


public class StudyMaterialSellFragment extends Fragment {

    private ImageView imgSM;
    private TextInputEditText title, desc;
    private Button btn_submit;

    public StudyMaterialSellFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_study_material_sell, container, false);
        imgSM = view.findViewById(R.id.imgStudyMaterial);
        title = view.findViewById(R.id.material_desc);
        btn_submit = view.findViewById(R.id.btnSubmit);
        return view;
    }

}
