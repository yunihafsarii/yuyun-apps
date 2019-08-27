package com.example.yunihafsari.fypversion3.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yunihafsari on 12/03/2017.
 */

public class SharedPrefUtil {

    // name of preference file
    private static final String APP_PREFS = "application_preferences";

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefUtil(Context context) {
        this.context = context;
    }

    // save string
    public void saveString(String key, String value){
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    // save integer
    public void saveInt (String key, int value){
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    // save boolean
    public void saveBoolean (String key, boolean value){
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    // get string
    public String getString(String key){
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    // get string , return defaultValue if it does not exist
    public String getString(String key, String defaultValue){
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    // get string
    public int getInteger(String key){
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    // get integer if it does not exist
    public int getInteger(String key, int defaultValue){
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    // get boolean
    public boolean getBoolean(String key){
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    // get boolean if it does not exist
    public boolean getBoolean(String key, boolean defaultValue){
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // clean the sharepreference file
    public void clear(){
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}
