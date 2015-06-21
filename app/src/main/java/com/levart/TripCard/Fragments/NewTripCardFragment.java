package com.levart.TripCard.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.levart.TripCard.API.LTConfig;
import com.levart.TripCard.Activities.NewTripCardActivity;
import com.levart.TripCard.Adapters.GooglePlacesAutocompleteAdapter;
import com.levart.TripCard.R;
import com.levart.TripCard.TripCard;
import com.levart.TripCard.utils.Utils;
import com.levart.TripCard.API.LocationElement;
import com.levart.TripCard.utils.LTLog;
import com.levart.TripCard.API.LTAPIConstants;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * This fragment manages the data entry for a
 * new Meal object. It lets the user input a
 * meal name, give it a rating, and take a
 * photo. If there is already a photo associated
 * with this meal, it will be displayed in the
 * preview at the bottom, which is a standalone
 * ParseImageView.
 */
public class NewTripCardFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ImageButton imagePreviewButton;
    private ImageButton photoButton;
    private Button saveButton;
    private Button cancelButton;
//    private TextView cardTitle;
    private TextView cardDescription;
    private Spinner cardTag;
    private ParseImageView cardPhotoPreview;
    private ImageView closeImage;
    private LocationElement location;
    private AutoCompleteTextView autoCompView;
    private TextView author;
    private static final String TAKE_PHOTO = LTAPIConstants.LOCALIZE.get("KEY_CAMERA");
    private static final String CANCEL = LTAPIConstants.LOCALIZE.get("KEY_CANCEL");;
    private static final String GALLERY = LTAPIConstants.LOCALIZE.get("KEY_GALLERY");;
    private static final String ADD_PHOTO = LTAPIConstants.LOCALIZE.get("KEY_ADD A PHOTO");;


    private ParseFile photoFile;

    private static final String LOG_TAG = NewTripCardFragment.class.getSimpleName();


    private static GooglePlacesAutocompleteAdapter googlePlaceAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_tripcard, parent, false);
        googlePlaceAdapter = new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.list_item);
