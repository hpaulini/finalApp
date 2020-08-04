package com.helenpaulini.ribbon_resources.models;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@Parcel(analyze = {RequestedConnections.class})
@ParseClassName("RequestedConnections")
public class RequestedConnections extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_REQUESTEDUSER = "requestedUser";
    public static final String KEY_REQUESTORPROFILE = "requestorProfile";
    public static final String KEY_REQUESTEDPROFILE = "requestedProfile";

    public RequestedConnections(){

    }

    //User
    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    //requested user
    public ParseUser getRequestedUser() {
        return getParseUser(KEY_REQUESTEDUSER);
    }

    public void setRequestedUser(ParseUser user) {
        put(KEY_REQUESTEDUSER, user);
    }

    //requestor profile
    public ParseObject getRequestorProfile() {
        return getParseObject(KEY_REQUESTORPROFILE);
    }

    public void setRequestorProfile(ParseObject profile) {
        put(KEY_REQUESTORPROFILE, profile);
    }

    //requested profile
    public ParseObject getRequestedProfile() {
        return getParseObject(KEY_REQUESTEDPROFILE);
    }

    public void setRequestedProfile(ParseObject profile) {
        put(KEY_REQUESTEDPROFILE, profile);
    }
}
