package com.zo0okadev.basichelpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import static android.content.Context.VIBRATOR_SERVICE;
import static android.content.Intent.ACTION_VIEW;

/**
 * Created by Zo0okaDev (https://github.com/zo0oka)
 * On 02 Jan, 2021.
 * Have a nice day!
 */
public class CommonMethods {

    @SuppressLint("HardwareIds")
    public static String getDeviceId(@NonNull Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static void showSoftKeyboard(@Nullable EditText editText, @NonNull Context context) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, 0);
        }
    }

    public static void hideSoftKeyboard(@Nullable EditText editText, @NonNull Context context) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static void shareLink(@NonNull Context context, String link) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check this on UAE Info App:\n" + link);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, "Share...");
        context.startActivity(shareIntent);
    }

    public static void sendHapticFeedback(@NonNull Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);

        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(10);
            }
        }
    }

    public static void copyTextToClipboard(@NonNull Context context, String stringToCopy, String tag) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(tag, stringToCopy);

        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipData);
        }

        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    public static void openSendSMS(@NonNull Context context, String message, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("sms_body", message);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void openFacebook(@Nullable FragmentActivity context, String facebookId) {
        String fbUri;
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            try {
                long versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                if (versionCode >= 3002850) {
                    //newer versions of fb app
                    fbUri = "fb://facewebmodal/f?href=https://www.facebook.com/" + facebookId;
                } else {
                    //older versions of fb app
                    fbUri = "fb://page/" + facebookId;
                }
            } catch (PackageManager.NameNotFoundException e) {
                fbUri = facebookId; //normal web url
            }
            Intent facebookIntent = new Intent(ACTION_VIEW, Uri.parse(fbUri));
            context.startActivity(facebookIntent);
        }
    }

    public static void openFacebook(Context context, String facebookLink) {
        Intent facebookIntent = new Intent(ACTION_VIEW, Uri.parse(facebookLink));
//        if (facebookIntent.resolveActivity(context.getPackageManager()) != null)
        context.startActivity(facebookIntent);
    }

    public static void openTwitter(@Nullable FragmentActivity context, String twitterHandle) {
        String twitterUri;
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            try {
                packageManager.getPackageInfo("com.twitter.android", 0);
                twitterUri = "twitter://user?screen_name=" + twitterHandle;
            } catch (Exception e) {
                twitterUri = "https://www.twitter.com/" + twitterHandle;
            }
            Intent twitterIntent = new Intent(ACTION_VIEW, Uri.parse(twitterUri));
            context.startActivity(twitterIntent);
        }
    }

    public static void openLinkInBrowser(Context context, String link) {
        if (!link.startsWith("http://") && !link.startsWith("https://"))
            link = "http://" + link;
        Intent Intent = new Intent(ACTION_VIEW, Uri.parse(link));
        try {
            context.startActivity(Intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void openTwitter(Context context, String twitterLink) {
        Intent twitterIntent = new Intent(ACTION_VIEW, Uri.parse(twitterLink));
//        if (twitterIntent.resolveActivity(context.getPackageManager()) != null)
        context.startActivity(twitterIntent);
    }

    public static void openInstagram(@Nullable FragmentActivity context, String instagramUsername) {
        String instagramUri;
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            try {
                packageManager.getPackageInfo("com.instagram.android", 0);
                instagramUri = "https://www.instagram.com/_u/" + instagramUsername;
            } catch (Exception e) {
                instagramUri = "https://www.instagram.com/" + instagramUsername;
            }
            Intent instagramIntent = new Intent(ACTION_VIEW, Uri.parse(instagramUri));
            context.startActivity(instagramIntent);
        }
    }

    public static void openInstagram(Activity activity, String instagramLink) {
        Uri uri = Uri.parse(instagramLink);
        Intent intent = new Intent(ACTION_VIEW, uri);
        intent.setPackage("com.instagram.android");
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Invalid Instagram account", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openLinkedIn(@Nullable FragmentActivity context, String linkedInId) {
        String linkedInUri = "https://www.linkedin.com/company/" + linkedInId;

        if (context != null) {
            try {
                Intent linkedInIntent = new Intent(Intent.ACTION_VIEW);
                linkedInIntent.setPackage("com.linkedin.android");
                linkedInIntent.setData(Uri.parse(linkedInUri));
                context.startActivity(linkedInIntent);
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent(ACTION_VIEW, Uri.parse(linkedInUri)));
            }
        }
    }

    public static void openYoutube(@Nullable FragmentActivity context, String youtubeUser) {
        String youtubeUri = "https://www.youtube.com/user/" + youtubeUser;

        if (context != null) {
            try {
                Intent youtubeIntent = new Intent(Intent.ACTION_VIEW);
                youtubeIntent.setPackage("com.google.youtube.android");
                youtubeIntent.setData(Uri.parse(youtubeUri));
                context.startActivity(youtubeIntent);
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent(ACTION_VIEW, Uri.parse(youtubeUri)));
            }
        }
    }

    public static void openGooglePlayStore(@NonNull Context context, String appLink) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appLink)));
    }

    public static float convertDpToPixel(float dp, @NonNull Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void openMapLink(Context context, String mapLink) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(mapLink));
//        if (intent.resolveActivity(context.getPackageManager()) != null)
        context.startActivity(intent);
    }

    public static void openEmail(Context context, String email) {
        Intent intent = new Intent(ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + email);
        intent.setData(data);
//        if (intent.resolveActivity(context.getPackageManager()) != null)
        context.startActivity(intent);
    }

    public static String getDayName() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        return dateFormat.format(currentTime);
    }

    public static String getCurrentDate() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(currentTime);
    }

    public static boolean isHtml(String text) {
        String PATTERN = "<.+?>";
        Pattern pattern = Pattern.compile(PATTERN);
        return pattern.matcher(text).matches();
    }
}