//        cardTitle = ((EditText) v.findViewById(R.id.meal_name));
        cardDescription = ((EditText) v.findViewById(R.id.card_description));
        author = ((EditText) v.findViewById(R.id.card_author));
        LTLog.debug(LOG_TAG,"creating a new card");
        cardTag = ((Spinner) v.findViewById(R.id.rating_spinner));
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.ratings_array,
                        android.R.layout.simple_spinner_dropdown_item);
        cardTag.setAdapter(spinnerAdapter);

        cardPhotoPreview = (ParseImageView) v.findViewById(R.id.meal_preview_image);
        cardPhotoPreview.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
            selectImage();
             }
         });

        autoCompView = (AutoCompleteTextView) v.findViewById(R.id.cardLocationAutoCompleteTextView);
        autoCompView.setAdapter(googlePlaceAdapter);
        autoCompView.setOnItemClickListener(this);
        return v;
    }


    /*
     * All data entry about a Meal object is managed from the NewMealActivity.
     * When the user wants to add a photo, we'll start up a custom
     * CameraFragment that will let them take the photo and save it to the Meal
     * object owned by the NewMealActivity. Create a new CameraFragment, swap
     * the contents of the fragmentContainer (see activity_new_meal.xml), then
     * add the NewMealFragment to the back stack so we can return to it when the
     * camera is finished.
     */
    public void startCamera() {
        Fragment cameraFragment = new CameraFragment();
        FragmentTransaction transaction = getActivity().getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragmentContainer, cameraFragment);
        transaction.addToBackStack("NewTripCardFragment");
        transaction.commit();
    }

    /*
     * On resume, check and see if a meal photo has been set from the
     * CameraFragment. If it has, load the image in this fragment and make the
     * preview image visible.
     */
    @Override
    public void onResume() {
        super.onResume();
        photoFile = ((NewTripCardActivity) getActivity())
                .getCurrentTripCard().getPhotoFile();
        if (photoFile != null) {
            cardPhotoPreview.setParseFile(photoFile);
            cardPhotoPreview.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    cardPhotoPreview.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    // image setting from Gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            ParcelFileDescriptor parcelFileDescriptor;
            try {
                parcelFileDescriptor = getActivity().getContentResolver().openFileDescriptor(selectedImageUri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                Bitmap mealImage = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();


                Bitmap mealImageScaled = Bitmap.createScaledBitmap(mealImage,
                        LTAPIConstants.IMAGE_WIDTH_SIZE, LTAPIConstants.IMAGE_WIDTH_SIZE
                                * mealImage.getHeight() / mealImage.getWidth(), false);

                Matrix matrix = new Matrix();
                //matrix.postRotate(90);
                Bitmap rotatedScaledMealImage = Bitmap.createBitmap(mealImageScaled, 0,
                        0, mealImageScaled.getWidth(), mealImageScaled.getHeight(),
                        matrix, true);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                rotatedScaledMealImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                byte[] scaledData = bos.toByteArray();
                photoFile = new ParseFile("tripcard_photo.jpg", scaledData);
                addPhotoToMealAndReturn(photoFile);

                photoFile.saveInBackground(new SaveCallback() {

                    public void done(ParseException e) {
                    if (e != null) {
                        Toast.makeText(getActivity(),
                            "Error saving: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                        } else {
                        debugShowToast("Saved???? ");
                    }
                    }
                });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // Override Android default landscape orientation and save portrait
        }
    }

    private void addPhotoToMealAndReturn(ParseFile photoFile) {
        ((NewTripCardActivity) getActivity()).getCurrentTripCard().setPhotoFile(
                photoFile);
        FragmentManager fm = getActivity().getFragmentManager();
        fm.popBackStack("MealListActivity",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        location = googlePlaceAdapter.getLocationElementItem(position);
        String countryCode = Utils.getCountryCodeFromString(location.getFullName());
        location.setCountryCode(countryCode);
        debugShowToast(location.getFullName() + "  ||  " + location.getCountryCode());
    }

    private void selectImage() {
        LTLog.debug(LOG_TAG, "begin to select image");
        final CharSequence[] items = { TAKE_PHOTO, GALLERY,
                CANCEL };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(ADD_PHOTO);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
            if (items[item].equals(TAKE_PHOTO)) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(cardTitle.getWindowToken(), 0);
                startCamera();
            } else if (items[item].equals(GALLERY)) {
//                 Intent intent = new Intent(Intent.ACTION_PICK,
//                         android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                 intent.setType("image/*");
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
//                 startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
            } else if (items[item].equals(CANCEL)) {
                 dialog.dismiss();
            }
            }
        });
        builder.create().show();
        LTLog.debug(LOG_TAG, "set up select image");
    }

    private void debugShowToast(String content) {
        if(LTConfig.DEBUG)
        {
            Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
        }
    }

    private void ShowToast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
    }

    public void returnAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create(); //Read Update
        alertDialog.setMessage(LTAPIConstants.LOCALIZE.get("KEY_DISCARD?"));
        alertDialog.setButton("OK!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });
        alertDialog.show();
    }

    public void submitCard() {
//        String title = cardTitle.getText().toString();
        String description = cardDescription.getText().toString();
        String tag = LTAPIConstants.NAME_TO_TAG.get(cardTag.getSelectedItem().toString());
        String locationText = autoCompView.getText().toString();
        String CardAuthor = author.getText().toString();
        boolean test = photoFile == null;
        LTLog.debug(LOG_TAG, "danjie: " + test);
        String emptyComplain = buildSubmitEmptyComplain(
                Utils.isEmptyString(description), "-1".equals(tag),
                Utils.isEmptyString(locationText), photoFile == null,
                Utils.isEmptyString(CardAuthor));
        if (!Utils.isEmptyString(emptyComplain)) {
            ShowToast(emptyComplain);
            return;
        }
        TripCard tCard = ((NewTripCardActivity) getActivity()).getCurrentTripCard();
        if (Utils.isEnglishchar(description.trim().charAt(0))) {
            tCard.setDescriptionLang("en");
        } else {
            tCard.setDescriptionLang("zh");
        }
//        tCard.setTitle(cardTitle.getText().toString());
        tCard.setDescription(cardDescription.getText().toString());
        tCard.setCountry(location.getCountryCode());
        tCard.setCardAuthor(CardAuthor);
        tCard.setLocationFullName(locationText);
        tCard.setStatus(1);
        tCard.setGooglelocationid(location.getLocationId());
        // Associate the meal with the current user
        tCard.setAuthor(ParseUser.getCurrentUser());

        // Add the rating
        tCard.setTag(LTAPIConstants.NAME_TO_TAG.get(cardTag.getSelectedItem().toString()));

        // If the user added a photo, that data will be
        // added in the CameraFragment

        // Save the meal and return
        tCard.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                } else {
                    debugShowToast("Error saving: " + e.getMessage());
                }
            }
        });
    }

    private String buildSubmitEmptyComplain(boolean bDescription, boolean bTag,
                                  boolean bLocation, boolean bPhoto, boolean bAuthor) {
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>();
//        if (bTitle) {
//            list.add("卡片名称");
//        }

        if (bPhoto) {
            list.add(LTAPIConstants.LOCALIZE.get("KEY_PHOTO"));
        }
        if (bLocation) {
            list.add(LTAPIConstants.LOCALIZE.get("KEY_LOCATION"));
        }
        if (bDescription) {
            list.add(LTAPIConstants.LOCALIZE.get("KEY_DESCRIPTION"));
        }
        if (bAuthor) {
            list.add(LTAPIConstants.LOCALIZE.get("KEY_AUTHOR"));
        }
        if (bTag) {
            list.add(LTAPIConstants.LOCALIZE.get("KEY_TAG"));
        }
        if (!list.isEmpty()) {
            sb.append(LTAPIConstants.LOCALIZE.get("KEY_PLEASE ADD:"));
            for (int i = 0; i < list.size() - 1; i++) {
                sb.append(list.get(i));
                sb.append(",");
            }
            sb.append(list.get(list.size() - 1));
            sb.append(LTAPIConstants.LOCALIZE.get("KEY_."));
        }
        return sb.toString();
    }


}