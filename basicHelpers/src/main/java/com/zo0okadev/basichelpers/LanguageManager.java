package com.zo0okadev.basichelpers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Locale;
import java.util.Map;

import timber.log.Timber;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by Zo0okaDev (https://github.com/zo0oka)
 * On 02 Jan, 2021.
 * Have a nice day!
 */
@SuppressWarnings("rawtypes")
public class LanguageManager {


    public static final String PREFS_LANG_KEY = "com.zo0okadev.basichelpers.lang_key";
    public static final String PREFS_IS_SET_BY_USER = "com.zo0okadev.basichelpers.lang_set_by_user";
    private static Application sAppContext;
    private static Language sCurrentLanguage;

    public static void init(Application application) {
        Timber.e("Init Language");
        sAppContext = application;
        loadLanguage();
//        updateLocale(sAppContext);
    }

    public static void setLanguage(@NonNull Activity activity, Language language) {
        setLanguage(activity, activity.getClass(), language);
    }

    public static void setLanguage(@NonNull Activity activity, Class activity2, Language language) {
        saveLanguage(language);
        apply(activity, activity2);
    }

    public static void updateLocale(@NonNull Context baseContext) {
        Locale locale = new Locale(sCurrentLanguage.path);
        Locale.setDefault(locale);
        Resources resources = baseContext.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
//        Locale.setDefault(sCurrentLanguage.locale);
//        Resources resources = baseContext.getResources();
//        Configuration configuration = resources.getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            configuration.setLocale(sCurrentLanguage.locale);
//        } else {
//            configuration.locale = sCurrentLanguage.locale;
//        }
//        baseContext.getResources().updateConfiguration(configuration, baseContext.getResources().getDisplayMetrics());
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//            baseContext.createConfigurationContext(configuration);
//        } else {
//            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//        }
    }

    public static Language getLanguage() {
        return sCurrentLanguage;
    }

    public static String path() {
        if (sCurrentLanguage.equals(Language.AR)) return Language.AR.path;
        return Language.EN.path;
    }

    public static Locale getLocale() {
        return sCurrentLanguage.locale;
    }

    private static void loadLanguage() {
        Timber.e("Load Language");
//        Timber.e("Loaded Language: " + getDefaultSharedPreferences(sAppContext).getString(PREFS_LANG_KEY, null));
        Map<String, ?> prefs = getDefaultSharedPreferences(sAppContext).getAll();

        for (Map.Entry<String, ?> entry : prefs.entrySet()) {
            Timber.e(entry.getKey() + "->" + entry.getValue());
        }

        Language saved = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .fromJson(getDefaultSharedPreferences(sAppContext).getString(PREFS_LANG_KEY, ""), Language.class);
        Timber.e("Saved Language: %s", saved);

        //TODO: Re-enable after adding the english language to the App
        sCurrentLanguage = saved != null ? saved : Language.EN;
//        sCurrentLanguage = Language.AR;
    }

    public static void saveLanguage(Language l) {
        sCurrentLanguage = l;
        getDefaultSharedPreferences(sAppContext).edit().putString(PREFS_LANG_KEY, new Gson().toJson(l)).apply();
        getDefaultSharedPreferences(sAppContext).edit().putBoolean(PREFS_IS_SET_BY_USER, true).apply();
    }

    private static void apply(@NonNull Activity activity, Class activity2) {
        activity.startActivity(new Intent(activity, activity2).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        ActivityCompat.finishAffinity(activity);
    }

    public enum Language {

        EN("English", "en", new Locale("en", "US")),
        AR("Arabic", "ar", new Locale("ar", "AE"));

        public final String title;
        public final String path;
        public final Locale locale;

        Language(String title, String path, Locale locale) {
            this.title = title;
            this.path = path;
            this.locale = locale;
        }
    }
}
