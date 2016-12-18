package com.example.trungnguyen.androidwatch;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Stopwatch", StopWatchFragment.class)
                        .add("Alarm Clock", AlarmClockFragment.class)
                        .create());
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager1);
        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(getArguments().getInt(LauchingFragment.PAGE_POSiTION));
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab1);
        viewPagerTab.setViewPager(viewPager);
    }
}
