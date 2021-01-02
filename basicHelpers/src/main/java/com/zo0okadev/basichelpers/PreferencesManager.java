package com.zo0okadev.basichelpers;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.service.autofill.UserData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Zo0okaDev (https://github.com/zo0oka)
 * On 02 Jan, 2021.
 * Have a nice day!
 */
public final class PreferencesManager {

    public static final String FIREBASE_TOKEN = "firebase_token";
    public static final String DEVICE_ID = "device_id";
    public static final String ENVIRONMENT = "environment";
    private static final String PREFERENCES = "com.barq.uaeinfo.settings_screen_preferences";
    private static Application context;
    private static SharedPreferences pref;
    private static WeakReference<Context> contextWeakReference;

    public static void init(@NonNull Application application, int preferencesXmlResId) {
        context = application;
        if (preferencesXmlResId != 0) {
            androidx.preference.PreferenceManager.setDefaultValues(application, preferencesXmlResId, false);
        }
        contextWeakReference = new WeakReference<>(context);
        pref = PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get());
        saveString(CommonMethods.getDeviceId(context), DEVICE_ID);
        Timber.d("Preferences Manager Initialized");
    }

    public static boolean hasKey(String key) {
        if (contextWeakReference != null) {
            return pref.contains(key);
        }
        return false;
    }

    public static SharedPreferences getPreferences() {
        return pref;
    }

    public static Map<String, ?> getAllKeys() {
        return pref.getAll();
    }

    public static void save(@NonNull Object o) {
        context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .edit().putString(o.getClass().getName(), new Gson().toJson(o))
                .apply();
        Timber.d("**** SAVED **** Object: " + o.getClass().getName() + " with value: " + new Gson().toJson(o));
    }

    @Nullable
    public static String getString(String key) {
        return context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .getString(key, "");
    }

    public static int getInt(String key) {
        return context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .getInt(key, 0);
    }

    public static void saveString(String value, String key) {
        context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .edit().putString(key, value)
                .apply();
        Timber.d("**** SAVED **** String: " + key + " with value: " + value);
    }

    public static void saveBoolean(boolean value, String key) {
        context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .edit().putBoolean(key, value)
                .apply();
        Timber.d("****SAVED**** Boolean: " + key + " with value: " + value);
    }

    public static void saveInt(int value, String key) {
        context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .edit().putInt(key, value)
                .apply();
        Timber.d("**** SAVED **** Integer: " + key + " with value: " + value);
    }

    public static void saveLong(String key, long value) {
        context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .edit().putLong(key, value)
                .apply();
        Timber.d("**** SAVED **** Long: " + key + " with value: " + value);
    }

    public static long getLong(String key) {
        return context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .getLong(key, 0);
    }

    public static boolean getBoolean(String key) {
        return context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .getBoolean(key, false);
    }

    public static void saveDate(long value, String key) {
        context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .edit().putLong(key, value)
                .apply();
        Timber.d("****SAVED**** Date: " + key + " with value: " + value);
    }

    public static long getDate(String key) {
        return context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .getLong(key, 0);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .getBoolean(key, defValue);
    }

    public static void clear(@NonNull Class c) {
        context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .edit().putString(c.getName(), null)
                .apply();
    }

    public static void delete(String key) {
        context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .edit().remove(key)
                .apply();
    }

    @Nullable
    public static Object load(@NonNull Class c) {
        return new Gson().fromJson(context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .getString(c.getName(), null), c);
    }

    public static void clearAppData() {
        context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                .edit().clear()
                .apply();
    }
}
