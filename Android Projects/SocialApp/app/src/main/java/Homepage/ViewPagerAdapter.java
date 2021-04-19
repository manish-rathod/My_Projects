package Homepage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static int TAB_COUNT = 4;
    List<Fragment> fragments;
    public ViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
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
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
