package com.helenpaulini.ribbon_resources.models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.List;

@Parcel(analyze = {MyConnections.class})
@ParseClassName("MyConnections")
public class MyConnections extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_SAVEDCONNECTIONS = "savedConnections";

    //empty constructor for parceler
    public MyConnections(){

    }

    //User
    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    //MyConnections
    public List<Profile> getMyConnections() {
        return getList(KEY_SAVEDCONNECTIONS);
    }

    public void setMyConnections(List<Profile> myConnections) {
        put(KEY_SAVEDCONNECTIONS, myConnections);
    }

}
