package com.levart.TripCard;



import android.app.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.widget.Toolbar;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.levart.TripCard.Adapters.TripCardAdapter;

import java.util.List;

public class TripCardDetailActivity extends Activity {

    TripCardAdapter mAdapter;

    ViewPager mViewPager;

    Toolbar mToolBar;

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
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }
}
