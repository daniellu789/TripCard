package com.levart.TripCard.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.levart.TripCard.API.LTAPIConstants;
import com.levart.TripCard.API.LocationElement;
import com.levart.TripCard.utils.LTLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by dlu on 5/30/15.
 */
public class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
    private static final String LOG_TAG = GooglePlacesAutocompleteAdapter.class.getSimpleName();
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private ArrayList<LocationElement> resultList;

    public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index).getFullName();
    }

    public LocationElement getLocationElementItem(int index)
    {
        return resultList.get(index);
    }

    public ArrayList<LocationElement> getResultList() {
        return resultList;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    resultList = locationAutocomplete(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }


    public static ArrayList<LocationElement> locationAutocomplete(String input) {
        ArrayList<LocationElement> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + LTAPIConstants.GOOGLE_PLACE_API_KEY);
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            LTLog.debug(LOG_TAG, sb.toString());

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            LTLog.error(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            LTLog.error(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        LTLog.debug(LOG_TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        String forAna = jsonResults.toString().replace("\n", "");
        LTLog.debug(LOG_TAG, forAna);
        LTLog.debug(LOG_TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                LTLog.debug(LOG_TAG, predsJsonArray.getJSONObject(i).getString("description"));
                LTLog.debug(LOG_TAG, "============================================================");
                LocationElement le = new LocationElement("GOOGLE",
                        predsJsonArray.getJSONObject(i).getString("description"),
                        predsJsonArray.getJSONObject(i).getString("place_id"));
                resultList.add(le);
            }
        } catch (JSONException e) {
            LTLog.error(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }
}
