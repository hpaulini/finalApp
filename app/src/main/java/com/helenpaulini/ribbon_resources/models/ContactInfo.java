package com.helenpaulini.ribbon_resources.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.Date;

@Parcel(analyze = {ContactInfo.class})
@ParseClassName("ContactInfo")
public class ContactInfo extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_INSTAGRAM = "instagram";
    public static final String KEY_FACEBOOK = "facebook";
    public static final String KEY_ADDRESS = "address";

    //empty constructor for parceler
    public ContactInfo(){
    }

    //User
    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    //Email
    public String getEmail() {
        return getString(KEY_EMAIL);
    }

    public void setEmail(String email) {
        put(KEY_EMAIL, email);
    }

    //Phone
    public String getPhone() {
        return getString(KEY_PHONE);
    }

    public void setPhone(String phone) {
        put(KEY_PHONE, phone);
    }

    //Instagram
    public String getInstagram() {
        return getString(KEY_INSTAGRAM);
    }

    public void setInstagram(String instagram) {
        put(KEY_INSTAGRAM, instagram);
    }

    //Facebook
    public String getFacebook() {
        return getString(KEY_FACEBOOK);
    }

    public void setFacebook(String facebook) {
        put(KEY_FACEBOOK, facebook);
    }

    //Address
    public String getAddress() {
        return getString(KEY_ADDRESS);
    }

    public void setAddress(String address) {
        put(KEY_ADDRESS, address);
    }
}
