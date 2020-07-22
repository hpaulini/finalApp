package com.helenpaulini.ribbon_resources.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class Hospital {

    public static final String TAG = "profile";

    String name;

    public Hospital (JSONObject jsonObject) throws JSONException {
        name = jsonObject.getJSONObject("attributes").getString("NAME");
    }

    public static List<Hospital> fromJsonArray(JSONArray hospitalJsonArray) throws JSONException {
        List<Hospital> hospitals = new ArrayList<>();
        for (int i = 0; i<hospitalJsonArray.length(); i++){
            hospitals.add(new Hospital(hospitalJsonArray.getJSONObject(i)));
        }
        return hospitals;
    }

    public String getName(){
        return name;
    }
}