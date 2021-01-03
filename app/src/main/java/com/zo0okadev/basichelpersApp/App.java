package com.zo0okadev.basichelpersApp;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

/**
 * Created by Zo0okaDev (https://github.com/zo0oka)
 * On 02 Jan, 2021.
 * Have a nice day!
 */
public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
