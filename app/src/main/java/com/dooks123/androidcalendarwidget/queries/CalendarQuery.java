package com.dooks123.androidcalendarwidget.queries;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import com.dooks123.androidcalendarwidget.MainApplication;
import com.dooks123.androidcalendarwidget.object.CalendarEvent;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarQuery {

    public static Uri getCalendarContractUri() {
        return Uri.parse(CalendarContract.CONTENT_URI.toString());
    }

    public static Uri getCalendarEventsContractUri() {
        return Uri.parse(CalendarContract.Events.CONTENT_URI.toString());
    }

    public static List<CalendarEvent> getCalendarEventsForToday() {
        Uri contentUri = getCalendarEventsContractUri();
        ContentResolver contentResolver = MainApplication.getContext().getContentResolver();

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.add(Calendar.DATE, 1);

        String selection = "strftime('%Y-%m-%d', " + CalendarContract.Events.DTSTART + " / 1000, 'unixepoch', 'utc', 'localtime') = strftime('%Y-%m-%d', 'now', 'localtime') "
                + " AND " + CalendarContract.Events.DELETED + " != 1";

        List<CalendarEvent> events = new ArrayList<>();

        try (Cursor cursor = contentResolver.query(contentUri, projection, selection, null, projection[0] + " ASC")) {
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        CalendarEvent calendarEvent = new CalendarEvent(cursor);
                        events.add(calendarEvent);
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception ex) {
            Log.d("CalendarQuery", "CalendarQuery.getCalendarEvents Exception: " + ex.getMessage());
            ex.printStackTrace();
        }

        return events;
    }

    private static final String[] projection = new String[]{
            CalendarContract.Events._ID,
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.ACCESS_LEVEL,
            CalendarContract.Events.ACCOUNT_NAME,
            CalendarContract.Events.ACCOUNT_TYPE,
            CalendarContract.Events.ALL_DAY,
            CalendarContract.Events.AVAILABILITY,
            CalendarContract.Events.CALENDAR_DISPLAY_NAME,
            CalendarContract.Events.CALENDAR_TIME_ZONE,
            CalendarContract.Events.DELETED,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.DISPLAY_COLOR,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DURATION,
            CalendarContract.Events.EVENT_COLOR,
            CalendarContract.Events.EVENT_COLOR_KEY,
            CalendarContract.Events.EVENT_END_TIMEZONE,
            CalendarContract.Events.EVENT_LOCATION,
            CalendarContract.Events.EVENT_TIMEZONE,
            CalendarContract.Events.EXDATE,
            CalendarContract.Events.EXRULE,
            CalendarContract.Events.IS_ORGANIZER,
            CalendarContract.Events.IS_PRIMARY,
            CalendarContract.Events.LAST_DATE,
            CalendarContract.Events.LAST_SYNCED,
            CalendarContract.Events.ORGANIZER,
            CalendarContract.Events.ORIGINAL_ALL_DAY,
            CalendarContract.Events.ORIGINAL_ID,
            CalendarContract.Events.ORIGINAL_INSTANCE_TIME,
            CalendarContract.Events.ORIGINAL_SYNC_ID,
            CalendarContract.Events.OWNER_ACCOUNT,
            CalendarContract.Events.RDATE,
            CalendarContract.Events.RRULE,
            CalendarContract.Events.SELF_ATTENDEE_STATUS,
            CalendarContract.Events.STATUS,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.UID_2445,
            CalendarContract.Events.VISIBLE
    };
}
