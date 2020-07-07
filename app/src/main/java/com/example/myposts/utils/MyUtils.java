package com.example.myposts.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.example.myposts.models.User;
import com.google.gson.Gson;

public class MyUtils {
    private MyUtils() {}

    public static boolean isLoggedIn(Context context){
        SharedPreferences preferences = context.getSharedPreferences("LOCAL_DATA", Context.MODE_PRIVATE);
        return preferences.getBoolean("IS_LOGGED_IN", false);
    }

    public static void logUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("LOCAL_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IS_LOGGED_IN", true);
        editor.apply();
        editor.commit();
    }

    public static void persistUser(Context context, User user){
        SharedPreferences preferences = context.getSharedPreferences("LOCAL_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user, User.class);
        editor.putString("userJsonData", json);
        editor.apply();
        editor.commit();



    }

    @Nullable
    public static User getPersistedUser(Context context){
        User user = null;
        SharedPreferences preferences = context.getSharedPreferences("LOCAL_DATA", Context.MODE_PRIVATE);
        String json = preferences.getString("userJsonData", "");
        Gson gson = new Gson();
        if (!json.isEmpty()) {
            user = gson.fromJson(json, User.class);
        }

        return user;
    }



    public static void logoutUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("LOCAL_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IS_LOGGED_IN", false);
        editor.apply();
        editor.commit();
    }
}
