package com.tds.gihbookmarks.NavigationMenuFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.tds.gihbookmarks.ItemFragment;
import com.tds.gihbookmarks.PageAdapter_HomePage;
import com.tds.gihbookmarks.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private TabLayout tabLayout;
    private TabItem books, tools, studymaterial, other;
    private ViewPager viewPager;
    private PageAdapter_HomePage pageAdapter;
    private View view;

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton floatingActionButton= view.findViewById(R.id.floatBtn);
        floatingActionButton.setOnClickListener(this);

        tabLayout = view.findViewById(R.id.tabLayout);
        books = view.findViewById(R.id.books);
        tools = view.findViewById(R.id.tools);
        studymaterial = view.findViewById(R.id.studyMaterial);
        other = view.findViewById(R.id.other);

        viewPager = view.findViewById(R.id.viewpager);

        pageAdapter = new PageAdapter_HomePage(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        try {
            tabLayout.getTabAt(0).setText("Books");
            tabLayout.getTabAt(1).setText("Tools");
            tabLayout.getTabAt(2).setText("Study Material");
            tabLayout.getTabAt(3).setText("Other");

        } catch (Exception e){
            Log.i("Khyati1234",e.toString());
        }


        /*tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0) {
                    pageAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pageAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 2) {
                    pageAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 3) {
                    pageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));*/


        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = new ItemFragment();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.placeHolder,fragment).commit();

    }
}


