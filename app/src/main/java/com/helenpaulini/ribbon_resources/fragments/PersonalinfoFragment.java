package com.helenpaulini.ribbon_resources.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.helenpaulini.ribbon_resources.MainActivity;
import com.helenpaulini.ribbon_resources.MyBounceInterpolator;
import com.helenpaulini.ribbon_resources.R;
import com.helenpaulini.ribbon_resources.models.ContactInfo;
import com.helenpaulini.ribbon_resources.models.Hospital;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalinfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalinfoFragment extends Fragment {
    public static final String TAG = "Contact Info";

    private TextInputEditText cityText;
    private TextInputEditText emailText;
    private TextInputEditText phoneText;
    private TextInputEditText facebookText;
    private TextInputEditText instagramText;
    private Button saveContactInfo;
    private Button getMatches;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PersonalinfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalinfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalinfoFragment newInstance(String param1, String param2) {
        PersonalinfoFragment fragment = new PersonalinfoFragment();
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
        return inflater.inflate(R.layout.fragment_personalinfo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.fetchInBackground();

        setViews(view);
        setPreviouslyInputted(currentUser);

        saveContactInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);
                getMatches.startAnimation(myAnim);
                String city = cityText.getText().toString();
                String email = emailText.getText().toString();
                String phone = phoneText.getText().toString();
                String facebook = facebookText.getText().toString();
                String instagram = instagramText.getText().toString();
                saveToContact(city, email, phone, facebook, instagram);
            }
        });

        getMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMatches();
            }
        });
    }

    private void setViews(View view){
        cityText = view.findViewById(R.id.cityText);
        emailText = view.findViewById(R.id.emailText);
        phoneText = view.findViewById(R.id.phoneText);
        facebookText = view.findViewById(R.id.facebookText);
        instagramText = view.findViewById(R.id.instagramText);
        saveContactInfo = view.findViewById(R.id.saveContactInfo);
        getMatches = view.findViewById(R.id.getMatches);
    }

    private void setPreviouslyInputted(ParseUser currentUser){
        //if this user already has made a profile, then restore the edit text field inputs
        try {
            if(currentUser.fetchIfNeeded().getParseObject("contactInfo")!=null){
                ContactInfo currentContact = (ContactInfo) currentUser.fetchIfNeeded().getParseObject("contactInfo");
                if(currentContact.fetchIfNeeded().getString("address")!=null){
                    cityText.setText(currentContact.fetchIfNeeded().getString("address"));
                }
                if(currentContact.fetchIfNeeded().getString("email")!=null){
                    emailText.setText(currentContact.fetchIfNeeded().getString("email"));
                }
                if(currentContact.fetchIfNeeded().getString("phone")!=null){
                    phoneText.setText(currentContact.fetchIfNeeded().getString("phone"));
                }
                if(currentContact.fetchIfNeeded().getString("facebook")!=null){
                    facebookText.setText(currentContact.fetchIfNeeded().getString("facebook"));
                }
                if(currentContact.fetchIfNeeded().getString("instagram")!=null){
                    instagramText.setText(currentContact.fetchIfNeeded().getString("instagram"));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void saveToContact(String city, String email, String phone, String facebook, String instagram){
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.fetchInBackground();
        ContactInfo contact;

        if(currentUser.getParseObject("contactInfo")!=null){
            contact = (ContactInfo) currentUser.getParseObject("contactInfo");
        } else {
            contact = new ContactInfo();
            contact.setUser(currentUser);
        }

        contact.setAddress(city);
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setFacebook(facebook);
        contact.setInstagram(instagram);

        contact.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Profile saved successfully!!");

                currentUser.put("contactInfo", contact);
                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                    }
                });
            }
        });
    }

    public void goToMatches(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fts = fm.beginTransaction();
        fts.replace(R.id.flContainer, new ResourcesFragment());
        fts.addToBackStack(TAG);
        fts.commit();

        BottomNavigationView bottom = getActivity().findViewById(R.id.bottomNavigation);
        bottom.setSelectedItemId(R.id.dashboard);
//        MainActivity main = new MainActivity();
//        BottomNavigationView bottomNav = main.getBottomNavigationView();
//        bottomNav.setSelectedItemId(R.id.dashboard);
    }
}