package org.smirl.julisha.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static Date getDate(String date) throws ParseException {
        return getDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getDate(String date, String srcFormat) throws ParseException {
        return getDate(date, srcFormat, Locale.ENGLISH);
    }

    public static Date getDate(String date, String srcFormat, Locale locale) throws ParseException {
        return new SimpleDateFormat(srcFormat, locale).parse(date);

    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String formatDate(String date, String srcFormat, String destFormat) throws ParseException {
        return formatDate(date, srcFormat, destFormat, Locale.ENGLISH);
    }


    public static String formatDate(long date, String destFormat, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(destFormat, locale);
        return sdf.format(new Date(date));
    }


    public static String formatDate(String date, String srcFormat, String destFormat, Locale locale) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(destFormat, Locale.FRANCE);
        return sdf.format(getDate(date, srcFormat, locale));
    }


    public static Date getDate() {
        return new Date();
    }

    public static String getFromCurrentDate(String format, Locale locale, int dayInterval) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DAY_OF_YEAR, dayInterval);
        return sdf.format(ca.getTime());
    }

    public static String getFromCurrentDate(String format, int dayInterval) {
        return getFromCurrentDate(format, Locale.ENGLISH, dayInterval);
    }

    public static String getCurrentDate(String format, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        return sdf.format(getDate());
    }

    public static String getCurrentDate(String format) {
        return getCurrentDate(format, Locale.ENGLISH);
    }

    public static String getCurrentDateTime() {
        String f = "yyyy-MM-dd HH:mm:ss";
        return getCurrentDate(f);

    }

    public static String now() {
        return getCurrentDateTime();

    }
}
