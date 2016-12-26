package com.example.trungnguyen.androidwatch.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Trung Nguyen on 12/21/2016.
 */
public class ConvertTimeMode {
    private static final String TAG = ConvertTimeMode.class.getSimpleName();

    public static String convertTo12HourMode(int currHour, int currMinute) {
        SimpleDateFormat format24Hour = new SimpleDateFormat("hh:mm");
        String mode24HourTime = String.valueOf(currHour) + ":" + String.valueOf(currMinute);
        Date date = null;
        try {
            date = format24Hour.parse(mode24HourTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format12Hour = new SimpleDateFormat("h:mm a");
        return format12Hour.format(date);
    }

    public static String[] convertTo24HourMode(String mode12Hour) {
        SimpleDateFormat format12Hour = new SimpleDateFormat("h:mm a");
        String mode12HourTime = mode12Hour;
        boolean isPM = false;
        if (mode12HourTime.contains("PM"))
            isPM = true;
        Date date = null;
        try {
            date = format12Hour.parse(mode12HourTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format24Hour = new SimpleDateFormat("hh:mm");
        String[] currentTime = new String[2];
        currentTime = format24Hour.format(date).split(":");
        if (isPM) {
            int currTimeIntMode = Integer.parseInt(currentTime[0]);
            currTimeIntMode += 12;
            currentTime[0] = String.valueOf(currTimeIntMode);
        }
        //format24Hour.format(date) sẻ trả về kiểu String dạng ví dụ "10:50"
        //ta cần split để lấy đc hai chuỗi "10" và "50"
        return currentTime;
    }
}
