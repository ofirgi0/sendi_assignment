package com.example.sendiassignment.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class Util {



    public static boolean isNetworkAvailable(Context context) {
        boolean connected;
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        return connected;
    }

    public static String getDateFormat(CreatedTime time){
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, -(time.getNumOfDays()));
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.getTime());
    }

    public static String[] getCreatedDateNames() {
        CreatedTime[] time = CreatedTime.values();
        String[] dateNames = new String[time.length];
        for (int i = 0; i < time.length; i++){
            dateNames[i] = time[i].getDisplayText();
        }
        return dateNames;
    }

    public static String getDate(String dateString) {

        try{
            SimpleDateFormat format1 = new SimpleDateFormat(Constants.DATE_TIME_FORMAT, Locale.getDefault());
            Date date = format1.parse(dateString);
            DateFormat sdf = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            return sdf.format(date);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return "xx";
        }
    }

    public static String getTime(String dateString) {

        try{
            SimpleDateFormat format1 = new SimpleDateFormat(Constants.DATE_TIME_FORMAT, Locale.getDefault());
            Date date = format1.parse(dateString);
            DateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
            return sdf.format((date));
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return "xx";
        }
    }


}
