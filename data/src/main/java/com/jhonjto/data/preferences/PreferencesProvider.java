package com.jhonjto.data.preferences;

import static com.jhonjto.data.utils.Constants.PREFERENCE_NAME;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class PreferencesProvider {
    private static PreferencesProvider sSharedPrefs;
    private static SharedPreferences mPref;
    private static SharedPreferences.Editor mEditor;

    private PreferencesProvider(
            Context context
    ) {
        mPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new PreferencesProvider(context.getApplicationContext());
        }
    }

    public static void putString(String key, String val) {
        mEditor = mPref.edit();
        mEditor.putString(key, val);
        mEditor.commit();
    }

    public static String getString(String key) {
        return mPref.getString(key, "defaultValue");
    }

    public static void putList(String key, Set<String> set) {
        mEditor = mPref.edit();
        mEditor.putStringSet(key, set);
        mEditor.commit();
    }

    public static Set<String> getList(String key) {
        return mPref.getStringSet(key, null);
    }

    public static void clearPreference(Context context) {
        mPref.edit().clear().apply();
    }

    public static void removePreference(String Key){
        mPref.edit().remove(Key).apply();
    }
}
