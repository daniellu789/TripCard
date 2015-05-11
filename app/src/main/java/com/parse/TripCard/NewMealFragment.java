package com.parse.TripCard;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.levart.TripCard.R;
import com.parse.GetDataCallback;
import com.parse.TripCard.API.LTAPIConstants;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * This fragment manages the data entry for a
 * new Meal object. It lets the user input a 
 * meal name, give it a rating, and take a 
 * photo. If there is already a photo associated
 * with this meal, it will be displayed in the 
 * preview at the bottom, which is a standalone
 * ParseImageView.
 */
public class NewMealFragment extends Fragment {

	private ImageButton photoButton;
	private Button saveButton;
	private Button cancelButton;
	private TextView mealName;
	private Spinner mealRating;
	private ParseImageView mealPreview;

    private ParseFile photoFile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle SavedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_new_meal, parent, false);

		mealName = ((EditText) v.findViewById(R.id.meal_name));

		// The mealRating spinner lets people assign favorites of meals they've
		// eaten.
		// Meals with 4 or 5 ratings will appear in the Favorites view.
		mealRating = ((Spinner) v.findViewById(R.id.rating_spinner));
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.ratings_array,
						android.R.layout.simple_spinner_dropdown_item);
		mealRating.setAdapter(spinnerAdapter);

		photoButton = ((ImageButton) v.findViewById(R.id.photo_button));
		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				InputMethodManager imm = (InputMethodManager) getActivity()
//						.getSystemService(Context.INPUT_METHOD_SERVICE);
//				imm.hideSoftInputFromWindow(mealName.getWindowToken(), 0);
//				startCamera();


                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
			}
		});

		saveButton = ((Button) v.findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Card card = ((NewMealActivity) getActivity()).getCurrentMeal();

				// When the user clicks "Save," upload the meal to Parse
				// Add data to the meal object:
				card.setTitle(mealName.getText().toString());

				// Associate the meal with the current user
				card.setAuthor(ParseUser.getCurrentUser());

				// Add the rating
				card.setTag(LTAPIConstants.NAME_TO_TAG.get(mealRating.getSelectedItem().toString()));

				// If the user added a photo, that data will be
				// added in the CameraFragment

				// Save the meal and return
				card.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							getActivity().setResult(Activity.RESULT_OK);
							getActivity().finish();
						} else {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Error saving: " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					}

				});

			}
		});

		cancelButton = ((Button) v.findViewById(R.id.cancel_button));
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().setResult(Activity.RESULT_CANCELED);
				getActivity().finish();
			}
		});

		// Until the user has taken a photo, hide the preview
		mealPreview = (ParseImageView) v.findViewById(R.id.meal_preview_image);
		mealPreview.setVisibility(View.INVISIBLE);

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
		transaction.addToBackStack("NewMealFragment");
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
		ParseFile photoFile = ((NewMealActivity) getActivity())
				.getCurrentMeal().getPhotoFile();
		if (photoFile != null) {
			mealPreview.setParseFile(photoFile);
			mealPreview.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					mealPreview.setVisibility(View.VISIBLE);
				}
			});
		}
	}

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
                    matrix.postRotate(90);
                    Bitmap rotatedScaledMealImage = Bitmap.createBitmap(mealImageScaled, 0,
                            0, mealImageScaled.getWidth(), mealImageScaled.getHeight(),
                            matrix, true);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    rotatedScaledMealImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                    byte[] scaledData = bos.toByteArray();
                    photoFile = new ParseFile("meal_photo.jpg", scaledData);

                    photoFile.saveInBackground(new SaveCallback() {

                        public void done(ParseException e) {
                            if (e != null) {
                                Toast.makeText(getActivity(),
                                        "Error saving: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(),
                                        "Saved???? ",
                                        Toast.LENGTH_LONG).show();
                                addPhotoToMealAndReturn(photoFile);
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
        ((NewMealActivity) getActivity()).getCurrentMeal().setPhotoFile(
                photoFile);
        FragmentManager fm = getActivity().getFragmentManager();
        fm.popBackStack("NewMealFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
