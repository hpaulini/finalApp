package com.helenpaulini.ribbon_resources.utilities;

import com.helenpaulini.ribbon_resources.models.PatientProfile;
import com.helenpaulini.ribbon_resources.models.SurvivorProfile;

public class Matching {

    private int matchCompatibility;
    private boolean matchAllowed = false;

    //city, hospital, cancertype, treatmenttype, birthday,
    //years of treatment, years post treatment, interests

    public int match(PatientProfile patientProfile, SurvivorProfile survivorProfile){
        if(patientProfile.getCanerType()==survivorProfile.getCanerType()){
            matchCompatibility+=5;
            matchAllowed = true;
        }
        if(patientProfile.getHospital()==survivorProfile.getHospital()){
            matchCompatibility+=4;
            matchAllowed = true;
        }
        if(patientProfile.getTreatmentType()==survivorProfile.getTreatmentType()){
            matchCompatibility+=4;
            matchAllowed = true;
        }
        if(patientProfile.getCity()==survivorProfile.getCity()){
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
