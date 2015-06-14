package com.levart.TripCard;

import com.parse.ParseACL;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;
/**
 * Created by dlu on 6/1/15.
 */
@ParseClassName("Feedback")
public class Feedback extends ParseObject implements Serializable {

    public static final String FEEDBACK_CONTENT = "feedback_content";
    public static final String FEEDBACK_CONTACT ="feedback_contact";
    public Feedback() {

    }

    public String getFeedback() {
        return getString(FEEDBACK_CONTENT);
    }

    public void setFeedback(String feedback) {
        put(FEEDBACK_CONTENT, feedback);
    }

    public String getFeedbackContact() {
        return getString(FEEDBACK_CONTACT);
    }

    public void setFeedbackContact(String contact) {
        put(FEEDBACK_CONTACT, contact);
    }

}
