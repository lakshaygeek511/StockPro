package com.example.machinetestproject.database.sharedPref;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

/*
* This is utility class to store data in device storage in form of key value pairs.
* */
public class SharedPref {

    private static SharedPreferences mSharedPref;

    /*
     * This is init method for create the instance of Shared Preference.
     * */
    public static void init(Context context) {
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(),MODE_PRIVATE);
    }

    /*
     * This function is responsible for returning the Boolean value from
     * the Shared Preferences that corresponds to the provided Key
     * */
    public static boolean getBoolean(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    /*
     * This function is responsible for store the Boolean value in
     * the Shared Preferences that corresponds to the provided Key
     * */
    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    /*
     * This function is responsible for store the string in
     * the Shared Preferences that corresponds to the provided Key
     * */
    public static void putString(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    /*
     * This function is responsible for returning the string from
     * the Shared Preferences that corresponds to the provided Key
     * */
    public static String getString(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    /*
     * This function is responsible for clear the shared preference.
     * */
    public static void clearPreference(Context context) {
        SharedPref.init(context);
        mSharedPref.edit().clear().apply();
    }

    /*
     * This function is responsible for remove the specific key from shared preference.
     * */
    public static void removePreference(String value) {
        mSharedPref.edit().remove(value).apply();
    }

}
