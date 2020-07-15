package com.helenpaulini.ribbon_resources.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("SurvivorProfile")
public class SurvivorProfile extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_FIRSTNAME = "firstName";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_CITY = "city";
    public static final String KEY_HOSPITAL = "hospital";
    public static final String KEY_CANCERTYPE = "cancerType";
    public static final String KEY_TREATMENTTYPE = "treatmentType";
    public static final String KEY_BIO = "bio";
    public static final String KEY_TREATMENTSTART = "treatmentStart";
    public static final String KEY_TREATMENTEND = "treatmentEnd";
    public static final String KEY_PROFILEPIC = "profilePic";
    public static final String KEY_BIRTHDAY = "birthday";

    public static final String KEY_EMAIL = "email";


    //empty constructor for parceler
    public SurvivorProfile() {}

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_PROFILEPIC);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_PROFILEPIC, parseFile);
    }

    public String getFirstName() {
        return getString(KEY_FIRSTNAME);
    }

    public void setFirstName(String firstName) {
        put(KEY_FIRSTNAME, firstName);
    }

    public String getLastName() {
        return getString(KEY_LASTNAME);
    }

    public void setLastName(String lastName) {
        put(KEY_LASTNAME, lastName);
    }

    public String getBio() {
        return getString(KEY_BIO);
    }

    public void setBio(String bio) {
        put(KEY_BIO, bio);
    }

    public String getCity() {
        return getString(KEY_CITY);
    }

    public void setCity(String city) {
        put(KEY_CITY, city);
    }

    public String getHospital() {
        return getString(KEY_HOSPITAL);
    }

    public void setHospital(String hospital) {
        put(KEY_HOSPITAL, hospital);
    }

    public String getCanerType() {
        return getString(KEY_CANCERTYPE);
    }

    public void setCancerType(String cancerType) {
        put(KEY_CANCERTYPE, cancerType);
    }

    public String getTreatmentType() {
        return getString(KEY_TREATMENTTYPE);
    }

    public void setTreatmentType(String treatmentType) {
        put(KEY_TREATMENTTYPE, treatmentType);
    }

    public Date getBirthday() {
        return getDate(KEY_BIRTHDAY);
    }

    public void setBirthday(Date birthday) {
        put(KEY_BIRTHDAY, birthday);
    }

    public Date getTreatmentStart() { return getDate(KEY_TREATMENTSTART); }

    public void setTreatmentStart(Date treatmentStart) { put(KEY_TREATMENTSTART, treatmentStart); }

    public Date getTreatmentEnd() {
        return getDate(KEY_TREATMENTEND);
    }

    public void setTreatmentEnd(Date treatmentEnd) { put(KEY_TREATMENTEND, treatmentEnd); }

}
