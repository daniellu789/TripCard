package com.levart.TripCard.utils;

import android.util.Log;

import com.levart.TripCard.LTApplication;

/**
 * Created by dlu on 5/3/15.
 */
public class LTLog {

    public static final boolean isDebugMode = Utils.isDebugMode(LTApplication.getInstance().getApplicationContext());

    public static void error(String TAG,String message,Throwable throwable) {
        if (isDebugMode) {
            Log.e(TAG, message, throwable);
        }

    }

    public static void error(String TAG,String message) {
        if (isDebugMode) {
            Log.e(TAG,message);
        }
    }

    public static void info(String TAG,String message) {
        if (isDebugMode) {
            Log.i(TAG,message);
        }
    }

    public static void debug(String TAG,String message) {
        if (isDebugMode) {
            Log.d(TAG,message);
        }
    }

    public static void debug(String TAG,String message,Throwable throwable) {
        if (isDebugMode) {
            Log.d(TAG,message,throwable);
        }
    }
}
