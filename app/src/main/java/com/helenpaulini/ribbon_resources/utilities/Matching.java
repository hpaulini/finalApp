package com.helenpaulini.ribbon_resources.utilities;

import android.util.Log;

import com.helenpaulini.ribbon_resources.models.PatientProfile;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.helenpaulini.ribbon_resources.models.SurvivorProfile;

import java.util.ArrayList;
import java.util.List;

public class Matching {

    public static final String TAG = "dashboard";
    public static final int HIGH_MATCH_VALUE = 10;
    public static final int MEDIUM_MATCH_VALUE = 5;
    public static final int LOW_MATCH_VALUE = 2;
    public static final int NO_MATCH_VALUE = 0;
    private int matchCompatibility = 0;
    private boolean matchAllowed = false;
//    private Profile user1;
//    private Profile user2;

    public Matching(){

    }

    public List<Profile> topMatchList(Profile currentProfile, List<Profile> allProfiles){
        List<Profile> topMatches = new ArrayList<>();
        for(int i=0; i<allProfiles.size(); i++){
            if(matchValue(currentProfile, allProfiles.get(i))>0){
                topMatches.add(allProfiles.get(i));
            }
        }
        return topMatches;
    }

    public int cancerMatchValue(Profile user1, Profile user2){
        return 0;
    }

    public int hospitalMatchValue(Profile user1, Profile user2){
        return 0;
    }

    public int treatmentMatchValue(Profile user1, Profile user2){
        return 0;
    }

    public int cityMatchValue(Profile user1, Profile user2){
        return 0;
    }

    public int interestsMatchValue(Profile user1, Profile user2){
        return 0;
    }

    public int currentPatientMatchValue(Profile user1, Profile user2){
        return 0;
    }

    public int previousPatientMatchValue(Profile user1, Profile user2){
        return 0;
    }

    public int parentMatchValue(Profile user1, Profile user2){
        return 0;
    }

    public int topMatchValue(Profile user1, Profile user2){
        int matchValue = 0;
        if(user1.getUser().equals(user2.getUser())){
            return NO_MATCH_VALUE; //cannot match with self
        }
        if(user1.getUserType().equals(user2.getUserType())){
            return NO_MATCH_VALUE; //cannot match with user of same type
        }
        if(user1.getHospital().equals(user2.getHospital())){
            matchValue+=HIGH_MATCH_VALUE;
        }
        if(user1.getCanerType().equals(user2.getCanerType())){
            matchValue+=HIGH_MATCH_VALUE;
        }
        if(user1.getCity().equals(user2.getCity())){
            matchValue+=HIGH_MATCH_VALUE;
        }
        if(user1.getTreatmentType().equals(user2.getTreatmentType())){
            matchValue+=MEDIUM_MATCH_VALUE;
        }
        if(user1.getInsterests().equals(user2.getInsterests())){
            for(int i=0; i<numSimilarInterest(user1, user2); i++){
                matchValue+=LOW_MATCH_VALUE;
            }
        }
        return matchValue;
    }

    private int numSimilarInterest(Profile user1, Profile user2){
        int numSimilar = 0;
        String[] user1Interests = user1.getInsterests().split(", ");
        String[] user2Interests = user2.getInsterests().split(", ");

        for(int j=0; j<user1Interests.length; j++){
            for(int i=0; i<user2Interests.length; i++){
                if(user1Interests[j].equals(user2Interests[i])){
                    numSimilar++;
                }
            }
        }
        return numSimilar;
    }


    //usertype, city, hospital, cancertype, treatmenttype, birthday,
    //years of treatment, years post treatment, interests
    public int matchValue(Profile user1, Profile user2){


        if (!(user1.getUserType()==null) && !(user2.getUserType()==null) && user1.getUserType().equals(user2.getUserType())){
            return 0; //users cannot be of the same type to be a match
        }
        if(!(user1.getCanerType()==null) && !(user2.getCanerType()==null) && user1.getCanerType().equals(user2.getCanerType())){
            matchCompatibility+=5;
            matchAllowed = true;
        }
        if(!(user1.getHospital()==null) && !(user2.getHospital()==null) && user1.getHospital().equals(user2.getHospital())){
            matchCompatibility+=4;
            matchAllowed = true;
        }
        if(!(user1.getTreatmentType()==null) && !(user2.getTreatmentType()==null) && user1.getTreatmentType().equals(user2.getTreatmentType())){
            matchCompatibility+=4;
            matchAllowed = true;
        }
        if(!(user1.getCity()==null) && !(user2.getCity()==null) && user1.getCity().equals(user2.getCity())){
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
