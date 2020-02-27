package com.tds.gihbookmarks.NavigationMenuFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.tds.gihbookmarks.PageAdapter_HomePage;
import com.tds.gihbookmarks.PageAdapter_KnowledgeFragment;
import com.tds.gihbookmarks.R;

public class KnowledgeFragment extends Fragment {


    private TabLayout KnowledgetabLayout;
    private TabItem info_notes,links,e_book,project;
    private ViewPager viewPager_knowledge;
    private PageAdapter_KnowledgeFragment pageAdapter;

    public KnowledgeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_knowledge, container, false);
        KnowledgetabLayout = view.findViewById(R.id.tabLayout_knowledge);
        e_book = view.findViewById(R.id.e_book_pdf);
        info_notes = view.findViewById(R.id.info_posts);
        links = view.findViewById(R.id.link);
        project = view.findViewById(R.id.project_hiring);

        viewPager_knowledge = view.findViewById(R.id.KnowledgeViewpager);

        pageAdapter = new PageAdapter_KnowledgeFragment(getChildFragmentManager(), KnowledgetabLayout.getTabCount());
        viewPager_knowledge.setAdapter(pageAdapter);
        KnowledgetabLayout.setupWithViewPager(viewPager_knowledge);

        try {
            KnowledgetabLayout.getTabAt(0).setText("Informative Posts");
            KnowledgetabLayout.getTabAt(1).setText("Links");
            KnowledgetabLayout.getTabAt(2).setText("E-Books(PDF)");
            KnowledgetabLayout.getTabAt(3).setText("Project Hiring");

        } catch (Exception e) {
            Log.i("Khyati1234", e.toString());
        }

        return view;
    }

}
