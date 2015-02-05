package com.usman.dailyfit;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

/**
 * An activity representing a list of BodyWeights. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BodyWeightDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link BodyWeightListFragment} and the item details
 * (if present) is a {@link BodyWeightDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link BodyWeightListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class BodyWeightListActivity extends ActionBarActivity
        implements BodyWeightListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodyweight_list);

        if (findViewById(R.id.bodyweight_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((BodyWeightListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.bodyweight_list))
                    .setActivateOnItemClick(true);
        }

        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.ic_launcher);
    }

    /**
     * Callback method from {@link BodyWeightListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(BodyWeightDetailFragment.ARG_ITEM_ID, id);
            BodyWeightDetailFragment fragment = new BodyWeightDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.bodyweight_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, BodyWeightDetailActivity.class);
            detailIntent.putExtra(BodyWeightDetailFragment.ARG_ITEM_ID, id);
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
}
