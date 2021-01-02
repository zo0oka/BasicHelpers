package com.zo0okadev.basichelpers;

/**
 * Created by Zo0okaDev (https://github.com/zo0oka)
 * On 02 Jan, 2021.
 * Have a nice day!
 */
public class AppEnvironment {
    public static final int DEV = 1;
    public static final int TEST = 2;
    public static final int PRODUCTION = 3;
    public static final String SUFFIX_DEV = "_dev";
    public static final String SUFFIX_TEST = "_test";
    public static final String SUFFIX_PRODUCTION = "_production";

    public static int getEnvironment() {
        return PreferencesManager.getInt(PreferencesManager.ENVIRONMENT);
    }

    public static void setEnvironment(int environment) {
        PreferencesManager.saveInt(environment, PreferencesManager.ENVIRONMENT);
    }

    public static String getTopicSuffix() {
        String suffix = "";

        switch (getEnvironment()) {
            case DEV:
                suffix = SUFFIX_DEV;
                break;
            case TEST:
                suffix = SUFFIX_TEST;
                break;
            case PRODUCTION:
                suffix = SUFFIX_PRODUCTION;
                break;
        }
        return suffix;
    }
}
