package com.jenle.interviewapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Jenle Samuel on 12/7/16.
 */

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
