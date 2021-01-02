package com.zo0okadev.basichelpers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.multidex.MultiDex;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.google.firebase.FirebaseApp;

import java.util.List;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by Zo0okaDev (https://github.com/zo0oka)
 * On 02 Jan, 2021.
 * Have a nice day!
 */
public class BasicHelpers {

    public static void init(Application application, int environment, int preferencesXmlResId) {
        MultiDex.install(application);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        int appsFlyerKeyResourceId = application.getResources().getIdentifier("apps_flyer_key", "string", application.getPackageName());

        AppsFlyerLib.getInstance().init(application.getString(appsFlyerKeyResourceId), new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {

                for (String attrName : conversionData.keySet()) {
                    Timber.d("attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Timber.d("error getting conversion data: %s", errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {

                for (String attrName : attributionData.keySet()) {
                    Timber.d("attribute: " + attrName + " = " + attributionData.get(attrName));
                }

            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Timber.d("error onAttributionFailure : %s", errorMessage);
            }
        }, application);

        PreferencesManager.init(application, preferencesXmlResId);

        AppEnvironment.setEnvironment(environment);
        LanguageManager.init(application);
        FirebaseHelper.init();
        FirebaseApp.initializeApp(application);
        AppsFlyerLib.getInstance().start(application);
    }

    public void showSoftKeyboard(EditText editText, Context context) {
        CommonMethods.showSoftKeyboard(editText, context);
    }

    public static void hideSoftKeyboard(EditText editText, Context context) {
        CommonMethods.hideSoftKeyboard(editText, context);
    }

    public static void shareLink(@NonNull Context context, String link) {
        CommonMethods.shareLink(context, link);
    }

    public static void sendHapticFeedback(@NonNull Context context) {
        CommonMethods.sendHapticFeedback(context);
    }

    public static void copyTextToClipboard(@NonNull Context context, String stringToCopy, String tag) {
        CommonMethods.copyTextToClipboard(context, stringToCopy, tag);
    }

    public static void openSendSMS(@NonNull Context context, String message, String phoneNumber) {
        CommonMethods.openSendSMS(context, message, phoneNumber);
    }

    public static void openFacebook(@Nullable FragmentActivity context, String facebookId) {
        CommonMethods.openFacebook(context, facebookId);
    }

    public static void openFacebook(Context context, String facebookLink) {
        CommonMethods.openFacebook(context, facebookLink);
    }

    public static void openTwitter(@Nullable FragmentActivity context, String twitterHandle) {
        CommonMethods.openTwitter(context, twitterHandle);
    }

    public static void openLinkInBrowser(Context context, String link) {
        CommonMethods.openLinkInBrowser(context, link);
    }

    public static void openTwitter(Context context, String twitterLink) {
        CommonMethods.openTwitter(context, twitterLink);
    }

    public static void openInstagram(@Nullable FragmentActivity context, String instagramUsername) {
        CommonMethods.openInstagram(context, instagramUsername);
    }

    public static void openInstagram(Activity activity, String instagramLink) {
        CommonMethods.openInstagram(activity, instagramLink);
    }

    public static void openLinkedIn(@Nullable FragmentActivity context, String linkedInId) {
        CommonMethods.openLinkedIn(context, linkedInId);
    }

    public static void openYoutube(@Nullable FragmentActivity context, String youtubeUser) {
        CommonMethods.openYoutube(context, youtubeUser);
    }

    public static void openGooglePlayStore(@NonNull Context context, String appLink) {
        CommonMethods.openGooglePlayStore(context, appLink);
    }

    public static float convertDpToPixel(float dp, @NonNull Context context) {
        return CommonMethods.convertDpToPixel(dp, context);
    }

    public static void showToast(Context context, String message) {
        CommonMethods.showToast(context, message);
    }

    public static void openMapLink(Context context, String mapLink) {
        CommonMethods.openMapLink(context, mapLink);
    }

    public static void openEmail(Context context, String email) {
        CommonMethods.openEmail(context, email);
    }

    public static String getDayName() {
        return CommonMethods.getDayName();
    }

    public static String getCurrentDate() {
        return CommonMethods.getCurrentDate();
    }

    public static boolean isHtml(String text) {
        return CommonMethods.isHtml(text);
    }

    public static void showAlertDialog(@NonNull Context context, String title, String message, @Nullable String negativeButtonText, @Nullable String positiveButtonText, AlertDialogListener listener) {
        DialogHelper.showAlertDialog(context, title, message, negativeButtonText, positiveButtonText, listener);
    }

    public static void showAlertDialog(@NonNull Context context, String title, @NonNull List<String> messages, String buttonText, @Nullable DialogInterface.OnDismissListener listener) {
        DialogHelper.showAlertDialog(context, title, messages, buttonText, listener);
    }

    public static void showListDialog(@NonNull Context context, String[] items, int selectedIndex, String positiveButton, String negativeButton, @Nullable String title, @NonNull ListDialogListener listener) {
        DialogHelper.showListDialog(context, items, selectedIndex, positiveButton, negativeButton, title, listener);
    }

//    public static void showSurveyDialog(FragmentManager fragmentManager) {
//        boolean isRatingDisplayedBefore = PreferencesManager.getBoolean(PreferencesManager.IS_RATING_DISPLAYED_BEFORE);
//        if (isRatingDisplayedBefore) {
//            Timber.d("The Rating dialog is displayed before, so do nothing");
//        } else {
//            long displayedTime = PreferencesManager.getLong(PreferencesManager.DISPLAYED_TIME);
//            Timber.d("Stored Displayed time: %s", displayedTime);
//            if (displayedTime < System.currentTimeMillis() - 259200000) {
//                Timber.d("The user spent 3 days...");
//                displayRatingDialog(fragmentManager);
//                PreferencesManager.saveLong(PreferencesManager.DISPLAYED_TIME, System.currentTimeMillis());
//            }
//
//        }
//
//    }
//
//    private static void displayRatingDialog(FragmentManager fragmentManager) {
//        AppFeedbackDialog feedbackDialog = new AppFeedbackDialog();
//        feedbackDialog.show(fragmentManager, AppFeedbackDialog.class.getSimpleName());
//    }

    public static void subscribeToFirebaseTopic(@NonNull String topic) {
        FirebaseHelper.subscribeToTopic(topic);
    }

    public static void unsubscribeFromFirebaseTopic(@NonNull String topic) {
        FirebaseHelper.unsubscribeFromTopic(topic);
    }

    public static void updateFirebaseTopicSubscription(@NonNull String topic, boolean subscribe, int type) {
        FirebaseHelper.updateFirebaseTopicSubscription(topic, subscribe, type);
    }

    public static void getDynamicLink(@NonNull Context context, String domain, String iosBundle, String iosAppStoreId, String link, String title, String description, String imageUrl, DynamicLinkListener listener) {
        FirebaseHelper.getDynamicLink(context, domain, iosBundle, iosAppStoreId, link, title, description, imageUrl, listener);
    }

    public static void setAppLanguage(@NonNull Activity activity, LanguageManager.Language language) {
        LanguageManager.setLanguage(activity, language);
    }

    public static void updateAppLocale(@NonNull Context baseContext) {
        LanguageManager.updateLocale(baseContext);
    }

    public static LanguageManager.Language getLanguage() {
        return LanguageManager.getLanguage();
    }

    public static String getConnectivityStatusString(Context context) {
        return NetworkUtil.getConnectivityStatusString(context);
    }

    public static boolean preferencesHasKey(String key) {
        return PreferencesManager.hasKey(key);
    }

    public static SharedPreferences getPreferences() {
        return PreferencesManager.getPreferences();
    }

    public static Map<String, ?> getAllPreferencesKeys() {
        return PreferencesManager.getAllKeys();
    }

    public static void saveInPreferences(@NonNull Object o) {
        PreferencesManager.save(o);
    }

    @Nullable
    public static String getStringFromPreferences(String key) {
        return PreferencesManager.getString(key);
    }

    public static int getIntFromPreferences(String key) {
        return PreferencesManager.getInt(key);
    }

    public static void saveStringInPreferences(String value, String key) {
        PreferencesManager.saveString(value, key);
    }

    public static void saveBooleanInPreferences(boolean value, String key) {
        PreferencesManager.saveBoolean(value, key);
    }

    public static void saveIntInPreferences(int value, String key) {
        PreferencesManager.saveInt(value, key);
    }

    public static void saveLongInPreferences(String key, long value) {
        PreferencesManager.saveLong(key, value);
    }

    public static long getLongFromPreferences(String key) {
        return PreferencesManager.getLong(key);
    }

    public static boolean getBooleanFromPreferences(String key) {
        return PreferencesManager.getBoolean(key);
    }

    public static void saveDateInPreferences(long value, String key) {
        PreferencesManager.saveDate(value, key);
    }

    public static long getDateFromPreferences(String key) {
        return PreferencesManager.getDate(key);
    }

    public static boolean getBooleanFromPreferences(String key, boolean defValue) {
        return PreferencesManager.getBoolean(key, defValue);
    }

    public static void clearPreferences(@NonNull Class c) {
        PreferencesManager.clear(c);
    }

    public static void deleteFromPreferences(String key) {
        PreferencesManager.delete(key);
    }

    @Nullable
    public static Object loadFromPreferences(@NonNull Class c) {
        return PreferencesManager.load(c);
    }

    public static void clearAppData() {
        PreferencesManager.clearAppData();
    }

    public void loadHtmlData(WebView webView, String data) {
        WebViewLoader webViewLoader = new WebViewLoader(webView);
        webViewLoader.loadHtmlData(data);
    }

    public void loadUrl(WebView webView, String url) {
        WebViewLoader webViewLoader = new WebViewLoader(webView);
        webViewLoader.loadUrl(url);
    }
}
