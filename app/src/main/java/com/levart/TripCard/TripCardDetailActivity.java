package com.levart.TripCard;



import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.View;
import android.widget.Toolbar;


import com.levart.TripCard.Views.BottomToolBar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.levart.TripCard.Adapters.TripCardAdapter;

import java.util.List;

public class TripCardDetailActivity extends Activity {

    TripCardAdapter mAdapter;

    ViewPager mViewPager;

    BottomToolBar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_card_detail);
        mToolBar = (BottomToolBar)findViewById(R.id.cards_tool_bar);
        mAdapter = new TripCardAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAdapter);
        mToolBar.setWriteCardListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mToolBar.setAlreadySaveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
