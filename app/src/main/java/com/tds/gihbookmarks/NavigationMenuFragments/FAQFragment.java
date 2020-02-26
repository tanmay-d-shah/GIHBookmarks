package com.tds.gihbookmarks.NavigationMenuFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import com.tds.gihbookmarks.R;

public class FAQFragment extends Fragment {

    ListView faq_list;
    String[] faq_item= {"How to sell book/material ?", "How to buy book/material ?"};

    public FAQFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        faq_list=view.findViewById(R.id.faq_list);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,faq_item);
        faq_list.setAdapter(adapter);
        return view;
    }

}
