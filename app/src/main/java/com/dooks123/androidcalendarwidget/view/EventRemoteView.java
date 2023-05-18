package com.dooks123.androidcalendarwidget.view;

import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.ColorInt;

import com.dooks123.androidcalendarwidget.R;
import com.dooks123.androidcalendarwidget.object.CalendarEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventRemoteView extends RemoteViews {

    private final CalendarEvent event;
    private final boolean darkText;

    /**
     * Create a new EventRemoteView.
     *
     * @param packageName Name of the package that contains the {@code R.layout.sample_event_remote_view} layout resource
     * @param event       A {@link CalendarEvent} instance
     */
    public EventRemoteView(String packageName, CalendarEvent event, boolean darkText) {
        super(packageName, R.layout.sample_event_remote_view);

        this.event = event;
        this.darkText = darkText;

        init();
    }

    private void init() {
        int eventColor = eventColor();
        setInt(R.id.eventContainer, "setBackgroundColor", adjustAlpha(eventColor, 0.1f));
        setInt(R.id.eventColor, "setBackgroundColor", eventColor);
        setTextViewText(R.id.eventTitle, event.getTITLE());
        setTextColor(R.id.eventTitle, eventColor);
        setTextViewText(R.id.eventDuration, eventDuration());
        setTextColor(R.id.eventDuration, eventColor);
    }

    @ColorInt
    private int eventColor() {
        if (event.getEVENT_COLOR() == 0) {
            return darkText ? 0xff000000 : 0xffffffff;
        }
        return event.getEVENT_COLOR();
    }

    @ColorInt
    public static int adjustAlpha(@ColorInt int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    private String eventDuration() {
        if (event.isALL_DAY()) {
            return "All day";
        } else if (event.getDTSTART() > 0 && event.getDTEND() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String start = sdf.format(new Date(event.getDTSTART()));
            String end = sdf.format(new Date(event.getDTEND()));
            return start + " - " + end;
        } else {
            return event.getDURATION();
        }
    }
}