package com.usman.dailyfit;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * An activity representing a single Workout detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link WorkoutListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link WorkoutDetailFragment}.
 */
public class WorkoutDetailActivity extends ActionBarActivity {


    //adding variables for DrawerList
    private String[] mWorkoutTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(WorkoutDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(WorkoutDetailFragment.ARG_ITEM_ID));
            WorkoutDetailFragment fragment = new WorkoutDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.workout_detail_container, fragment)
                    .commit();
        }

        //Enabling an app icon as an up button
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        //Display the icon in the action bar
        final ActionBar ab =getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.ic_launcher);
        //ab.setDisplayHomeAsUpEnabled(true);
        //ab.setHomeButtonEnabled(true);

        //changing title of action bar when drawer is open
        mTitle = mDrawerTitle = getTitle();

        //Initializing the drawer's list of items using ListView
        //Populated using and AdapterView
        //Code also calls setOnItemClickListener() to receive click events
        // in the navigation drawer's list
        mWorkoutTitles = getResources().getStringArray(R.array.items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_detail);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_workout_detail);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mWorkoutTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                   //host activity
                mDrawerLayout,          //DrawerLayout
                //R.drawable.ic_drawer,
                R.string.drawer_open,   //"open drawer" string description
                R.string.drawer_close  //"close drawer" string description
        ) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                ab.setTitle(mTitle);
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ab.setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        //set actionBarDrawerToggle as the DrawerListener
        //mDrawerLayout.setDrawerListener(mDrawerToggle);

        //Replacing Drawer Icon with back button to master activity
        //User can still access drawer with swipe action
        mDrawerToggle.setDrawerIndicatorEnabled(false);

        //styling option to add shadow the right edge of the drawer
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
  //      menu.findItem(R.id.add_exercise).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, WorkoutListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
    * Next Two methods are required when using the ActionBarDrawerToggle
    */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Toast.makeText(WorkoutDetailActivity.this, ((TextView) view).getText(), Toast.LENGTH_LONG).show();
            mDrawerLayout.closeDrawer(mDrawerList);
            //selectItem(position);
        }

        /*
        Method implementation to fix later. This method will change activity depending on
        users's choice from the list. Currently I am getting a IllegalStateException
        stating "Activity must implement fragment's callbacks"

        private void selectItem(int position) {
            BodyWeightListFragment bfragment = null;
            WorkoutListFragment wfragment = null;

            switch (position) {
                case 0:
                    wfragment = new WorkoutListFragment();
                    break;
                case 1:
                    bfragment = new BodyWeightListFragment();
                    break;
                default:
                    break;
            }

            if (position == 0 && wfragment != null) {
                //FragmentManager fragmentManager = getFragmentManager();
                getSupportFragmentManager().beginTransaction().replace(R.id.workout_list, bfragment).commit();

                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                //getActionBar().setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);

            }
            else if (position == 1 && bfragment != null) {
                    //FragmentManager fragmentManager = getFragmentManager();
                    getSupportFragmentManager().beginTransaction().replace(R.id.bodyweight_list, wfragment).commit();

                    mDrawerList.setItemChecked(position, true);
                    mDrawerList.setSelection(position);
                    //getActionBar().setTitle(mNavigationDrawerItemTitles[position]);
                    mDrawerLayout.closeDrawer(mDrawerList);

            }
            else {
                    Log.e("MainActivity", "Error in creating fragment");
            }
        }*/
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

}
