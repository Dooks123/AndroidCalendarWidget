package com.dooks123.androidcalendarwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RemoteViews;

import androidx.annotation.ColorInt;

import com.dooks123.androidcalendarwidget.helpers.ResourceHelper;
import com.dooks123.androidcalendarwidget.prefs.CalendarAppSharedPreferences;
import com.dooks123.androidcalendarwidget.services.WidgetEventRemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link CalendarAppWidgetConfigureActivity CalendarAppWidgetConfigureActivity}
 */
public class CalendarAppWidget extends AppWidgetProvider {

    public static void updateAppWidget(
            Context context,
            AppWidgetManager appWidgetManager,
            int appWidgetId,
            int widgetColumnSpan
    ) {
        CalendarAppSharedPreferences prefs = CalendarAppSharedPreferences.getInstance();

        int backgroundOpacity = prefs.getInt(CalendarAppSharedPreferences.KEY_BACKGROUND_OPACITY, appWidgetId, 100);
        boolean darkBackground = prefs.getBoolean(CalendarAppSharedPreferences.KEY_BACKGROUND_DARK, appWidgetId, true);
        int backgroundColor = CalendarAppWidgetConfigureActivity.getBackgroundColor(darkBackground, backgroundOpacity);
        boolean darkText = prefs.getBoolean(CalendarAppSharedPreferences.KEY_TEXT_DARK, appWidgetId, false);
        int textColor = darkText ? 0xff000000 : 0xffffffff;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.calendar_app_widget);
        views.setInt(android.R.id.background, "setBackgroundColor", backgroundColor);

        setContentContainer(views, widgetColumnSpan);
        setDateContainer(views, textColor, widgetColumnSpan);
        setEventsListView(context, views, appWidgetId, textColor);

        // Instruct the widget manager to update the widget
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.eventsListView);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
            int widgetColumnSpan = (int) options.get("semAppWidgetColumnSpan");

            updateAppWidget(context, appWidgetManager, appWidgetId, widgetColumnSpan);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        int widgetColumnSpan = (int) newOptions.get("semAppWidgetColumnSpan");

        updateAppWidget(context, appWidgetManager, appWidgetId, widgetColumnSpan);

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            CalendarAppSharedPreferences prefs = CalendarAppSharedPreferences.getInstance();
            prefs.remove(CalendarAppSharedPreferences.KEY_BACKGROUND_OPACITY, appWidgetId);
            prefs.remove(CalendarAppSharedPreferences.KEY_BACKGROUND_DARK, appWidgetId);
            prefs.remove(CalendarAppSharedPreferences.KEY_TEXT_DARK, appWidgetId);
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

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        int appWidgetId = intent.getIntExtra("appWidgetId", AppWidgetManager.INVALID_APPWIDGET_ID);
        if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
            Bundle options = intent.getBundleExtra("appWidgetOptions");
            if (options != null) {
                int widgetColumnSpan = options.getInt("semAppWidgetColumnSpan", 0);
                if (widgetColumnSpan > 0) {
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    updateAppWidget(context, appWidgetManager, appWidgetId, widgetColumnSpan);
                }
            }
        }
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    private static void setContentContainer(
            RemoteViews views,
            int widgetColumnSpan
    ) {
        boolean large = widgetColumnSpan > 2;
        int dp8 = ResourceHelper.getDP(8);
        int dp12 = ResourceHelper.getDP(12);
        int paddingLeftRight = large ? dp12 : dp8;

        views.setViewPadding(R.id.contentContainer, paddingLeftRight, dp12, paddingLeftRight, 0);
    }

    private static void setDateContainer(
            RemoteViews views,
            @ColorInt int textColor,
            int widgetColumnSpan
    ) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String day = dayFormat.format(calendar.getTime());
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        String month = monthFormat.format(calendar.getTime());

        views.setTextViewText(R.id.lblDay, day);
        views.setTextColor(R.id.lblDay, textColor);

        views.setTextViewText(R.id.lblDate, String.valueOf(date));
        views.setTextColor(R.id.lblDate, textColor);

        views.setTextViewText(R.id.lblMonth, month);
        views.setTextColor(R.id.lblMonth, textColor);

        views.setViewLayoutMargin(R.id.dateContainer, RemoteViews.MARGIN_TOP, ResourceHelper.getDP(8), TypedValue.COMPLEX_UNIT_PX);
        boolean large = widgetColumnSpan > 2;
        int dp8 = ResourceHelper.getDP(8);
        int dp12 = ResourceHelper.getDP(12);
        views.setViewLayoutMargin(R.id.dateContainer, RemoteViews.MARGIN_END, large ? dp12 : dp8, TypedValue.COMPLEX_UNIT_PX);
    }

    private static void setEventsListView(Context context, RemoteViews views, int appWidgetId, @ColorInt int textColor) {
        Intent eventsIntent = new Intent(context, WidgetEventRemoteViewsService.class);
        eventsIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        views.setRemoteAdapter(R.id.eventsListView, eventsIntent);
        views.setEmptyView(R.id.eventsListView, R.id.noEvents);
        views.setTextColor(R.id.noEvents, textColor);
    }
}