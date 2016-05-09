package com.fsdeveloper.jobmanager.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.fragments.JobListFragment;

/**
 *
 * @author Created by Douglas Rafael on 08/05/2016.
 * @version 1.0
 */
public class TabsAdapter extends FragmentPagerAdapter {
    private String tabTitles[];

    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        tabTitles = context.getResources().getStringArray(R.array.tabs);
    }

    /**
     * Return fragment with respect to Position .
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new JobListFragment();
            case 1:
                return new JobListFragment();
            case 2:
                return new JobListFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
