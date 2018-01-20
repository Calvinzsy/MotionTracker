package com.example.calvin.motiontracker.application.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.calvin.motiontracker.journeyTracking.view.JourneyTrackingFragment;
import com.example.calvin.motiontracker.journeylist.view.JourneyListFragment;

/**
 * Adapter used for tab content {@link android.support.v4.view.ViewPager}. It only contains
 * the two fragments used by our application.
 */
public class ContentPagerAdapter extends FragmentPagerAdapter {

    /**
     * Number of items for the {@link android.support.v4.view.ViewPager}.
     */
    private static final int ITEM_COUNT = 2;

    /**
     * Title for first item.
     */
    private static final String TITLE_JOURNEY_TRACKING = "Tracking";

    /**
     * Title for second item.
     */
    private static final String TITLE_JOURNEY_LIST = "History";

    /**
     * Construct the adapter with {@link FragmentManager}.
     * @param fm The {@link FragmentManager} used to manage the content fragments.
     */
    ContentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new JourneyTrackingFragment();
            default:
                return new JourneyListFragment();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    /**
     * {@inheritDoc}
     */
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
