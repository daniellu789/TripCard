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
    public static final String GOOGLE_PROVIDER = "GOOGLE";
    public static final int IMAGE_WIDTH_SIZE = 800;
    public static final String GOOGLE_PLACE_API_KEY = "AIzaSyBcBkS15uizw9ZDv6NSv3toBj-NhV1I-n8";
    public static final String KEYWORD = "关键词：";
    public static final HashMap<String, String> TAG_TO_NAME = new HashMap<String, String>();
    public static final HashMap<String, String> NAME_TO_TAG = new HashMap<String, String>();

    static{
        TAG_TO_NAME.put("0", "全部");
        TAG_TO_NAME.put("1", "交通");
        TAG_TO_NAME.put("2", "景点");
        TAG_TO_NAME.put("3", "酒店");

        for(Map.Entry<String, String> entry: TAG_TO_NAME.entrySet())
        {
            NAME_TO_TAG.put(entry.getValue(), entry.getKey());
        }
    }
}
