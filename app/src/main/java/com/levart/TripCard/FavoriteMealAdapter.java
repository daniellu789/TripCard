package com.levart.TripCard;

import java.util.Arrays;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.levart.TripCard.API.LTAPIConstants;
import com.levart.TripCard.utils.LTLog;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;


/*
 * The FavoriteMealAdapter is an extension of ParseQueryAdapter
 * that has a custom layout for favorite meals, including a 
 * bigger preview image, the meal's rating, and a "favorite"
 * star. 
 */

public class FavoriteMealAdapter extends ParseQueryAdapter<Card> {

    private SharedPreferences sharePrefs;


	public FavoriteMealAdapter(final Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<Card>() {
			public ParseQuery<Card> create() {
				// Here we can configure a ParseQuery to display
				// only top-rated meals.
				ParseQuery query = new ParseQuery("Card");
                SharedPreferences sharePrefs =
                        PreferenceManager.getDefaultSharedPreferences(context);
                int tag = Integer.parseInt(sharePrefs.getString("tag_list",
                        "0"));
                if (tag != 0)
                {
                    query.whereContainedIn("tag", Arrays.asList(tag));
                }
				query.orderByDescending("tag");
				return query;
			}
		});
	}

	@Override
	public View getItemView(Card card, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.item_list_favorites, null);
		}

		super.getItemView(card, v, parent);

		ParseImageView mealImage = (ParseImageView) v.findViewById(R.id.icon);
		ParseFile photoFile = card.getParseFile("photo");
		if (photoFile != null) {
			mealImage.setParseFile(photoFile);
			mealImage.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					// nothing to do
				}
			});
		}

		TextView titleTextView = (TextView) v.findViewById(R.id.text1);
		titleTextView.setText(card.getTitle());
		TextView ratingTextView = (TextView) v
				.findViewById(R.id.favorite_meal_rating);
        LTLog.debug("test", LTAPIConstants.TAG_TO_NAME.toString());
		ratingTextView.setText(LTAPIConstants.KEYWORD+LTAPIConstants.TAG_TO_NAME.get(card.getTag()));
		return v;
	}

}
