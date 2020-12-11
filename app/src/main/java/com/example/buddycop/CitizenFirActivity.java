package com.example.buddycop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class CitizenFirActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private SectionPagerAdapter mSectionPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_fir);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager= findViewById(R.id.main_tab_pager);
        mSectionPagerAdapter=new SectionPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionPagerAdapter);

        mTabLayout= findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
