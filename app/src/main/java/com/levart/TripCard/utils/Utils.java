package com.levart.TripCard.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.levart.TripCard.API.LTAPIConstants;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dlu on 5/3/15.
 */
public class Utils {
    // is DebugMode or Release mode
    public static boolean isDebugMode(Context context) {
        return  (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
    }

    public static boolean isEmptyString(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static boolean isEmptySafe(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEnglishchar(char c) {
        return c>=0x0000 && c<=0x00FF;
    }

    //now only Support Chinese and English
    public static String getCountryCodeFromString (String fullName) {
        if (fullName == null || fullName.trim().length() == 0) {
            return LTAPIConstants.S_FAIL_TO_PARSE;
        }
        fullName = fullName.trim();
        Map<String, String> CountryToCode;
        if (isEnglishchar(fullName.charAt(0))) {
            CountryToCode = LTAPIConstants.ENGLISH_COUNTRY_TO_CODE;
        }
        else
        {
            CountryToCode = LTAPIConstants.CHINESE_COUNTRY_TO_CODE;
        }
        for (Map.Entry<String, String> entry: CountryToCode.entrySet()) {
            if (fullName.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return LTAPIConstants.S_FAIL_TO_PARSE;
    }


}

