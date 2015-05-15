package com.parse.TripCard.Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;


import com.parse.TripCard.Fragments.TripCardDetailFragment;
import com.parse.TripCard.TripCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiecongwang on 5/11/15.
 */
public class TripCardAdapter extends FragmentPagerAdapter {

    private final List<TripCard> mTripCards = new ArrayList<TripCard>();


    public TripCardAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int index) {
        return TripCardDetailFragment.newInstance(mTripCards.get(index));
    }

    @Override
    public int getCount() {
        return mTripCards.size();
    }

    public synchronized void updateData(List<TripCard> cards) {
        mTripCards.addAll(cards);
        notifyDataSetChanged();
    }

    public synchronized boolean isEmpty() {
        return mTripCards.isEmpty();
    }

    public synchronized void clearResult() {
        mTripCards.clear();
        notifyDataSetChanged();
    }
}
