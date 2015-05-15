package com.parse.TripCard;



import android.app.Activity;
import android.app.ActionBar;

import android.app.FragmentTransaction;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.levart.TripCard.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.TripCard.Adapters.TripCardAdapter;

import java.util.List;

public class TripCardDetailActivity extends Activity {

    TripCardAdapter mAdapter;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_card_detail);

        mAdapter = new TripCardAdapter(getFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAdapter.isEmpty()) {
            ParseQuery<TripCard> parseQuery = ParseQuery.getQuery("TripCard");
            parseQuery.findInBackground(new FindCallback<TripCard>() {
                @Override
                public void done(List<TripCard> cards, ParseException e) {
                    if (e == null) {
                        mAdapter.updateData(cards);
                    } else {

                    }
                }
            });
        }
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

}
