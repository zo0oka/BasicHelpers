package com.zo0okadev.basichelpers;

import android.app.Application;
import android.content.Context;

/**
 * Created by Zo0okaDev (https://github.com/zo0oka)
 * On 02 Jan, 2021.
 * Have a nice day!
 */
public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        BasicHelpers.init(this, 1, 0);
        super.attachBaseContext(base);
    }
}
