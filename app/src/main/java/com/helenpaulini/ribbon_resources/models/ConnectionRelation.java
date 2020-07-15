package com.helenpaulini.ribbon_resources.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("ConnectionRelation")
public class ConnectionRelation extends ParseObject {

    public static final String KEY_PATIENTUSER = "patientUser";
    public static final String KEY_SURVIVORUSER = "survivorUser";

    //empty constructor for parceler
    public ConnectionRelation() {}

    public ParseUser getPatientUser() {
        return getParseUser(KEY_PATIENTUSER);
    }

    public void setPatientUser(ParseUser user) {
        put(KEY_PATIENTUSER, user);
    }

    public ParseUser getSurvivorUser() {
        return getParseUser(KEY_SURVIVORUSER);
    }

    public void setSurvivorUser(ParseUser user) {
        put(KEY_SURVIVORUSER, user);
    }

}
