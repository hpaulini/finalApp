package com.helenpaulini.ribbon_resources.utilities;

import android.util.Log;

import com.helenpaulini.ribbon_resources.models.PatientProfile;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.helenpaulini.ribbon_resources.models.SurvivorProfile;

public class Matching {

    public static final String TAG = "dashboard";

    private int matchCompatibility = 0;
    private boolean matchAllowed = false;
//    private Profile user1;
//    private Profile user2;

    public Matching(){

    }

//    public Matching(Profile user1, Profile user2){
//        this.user1 = user1;
//        this.user2 = user2;
//    }

    //usertype, city, hospital, cancertype, treatmenttype, birthday,
    //years of treatment, years post treatment, interests
    public int matchValue(Profile user1, Profile user2){
        Log.i(TAG, "usertype of "+ user1.getUser().getUsername() + ": "+ user1.getIsCurrentPatient());
        Log.i(TAG, "usertype of "+ user2.getUser().getUsername() + ": "+ user2.getIsCurrentPatient());

        if (user1.getIsCurrentPatient().equals(user2.getIsCurrentPatient())){
            return 0; //users cannot be of the same type to be a match
        }
        if(user1.getCanerType().equals(user2.getCanerType())){
            matchCompatibility+=5;
            matchAllowed = true;
        }
        if(user1.getHospital().equals(user2.getHospital())){
            matchCompatibility+=4;
            matchAllowed = true;
        }
        if(user1.getTreatmentType().equals(user2.getTreatmentType())){
            matchCompatibility+=4;
            matchAllowed = true;
        }
        if(user1.getCity().equals(user2.getCity())){
            matchCompatibility+=3;
        }
        if(matchAllowed==false){
            Log.i(TAG, "is match allowed: "+ matchAllowed);
            return 0;
        } else{
            // a ranking between 4 and 16, with 16 being the closest match
            return matchCompatibility;
        }
    }
}
