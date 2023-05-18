package com.dooks123.androidcalendarwidget.services;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.dooks123.androidcalendarwidget.factories.WidgetEventRemoteViewsFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WidgetEventRemoteViewsService extends RemoteViewsService {

    private final HashMap<Integer, WidgetEventRemoteViewsFactory> factories = new HashMap<>();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
            if (factories.containsKey(appWidgetId)) {
                return factories.get(appWidgetId);
            } else {
                WidgetEventRemoteViewsFactory factory = new WidgetEventRemoteViewsFactory(appWidgetId);
                factories.put(appWidgetId, factory);
                return factory;
            }
        }
        return null;
    }
}
