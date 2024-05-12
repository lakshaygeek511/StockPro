package com.example.machinetestproject.di;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
