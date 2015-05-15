package com.parse.TripCard;

import com.parse.ParseACL;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.Serializable;


/**
 * Created by jiecongwang on 5/12/15.
 */
@ParseClassName("TripCard")
public class TripCard extends ParseObject implements Serializable {

    private static final String ID = "objectId";
    private static final String DESCRIPTION ="description";
    private static final String PRAISE ="praise";
    private static final String PHOTO ="photo";
    private static final String LOCATIONNAME ="locationname";
    private static final String STATUS ="status";
    private static final String TAG ="tag";

    public TripCard() {

    }

    public String getId() {
        return getString(ID);
    }

    public String getDescription() {
        return getString(DESCRIPTION);
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

    public String getLocationname() {
        return getString(LOCATIONNAME);
    }

    public int getPraiseCount() {
        return getInt(PRAISE);
    }

    public int getStatus() {
        return getInt(STATUS);
    }

}