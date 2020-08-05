package com.helenpaulini.ribbon_resources.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.helenpaulini.ribbon_resources.R;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NameAndBioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NameAndBioFragment extends Fragment {

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    public static final String TAG = "NameBio";

    private ImageView profile_image;
    private File photoFile;
    public String photoFileName = "photo.jpg";

    private Button btnAddPic;
    private TextInputEditText etFirst;
    private TextInputEditText etLast;
    private TextInputEditText bday;
    private TextInputEditText biography;
    private Button saveProf;

    private String firstName, lastName, birthday, bio;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NameAndBioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NameAndBioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NameAndBioFragment newInstance(String param1, String param2) {
        NameAndBioFragment fragment = new NameAndBioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name_and_bio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.fetchInBackground();

        setViews(view);
        setPreviouslyInputted(currentUser);
        btnAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });

        saveProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = etFirst.getText().toString();
                lastName = etLast.getText().toString();
                birthday = bday.getText().toString();
                bio = biography.getText().toString();

                if (firstName.isEmpty()) {
                    Toast.makeText(getContext(), "Required fields cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveProfile(photoFile, firstName, lastName, birthday, bio);
            }
        });
    }

    private void setViews(View view) {
        profile_image = view.findViewById(R.id.profile_image);
        btnAddPic = view.findViewById(R.id.btnAddPic);
        etFirst = view.findViewById(R.id.first);
        etLast = view.findViewById(R.id.last);
        bday = view.findViewById(R.id.bday);
        biography = view.findViewById(R.id.biography);
        saveProf = view.findViewById(R.id.saveProf);
    }

    private void setPreviouslyInputted(ParseUser currentUser) {
        //if this user already has made a profile, then restore the edit text field inputs
        try {
            if (currentUser.fetchIfNeeded().getParseObject("profile") != null) {
                Profile currentProfile = (Profile) currentUser.fetchIfNeeded().getParseObject("profile");
                if (currentProfile.fetchIfNeeded().getParseFile("profilePic") != null) {
                    Glide.with(getContext()).load((currentProfile.getImage()).getUrl()).into(profile_image);
                }
                if (currentProfile.fetchIfNeeded().getString("firstName") != null) {
                    etFirst.setText(currentProfile.fetchIfNeeded().getString("firstName"));
                }
                if (currentProfile.fetchIfNeeded().getString("lastName") != null) {
                    etLast.setText(currentProfile.fetchIfNeeded().getString("lastName"));
                }
                if (currentProfile.fetchIfNeeded().getString("birthday") != null) {
                    bday.setText(currentProfile.fetchIfNeeded().getString("birthday"));
                }
                if (currentProfile.fetchIfNeeded().getString("bio") != null) {
                    biography.setText(currentProfile.fetchIfNeeded().getString("bio"));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    private File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // Load the taken image into a preview
                profile_image.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveProfile(File photoFile, String firstName, String lastName, String birthday, String bio) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.fetchInBackground();
        Profile currentProfile;
        Log.i(TAG, "current user: " +currentUser.getUsername());
        if(currentUser.getParseObject("profile")!=null){
            currentProfile = (Profile) currentUser.getParseObject("profile");
            currentProfile.fetchInBackground();
        } else {
            currentProfile = new Profile();
            currentProfile.fetchInBackground();
        }

        currentProfile.setFirstName(firstName);
        currentProfile.setLastName(lastName);
        currentProfile.setBirthday(birthday);
        currentProfile.setBio(bio);
        currentProfile.put("user", currentUser);

        currentProfile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Profile saved successfully!!");
            }
        });

        currentUser.put("profile", currentProfile);
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
            }
        });
    }
}