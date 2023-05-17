package com.dooks123.androidcalendarwidget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dooks123.androidcalendarwidget.helpers.WindowHelper;
import com.dooks123.androidcalendarwidget.object.CalendarEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView centerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowHelper.setWindow(getWindow());

        setContentView(R.layout.activity_main);

        LinearLayout llRoot = findViewById(R.id.root);
        WindowHelper.setInsets(llRoot);

        centerText = findViewById(R.id.centerText);
    }

    @Override
    protected void onResume() {
        super.onResume();

        requestPermissionsManually();
    }

    public void requestPermissionsManually() {
        try {
            ArrayList<String> plist = new ArrayList<>(Arrays.asList(
                    Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.INTERNET,
                    Manifest.permission.QUERY_ALL_PACKAGES,
                    Manifest.permission.READ_CALENDAR,
                    Manifest.permission.WRITE_CALENDAR
            ));

            List<String> p = new ArrayList<>();
            for (String permission : plist) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    p.add(permission);
                }
            }

            if (p.size() > 0) {
                ActivityCompat.requestPermissions(this, p.toArray(new String[0]), 10522);
            } else {
                getCalendarEvents();
            }
        } catch (Exception ex) {
            Log.d("MainActivity", ".requestPermissionsManually " + ex.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 10522) {
            boolean allGranted = true;
            boolean shouldAskAgain = true;

            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                }
                if (!shouldShowRequestPermissionRationale(permissions[i])) {
                    shouldAskAgain = false;
                }
            }

            if (allGranted) {
                getCalendarEvents();
            } else {
                if (!shouldAskAgain) {
                    Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.setData(Uri.fromParts("package", "com.dooks123.androidcalendarwidget", null));
                    startActivity(i);
                    finish();
                } else {
                    requestPermissionsManually();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getCalendarEvents() {
        Uri contentUri = Uri.parse(CalendarContract.Events.CONTENT_URI.toString());
        ContentResolver contentResolver = getContentResolver();

        try (Cursor cursor = contentResolver.query(contentUri, projection, null, null, projection[0] + " ASC")) {
            if (cursor != null) {
                Log.d("MainActivity", "Calendar Cursor Count: " + cursor.getCount());

                if (cursor.moveToFirst()) {
                    do {
                        CalendarEvent calendarEvent = new CalendarEvent(cursor);
                        // Log.d("MainActivity", calendarEvent.toString());
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception ex) {
            Log.d("MainActivity", "Calendar Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
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