package com.se.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStrHelper {

    public static String getCurrentTime() {
        String pattern = "yyyy-MM-dd-HH-mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date currentTime = new Date();
        String time = dateFormat.format(currentTime);
        return time;
    }

    public static String getTimeBefore(int min) {
        String pattern = "yyyy-MM-dd-HH-mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date currentTime = new Date();
        Date queryTime = new Date(currentTime.getTime() - 20*60*1000);
        String queryTimeStr = dateFormat.format(queryTime);
        return queryTimeStr;
    }
}
