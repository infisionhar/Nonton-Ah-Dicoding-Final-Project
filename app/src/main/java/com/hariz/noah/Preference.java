package com.hariz.noah;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    private String KEY_UPCOMING = "upcoming";
    private String KEY_DAILY = "daily";
    private String PREF_NAME = "UserPref";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Preference(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public boolean isUpcoming() {
        return preferences.getBoolean(KEY_UPCOMING, false);
    }

    public void setUpcoming(boolean status) {
        editor.putBoolean(KEY_UPCOMING, status);
        editor.apply();
    }

    public boolean isDaily() {
        return preferences.getBoolean(KEY_DAILY, false);
    }

    public void setDaily(boolean status) {
        editor.putBoolean(KEY_DAILY, status);
        editor.apply();
    }
}
