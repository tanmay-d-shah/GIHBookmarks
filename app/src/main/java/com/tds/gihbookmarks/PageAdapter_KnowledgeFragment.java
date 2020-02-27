package com.tds.gihbookmarks;

import android.util.Log;

import com.tds.gihbookmarks.HomePageFragments.BooksFragment;
import com.tds.gihbookmarks.HomePageFragments.OtherFragment;
import com.tds.gihbookmarks.HomePageFragments.StudyMaterialFragment;
import com.tds.gihbookmarks.HomePageFragments.ToolsFragment;
import com.tds.gihbookmarks.KnowledgeFragmentsTabs.EBooksFragment;
import com.tds.gihbookmarks.KnowledgeFragmentsTabs.InformativePostFragment;
import com.tds.gihbookmarks.KnowledgeFragmentsTabs.LinksFragment;
import com.tds.gihbookmarks.KnowledgeFragmentsTabs.ProjectHiringFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

public class PageAdapter_KnowledgeFragment extends FragmentPagerAdapter {
    private  int numoftabs;

    public PageAdapter_KnowledgeFragment(@NonNull FragmentManager fm, int numOfTabs){
        super(fm,numOfTabs);
        this.numoftabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        Log.d("HomePage", "getItem: HomePAge" + position);
        switch (position) {
            case 0:
                fragment = new InformativePostFragment();
                return fragment;

            case 1:
                fragment = new LinksFragment();
                return fragment;

            case 2:
                fragment = new EBooksFragment();
                return fragment;

            case 3:
                fragment = new ProjectHiringFragment();
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
