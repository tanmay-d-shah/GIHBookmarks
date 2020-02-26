package com.tds.gihbookmarks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class SellAllActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabItem BookSell,ToolSell,SMSell,OtherSell;
    private PageAdapter_SellActivity pageAdapter_sellActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_all);

        viewPager= findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tabLayout);

        pageAdapter_sellActivity = new PageAdapter_SellActivity(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter_sellActivity);
        tabLayout.setupWithViewPager(viewPager);

        try {
            tabLayout.getTabAt(0).setText("Book Sell");
            tabLayout.getTabAt(1).setText("Tool Sell");
            tabLayout.getTabAt(2).setText("Study Material");
            tabLayout.getTabAt(3).setText("Other Material Sell");

        } catch (Exception e){
            Log.i("Khyati1234",e.toString());
        }


    }
}
