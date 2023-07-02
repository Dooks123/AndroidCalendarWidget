package com.dooks123.androidcalendarwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.dooks123.androidcalendarwidget.helpers.ResourceHelper;
import com.dooks123.androidcalendarwidget.prefs.CalendarAppSharedPreferences;
import com.dooks123.androidcalendarwidget.queries.CalendarQuery;
import com.dooks123.androidcalendarwidget.services.WidgetEventRemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link CalendarAppWidgetConfigureActivity CalendarAppWidgetConfigureActivity}
 */
public class CalendarAppWidget extends AppWidgetProvider {

    public static void updateAppWidget(
            Context context,
            AppWidgetManager appWidgetManager,
            int appWidgetId,
            int widgetRowSpan,
            int widgetColumnSpan
    ) {
        CalendarAppSharedPreferences prefs = CalendarAppSharedPreferences.getInstance();

        Date lastRefreshed = new Date();
        prefs.setDate(CalendarAppSharedPreferences.KEY_LAST_REFRESH_DATE, appWidgetId, lastRefreshed);

        int backgroundOpacity = prefs.getInt(CalendarAppSharedPreferences.KEY_BACKGROUND_OPACITY, appWidgetId, 100);
        boolean darkBackground = prefs.getBoolean(CalendarAppSharedPreferences.KEY_BACKGROUND_DARK, appWidgetId, true);
        int backgroundColor = CalendarAppWidgetConfigureActivity.getBackgroundColor(darkBackground, backgroundOpacity);
        boolean darkText = prefs.getBoolean(CalendarAppSharedPreferences.KEY_TEXT_DARK, appWidgetId, false);
        int textColor = darkText ? 0xff000000 : 0xffffffff;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.calendar_app_widget);

        setContainer(views, backgroundColor, widgetColumnSpan);
        setDateContainer(views, textColor, widgetRowSpan, widgetColumnSpan);
        setEventsListView(context, views, appWidgetId, textColor, widgetColumnSpan);
        setRefreshView(context, views, appWidgetId, darkText, textColor, lastRefreshed, widgetColumnSpan);

        Intent openCalendarIntent = new Intent(Intent.ACTION_VIEW, CalendarQuery.getCalendarContractUri());
        PendingIntent openCalendarPendingIntent = PendingIntent.getActivity(context, 0, openCalendarIntent, PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(android.R.id.background, openCalendarPendingIntent);

        views.setViewVisibility(R.id.refreshImage, View.VISIBLE);
        views.setViewVisibility(R.id.refreshProgressBarLight, View.GONE);
        views.setViewVisibility(R.id.refreshProgressBarDark, View.GONE);

        // Instruct the widget manager to update the widget
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.eventsListView);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateAppWidgetRefreshing(final Context context, final int appWidgetId) {
        CalendarAppSharedPreferences prefs = CalendarAppSharedPreferences.getInstance();
        boolean darkText = prefs.getBoolean(CalendarAppSharedPreferences.KEY_TEXT_DARK, appWidgetId, false);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.calendar_app_widget);

        views.setViewVisibility(R.id.refreshImage, View.GONE);
        views.setViewVisibility(R.id.refreshProgressBarLight, View.GONE);
        views.setViewVisibility(R.id.refreshProgressBarDark, View.GONE);

