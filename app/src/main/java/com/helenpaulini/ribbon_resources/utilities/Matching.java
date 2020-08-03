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
    int matchValue = 0;
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

    public List<Profile> cancerTypeMatchList(Profile currentProfile, List<Profile> allProfiles){
        List<Profile> cancerTypeMatches = new ArrayList<>();
        for(int i=0; i<allProfiles.size(); i++){
            if(cancerMatchValue(currentProfile, allProfiles.get(i))>0){
                cancerTypeMatches.add(allProfiles.get(i));
            }
        }
        return cancerTypeMatches;
    }

    public List<Profile> hospitalMatchList(Profile currentProfile, List<Profile> allProfiles){
        List<Profile> hospitalMatches = new ArrayList<>();
        for(int i=0; i<allProfiles.size(); i++){
            if(hospitalMatchValue(currentProfile, allProfiles.get(i))>0){
                hospitalMatches.add(allProfiles.get(i));
            }
        }
        return hospitalMatches;
    }

    public List<Profile> treatmentTypeMatchList(Profile currentProfile, List<Profile> allProfiles){
        List<Profile> treatmentTypeMatches = new ArrayList<>();
        for(int i=0; i<allProfiles.size(); i++){
            if(treatmentMatchValue(currentProfile, allProfiles.get(i))>0){
                treatmentTypeMatches.add(allProfiles.get(i));
            }
        }
        return treatmentTypeMatches;
    }

    public List<Profile> cityMatchList(Profile currentProfile, List<Profile> allProfiles){
        List<Profile> cityMatches = new ArrayList<>();
        for(int i=0; i<allProfiles.size(); i++){
            if(cityMatchValue(currentProfile, allProfiles.get(i))>0){
                cityMatches.add(allProfiles.get(i));
            }
        }
        return cityMatches;
    }

    public List<Profile> interestsMatchList(Profile currentProfile, List<Profile> allProfiles){
        List<Profile> interestsMatches = new ArrayList<>();
        for(int i=0; i<allProfiles.size(); i++){
            if(interestsMatchValue(currentProfile, allProfiles.get(i))>0){
                interestsMatches.add(allProfiles.get(i));
            }
        }
        return interestsMatches;
    }

    public List<Profile> currentPatientList(List<Profile> allProfiles){
        List<Profile> userTypeMatches = new ArrayList<>();
        for(int i=0; i<allProfiles.size(); i++){
            if(allProfiles.get(i).getUserType().equals("Current Patient")){
                userTypeMatches.add(allProfiles.get(i));
            }
        }
        return userTypeMatches;
    }

    public List<Profile> previousPatientList(List<Profile> allProfiles){
        List<Profile> userTypeMatches = new ArrayList<>();
        for(int i=0; i<allProfiles.size(); i++){
            if(allProfiles.get(i).getUserType().equals("Previous Patient")){
                userTypeMatches.add(allProfiles.get(i));
            }
        }
        return userTypeMatches;
    }

    public List<Profile> parentList(List<Profile> allProfiles){
        List<Profile> userTypeMatches = new ArrayList<>();
        for(int i=0; i<allProfiles.size(); i++){
            if(allProfiles.get(i).getUserType().equals("Parent of Patient")){
                userTypeMatches.add(allProfiles.get(i));
            }
        }
        return userTypeMatches;
    }

    public int topMatchValue(Profile user1, Profile user2){
        matchValue = 0;
        if(user1.getUser().equals(user2.getUser())){
            return NO_MATCH_VALUE; //cannot match with self
        }
        if(user1.getUserType().equals(user2.getUserType())){
            return NO_MATCH_VALUE; //cannot match with user of same type
        }
        if(user1.getHospital().equals(user2.getHospital())){
            matchValue+=HIGH_MATCH_VALUE;
        }
        if(user1.getCancerType().equals(user2.getCancerType())){
            matchValue+=HIGH_MATCH_VALUE;
        }
        if(user1.getCity().equals(user2.getCity())){
            matchValue+=HIGH_MATCH_VALUE;
        }
        if(user1.getTreatmentType().equals(user2.getTreatmentType())){
            matchValue+=MEDIUM_MATCH_VALUE;
        }
        if(user1.getInsterests().equals(user2.getInsterests())){
            for(int i=0; i<interestsMatchValue(user1, user2); i++){
                matchValue+=LOW_MATCH_VALUE;
            }
        }
        return matchValue;
    }

    public int cancerMatchValue(Profile user1, Profile user2){
        matchValue = 0;
        if(user1.getCancerType().equals(user2.getCancerType())){
            matchValue+=HIGH_MATCH_VALUE;
        } else if (user1.getCancerType().contains(user2.getCancerType())){
            matchValue+=MEDIUM_MATCH_VALUE;
        }
        return matchValue;
    }

    public int hospitalMatchValue(Profile user1, Profile user2){
        matchValue = 0;
        if (user1.getHospital().equals(user2.getHospital())){
            matchValue+=HIGH_MATCH_VALUE;
        }
        return matchValue;
    }

    public int treatmentMatchValue(Profile user1, Profile user2){
        matchValue = 0;
        String[] user1Treatment=user1.getTreatmentType().split(" ,");
        String[] user2Treatment=user2.getTreatmentType().split(" ,");

        for(int i=0; i<user1Treatment.length; i++){
            for(int j=0; j<user2Treatment.length; j++){
                if (user1Treatment[i].equals(user2Treatment[j])){
                    matchValue+=HIGH_MATCH_VALUE;
                }
            }
        }
        return matchValue;
    }

    public int cityMatchValue(Profile user1, Profile user2){
        matchValue = 0;
        if(user1.getCity().equals(user2.getCity())){
            matchValue+=HIGH_MATCH_VALUE;
        } else{
            return 0;
        }
        return matchValue;
    }

    private int interestsMatchValue(Profile user1, Profile user2){
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
        if(!(user1.getCancerType()==null) && !(user2.getCancerType()==null) && user1.getCancerType().equals(user2.getCancerType())){
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
