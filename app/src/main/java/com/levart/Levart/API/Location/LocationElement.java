package com.levart.Levart.API.Location;

import android.location.Location;

/**
 * Created by dlu on 5/10/15.
 */
public class LocationElement{
    private String sProvider;
    private String sFullName;
    private String sLocationId;
    private String sLongitude;
    private String sLatitude;

    public LocationElement(String provider, String fullName, String locationId)
    {
        sProvider = provider;
        sFullName = fullName;
        sLocationId = locationId;
    }

    public String getProvider ()
    {
        return sProvider;
    }

    public String getLocationId ()
    {
        return sLocationId;
    }

    public String getFullName ()
    {
        return sFullName;
    }

    public void setlongitude (String lng)
    {
        sLongitude = lng;
    }
    public void setlatitude (String lat)
    {
        sLatitude = lat;
    }

}
