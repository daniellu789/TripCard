package com.levart.TripCard.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.levart.TripCard.R;


/**
 * Created by jiecongwang on 5/15/15.
 */
public class BottomToolBar extends LinearLayout {

    private ImageButton mWriteCard;
    private ImageButton mShuffleCars;
    private ImageButton mAlreadySave;
    private LinearLayout mWriteCardLayout;
    private LinearLayout mShuffleCardsLayout;
    private int currentActivity = 0;


    public BottomToolBar(Context context) {
        super(context);
        initLayout(context,null);
    }

    public BottomToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context,attrs);
    }

    public BottomToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context,attrs);
    }

    public BottomToolBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initLayout(context,attrs);
    }

    private void initLayout(Context context,AttributeSet attrs) {
         inflate(context, R.layout.bottom_tool_bar, this);
         TypedArray array = context.getTheme().obtainStyledAttributes(attrs,R.styleable.BottomToolBar,0,0);
         try {
             currentActivity = array.getInt(R.styleable.BottomToolBar_Activity,0);
         } finally {
             array.recycle();
         }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mWriteCard = (ImageButton) findViewById(R.id.write_a_card_button);
        mAlreadySave = (ImageButton) findViewById(R.id.already_save_button);
        mShuffleCars = (ImageButton) findViewById(R.id.shuffle_cards_button);
        mWriteCardLayout = (LinearLayout) findViewById(R.id.write_a_card_layout);
        mShuffleCardsLayout = (LinearLayout) findViewById(R.id.shuffle_cards_layout);
        switch (currentActivity) {
            case 0:
                mWriteCard.setClickable(false);
                mWriteCard.setPressed(true);
                return;
            case 1:
                mShuffleCars.setClickable(false);
                mShuffleCars.setPressed(true);
                return;
            case 2:
                mAlreadySave.setClickable(false);
                mAlreadySave.setPressed(true);

        }
    }

    public void setWriteCardListener(OnClickListener listener) {
        mWriteCard.setOnClickListener(listener);
        mWriteCardLayout.setOnClickListener(listener);
    }

    public void setShuffleListener(OnClickListener listener) {
        mShuffleCars.setOnClickListener(listener);
        mShuffleCardsLayout.setOnClickListener(listener);
    }

}
