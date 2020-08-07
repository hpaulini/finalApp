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
import com.helenpaulini.ribbon_resources.models.ContactInfo;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.parse.ParseException;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserdetailsnocontactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserdetailsnocontactFragment extends Fragment {

    public static final String TAG = "Nocontactdetails";

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

    private Profile profile;
    private ContactInfo contactInfo;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserdetailsnocontactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserdetailsnocontactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserdetailsnocontactFragment newInstance(String param1, String param2) {
        UserdetailsnocontactFragment fragment = new UserdetailsnocontactFragment();
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
        return inflater.inflate(R.layout.fragment_userdetailsnocontact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profile = getArguments().getParcelable("profileDetails");
        try {
            if(profile.fetchIfNeeded().getParseUser("user").getParseObject("contactInfo")!=null) {
                contactInfo = (ContactInfo) profile.fetchIfNeeded().getParseUser("user").getParseObject("contactInfo");
                contactInfo.fetchInBackground();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
    }

    private void setTexts(){
        firstName.setText(profile.getFirstName());
        lastName.setText(profile.getLastName());
        birthday.setText(getAge(profile.getBirthday())+" years old");
        bio.setText(profile.getBio());
        interests.setText(profile.getInsterests());
        userType.setText(profile.getUserType());
        cancerType.setText(profile.getCancerType());
        hospital.setText(profile.getHospital());
        treatmentType.setText(profile.getTreatmentType());
        treatmentStart.setText(profile.getTreatmentStart());
        treatmentEnd.setText(profile.getTreatmentEnd());
        try {
            if(profile.fetchIfNeeded().getParseUser("user").getParseObject("contactInfo")!=null) {
                city.setText(contactInfo.fetchIfNeeded().getString("address"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getAge(String birthday){
        String monthString = birthday.substring(0, 3);
        String dayString = birthday.substring(4, birthday.indexOf(','));
        String yearString = birthday.substring(birthday.indexOf(',')+2);

        int month = monthToInt(monthString);
        int year = Integer.parseInt(yearString);
        int day = Integer.parseInt(dayString);
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    private int monthToInt(String month){
        int monthInt;
        if(month.equals("Jan")) {
            monthInt = 1;
        } else if (month.equals("Feb")){
            monthInt = 2;
        } else if (month.equals("Mar")){
            monthInt = 3;
        } else if (month.equals("Apr")){
            monthInt = 4;
        } else if (month.equals("May")){
            monthInt = 5;
        } else if (month.equals("Jun")){
            monthInt = 6;
        } else if (month.equals("Jul")){
            monthInt = 7;
        } else if (month.equals("Aug")){
            monthInt = 8;
        } else if (month.equals("Sep")){
            monthInt = 9;
        } else if (month.equals("Oct")){
            monthInt = 10;
        } else if (month.equals("Nov")){
            monthInt = 11;
        } else {
            monthInt = 12;
        }
        return monthInt;
    }
}