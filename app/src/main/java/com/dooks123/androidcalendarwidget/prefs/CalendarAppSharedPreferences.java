package com.dooks123.androidcalendarwidget.prefs;

import android.content.SharedPreferences;

import com.dooks123.androidcalendarwidget.MainApplication;

public class CalendarAppSharedPreferences {
    private static final String PREFS_NAME = "com.dooks123.androidcalendarwidget.CalendarAppWidget";

    public static final String KEY_BACKGROUND_OPACITY = "background_opacity_";
    public static final String KEY_BACKGROUND_DARK = "background_dark_";
    public static final String KEY_TEXT_DARK = "text_dark_";

    private static CalendarAppSharedPreferences instance;

    public static CalendarAppSharedPreferences getInstance() {
        if (instance == null) {
            instance = new CalendarAppSharedPreferences();
        }
        return instance;
    }

    private CalendarAppSharedPreferences() {
    }

    private SharedPreferences.Editor getEditor() {
        return MainApplication.getContext().getSharedPreferences(PREFS_NAME, 0).edit();
    }

    private SharedPreferences getPreferences() {
        return MainApplication.getContext().getSharedPreferences(PREFS_NAME, 0);
    }

    public void setInt(String key, int appWidgetId, int value) {
        SharedPreferences.Editor prefs = getEditor();
        prefs.putInt(key + appWidgetId, value);
        prefs.apply();
    }

    public int getInt(String key, int appWidgetId) {
        return getInt(key, appWidgetId, 0);
    }

    public int getInt(String key, int appWidgetId, int defaultValue) {
        return getPreferences().getInt(key + appWidgetId, defaultValue);
    }

    public void setBoolean(String key, int appWidgetId, boolean value) {
        SharedPreferences.Editor prefs = getEditor();
        prefs.putBoolean(key + appWidgetId, value);
        prefs.apply();
    }

    public boolean getBoolean(String key, int appWidgetId) {
        return getBoolean(key, appWidgetId, false);
    }

    public boolean getBoolean(String key, int appWidgetId, boolean defaultValue) {
        return getPreferences().getBoolean(key + appWidgetId, defaultValue);
    }

    public void remove(String key, int appWidgetId) {
        SharedPreferences.Editor prefs = getEditor();
        prefs.remove(key + appWidgetId);
        prefs.apply();
    }
}
