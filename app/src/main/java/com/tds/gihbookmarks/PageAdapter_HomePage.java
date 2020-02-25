package com.tds.gihbookmarks;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.tds.gihbookmarks.HomePageFragments.BooksFragment;
import com.tds.gihbookmarks.HomePageFragments.OtherFragment;
import com.tds.gihbookmarks.HomePageFragments.StudyMaterialFragment;
import com.tds.gihbookmarks.HomePageFragments.ToolsFragment;

public class PageAdapter_HomePage extends FragmentPagerAdapter {

    private int numoftabs;




    public PageAdapter_HomePage(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm, numOfTabs);
        this.numoftabs = numOfTabs;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        Log.d("HomePage", "getItem: HomePAge"+position);
        switch (position) {
            case 0:
                fragment = new BooksFragment();
                return fragment;

            case 1:
                fragment = new ToolsFragment();
                return fragment;

            case 2:
                fragment = new StudyMaterialFragment();
                return fragment;

            case 3:
                fragment = new OtherFragment();
                return fragment;

            default:
                return fragment;

        }


    }

    @Override
    public int getCount() {
        return numoftabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
