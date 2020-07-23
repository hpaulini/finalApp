package com.helenpaulini.ribbon_resources;

import android.app.Application;

import com.helenpaulini.ribbon_resources.models.ContactInfo;
import com.helenpaulini.ribbon_resources.models.MyConnections;
import com.helenpaulini.ribbon_resources.models.Post;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.helenpaulini.ribbon_resources.models.SurvivorProfile;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(SurvivorProfile.class);
        ParseObject.registerSubclass(Profile.class);
        ParseObject.registerSubclass(ContactInfo.class);
        ParseObject.registerSubclass(MyConnections.class);

        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("ribbon-resources") // should correspond to APP_ID env variable
                .clientKey("helenpauliniRibbonResources")  // set explicitly unless clientKey is explicitly configured on Parse server
                .server("https://ribbon-resources.herokuapp.com/parse/").build());
    }
}
