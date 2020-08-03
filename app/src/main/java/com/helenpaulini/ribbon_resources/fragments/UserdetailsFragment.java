package com.helenpaulini.ribbon_resources.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.helenpaulini.ribbon_resources.R;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserdetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserdetailsFragment extends Fragment {

    public static final String TAG = "User details";

    private ImageView profile_image;
    private TextView firstName;
    private TextView lastName;
    private TextView birthday;
    private TextView city;
    private TextView bio;
    private TextView interests;
    private TextView userType;
    private TextView cancerType;
    private TextView hospital;
    private TextView treatmentType;
    private TextView treatmentStart;
    private TextView treatmentEnd;
    private TextView email;
    private TextView phone;
    private TextView facebook;
    private TextView instagram;

    private Profile profile;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserdetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserdetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserdetailsFragment newInstance(String param1, String param2) {
        UserdetailsFragment fragment = new UserdetailsFragment();
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
        return inflater.inflate(R.layout.fragment_userdetails, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profile = getArguments().getParcelable("profileDetails");

        setViews(view);
        setTexts();
        Glide.with(getContext()).load((profile.getImage()).getUrl()).into(profile_image);
    }

    private void setViews(View view){
        profile_image = view.findViewById(R.id.profile_image);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        birthday = view.findViewById(R.id.birthday);
        city = view.findViewById(R.id.city);
        bio = view.findViewById(R.id.bio);
        interests = view.findViewById(R.id.interests);
        userType = view.findViewById(R.id.userType);
        cancerType = view.findViewById(R.id.cancerType);
        hospital = view.findViewById(R.id.hospital);
        treatmentType = view.findViewById(R.id.treatmentType);
        treatmentStart = view.findViewById(R.id.treatmentStart);
        treatmentEnd = view.findViewById(R.id.treatmentEnd);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        facebook = view.findViewById(R.id.facebook);
        instagram = view.findViewById(R.id.instagram);
    }

    private void setTexts(){
        firstName.setText(profile.getFirstName());
        lastName.setText(profile.getLastName());
        birthday.setText(profile.getBirthday());
        city.setText(profile.getCity());
        bio.setText(profile.getBio());
        interests.setText(profile.getInsterests());
        userType.setText(profile.getUserType());
        cancerType.setText(profile.getCancerType());
        hospital.setText(profile.getHospital());
        treatmentType.setText(profile.getTreatmentType());
        treatmentStart.setText(profile.getTreatmentStart());
        treatmentEnd.setText(profile.getTreatmentEnd());
        email.setText(profile.getFirstName());
        phone.setText(profile.getFirstName());
        facebook.setText(profile.getFirstName());
        instagram.setText(profile.getFirstName());
    }
}