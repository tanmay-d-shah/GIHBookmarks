package com.tds.gihbookmarks.NavigationMenuFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.tds.gihbookmarks.R;
import com.tds.gihbookmarks.faq_1Activity;
import com.tds.gihbookmarks.faq_que2Activity;

public class FAQFragment extends Fragment {

    ListView faq_list;
    String[] faq_item = {"How to sell book/material ?", "How to buy book/material ?","What conditions my books or Tools be in to sell? "};

    public FAQFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        faq_list = view.findViewById(R.id.faq_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, faq_item);
        faq_list.setAdapter(adapter);

        faq_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent Que1intent=new Intent(getContext(), faq_1Activity.class);
                        startActivity(Que1intent);
                        break;

                    case 1:
                        Intent Que2intent=new Intent(getContext(), faq_que2Activity.class);
                        startActivity(Que2intent);
                        break;
                }
            }
        });
        return view;
    }


}
