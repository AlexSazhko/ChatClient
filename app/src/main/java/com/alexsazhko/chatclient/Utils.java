package com.alexsazhko.chatclient;


import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    /** get image id from resource
     *
     * @param pictureName picture name include a dot and extension
     * @param context
     * @return image id
     */
    public static int getImageId(String pictureName, Context context){

        pictureName = pictureName.substring(0, pictureName.lastIndexOf("."));
        int id_image = context.getResources().getIdentifier((context.getPackageName() + ":drawable/" + pictureName), null, null);
        return id_image;

    }

    /**
     Get time in format HH:mm

     @param mills Time in milliseconds.
     @return Time in string.

     */
    public static String getShortestTimeFormat(Long mills){

        // New date object from mills
        Date date = new Date(mills);
        //Set Time zone
        //TimeZone destTz = TimeZone.getTimeZone("GMT");

        // formatter
        SimpleDateFormat df= new SimpleDateFormat("HH:mm", Locale.UK);
        //df.setTimeZone(destTz);

        // Pass date object
        String formatted = df.format(date);

        return formatted;
    }
}
