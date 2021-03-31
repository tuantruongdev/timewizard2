package com.example.testnavbottom;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.testnavbottom.ui.dashboard.DashboardFragment;
import com.example.testnavbottom.ui.home.HomeFragment;
import com.example.testnavbottom.ui.notifications.NotificationsFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return new HomeFragment();
            case 1:
                return new DashboardFragment();
            case 2:
                return new NotificationsFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "TOTAL";
            case 1:
                return "STATS";
            case 2:
                return "FEEDBACK";
            case 3:
                return "MANAGE";
        }
        return null;
    }
}
