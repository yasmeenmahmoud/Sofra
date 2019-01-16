package com.example.dell.sofra.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPereferenceClass {

    public static final String USER = "USER_ORDER";
    public static final String SELL = "SELL_ORDER";
    public static final String API_TOKEN = "api_token";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Context context;
    public void storeKey(Context context,String key,String value) {
        SharedPreferences msharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = msharedPreferences.edit();
      editor.putString(key, value);
        editor.apply();

    }
    public void storeKey(Context context,String key,int value) {
        SharedPreferences msharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = msharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();

    }

    public void storeKey(Context context,String key,Boolean value) {
        SharedPreferences msharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = msharedPreferences.edit();
        editor.putBoolean(key, true);
        editor.apply();

    }
   public  String getStoredKey(Context context,String key) {
        SharedPreferences msharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String storedvalue = msharedPreferences.getString(key,"");
        return storedvalue;
    }
    public  Boolean getbooleanStoredKey(Context context,String key) {
        SharedPreferences msharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        boolean storedvalue = msharedPreferences.getBoolean(key,false);
        return storedvalue;
    }
    public  int getintStoredKey(Context context,String key) {
        SharedPreferences msharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int storedvalue = msharedPreferences.getInt(key,0);
        return storedvalue;
    }

}


