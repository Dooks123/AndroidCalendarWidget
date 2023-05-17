package com.dooks123.androidcalendarwidget.util;

public class Utils {
    private static final String TAG = Utils.getClassTag(Utils.class);

    public static String getClassTag(Class<?> classy) {
        return classy.getSimpleName();
    }
}
