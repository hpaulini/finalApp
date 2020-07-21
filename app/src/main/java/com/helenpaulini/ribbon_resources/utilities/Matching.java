package com.helenpaulini.ribbon_resources.utilities;

import com.helenpaulini.ribbon_resources.models.PatientProfile;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.helenpaulini.ribbon_resources.models.SurvivorProfile;

public class Matching {

    private int matchCompatibility;
    private boolean matchAllowed = false;
    private Profile user1;
    private Profile user2;

    public Matching(){

    }

    public Matching(Profile user1, Profile user2){
        this.user1 = user1;
        this.user2 = user2;
    }

    //usertype, city, hospital, cancertype, treatmenttype, birthday,
    //years of treatment, years post treatment, interests
    public int match(){
        if (user1.getIsCurrentPatient()==user2.getIsCurrentPatient()){
            return 0; //users cannot be of the same type to be a match
        }
        if(user1.getCanerType()==user2.getCanerType()){
            matchCompatibility+=5;
            matchAllowed = true;
        }
        if(user1.getHospital()==user2.getHospital()){
            matchCompatibility+=4;
            matchAllowed = true;
        }
        if(user1.getTreatmentType()==user2.getTreatmentType()){
            matchCompatibility+=4;
            matchAllowed = true;
        }
        if(user1.getCity()==user2.getCity()){
            matchCompatibility+=3;
        }
        if(matchAllowed==false){
            return 0;
        } else{
            // a ranking between 4 and 16, with 16 being the closest match
            return matchCompatibility;
        }
    }
}
