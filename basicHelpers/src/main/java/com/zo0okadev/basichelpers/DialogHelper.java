package com.zo0okadev.basichelpers;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

/**
 * Created by Zo0okaDev (https://github.com/zo0oka)
 * On 02 Jan, 2021.
 * Have a nice day!
 */
public class DialogHelper {

    public static final int BUTTON_POSITIVE = 1;
    public static final int BUTTON_NEGATIVE = 2;

    public static void showAlertDialog(@NonNull Context context, String title, String message, @Nullable String negativeButtonText, @Nullable String positiveButtonText, AlertDialogListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setMessage(message);

        if (title != null) {
            dialog.setTitle(title);
        }

        if (positiveButtonText != null) {
            dialog.setPositiveButton(positiveButtonText, (dialog12, which) -> listener.onPositive());
        }

        if (negativeButtonText != null) {
            dialog.setNegativeButton(negativeButtonText, (dialog1, which) -> listener.onNegative());
        }

        dialog.create().show();
    }

    public static void showAlertDialog(@NonNull Context context, String title, @NonNull List<String> messages, String buttonText, @Nullable DialogInterface.OnDismissListener listener) {
        StringBuilder message = new StringBuilder();
        for (String error : messages) {
            message.append(error).append("\n");
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setMessage(message.toString())
                .setTitle(title)
                .setNegativeButton(buttonText, (dialog1, which) -> dialog1.dismiss());
        if (listener != null) {
            dialog.setOnDismissListener(listener);
        }
        dialog.create().show();
    }

    public static void showListDialog(@NonNull Context context, String[] items, int selectedIndex, String positiveButton, String negativeButton, @Nullable String title, @NonNull ListDialogListener listener) {
        final int[] index = {-1};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (title != null) {
            builder.setTitle(title);
        }

        builder.setSingleChoiceItems(items, selectedIndex, (dialogInterface, i) -> {
            index[0] = i;
            listener.onSelect(i);
            dialogInterface.dismiss();

        });

        builder.setPositiveButton(positiveButton, (DialogInterface.OnClickListener) (dialogInterface, i) -> listener.onSelect(index[0]));

        builder.setNegativeButton(negativeButton, (dialogInterface, i) -> dialogInterface.dismiss());


        AlertDialog dialog = builder.create();
        dialog.show();
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
}