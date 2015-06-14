package com.levart.TripCard;

import com.parse.ParseACL;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by jiecongwang on 5/12/15.
 */
@ParseClassName("TripCard")
public class TripCard extends ParseObject implements Serializable {

    public static final String ID = "objectId";
    public static final String DESCRIPTION ="description";
    public static final String DESCRIPTION_LANG ="description_lang";
    public static final String PRAISE ="praise";
    public static final String PHOTO ="photo";
    public static final String GOOGLELOCATIONID ="googlelocationid";
    public static final String STATUS ="status";
    public static final String TAG ="tag";
    public static final String COUNTRY ="country";
    public static final String CARD_TITLE = "title";
    public static final String LOCATION_FULL_NAME = "location_full_name";

    public TripCard() {

    }

    public String getId() {
        return getString(ID);
    }

    public String getDescription() {
        return getString(DESCRIPTION);
    }

    public void setDescription(String description) {
        put(DESCRIPTION, description);
    }

    public String getDescriptionLang() {
        return getString(DESCRIPTION_LANG);
    }

    public void setDescriptionLang(String descriptionLang) {
        put(DESCRIPTION_LANG, descriptionLang);
    }

    public ParseACL getUser() {
        return getACL();
    }

    public ParseFile getPhoto() {
        return getParseFile(PHOTO);
    }

    public String getTag() {
        return getString(TAG);
    }

    public void setTag(String tag) {
        put(TAG, tag);
    }

    public String getGooglelocationide() {
        return getString(GOOGLELOCATIONID);
    }

    public void setGooglelocationid(String id) {
        put(GOOGLELOCATIONID, id);
    }
    public int getPraiseCount() {
        return getInt(PRAISE);
    }

    public int getStatus() {
        return getInt(STATUS);
    }

    public void setStatus(int status) {
        put(STATUS, status);
    }

    public void setLocationFullName(String location) {
        put(LOCATION_FULL_NAME, location);
    }

    public String getLocationFullName() {
        return getString(LOCATION_FULL_NAME);
    }

    public String getTitle() {
        return getString(CARD_TITLE);
    }

    public void setTitle(String title) {
        put(CARD_TITLE, title);
    }

    public ParseUser getAuthor() {
        return getParseUser("author");
    }

    public void setAuthor(ParseUser user) {
        put("author", user);
    }

    public String getCountry() {
        return getString(COUNTRY);
    }

    public void setCountry(String country) {
        put(COUNTRY, country);
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }



}
