package com.helenpaulini.ribbon_resources.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("PatientProfile")
public class PatientProfile extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_FIRSTNAME = "firstName";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CITY = "city";
    public static final String KEY_HOSPITAL = "hospital";
    public static final String KEY_CANCERTYPE = "cancerType";
    public static final String KEY_TREATMENTTYPE = "treatmentType";
    public static final String KEY_BIO = "bio";
    public static final String KEY_TREATMENTSTART = "treatmentStart";
    public static final String KEY_PROFILEPIC = "profilePic";
    public static final String KEY_BIRTHDAY = "birthday";


    //empty constructor for parceler
    public PatientProfile() {}

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

}
