package com.levart.TripCard.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.levart.TripCard.R;
import com.levart.TripCard.TripCard;
import com.parse.ParseImageView;


public class TripCardDetailFragment extends Fragment {

    private final static String CARD ="TripCard";
    private TripCard mCard;
    private TextView mDescription;
    private ParseImageView mPicture;

    public static TripCardDetailFragment newInstance(TripCard card) {
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
            mCard = (TripCard)getArguments().getSerializable(CARD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_card_detail, container, false);
        mDescription =(TextView) view.findViewById(R.id.card_description);
        mPicture = (ParseImageView) view.findViewById(R.id.picture);
        if (mCard !=null) {
            mDescription.setText(mCard.getDescription());
            mPicture.setParseFile(mCard.getPhoto());
            mPicture.loadInBackground();
        }
        return view;
    }


}
