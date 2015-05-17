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

    private  ImageButton mWriteCard;
    private  ImageButton mReadCard;
    private  ImageButton mAlreadySave;
    private  int currentActivity =0;


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
        mReadCard = (ImageButton) findViewById(R.id.read_card_button);
        switch (currentActivity) {
            case 0:
                mWriteCard.setClickable(false);
                mWriteCard.setPressed(true);
                return;
            case 1:
                mReadCard.setClickable(false);
                mReadCard.setPressed(true);
                return;
            case 2:
                mAlreadySave.setClickable(false);
                mAlreadySave.setPressed(true);

        }
    }

    public void setWriteCardListener(OnClickListener listener) {
        mWriteCard.setOnClickListener(listener);
    }

    public void setReadCardListener(OnClickListener listener) {
        mReadCard.setOnClickListener(listener);
    }

    public void setAlreadySaveListener(OnClickListener listener) {
        mReadCard.setOnClickListener(listener);
    }

}
