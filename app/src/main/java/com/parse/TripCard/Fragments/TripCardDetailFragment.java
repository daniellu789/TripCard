package com.parse.TripCard.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levart.TripCard.R;
import com.parse.TripCard.Card;


public class TripCardDetailFragment extends Fragment {

    private final static String CARD ="TripCard";
    private Card mCard;
    public static TripCardDetailFragment newInstance(Card card) {
        TripCardDetailFragment fragment = new TripCardDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(CARD,card);
        fragment.setArguments(args);
        return fragment;
    }

    public TripCardDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null) {
            mCard = (Card)getArguments().getSerializable(CARD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trip_card_detail, container, false);
        return view;
    }


}
