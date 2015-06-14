package com.levart.TripCard.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.levart.TripCard.API.LTAPIConstants;
import com.levart.TripCard.R;
import com.levart.TripCard.TripCard;
import com.levart.TripCard.utils.LTLog;
import com.parse.ParseImageView;


public class TripCardDetailFragment extends Fragment {
    private static final String LOG_TAG = TripCardDetailFragment.class.getSimpleName();

    private final static String CARD ="TripCard";
    private TripCard mCard;
    private TextView mDescription;
    private TextView mTag;
    private TextView mLocation;
    private ParseImageView mPicture;
    private boolean isImageLoad = false;

    public static TripCardDetailFragment newInstance(TripCard card) {
        TripCardDetailFragment fragment = new TripCardDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(CARD,card);
        fragment.setArguments(args);
        return fragment;
    }

    public TripCardDetailFragment() {

    }

    public void setTripCard(TripCard tripCard) {
        if (tripCard !=null && mDescription != null) {
            mDescription.setText(tripCard.getDescription());
            mTag.setText(LTAPIConstants.TAG_TO_NAME.get(tripCard.getTag()));
            mLocation.setText(tripCard.getLocationFullName());
            mPicture.setParseFile(tripCard.getPhoto());
            LTLog.debug(LOG_TAG, "b image loaded!!" + tripCard.getPhoto());
            mPicture.loadInBackground();
            LTLog.debug(LOG_TAG, "d image loaded!!");

        }
    }

    public void loadImage() {
        if (!isImageLoad) {
            mPicture.loadInBackground();
            isImageLoad = true;
            LTLog.debug(LOG_TAG, "image loaded!!");
        }
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
        mTag = (TextView) view.findViewById(R.id.card_tag);
        mLocation = (TextView) view.findViewById(R.id.location);
        setTripCard(mCard);
        return view;
    }
}
