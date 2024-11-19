package com.example.customer_mobile.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.customer_mobile.data.model.User;
import com.google.gson.Gson;

public class SharedPrefManager {
    private static final String PREF_NAME = "app_prefs";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_USER = "user";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveAccessToken(String token) {
        editor.putString(KEY_ACCESS_TOKEN, token);
        editor.apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    public void saveUser(User user) {
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        editor.putString(KEY_USER, userJson);
        editor.apply();
    }

    public User getUser() {
        String userJson = sharedPreferences.getString(KEY_USER, null);
        return userJson != null ? new Gson().fromJson(userJson, User.class) : null;
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }
}


