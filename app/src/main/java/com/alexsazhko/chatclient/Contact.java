package com.alexsazhko.chatclient;

import android.graphics.Bitmap;

public class Contact {

    private String name = null;
    private boolean isConnected = false;
    private Bitmap bmpPicture = null;

    public Contact(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

}
