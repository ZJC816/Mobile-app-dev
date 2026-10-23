package com.example.smartpantry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class AppUtils {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final long MILLIS_PER_DAY = 86400000L;
    public static final int EXPIRY_WARNING_DAYS = 3;

    private AppUtils() {
    }

    public static String formatQuantity(double quantity) {
        if (quantity == Math.floor(quantity)) {
            return String.valueOf((long) quantity);
        }
        String text = String.format(Locale.US, "%.2f", quantity);
        text = text.replaceAll("0+$", "");
        return text.replaceAll("\\.$", "");
    }

    public static String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return text.substring(0, 1).toUpperCase(Locale.ROOT) + text.substring(1);
    }

    public static String formatIsoDate(int year, int monthZeroBased, int day) {
        return String.format(Locale.US, "%04d-%02d-%02d", year, monthZeroBased + 1, day);
    }

    public static long daysUntil(String isoDate) {
        if (isoDate == null || isoDate.trim().isEmpty()) {
            return Long.MAX_VALUE;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.US);
            format.setLenient(false);
            Date target = format.parse(isoDate.trim());
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);
            long difference = target.getTime() - today.getTimeInMillis();
            return Math.round(difference / (double) MILLIS_PER_DAY);
        } catch (ParseException e) {
            return Long.MAX_VALUE;
        }
    }

    public static boolean isExpiringSoon(String isoDate) {
        return daysUntil(isoDate) <= EXPIRY_WARNING_DAYS;
    }
}
