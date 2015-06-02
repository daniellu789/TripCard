package com.levart.TripCard.Activities;



import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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

public class TripCardDetailActivity extends ActionBarActivity {
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
        setTheme(R.style.actionBarSytle);

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
        updateCards(FIRST_LOAD_CARD_NUM, true);
    }

    private void updateCards(int loadCardNum, boolean bDescending) {
        ParseQuery<TripCard> parseQuery = ParseQuery.getQuery("TripCard");
        parseQuery.whereEqualTo(TripCard.STATUS, 1);
        if (bDescending) {
            parseQuery.orderByDescending("createdAt");
        } else {
            parseQuery.orderByAscending("createdAt");
        }
        parseQuery.setLimit(loadCardNum);
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
        List<TripCard> randomCards = getRandomCards();
        if (randomCards.size() > 0) {
            mAdapter.updateData(randomCards);
        } else {
            updateCards(FIRST_LOAD_CARD_NUM * 4, true);
        }
        mViewPager.setCurrentItem(0);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_card_detail, menu);
        LTLog.debug(LOG_TAG, "menu???");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.detail_activity_feedback) {
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }
}
