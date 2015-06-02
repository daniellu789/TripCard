package com.levart.TripCard.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.levart.TripCard.API.LTAPIConstants;
import com.levart.TripCard.Feedback;
import com.levart.TripCard.R;
import com.levart.TripCard.utils.Utils;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class FeedbackActivity extends ActionBarActivity {

    private TextView feedback;
    private TextView contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.actionBarSytle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedback = ((TextView) findViewById(R.id.feedback_content));
        contact = ((TextView) findViewById(R.id.feedback_contact));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.feedback_submit) {
            String sFeedbackContent = feedback.getText().toString();
            String sFeedbackContact = contact.getText().toString();
            if(Utils.isEmptyString(sFeedbackContent)) {
                returnAlert();
            } else {
                Feedback fb = new Feedback();
                fb.setFeedback(sFeedbackContent);
                fb.setFeedbackContact(sFeedbackContact);
                fb.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                    if (e == null) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                    }
                    }
                });
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void returnAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create(); //Read Update
        alertDialog.setMessage(getResources().getString(R.string.feedback_empty_warn));
        alertDialog.setButton("OK!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
}
