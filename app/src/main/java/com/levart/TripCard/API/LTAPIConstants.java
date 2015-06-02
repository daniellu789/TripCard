package com.levart.TripCard.API;

import com.levart.TripCard.utils.LTLog;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by dlu on 5/2/15.
 */
public class LTAPIConstants {
    private static final String LOG_TAG = LTAPIConstants.class.getSimpleName();

    public static final int TAG_ALL = 0;
    public static final int TAG_TRAFFIC = 1;
    public static final int TAG_ATTRACTION = 2;
    public static final String GOOGLE_PROVIDER = "GOOGLE";
    public static final int IMAGE_WIDTH_SIZE = 800;
    public static final String GOOGLE_PLACE_API_KEY = "AIzaSyBcBkS15uizw9ZDv6NSv3toBj-NhV1I-n8";
    public static final String KEYWORD = "关键词：";
    public static final String S_FAIL_TO_PARSE = "fail_to_parse";
    public static final HashMap<String, String> TAG_TO_NAME = new HashMap<String, String>();
    public static final HashMap<String, String> NAME_TO_TAG = new HashMap<String, String>();
    public static final HashMap<String, String> CHINESE_COUNTRY_TO_CODE = new HashMap<String, String>();
    public static final HashMap<String, String> ENGLISH_COUNTRY_TO_CODE = new HashMap<String, String>();
    public static final HashMap<String, String> LOCALIZE = new HashMap<String, String>();

    static{
         /*
           ini LOCALIZE MAP, now only supporting English and Chinese
         */

        String[] Contents = {"Select", "Others", "Transportation", "Attraction", "Hotel", "History",
                "Season", "Animal", "Schedule", "Lodging", "Restaurant", "Camera", "Gallery",
                "Cancel", "Add a Photo", "Description", "Tag", "Location", "Photo", "Please add ", "."
                , "DISCARD?"};
        String lang = Locale.getDefault().getDisplayLanguage();
        LTLog.debug(LOG_TAG, "the language of this device is " + lang);
        if (lang.equalsIgnoreCase("English")) {
            for (int i = 0; i < Contents.length; i++) {
                String item = Contents[i];
                LOCALIZE.put("KEY_" + item.toUpperCase().trim(), item);
            }
        } else {
            String[] ChineseContents = {"请选择", "其他", "交通", "景点", "酒店", "历史",
                    "季节", "动物", "行程", "住宿", "餐厅", "相机", "相册", "取消","添加照片",
                    "描述", "标签", "地点", "照片", "请添加", "。", "取消编辑？"};
            for (int i = 0; i < Contents.length; i++) {
                String item = Contents[i];
                LOCALIZE.put("KEY_" + item.toUpperCase().trim(), ChineseContents[i]);
            }
        }
        for (int i = 0; i < Contents.length; i++) {
            TAG_TO_NAME.put(String.valueOf(i - 1), LOCALIZE.get("KEY_" + Contents[i].toUpperCase()));
        }

//        TAG_TO_NAME.put("-1", "请选择");
//        TAG_TO_NAME.put("0", "其他");
//        TAG_TO_NAME.put("1", "交通");
//        TAG_TO_NAME.put("2", "景点");
//        TAG_TO_NAME.put("3", "酒店");
//        TAG_TO_NAME.put("4", "历史");
//        TAG_TO_NAME.put("5", "季节");
//        TAG_TO_NAME.put("6", "动物");
//        TAG_TO_NAME.put("7", "行程");
//        TAG_TO_NAME.put("8", "住宿");
//        TAG_TO_NAME.put("9", "餐厅");

        for(Map.Entry<String, String> entry: TAG_TO_NAME.entrySet())
        {
            NAME_TO_TAG.put(entry.getValue(), entry.getKey());
        }

        // build map for CountryName to country ISO code
        Locale ChinaLocale = Locale.CHINESE;
        Locale EnglishLocale = Locale.ENGLISH;
        Locale[] localeList = Locale.getAvailableLocales();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            CHINESE_COUNTRY_TO_CODE.put(l.getDisplayCountry(ChinaLocale), iso);
            ENGLISH_COUNTRY_TO_CODE.put(l.getDisplayCountry(EnglishLocale), iso);
        }




    }
}
