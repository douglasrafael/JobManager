package com.fsdeveloper.jobmanager.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.bean.Job;
import com.fsdeveloper.jobmanager.fragments.BalanceFragment;
import com.fsdeveloper.jobmanager.fragments.ClientListFragment;
import com.fsdeveloper.jobmanager.fragments.JobListFragment;

import java.util.List;

/**
 *
 * @author Created by Douglas Rafael on 08/05/2016.
 * @version 1.0
 */
public class PagerTabAdapter extends FragmentPagerAdapter {
    private String tabTitles[];
    private Context context;

    public PagerTabAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        tabTitles = context.getResources().getStringArray(R.array.tabs);
    }

    /**
     * Return fragment with respect to Position .
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new JobListFragment(context);
            case 1:
                return new ClientListFragment(context);
            case 2:
                return new BalanceFragment();
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
