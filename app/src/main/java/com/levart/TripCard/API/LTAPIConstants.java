package com.levart.TripCard.API;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dlu on 5/2/15.
 */
public class LTAPIConstants {
    public static final int TAG_ALL = 0;
    public static final int TAG_TRAFFIC = 1;
    public static final int TAG_ATTRACTION = 2;
    public static final int IMAGE_WIDTH_SIZE = 500;
    public static final String KEYWORD = "关键词：";
    public static final HashMap<Integer, String> TAG_TO_NAME = new HashMap<Integer, String>();
    public static final HashMap<String, Integer> NAME_TO_TAG = new HashMap<String, Integer>();

    static{
        TAG_TO_NAME.put(0, "全部");
        TAG_TO_NAME.put(1, "交通");
        TAG_TO_NAME.put(2, "景点");

        for(Map.Entry<Integer, String> entry: TAG_TO_NAME.entrySet())
        {
            NAME_TO_TAG.put(entry.getValue(), entry.getKey());
        }
    }
}
