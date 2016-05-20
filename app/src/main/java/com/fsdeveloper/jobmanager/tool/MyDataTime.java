package com.fsdeveloper.jobmanager.tool;

import android.util.Log;

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
     * Returns current datetime of system.
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
     * Returns datetime according to the parameters.
     *
     * @param year        The year
     * @param month       The month
     * @param day         The day
     * @param hourOfDay   The hour
     * @param minute      The minute
     * @param format_date The format date output.
     * @return The string datetime.
     */
    public static String getDataTime(int year, int month, int day, int hourOfDay, int minute, String format_date) {
        Calendar calendar = GregorianCalendar.getInstance();
        // Value to be used for MONTH field. 0 is January
        calendar.set(year, month, day, hourOfDay, minute);

        dateFormat = new SimpleDateFormat(format_date);
        return dateFormat.format(calendar.getTime());
    }


    /**
     * yyyy-MM-dd hh:mm:ss
     *
     * @param date_input
     * @param format_date
     * @param date
     * @param time
     * @return
     */
    public static String getFormatDataTimeDB(String date_input, String format_date, boolean date, boolean time) {
        String result = "";

        if (date && !time) {
            result = getDataTime(Integer.parseInt(date_input.substring(0, 4)), Integer.parseInt(date_input.substring(5, 7)),
                    Integer.parseInt(date_input.substring(8, 10)), 0, 0, format_date);
        } else if (time && !date) {
            result = getDataTime(0, 0, 0, Integer.parseInt(date_input.substring(11, 13)), Integer.parseInt(date_input.substring(14, 16)), format_date);
        } else {
            result = getDataTime(Integer.parseInt(date_input.substring(0, 4)), Integer.parseInt(date_input.substring(5, 7)),
                    Integer.parseInt(date_input.substring(8, 10)), Integer.parseInt(date_input.substring(11, 13)), Integer.parseInt(date_input.substring(14, 16)), format_date);
        }

        return result;
    }

    /**
     * Convert string in Date.
     * Formats have to be equal.
     *
     * @param date_time   The string datetime.
     * @param format_date The date and time format.
     * @return The Object Date
     * @throws ParseException If an error occurs in converting string to date.
     */
    public static Date toStringDataTime(String date_time, String format_date) throws ParseException {
        return new SimpleDateFormat(format_date).parse(date_time);
    }

}
