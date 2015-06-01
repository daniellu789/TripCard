package com.levart.TripCard.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.levart.TripCard.Fragments.NewTripCardFragment;
import com.levart.TripCard.R;
import com.levart.TripCard.TripCard;
import com.levart.TripCard.utils.LTLog;


/*
 * NewMealActivity contains two fragments that handle
 * data entry and capturing a photo of a given meal.
 * The Activity manages the overall meal data.
 */
public class NewTripCardActivity extends ActionBarActivity {

	private TripCard tCard;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        tCard = new TripCard();
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.newCard);

        super.onCreate(savedInstanceState);
		// Begin with main data entry view,
		// NewMealFragment
		setContentView(R.layout.activity_new_meal);
		FragmentManager manager = getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

		if (fragment == null) {
			fragment = new NewTripCardFragment();
			manager.beginTransaction().add(R.id.fragmentContainer, fragment)
					.commit();
		}
	}

	public TripCard getCurrentTripCard() {
		return tCard;
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater manueflater = getMenuInflater();
        manueflater.inflate(R.menu.new_tripcard_submit, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        final NewTripCardFragment fragment = (NewTripCardFragment) getFragmentManager().findFragmentById(R.id.fragmentContainer);
        fragment.returnAlert();
//        if (fragment.allowBackPressed()) { // and then you define a method allowBackPressed with the logic to allow back pressed or not
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        final NewTripCardFragment fragment = (NewTripCardFragment) getFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (id == android.R.id.home)
        {
            fragment.returnAlert();
            return true;

        } else if (id == R.id.submit) {
            fragment.submitCard();
        }
        return super.onOptionsItemSelected(item);
    }
}
