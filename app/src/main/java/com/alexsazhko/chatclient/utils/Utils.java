package com.alexsazhko.chatclient.utils;


import android.content.Context;
import android.widget.Toast;

import com.alexsazhko.chatclient.ChatApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    private static Context context = ChatApplication.getInstance();

    /** get image id from resource
     *
     * @param pictureName picture name include a dot and extension
     * @return image id
     */
    public static int getImageId(String pictureName){

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

    public static void showShortToast(int message){
        Toast toast = Toast.makeText(context, context.getResources().getString(message), Toast.LENGTH_SHORT);
        toast.show();
    }
}
