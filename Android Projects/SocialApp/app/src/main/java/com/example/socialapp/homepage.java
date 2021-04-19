package com.example.socialapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class homepage extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    HomepageFragment HomepageFragment = new HomepageFragment();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Log.d("TAG", "onCreate: "+getSupportFragmentManager());

//        fragmentManager= getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.addToBackStack(null).add(R.id.mainScreen, HomepageFragment);
//        fragmentTransaction.commit();

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        setSupportActionBar(toolbar);
        setViewPager();
        setTabIcons();
    }

    private void setViewPager(){
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setTabIcons(){
        tabLayout.getTabAt(0).setIcon(R.drawable.home_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.add_friend_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.notifications_icon);
        tabLayout.getTabAt(3).setIcon(R.drawable.profile_icon);
    }
}
