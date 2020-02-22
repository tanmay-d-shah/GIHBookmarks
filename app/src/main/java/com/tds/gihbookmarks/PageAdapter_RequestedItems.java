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
import com.tds.gihbookmarks.requestedItemPackage.BuyerRequestedItemsFragment;
import com.tds.gihbookmarks.requestedItemPackage.YourRequestedItemsFragment;

public class PageAdapter_RequestedItems extends FragmentPagerAdapter {

    private int numOfTabs;

    public PageAdapter_RequestedItems(@NonNull FragmentManager fm,int numOfTabs){
        super(fm,numOfTabs);
        this.numOfTabs=numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = new YourRequestedItemsFragment();
                Log.d("page Adapter", "getItem: YourRequestedItemFragment Returned");
                return fragment;


            case 1:
                fragment = new BuyerRequestedItemsFragment();
                Log.d("page Adapter", "getItem: BuyerRequestedItemFragment Returned");
                return fragment;



            default:
                return fragment;

        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
