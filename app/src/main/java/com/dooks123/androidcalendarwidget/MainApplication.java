package com.dooks123.androidcalendarwidget;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dooks123.androidcalendarwidget.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = Utils.getClassTag(MainApplication.class);

    private static Context context;

    private final List<String> runningActivities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        registerActivityLifecycleCallbacks(this);

        log(TAG + ".onCreate");
    }

    @Override
    public void onTerminate() {
        unregisterActivityLifecycleCallbacks(this);

        log(TAG + ".onTerminate");

        super.onTerminate();
    }

    @Nullable
    @Override
    public ComponentName startService(Intent service) {
        log(TAG + ".startService", "Action: " + service.getAction());

        return super.startService(service);
    }

    @Nullable
    @Override
    public ComponentName startForegroundService(Intent service) {
        log(TAG + ".startForegroundService", "Action: " + service.getAction());

        return super.startForegroundService(service);
    }

    @Override
    public boolean stopService(Intent name) {
        log(TAG + ".stopService", "Action: " + name.getAction());

        return super.stopService(name);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        runningActivities.add(activity.getClass().getSimpleName());

        log(TAG + ".onActivityCreated");
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        log(TAG + ".onActivityStarted");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        log(TAG + ".onActivityResumed");
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        log(TAG + ".onActivityPaused");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        log(TAG + ".onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        log(TAG + ".onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        runningActivities.remove(activity.getClass().getSimpleName());

        log(TAG + ".onActivityDestroyed");
    }

    private void log(String Tag) {
        log(Tag, null);
    }

    private void log(String Tag, String preInfo) {
        Log.d(Tag, (preInfo != null ? preInfo + " " : "") + "" + getActivityInfo());
    }

    public static Context getContext() {
        return context;
    }

    private String getActivityInfo() {
        return "(" + runningActivities.size() + ") Activities: " + Arrays.toString(runningActivities.toArray(new String[0]));
    }
}
