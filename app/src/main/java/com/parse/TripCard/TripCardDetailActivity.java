package com.parse.TripCard;



import android.app.Activity;
import android.app.ActionBar;

import android.app.FragmentTransaction;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.MenuItem;


import com.levart.TripCard.R;
import com.parse.TripCard.Adapters.TripCardAdapter;

public class TripCardDetailActivity extends Activity implements ActionBar.TabListener {


    TripCardAdapter mAdapter;

    ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_card_detail);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        String[] tabsTitle = getResources().getStringArray(R.array.ratings_array);
        for (String title :tabsTitle) {
            actionBar.addTab(actionBar.newTab().setText(title).setTabListener(this));
        }

        mAdapter = new TripCardAdapter(getFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_trip_card_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


}
