package com.alexsazhko.chatclient;

import android.app.Application;


public class ChatApplication extends Application {

    private static ChatApplication instance = null;

    public static ChatApplication getInstance() {
        return instance;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
    }
}
