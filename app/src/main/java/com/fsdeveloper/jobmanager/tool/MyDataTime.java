package com.fsdeveloper.jobmanager.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * It contains useful methods that manipulate date and time.
 *
 * @author Created by Douglas Rafael on 02/05/2016.
 * @version 1.0
 */
public final class MyDataTime {
    private static DateFormat dateFormat;

    /**
     * Get/ Retrieve calendar instance.
     *
     * @return The Calendar.
     */
    public static Calendar getCalendar() {
        return GregorianCalendar.getInstance();
    }

    /**
     * Returns current date time of system.
     *
     * @param format_date The date and time format.
     * @return The data time.
     */
    public static String getDataTime(String format_date) {
        Calendar calendar = GregorianCalendar.getInstance();

        dateFormat = new SimpleDateFormat(format_date);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Convert string in Date.
     *
     * @param s The string datetime.
     * @param format_date The date and time format.
     * @return The Object Date
     * @throws ParseException If an error occurs in converting string to date.
     */
    public static Date toStringDataTime(String s, String format_date) throws ParseException {
        dateFormat = new SimpleDateFormat(format_date);

        return dateFormat.parse(s);
    }
}
