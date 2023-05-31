package com.dooks123.androidcalendarwidget.factories;

import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dooks123.androidcalendarwidget.MainApplication;
import com.dooks123.androidcalendarwidget.object.CalendarEvent;
import com.dooks123.androidcalendarwidget.prefs.CalendarAppSharedPreferences;
import com.dooks123.androidcalendarwidget.queries.CalendarQuery;
import com.dooks123.androidcalendarwidget.view.EventRemoteView;

import java.util.List;

public class WidgetEventRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final int appWidgetId;
    private List<CalendarEvent> eventList;

    public WidgetEventRemoteViewsFactory(int appWidgetId) {
        this.appWidgetId = appWidgetId;

        setEventList();
    }

    private void setEventList() {
        eventList = CalendarQuery.getCalendarEventsForToday();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        setEventList();
    }

    @Override
    public void onDestroy() {
        eventList.clear();
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        CalendarAppSharedPreferences prefs = CalendarAppSharedPreferences.getInstance();
        boolean darkText = prefs.getBoolean(CalendarAppSharedPreferences.KEY_TEXT_DARK, appWidgetId, false);
        return new EventRemoteView(MainApplication.getContext().getPackageName(), eventList.get(position), darkText);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
