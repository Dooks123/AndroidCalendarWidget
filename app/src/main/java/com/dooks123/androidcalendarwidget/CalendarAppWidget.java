package com.dooks123.androidcalendarwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link CalendarAppWidgetConfigureActivity CalendarAppWidgetConfigureActivity}
 */
public class CalendarAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int widgetRowSpan, int widgetColumnSpan) {

        int backgroundOpacity = CalendarAppWidgetConfigureActivity.loadBackgroundOpacityPref(context, appWidgetId);
        boolean darkBackground = CalendarAppWidgetConfigureActivity.loadDarkBackgroundPref(context, appWidgetId);
        int backgroundColor = CalendarAppWidgetConfigureActivity.getBackgroundColor(darkBackground, backgroundOpacity);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.calendar_app_widget);
        views.setInt(android.R.id.background, "setBackgroundColor", backgroundColor);
        views.setTextViewText(R.id.appwidget_text, "PIELE " + widgetRowSpan + "x" + widgetColumnSpan);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
            int widgetRowSpan = (int) options.get("semAppWidgetRowSpan");
            int widgetColumnSpan = (int) options.get("semAppWidgetColumnSpan");

            updateAppWidget(context, appWidgetManager, appWidgetId, widgetRowSpan, widgetColumnSpan);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        for (String key : newOptions.keySet()) {
            Object obj = newOptions.get(key);
            Log.d("CalendarAppWidget", "OPTIONS CHANGED " + key + ": " + obj);
        }

        int widgetRowSpan = (int) newOptions.get("semAppWidgetRowSpan");
        int widgetColumnSpan = (int) newOptions.get("semAppWidgetColumnSpan");

        updateAppWidget(context, appWidgetManager, appWidgetId, widgetRowSpan, widgetColumnSpan);

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            CalendarAppWidgetConfigureActivity.deleteBackgroundOpacityPref(context, appWidgetId);
            CalendarAppWidgetConfigureActivity.deleteDarkBackgroundPref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}