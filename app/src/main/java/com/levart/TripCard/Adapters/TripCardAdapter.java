package com.levart.TripCard.Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;


import com.levart.TripCard.API.LTConfig;
import com.levart.TripCard.TripCard;
import com.levart.TripCard.Fragments.TripCardDetailFragment;
import com.levart.TripCard.utils.LTLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiecongwang on 5/11/15.
 */
public class TripCardAdapter extends FragmentStatePagerAdapter {
    private static final String LOG_TAG = TripCardAdapter.class.getSimpleName();

    private final List<TripCard> mTripCards = new ArrayList<TripCard>();
    private Map<Integer, TripCardDetailFragment> hm = new HashMap<>();

    public TripCardAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int index) {
        LTLog.debug(LOG_TAG, "going to getItem: " + index);
        hm.put(index, TripCardDetailFragment.newInstance(mTripCards.get(index)));
        return hm.get(index);
    }

    public void loadImages(int begin, int end) {
        for (int i = begin; i <= end; i++) {
            loadImage(i);
        }
    }

    public void loadImage(int index) {
        TripCardDetailFragment cur = hm.get(index);
        LTLog.debug(LOG_TAG, "going to load image: " + index);
        if (cur != null) {
            LTLog.debug(LOG_TAG, "loading image: " + index);
            hm.get(index).loadImage();
        }
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
        hm.clear();
        notifyDataSetChanged();
    }
}
