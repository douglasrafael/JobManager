package com.fsdeveloper.jobmanager.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.adapter.TabsAdapter;


public class TabFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View view =  inflater.inflate(R.layout.tab_layout, null);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        /**
         *Set an Adapter for the View Pager
         */
        viewPager.setAdapter(new TabsAdapter(getContext(), getChildFragmentManager()));

        /**
         * Now, this is a workaround.
         * The setupWithViewPager dose't works without the runnable.
         * Maybe a Support Library Bug.
         */
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;

    }
}
