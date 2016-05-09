package com.fsdeveloper.jobmanager.activity;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.fsdeveloper.jobmanager.fragments.TabFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        setupToolbar();

        // Find our drawer view
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        // Setup drawer view
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container_view, new TabFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
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
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Treat the search field.
     *
     * @param intent The intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
        }
    }

    /**
     * Set toolbar
     */
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }

    /**
     * Set navigation drawer.
     *
     * @param navigationView The navigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    /**
     * Treat the items clicked in menu navigation drawer.
     *
     * @param menuItem The menu clicked.
     */
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;

        setTitle(getString(R.string.app_name));
        switch (menuItem.getItemId()) {
            case R.id.action_job:
//                fragmentClass = FirstFragment.class;
                Log.i("MAIN", "JOb clicked");
                break;
            case R.id.action_client:
//                fragmentClass = SecondFragment.class;
                Log.i("MAIN", "Client clicked");
                break;
            case R.id.action_balance:
//                fragmentClass = ThirdFragment.class;
                Log.i("MAIN", "Balance clicked");
                break;
            default:
                setTitle(menuItem.getTitle());
//                fragmentClass = FirstFragment.class;
                Log.i("MAIN", "None");
        }

//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.frame_content, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
//        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
    }

    public void test(View v) {
        Log.i("FOI", "CLICOU");
    }

}
