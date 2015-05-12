package com.parse.TripCard.Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.parse.TripCard.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiecongwang on 5/11/15.
 */
public class TripCardAdapter extends FragmentPagerAdapter {

    private final List<Card> mTripCards = new ArrayList<Card>();


    public TripCardAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int index) {
        return null;
    }

    @Override
    public int getCount() {
        return mTripCards.size();
    }

    public synchronized void updateData(List<Card> cards) {
        mTripCards.clear();
        mTripCards.addAll(cards);
        notifyDataSetChanged();
    }
}
