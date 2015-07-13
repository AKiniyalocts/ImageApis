package com.example.anthony.wedpics.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by anthony on 7/11/15.
 *
 * Helper to write/read shared prefs
 */
public class SharedPreferenceHelper {
    private static final String PREF_NAME = "com.example.anthony.wedpics";
    private static final String GOOD_SESSION_KEY = "key::good_session";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void writeGoodSession(boolean goodSession, Context context){
        getPreferences(context).edit().putBoolean(GOOD_SESSION_KEY, goodSession).apply();
    }

    public static boolean getGoodSession(Context context){
        return getPreferences(context).getBoolean(GOOD_SESSION_KEY, false);
    }
}
