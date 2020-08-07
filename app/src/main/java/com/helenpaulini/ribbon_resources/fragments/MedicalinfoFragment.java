package com.helenpaulini.ribbon_resources.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.helenpaulini.ribbon_resources.MainActivity;
import com.helenpaulini.ribbon_resources.R;
import com.helenpaulini.ribbon_resources.models.Hospital;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.hootsuite.nachos.validator.ChipifyingNachoValidator;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicalinfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicalinfoFragment extends Fragment {

    public static final String TAG = "Medicalinfo";
    public static final String API_URL = "https://services1.arcgis.com/Hp6G80Pky0om7QvQ/arcgis/rest/services/Hospitals_1/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json";

    //private static String[] SUGGESTIONS = new String[]{"Nachos", "Chip", "Tortilla Chips", "Melted Cheese", "Salsa", "Guacamole", "Cheddar", "Mozzarella", "Mexico", "Jalapeno"};

    List<Hospital> hospitals = new ArrayList<>();
    ArrayList<String> items = new ArrayList<>();
    SpinnerDialog spinnerDialog;

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
    private NachoTextView interestsText;
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

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(API_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onsuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray features = jsonObject.getJSONArray("features");
                    hospitals = Hospital.fromJsonArray(features); //hospitals is a list of [{key:{key1:value, key2:value, ...}}, ...]
                    items = hospitalNameList(hospitals);
                    spinnerDialog = new SpinnerDialog(getActivity(), hospitalNameList(hospitals),
                            "Search and Select a Hospital");

                    spinnerDialog.setTitleColor(getResources().getColor(R.color.pink));
                    spinnerDialog.setSearchIconColor(getResources().getColor(R.color.pink));
                    spinnerDialog.setSearchTextColor(getResources().getColor(R.color.pink));
                    spinnerDialog.setItemColor(getResources().getColor(R.color.pink));
                    spinnerDialog.setItemDividerColor(getResources().getColor(R.color.lightpink));
                    spinnerDialog.setCloseColor(getResources().getColor(R.color.pink));

                    spinnerDialog.setCancellable(true);
                    spinnerDialog.setShowKeyboard(false);

                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
                            hospitalText.setText(item);
                        }
                    });

                    //view.findViewById(R.id.show).setOnClickListener(new View.OnClickListener()
                    hospitalText.setInputType(InputType.TYPE_NULL);
                    hospitalText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            spinnerDialog.showSpinerDialog();
                        }
                    });

                } catch (JSONException e) {
                    Log.e(TAG, "json exception", e);
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onfailure", throwable);
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.fetchInBackground();

        setViews(view);
        setPreviouslyInputted(currentUser);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Treatment Start");
        final MaterialDatePicker materialDatePicker = builder.build();

        startText.setInputType(InputType.TYPE_NULL);
        startText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getFragmentManager(), "Date Picker");
            }
        });

        MaterialDatePicker.Builder builder1 = MaterialDatePicker.Builder.datePicker();
        builder1.setTitleText("Treatment End");
        final MaterialDatePicker materialDatePicker1 = builder.build();

        endText.setInputType(InputType.TYPE_NULL);
        endText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker1.show(getFragmentManager(), "Date Picker");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                startText.setText(materialDatePicker.getHeaderText());
            }
        });

        materialDatePicker1.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                endText.setText(materialDatePicker1.getHeaderText());
            }
        });

        savePersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userType = getRadioButtonString();
                String hospital = hospitalText.getText().toString();
                String cancer = cancerText.getText().toString();
                String treatment = treatmentText.getText().toString();
                String start = startText.getText().toString();
                String end = endText.getText().toString();
                String interests = "";
                for(int i=0; i<interestsText.getChipAndTokenValues().size(); i++){
                    interests = interests + interestsText.getChipAndTokenValues().get(i)+", ";
                }
                saveToProfile(userType, hospital, cancer, treatment, start, end, interests);
            }
        });
    }

    private ArrayList<String> hospitalNameList(List<Hospital> hospitals){
        ArrayList<String> hospitalNameList = new ArrayList<>();
        for(int i=0; i<hospitals.size(); i++){
            hospitalNameList.add(fixCapitalization(hospitals.get(i).getName()));
        }
        String[] splitString = getResources().getString(R.string.additionalHospitals).split("[\r\n]+");
        List<String> myResArrayList = Arrays.asList(splitString);
        hospitalNameList.addAll(myResArrayList);
        Collections.sort(hospitalNameList);

        return hospitalNameList;
    }

    private String fixCapitalization(String name){
        String properlyCapitalizedString = "";
        String lowerCase = name.toLowerCase();
        String[] splitString = lowerCase.split("\\s+");
        for(int i=0; i<splitString.length; i++){
            properlyCapitalizedString = properlyCapitalizedString + splitString[i].substring(0,1).toUpperCase()+splitString[i].substring(1)+" ";
        }
        return properlyCapitalizedString;
    }

    private void setViews(View view){
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

        setupChipTextView(interestsText);
    }

    private void setupChipTextView(NachoTextView nachoTextView) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_dropdown_item_1line);
        nachoTextView.setAdapter(adapter);
        nachoTextView.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
        //nachoTextView.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);
        nachoTextView.addChipTerminator(';', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);
        nachoTextView.addChipTerminator(',', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);
        nachoTextView.setNachoValidator(new ChipifyingNachoValidator());
        nachoTextView.enableEditChipOnTouch(true, true);
        nachoTextView.setOnChipClickListener(new NachoTextView.OnChipClickListener() {
            @Override
            public void onChipClick(Chip chip, MotionEvent motionEvent) {
                Log.d(TAG, "onChipClick: " + chip.getText());
            }
        });
    }

    private void setPreviouslyInputted(ParseUser currentUser){
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
    }

    private String getRadioButtonString(){
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

    private void saveToProfile(String userType, String hospital, String cancer, String treatment, String start, String end, String interests){
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

    private void setRadioButtonSelected(String userType){
        if(userType.equals("Current Patient")){
            currentPatient.setChecked(true);
        } else if(userType.equals("Previous Patient")){
            previousPatient.setChecked(true);
        } else {
            parentOfPatient.setChecked(true);
        }
    }
}