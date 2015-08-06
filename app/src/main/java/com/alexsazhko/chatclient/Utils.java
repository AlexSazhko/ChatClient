package com.alexsazhko.chatclient;


import android.content.Context;

public class Utils {

    //get image id from resource
    public static int getImageId(String pictureName, Context context){

        pictureName = pictureName.substring(0, pictureName.lastIndexOf("."));
        int id_image = context.getResources().getIdentifier((context.getPackageName() + ":drawable/" + pictureName), null, null);
        return id_image;

    }
}
