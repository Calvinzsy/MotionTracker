package com.example.calvin.motiontracker.util;

import java.text.DateFormat;
import java.util.Date;

public class Utils {

    private static DateFormat dateFormat = DateFormat.getDateTimeInstance();

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }
}
