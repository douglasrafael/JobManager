package com.fsdeveloper.jobmanager.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.adapter.PagerTabAdapter;
import com.fsdeveloper.jobmanager.bean.User;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;
import com.fsdeveloper.jobmanager.fragments.ClientListFragment;
import com.fsdeveloper.jobmanager.fragments.GenericDialogFragment;
import com.fsdeveloper.jobmanager.fragments.JobListFragment;
import com.fsdeveloper.jobmanager.manager.Manager;
import com.fsdeveloper.jobmanager.tool.MyAnimation;

/**
 * Activity main system.
 *
 * @author Created by Douglas Rafael
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MainActivity";
    private final int CHANGE = 1;
    private DrawerLayout mDrawerLayout;
    //    private ActionBarDrawerToggle mDrawerToggle;
    //    private NavigationView mNavigationView;
    private Toolbar toolbar;
    private ViewPager mViewPager;
    private PagerTabAdapter mAdapter;
    private SearchView mSearchView;
    private Intent intent;
    private Menu mMenu;
    private FloatingActionButton fabAddJob, fabBtBalance;
    private boolean fabChanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        setupToolbar();

        setupTabLayout();

//        // Find our drawer view
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
//
//        // Tie DrawerLayout events to the ActionBarToggle
//        mDrawerLayout.addDrawerListener(mDrawerToggle);

        // Setup drawer view
//        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
//        setupDrawerContent(mNavigationView);

        /**
         * Button floating
         */
        fabAddJob = (FloatingActionButton) findViewById(R.id.fab_add_job);
        fabBtBalance = (FloatingActionButton) findViewById(R.id.fab_change_date_balance);
        fabAddJob.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add_job:
                intent = new Intent(this, JobFormActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        switch (item.getItemId()) {
            case R.id.action_about:
                GenericDialogFragment dialogAbout = GenericDialogFragment.newDialog(
                        1, R.string.about_title, R.string.about_message, R.drawable.ic_launcher_logo, new int[]{android.R.string.ok}, null);
                dialogAbout.show(getSupportFragmentManager());

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.

//        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
//        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        setupSearchView(menu);

        return super.onCreateOptionsMenu(menu);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupSearchView(Menu menu) {
        mMenu = menu;

        // Associate searchable configuration with the SearchView
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String filter) {
                // tab client
                switch (mViewPager.getCurrentItem()) {
                    case 0:
                        JobListFragment.search(filter);
                        break;
                    case 1:
                        ClientListFragment.search(filter);
                        break;
                }

                return false;
            }
        });
    }

    /**
     * Set toolbar.
     */
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }

    /**
     * Set tab layout.
     */
    private void setupTabLayout() {
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        final String[] tabs = getResources().getStringArray(R.array.tabs);
        fabChanger = false;

        for (String titleTab : tabs) {
            tabLayout.addTab(tabLayout.newTab().setText(titleTab));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) findViewById(R.id.container_view);
        mAdapter = new PagerTabAdapter(this, getSupportFragmentManager());

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                /**
                 * Remove and icons in toolbar
                 * Remove and add fab
                 */
                if (tab.getPosition() == 2) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                        MyAnimation.crossFade(fabBtBalance, fabAddJob, 400, View.VISIBLE);
                    } else {
                        fabBtBalance.setVisibility(View.VISIBLE);
                        fabAddJob.setVisibility(View.GONE);
                    }
                    fabChanger = true;

//                    toolbar.getMenu().clear();
//                    toolbar.inflateMenu(R.menu.share_menu);
                } else {
                    if (fabChanger) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                            MyAnimation.crossFade(fabAddJob, fabBtBalance, 400, View.VISIBLE);
                        } else {
                            fabAddJob.setVisibility(View.VISIBLE);
                            fabBtBalance.setVisibility(View.GONE);
                        }
                        fabChanger = false;

                        toolbar.getMenu().clear();
                        toolbar.inflateMenu(R.menu.main_menu);
                        setupSearchView(mMenu);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (mSearchView.isShown() && tab.getPosition() < 2) {
                    mSearchView.setQuery("", true);
                    mSearchView.setIconified(true);
                    mSearchView.clearFocus();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

//    /**
//     * Set navigation drawer.
//     *
//     * @param navigationView The navigationView
//     */
//    private void setupDrawerContent(NavigationView navigationView) {
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        selectDrawerItem(menuItem);
//                        return true;
//                    }
//                });
//    }

//    /**
//     * Treat the items clicked in menu navigation drawer.
//     *
//     * @param menuItem The menu clicked.
//     */
//    public void selectDrawerItem(MenuItem menuItem) {
//        // Create a new fragment and specify the fragment to show based on nav item clicked
//        Fragment fragment = null;
//        Class fragmentClass = null;
//
//        setTitle(getString(R.string.app_name));
//        switch (menuItem.getItemId()) {
//            case R.id.action_job:
////                fragmentClass = FirstFragment.class;
//                Log.i("MAIN", "JOb clicked");
//                break;
//            case R.id.action_client:
////                fragmentClass = SecondFragment.class;
//                Log.i("MAIN", "Client clicked");
//                break;
//            case R.id.action_balance:
////                fragmentClass = ThirdFragment.class;
//                Log.i("MAIN", "Balance clicked");
//                break;
//            default:
//                setTitle(menuItem.getTitle());
////                fragmentClass = FirstFragment.class;
//                Log.i("MAIN", "None");
//        }
//
////        try {
////            fragment = (Fragment) fragmentClass.newInstance();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        // Insert the fragment by replacing any existing fragment
////        FragmentManager fragmentManager = getSupportFragmentManager();
////        fragmentManager.beginTransaction().replace(R.id.frame_content, fragment).commit();
//
//        // Highlight the selected item has been done by NavigationView
//        menuItem.setChecked(true);
//        // Set action bar title
////        setTitle(menuItem.getTitle());
//        // Close the navigation drawer
//        mDrawerLayout.closeDrawers();
//    }
}
