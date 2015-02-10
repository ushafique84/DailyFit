package com.usman.dailyfit;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * An activity representing a list of Workouts. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link WorkoutDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link WorkoutListFragment} and the item details
 * (if present) is a {@link WorkoutDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link WorkoutListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class WorkoutListActivity extends ActionBarActivity
        implements WorkoutListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

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
        setContentView(R.layout.activity_workout_list);

        if (findViewById(R.id.workout_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((WorkoutListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.workout_list))
                    .setActivateOnItemClick(true);
        }

        //Creating actionbar instance and configuring action bar
        final ActionBar ab =getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.ic_launcher);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        //changing title of action bar when drawer is open
        mTitle = mDrawerTitle = getTitle();

        //Initializing the drawer's list of items using ListView
        //Populated using and AdapterView
        //Code also calls setOnItemClickListener() to receive click events
        // in the navigation drawer's list
        mWorkoutTitles = getResources().getStringArray(R.array.items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_list);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_workout_list);
        // Set the adapter for the list view
        System.out.println(mDrawerList);
        System.out.println("****************************");
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mWorkoutTitles));
        System.out.println(mDrawerList);
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
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //styling option to add shadow the right edge of the drawer
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        menu.findItem(R.id.add_exercise).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Callback method from {@link WorkoutListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(WorkoutDetailFragment.ARG_ITEM_ID, id);
            WorkoutDetailFragment fragment = new WorkoutDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.workout_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, WorkoutDetailActivity.class);
            detailIntent.putExtra(WorkoutDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
     //Inflate menu items to use in action bar
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_main_menu, menu);

        //These lines will be used to search for exercises in the master list
        //Get the search view and set the searchable configuration in res/xml/searchable.xml
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.search_button:
                Log.i("ActivityName", "Search");
                return true;
            case R.id.add_exercise:
                Log.i("Add a new exercise", "Search");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
            Toast.makeText(WorkoutListActivity.this, ((TextView) view).getText(), Toast.LENGTH_LONG).show();
            mDrawerLayout.closeDrawer(mDrawerList);
            //selectItem(position);
        }

        /*
        Method implementation to fix later. This method will change activity depending on
        users's choice from the list. Currently I am getting a IllegalStateException
        stating "Activity must implement fragment's callbacks"

        private void selectItem(int position) {
            //BodyWeightListFragment bfragment = null;

            switch (position) {
                case 0:
                    mDrawerLayout.closeDrawers();
                    break;
                case 1:
                    //bfragment = new BodyWeightListFragment();
                    break;
                default:
                    break;
            }

            if (bfragment != null) {
                //FragmentManager fragmentManager = getFragmentManager();
                getSupportFragmentManager().beginTransaction().replace(R.id.bodyweight_list, bfragment).commit();

                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                //getActionBar().setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);

            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        } */
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

}
