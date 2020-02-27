package com.tds.gihbookmarks;


import com.tds.gihbookmarks.HomePageFragments.OtherFragment;
import com.tds.gihbookmarks.SellFragments.BookSellFragment;
import com.tds.gihbookmarks.SellFragments.StudyMaterialSellFragment;
import com.tds.gihbookmarks.SellFragments.ToolSellFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter_SellActivity extends FragmentPagerAdapter {

    private int numOfTabs;

    public PageAdapter_SellActivity(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs=numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = new BookSellFragment();
                return fragment;

            case 1:
                fragment = new ToolSellFragment();
                return fragment;

            case 2:
                fragment = new StudyMaterialSellFragment();
                return fragment;

            case 3:
                fragment = new OtherFragment();
                return fragment;

            default:
                return fragment;

        }
    }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
