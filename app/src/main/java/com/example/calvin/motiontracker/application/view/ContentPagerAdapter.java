package com.example.calvin.motiontracker.application.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.calvin.motiontracker.journeyTracking.view.JourneyTrackingFragment;
import com.example.calvin.motiontracker.journeylist.view.JourneyListFragment;

public class ContentPagerAdapter extends FragmentPagerAdapter {

    private static final int ITEM_COUNT = 2;

    private static final String TITLE_JOURNEY_TRACKING = "Tracking";

    private static final String TITLE_JOURNEY_LIST = "History";

    public ContentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new JourneyTrackingFragment();
            default:
                return new JourneyListFragment();
        }
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return TITLE_JOURNEY_TRACKING;
            default:
                return TITLE_JOURNEY_LIST;
        }
    }
}