        if (darkText) {
            views.setViewVisibility(R.id.refreshProgressBarDark, View.VISIBLE);
        } else {
            views.setViewVisibility(R.id.refreshProgressBarLight, View.VISIBLE);
        }

        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        appWidgetManager.partiallyUpdateAppWidget(appWidgetId, views);

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        final int widgetRowSpan = options.getInt("semAppWidgetRowSpan", 0);
        final int widgetColumnSpan = options.getInt("semAppWidgetColumnSpan", 0);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                updateAppWidget(context, appWidgetManager, appWidgetId, widgetRowSpan, widgetColumnSpan);
            }
        }, 800);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
            int widgetRowSpan = options.getInt("semAppWidgetRowSpan", 0);
            int widgetColumnSpan = options.getInt("semAppWidgetColumnSpan", 0);

            updateAppWidget(context, appWidgetManager, appWidgetId, widgetRowSpan, widgetColumnSpan);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        int widgetRowSpan = (int) newOptions.get("semAppWidgetRowSpan");
        int widgetColumnSpan = (int) newOptions.get("semAppWidgetColumnSpan");

        updateAppWidget(context, appWidgetManager, appWidgetId, widgetRowSpan, widgetColumnSpan);

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
        if (intent.getAction().equals("ACTION_REFRESH")) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            updateAppWidgetRefreshing(context, appWidgetId);
        } else {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                Bundle options = intent.getBundleExtra("appWidgetOptions");
                if (options != null) {
                    int widgetRowSpan = options.getInt("semAppWidgetColumnSpan", 0);
                    int widgetColumnSpan = options.getInt("semAppWidgetColumnSpan", 0);
                    if (widgetRowSpan > 0 && widgetColumnSpan > 0) {
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                        updateAppWidget(context, appWidgetManager, appWidgetId, widgetRowSpan, widgetColumnSpan);
                    }
                }
            }
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    private static void setContainer(
            RemoteViews views,
            @ColorInt int backgroundColor,
            int widgetColumnSpan
    ) {
        views.setInt(android.R.id.background, "setBackgroundColor", backgroundColor);

        Rect padding = getWidgetHorizontalPadding(widgetColumnSpan);
        views.setViewPadding(android.R.id.background, padding.left, 0, 0, 0);
    }

    private static void setDateContainer(
            RemoteViews views,
            @ColorInt int textColor,
            int widgetRowSpan,
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

        boolean large = widgetColumnSpan > 2;
        int dp8 = ResourceHelper.getDP(8);
        int dp16 = ResourceHelper.getDP(16);
        views.setViewLayoutMargin(R.id.dateContainer, RemoteViews.MARGIN_END, large ? dp16 : dp8, TypedValue.COMPLEX_UNIT_PX);

        if (widgetRowSpan > 1) {
            views.setViewLayoutHeight(R.id.dateContainer, 130, TypedValue.COMPLEX_UNIT_DIP);
        } else {
            views.setViewLayoutHeight(R.id.dateContainer, LinearLayout.LayoutParams.MATCH_PARENT, TypedValue.COMPLEX_UNIT_PX);
        }
    }

    private static void setEventsListView(
            Context context,
            RemoteViews views,
            int appWidgetId,
            @ColorInt int textColor,
            int widgetColumnSpan
    ) {
        Intent eventsIntent = new Intent(context, WidgetEventRemoteViewsService.class);
        eventsIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        views.setRemoteAdapter(R.id.eventsListView, eventsIntent);
        views.setEmptyView(R.id.eventsListView, R.id.noEvents);
        views.setTextColor(R.id.noEvents, textColor);

        Rect padding = getWidgetHorizontalPadding(widgetColumnSpan);
        views.setViewPadding(R.id.eventsListView, 0, 0, padding.right, 0);
    }

    private static void setRefreshView(
            Context context,
            RemoteViews views,
            int appWidgetId,
            boolean darkText,
            @ColorInt int textColor,
            Date lastRefreshed,
            int widgetColumnSpan
    ) {
        SimpleDateFormat lastRefreshedDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String lastRefreshedDate = lastRefreshedDateFormat.format(lastRefreshed);

        views.setTextViewText(R.id.lastRefreshDate, lastRefreshedDate);
        views.setTextColor(R.id.lastRefreshDate, textColor);

        views.setInt(R.id.refreshImage, "setColorFilter", textColor);

        Intent refreshIntent = new Intent(context, CalendarAppWidget.class);
        refreshIntent.setAction("ACTION_REFRESH");
        refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0, refreshIntent, PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.refreshLayout, refreshPendingIntent);

        Rect padding = getWidgetHorizontalPadding(widgetColumnSpan);
        views.setViewPadding(R.id.refreshLayout, 0, ResourceHelper.getDP(8), padding.right, 0);
    }

    private static Rect getWidgetHorizontalPadding(int widgetColumnSpan) {
        boolean large = widgetColumnSpan > 2;
        int dp8 = ResourceHelper.getDP(8);
        int dp20 = ResourceHelper.getDP(20);
        int padding = large ? dp20 : dp8;

        return new Rect(padding, 0, padding, 0);
    }
}