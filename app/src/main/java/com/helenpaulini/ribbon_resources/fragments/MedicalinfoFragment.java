package com.helenpaulini.ribbon_resources.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.helenpaulini.ribbon_resources.R;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicalinfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicalinfoFragment extends Fragment {

    public static final String TAG = "Medicalinfo";

    private RadioGroup radio_group;
    private RadioButton currentPatient;
    private RadioButton previousPatient;
    private RadioButton parentOfPatient;
    private RadioButton selectedRadioButton;

    private TextInputEditText hospitalText;
    private TextInputEditText cancerText;
    private TextInputEditText treatmentText;
    private TextInputEditText startText;
    private TextInputEditText endText;
    private TextInputEditText interestsText;
    private Button savePersonalInfo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MedicalinfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicalinfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicalinfoFragment newInstance(String param1, String param2) {
        MedicalinfoFragment fragment = new MedicalinfoFragment();
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
        return inflater.inflate(R.layout.fragment_medicalinfo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.fetchInBackground();

        radio_group = view.findViewById(R.id.radio_group);
        currentPatient = view.findViewById(R.id.currentPatient);
        previousPatient = view.findViewById(R.id.previousPatient);
        parentOfPatient = view.findViewById(R.id.parentOfPatient);

        hospitalText = view.findViewById(R.id.hospitalText);
        cancerText = view.findViewById(R.id.cancerText);
        treatmentText = view.findViewById(R.id.treatmentText);
        startText = view.findViewById(R.id.startText);
        endText = view.findViewById(R.id.endText);
        interestsText = view.findViewById(R.id.interestsText);
        savePersonalInfo = view.findViewById(R.id.savePersonalInfo);

        //if this user already has made a profile, then restore the edit text field inputs
        try {
            if(currentUser.fetchIfNeeded().getParseObject("profile")!=null){
                Profile currentProfile = (Profile) currentUser.fetchIfNeeded().getParseObject("profile");
                if(currentProfile.fetchIfNeeded().getString("userType")!=null){
                    setRadioButtonSelected(currentProfile.fetchIfNeeded().getString("userType"));
                }
                if(currentProfile.fetchIfNeeded().getString("hospital")!=null){
                    hospitalText.setText(currentProfile.fetchIfNeeded().getString("hospital"));
                }
                if(currentProfile.fetchIfNeeded().getString("cancerType")!=null){
                    cancerText.setText(currentProfile.fetchIfNeeded().getString("cancerType"));
                }
                if(currentProfile.fetchIfNeeded().getString("treatmentType")!=null){
                    treatmentText.setText(currentProfile.fetchIfNeeded().getString("treatmentType"));
                }
                if(currentProfile.fetchIfNeeded().getString("treatmentStart")!=null){
                    startText.setText(currentProfile.fetchIfNeeded().getString("treatmentStart"));
                }
                if(currentProfile.fetchIfNeeded().getString("treatmentEnd")!=null){
                    endText.setText(currentProfile.fetchIfNeeded().getString("treatmentEnd"));
                }
                if(currentProfile.fetchIfNeeded().getString("interests")!=null){
                    interestsText.setText(currentProfile.fetchIfNeeded().getString("interests"));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        savePersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userType = getRadioButtonString();
                String hospital = hospitalText.getText().toString();
                String cancer = cancerText.getText().toString();
                String treatment = treatmentText.getText().toString();
                String start = startText.getText().toString();
                String end = endText.getText().toString();
                String interests = interestsText.getText().toString();
                saveToProfile(userType, hospital, cancer, treatment, start, end, interests);
            }
        });
    }

    public String getRadioButtonString(){
        int selectedButtonID = radio_group.getCheckedRadioButtonId();
        if(selectedButtonID == currentPatient.getId())
        {
            return "Current Patient";
        } else if(selectedButtonID == previousPatient.getId()){
            return "Previous Patient";
        } else {
            return "Parent of Patient";
        }
    }

    public void saveToProfile(String userType, String hospital, String cancer, String treatment, String start, String end, String interests){
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.fetchInBackground();
        try {
            Profile currentProfile = (Profile) currentUser.fetchIfNeeded().getParseObject("profile");

            currentProfile.setUserType(userType);
            currentProfile.setHospital(hospital);
            currentProfile.setCancerType(cancer);
            currentProfile.setTreatmentType(treatment);
            currentProfile.setTreatmentStart(start);
            currentProfile.setTreatmentEnd(end);
            currentProfile.setInterests(interests);

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
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setRadioButtonSelected(String userType){
        if(userType.equals("Current Patient")){
            currentPatient.setChecked(true);
        } else if(userType.equals("Previous Patient")){
            previousPatient.setChecked(true);
        } else {
            parentOfPatient.setChecked(true);
        }
    }
}