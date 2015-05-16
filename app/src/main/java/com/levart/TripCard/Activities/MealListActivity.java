package com.levart.TripCard.Activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.levart.TripCard.Card;
import com.levart.TripCard.FavoriteMealAdapter;
import com.levart.TripCard.NewMealActivity;
import com.levart.TripCard.R;
import com.parse.ParseQueryAdapter;


public class MealListActivity extends ListActivity {

	private ParseQueryAdapter<Card> mainAdapter;
	private FavoriteMealAdapter favoritesAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setClickable(false);

		mainAdapter = new ParseQueryAdapter<Card>(this, Card.class);
		mainAdapter.setTextKey("title");
		mainAdapter.setImageKey("photo");

		// Subclass of ParseQueryAdapter
		favoritesAdapter = new FavoriteMealAdapter(this);

		// Default view is all meals
		setListAdapter(favoritesAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_meal_list, menu);
		return true;
	}

	/*
	 * Posting meals and refreshing the list will be controlled from the Action
	 * Bar.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

            case R.id.action_refresh: {
                updateMealList();
                break;
            }

            case R.id.settings: {
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            }

            case R.id.action_new: {
                newMeal();
                break;
            }
        }
		return super.onOptionsItemSelected(item);
	}

	private void updateMealList() {
		mainAdapter.loadObjects();
		setListAdapter(mainAdapter);
	}

	private void showFavorites() {
		favoritesAdapter.loadObjects();
		setListAdapter(favoritesAdapter);
	}

	private void newMeal() {
		Intent i = new Intent(this, NewMealActivity.class);
		startActivityForResult(i, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			// If a new post has been added, update
			// the list of posts
			updateMealList();
		}
	}

}
