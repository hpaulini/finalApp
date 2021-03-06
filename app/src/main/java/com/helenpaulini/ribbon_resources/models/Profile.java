package com.helenpaulini.ribbon_resources.models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Parcel(analyze = {Profile.class})
@ParseClassName("Profile")
public class Profile extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_FIRSTNAME = "firstName";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_PROFILEPIC = "profilePic";
    public static final String KEY_CITY = "city";
    public static final String KEY_BIRTHDAY =  "birthday";
    public static final String KEY_HOSPITAL = "hospital";
    public static final String KEY_CANCERTYPE = "cancerType";
    public static final String KEY_TREATMENTTYPE = "treatmentType";
    public static final String KEY_BIO = "bio";
    public static final String KEY_TREATMENTSTART = "treatmentStart";
    public static final String KEY_TREATMENTEND = "treatmentEnd";
    public static final String KEY_USERTYPE = "userType";
    public static final String KEY_MYCONNECTIONS = "myConnections";
    public static final String KEY_INTERESTS = "interests";
    public static final String KEY_PROFILEARRAY = "profileArray";

    //empty constructor for parceler
    public Profile(){

    }

    //User
    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    //First name
    public String getFirstName() {
        return getString(KEY_FIRSTNAME);
    }

    public void setFirstName(String firstName) {
        put(KEY_FIRSTNAME, firstName);
    }

    //Last name
    public String getLastName() {
        return getString(KEY_LASTNAME);
    }

    public void setLastName(String lastName) {
        put(KEY_LASTNAME, lastName);
    }

    //Profile pic
    public ParseFile getImage() {
        return getParseFile(KEY_PROFILEPIC);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_PROFILEPIC, parseFile);
    }

    //City
    public String getCity() {
        return getString(KEY_CITY);
    }

    public void setCity(String city) {
        put(KEY_CITY, city);
    }

    //Birthday
    public String getBirthday() {
        return getString(KEY_BIRTHDAY);
    }

    public void setBirthday(String birthday) {
        put(KEY_BIRTHDAY, birthday);
    }

    //Hospital
    public String getHospital() {
        return getString(KEY_HOSPITAL);
    }

    public void setHospital(String hospital) {
        put(KEY_HOSPITAL, hospital);
    }

    //Cancer type
    public String getCancerType() {
        return getString(KEY_CANCERTYPE);
    }

    public void setCancerType(String cancerType) {
        put(KEY_CANCERTYPE, cancerType);
    }

    //Treatment type
    public String getTreatmentType() {
        return getString(KEY_TREATMENTTYPE);
    }

    public void setTreatmentType(String treatmentType) {
        put(KEY_TREATMENTTYPE, treatmentType);
    }

    //Bio
    public String getBio() {
        return getString(KEY_BIO);
    }

    public void setBio(String bio) {
        put(KEY_BIO, bio);
    }

    //Treatment start
    public String getTreatmentStart() {
        return getString(KEY_TREATMENTSTART);
    }

    public void setTreatmentStart(String treatmentStart) { put(KEY_TREATMENTSTART, treatmentStart); }

    //Treatment End
    public String getTreatmentEnd() {
        return getString(KEY_TREATMENTEND);
    }

    public void setTreatmentEnd(String treatmentEnd) {
        put(KEY_TREATMENTEND, treatmentEnd);
    }

    //User Type
    public String getUserType(){ return getString(KEY_USERTYPE); }

    public void setUserType(String userType){ put(KEY_USERTYPE, userType); }

    //MyConnections
    public List<Profile> getMyConnections() {
        return getList(KEY_MYCONNECTIONS);
    }

    public void setMyConnections(List<Profile> myConnections) {
        Log.i("Profile", "myConnections list: "+myConnections.get(0).getUser().getUsername());
        put(KEY_MYCONNECTIONS, myConnections);
    }

    //Interests
    public String getInsterests() {
        return getString(KEY_INTERESTS);
    }

    public void setInterests(String interests) {
        put(KEY_INTERESTS, interests);
    }

    //Profile array
    public List<Profile> getProfileArray() {
        return getList(KEY_PROFILEARRAY);
    }

    public void setProfileArray(List<Profile> profiles) {
        put(KEY_PROFILEARRAY, profiles);
    }

}
