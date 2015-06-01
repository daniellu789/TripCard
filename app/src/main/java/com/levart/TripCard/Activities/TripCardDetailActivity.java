package com.levart.TripCard.Activities;



import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.View;
import android.widget.Toast;


import com.levart.TripCard.API.LTConfig;
import com.levart.TripCard.R;
import com.levart.TripCard.TripCard;
import com.levart.TripCard.Views.BottomToolBar;
import com.levart.TripCard.utils.LTLog;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.levart.TripCard.Adapters.TripCardAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TripCardDetailActivity extends Activity {
    private static final String LOG_TAG = TripCardDetailActivity.class.getSimpleName();

    TripCardAdapter mAdapter;

    private TripCard[] mArrayCards;

    private static final int PAGE_CARD_NUM = LTConfig.DETAIL_PAGE_CARD_NUM;
    private static final int FIRST_LOAD_CARD_NUM = LTConfig.DETAIL_PAGE_FIRST_LOAD_CARD_NUM;

    private int nRandomCounter;

    ViewPager mViewPager;

    BottomToolBar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_card_detail);
        nRandomCounter = 0;
        mToolBar = (BottomToolBar)findViewById(R.id.cards_tool_bar);
        mAdapter = new TripCardAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAdapter);
        final Activity thisActivity = this;
        mToolBar.setWriteCardListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, NewTripCardActivity.class);
                startActivity(intent);
            }
        });

        mToolBar.setShuffleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleCards();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        nRandomCounter = 0;
        updateCards();
    }

    private void updateCards() {
        ParseQuery<TripCard> parseQuery = ParseQuery.getQuery("TripCard");
        parseQuery.whereEqualTo(TripCard.STATUS, 1);
        parseQuery.orderByDescending("createdAt");
        parseQuery.setLimit(FIRST_LOAD_CARD_NUM);
        parseQuery.findInBackground(new FindCallback<TripCard>() {
            @Override
            public void done(List<TripCard> cards, ParseException e) {
            if (e == null) {
                mArrayCards = cards.toArray(new TripCard[cards.size()]);
                if (mArrayCards != null && mArrayCards.length > 0) {
                    mAdapter.clearResult();
                    int index = Math.min(mArrayCards.length, PAGE_CARD_NUM);
                    mAdapter.updateData(Arrays.asList(mArrayCards).subList(0, index));
                    nRandomCounter = index;
                }
            } else {
            }
            }
        });
    }

    private void shuffleCards() {
        mAdapter.clearResult();
        mAdapter.updateData(getRandomCards());
    }

    private List<TripCard> getRandomCards() {
        List<TripCard> ret = new ArrayList<>();
        int len = mArrayCards.length;
        int index = Math.min(len, nRandomCounter + PAGE_CARD_NUM);
        for (int i = nRandomCounter; i < index; i++) {
            int randomIndex = (int)(Math.random() * (len - i)) + i;
            ret.add(mArrayCards[randomIndex]);
            TripCard temp = mArrayCards[randomIndex];
            mArrayCards[randomIndex] = mArrayCards[i];
            mArrayCards[i] = temp;
        }
        nRandomCounter += PAGE_CARD_NUM;
        return ret;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAdapter.isEmpty()) {
            ParseQuery<TripCard> parseQuery = ParseQuery.getQuery("TripCard");
            parseQuery.whereEqualTo(TripCard.STATUS, 1);
            parseQuery.orderByDescending("createdAt");
            parseQuery.setLimit(FIRST_LOAD_CARD_NUM);
            parseQuery.findInBackground(new FindCallback<TripCard>() {
                @Override
                public void done(List<TripCard> cards, ParseException e) {
                if (e == null) {
                    mArrayCards = cards.toArray(new TripCard[cards.size()]);
                    if (mArrayCards != null && mArrayCards.length > 0) {
                        mAdapter.updateData(getRandomCards());
                    }
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

    private void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }
}
