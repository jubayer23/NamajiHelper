package com.creative.namajihelper.sharedprefs;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.creative.namajihelper.model.Mosque;
import com.creative.namajihelper.model.Namaji;
import com.google.gson.Gson;


public class PrefManager {
    private static final String TAG = PrefManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "com.creative.namajihelper";

    // Google's username
    private static final String KEY_USERNAME = "user_name";
    private static final String KEY_MOSQUE_OBJECT = "mosque_object";
    private static final String KEY_NAMAJI_OBJECT = "namaji_object";
    private static final String KEY_LOGIN_TYPE = "login_type";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

    }

    public void setLoginType(String type) {
        editor = pref.edit();

        editor.putString(KEY_LOGIN_TYPE, type);

        // commit changes
        editor.commit();
    }

    public String getLoginType() {
        return pref.getString(KEY_LOGIN_TYPE, "");
    }

    public void setMosqueObject(Mosque mosque) {
        editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mosque);
        editor.putString(KEY_MOSQUE_OBJECT, json);
        editor.commit();
    }

    public Mosque getMosqueObject() {

        Gson gson = new Gson();
        String json = pref.getString(KEY_MOSQUE_OBJECT, "");
        Mosque mosque = gson.fromJson(json, Mosque.class);

        return mosque;
    }

    public void setNamajiObject(Namaji namaji) {
        editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(namaji);
        editor.putString(KEY_NAMAJI_OBJECT, json);
        editor.commit();
    }

    public Namaji getNamajiObject() {

        Gson gson = new Gson();
        String json = pref.getString(KEY_NAMAJI_OBJECT, "");
        Namaji namaji = gson.fromJson(json, Namaji.class);

        return namaji;
    }


}