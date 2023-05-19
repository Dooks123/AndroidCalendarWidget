package com.dooks123.androidcalendarwidget.receivers;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dooks123.androidcalendarwidget.CalendarAppWidget;

public class CalendarChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, CalendarAppWidget.class));

        for (int appWidgetId : appWidgetIds) {
            Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
            int widgetRowSpan = (int) options.get("semAppWidgetRowSpan");
            int widgetColumnSpan = (int) options.get("semAppWidgetColumnSpan");

            CalendarAppWidget.updateAppWidget(context, appWidgetManager, appWidgetId, widgetRowSpan, widgetColumnSpan);
        }
    }
}