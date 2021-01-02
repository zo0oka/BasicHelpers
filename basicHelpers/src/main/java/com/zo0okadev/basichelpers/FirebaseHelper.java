package com.zo0okadev.basichelpers;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

import timber.log.Timber;

/**
 * Created by Zo0okaDev (https://github.com/zo0oka)
 * On 02 Jan, 2021.
 * Have a nice day!
 */
public class FirebaseHelper {

    public static void init() {
        getFirebaseToken();
    }

    public static void subscribeToTopic(@NonNull String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic + AppEnvironment.getTopicSuffix())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Timber.d("Subscribed to topic: " + topic + AppEnvironment.getTopicSuffix());
                    }
                });
    }

    public static void unsubscribeFromTopic(@NonNull String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic + AppEnvironment.getTopicSuffix())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Timber.d("Unsubscribed from topic: " + topic + AppEnvironment.getTopicSuffix());
                    }
                });
    }

    public static void updateFirebaseTopicSubscription(@NonNull String topic, boolean subscribe, int type) {
        if (subscribe) {
            subscribeToTopic(topic);
        } else {
            unsubscribeFromTopic(topic);
        }
    }

    public static void getFirebaseToken() {
        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult() != null) {
                    String token = task.getResult();
                    Timber.d("FCM Token: %s", token);
                    PreferencesManager.saveString(token, PreferencesManager.FIREBASE_TOKEN);
                }
            }
        });
    }

    public static void getDynamicLink(@NonNull Context context, String domain, String iosBundle, String iosAppStoreId, String link, String title, String description, String imageUrl, DynamicLinkListener listener) {
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(link != null ? link : domain))
                .setDomainUriPrefix(domain)
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder(context.getPackageName())
                                .build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder(iosBundle)
                                .setAppStoreId(iosAppStoreId)
                                .setMinimumVersion("1.0")
                                .build())
                //TODO: use proper values
//                .setGoogleAnalyticsParameters(
//                        new DynamicLink.GoogleAnalyticsParameters.Builder()
//                                .setSource("Facebook")
//                                .setMedium("Social")
//                                .setCampaign("Marketing")
//                                .build())
                //TODO: Complete
//                .setItunesConnectAnalyticsParameters(
//                        new DynamicLink.ItunesConnectAnalyticsParameters.Builder()
//                                .setProviderToken("123456")
//                                .setCampaignToken("example-promo")
//                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle(title)
                                .setDescription(description)
                                .setImageUrl(Uri.parse(imageUrl))
                                .build())
                .buildDynamicLink();

        Timber.d("Dynamic Link: %s", dynamicLink.getUri().toString());

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(Uri.parse(dynamicLink.getUri().toString()))
                .buildShortDynamicLink()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            Uri shortLink = task.getResult().getShortLink();
                            if (shortLink != null) {
                                Timber.d("Short Link: %s", shortLink.toString());
                                listener.onLinkCreated(context, shortLink.toString());
                            }
                        }
                    }
                });

    }
}
