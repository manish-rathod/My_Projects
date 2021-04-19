package com.example.socialapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static int TAB_COUNT = 4;
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return HomepageFragment.Title;
//            case 1:
//                return FriendsFragment.Title;
//            case 2:
//                return NotificationFragment.Title;
//            case 3:
//                return ProfileFragment.Title;
//        }
        return null;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomepageFragment.newInstance();
            case 1:
                return FriendsFragment.newInstance();
            case 2:
                return NotificationFragment.newInstance();
            case 3:
                return ProfileFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
