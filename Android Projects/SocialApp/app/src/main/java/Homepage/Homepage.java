package Homepage;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import FriendRequest.FriendRequestFragment;
import Notifications.NotificationFragment;
import Profile.ProfileFragment;
import com.example.socialapp.R;
import Homepage.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class Homepage extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Fragment[] fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Log.d("TAG", "onCreate: "+getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tab);
        setViewPager();
        setTabIcons();
    }

    private void setViewPager(){
        viewPager = (ViewPager) findViewById(R.id.pager);
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(HomepageFragment.newInstance());
        fragments.add(FriendRequestFragment.newInstance());
        fragments.add(NotificationFragment.newInstance());
        fragments.add(ProfileFragment.newInstance());
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
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
