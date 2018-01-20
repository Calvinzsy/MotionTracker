package com.example.calvin.motiontracker.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility class that provides common methods for convenience.
 */
public class Utils {

    private static DateFormat dateFormat = DateFormat.getDateTimeInstance();

    /**
     * Format a date to string based on current locale.
     * @param date The date to be formatted.
     * @return A string representation of the date.
     */
    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    /**
     * Get current UTC time in milliseconds.
     * @return Current UTC time in milliseconds.
     */
    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
