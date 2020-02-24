package com.tds.gihbookmarks.SellFragments;

import android.R.layout;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.tds.gihbookmarks.R;

import java.util.ArrayList;
import java.util.List;

public class ToolSellFragment extends Fragment {

    private Spinner spinner;
    //private View view;

    public ToolSellFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tool_sell, container, false);
        /*spinner=view.findViewById(R.id.toolList);


        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(view.getContext(),R.array.tools, layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemClickListener(this);*/
        return view;
    }

    /*@Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(),"clicked",Toast.LENGTH_SHORT).show();
    }*/
}
