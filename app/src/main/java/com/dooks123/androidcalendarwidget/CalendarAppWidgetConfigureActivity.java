package com.dooks123.androidcalendarwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;

import com.dooks123.androidcalendarwidget.databinding.CalendarAppWidgetConfigureBinding;
import com.dooks123.androidcalendarwidget.helpers.ResourceHelper;
import com.dooks123.androidcalendarwidget.helpers.WindowHelper;

public class CalendarAppWidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "com.dooks123.androidcalendarwidget.CalendarAppWidget";
    private static final String PREF_PREFIX_OPACITY_KEY = "appwidget_opacity_";
    private static final String PREF_PREFIX_DARK_KEY = "appwidget_dark_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    int seekBarOpacityValue = 100;
    boolean darkBackground = true;

    AppWidgetManager appWidgetManager;

    SwitchCompat darkBackgroundSwitch;
    AppCompatSeekBar seekBarBackgroundOpacity;
    View calendarAppWidgetView;
    View calendarAppWidgetRootView;

    CompoundButton.OnCheckedChangeListener darkBackgroundListener = (buttonView, isChecked) -> {
        darkBackground = isChecked;
        setCalendarAppWidgetRootViewBackground();
    };

    SeekBar.OnSeekBarChangeListener seekBarBackgroundOpacityListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            seekBarOpacityValue = progress;
            setCalendarAppWidgetRootViewBackground();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            seekBarOpacityValue = seekBar.getProgress();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            seekBarOpacityValue = seekBar.getProgress();
        }
    };

    View.OnClickListener applyOnClickListener = v -> {
        final Context context = CalendarAppWidgetConfigureActivity.this;

        // When the button is clicked, store the string locally
        saveBackgroundOpacityPref(context, mAppWidgetId, seekBarOpacityValue);
        saveDarkBackgroundPref(context, mAppWidgetId, darkBackground);

        Bundle options = appWidgetManager.getAppWidgetOptions(mAppWidgetId);
        int widgetRowSpan = (int) options.get("semAppWidgetRowSpan");
        int widgetColumnSpan = (int) options.get("semAppWidgetColumnSpan");

        CalendarAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId, widgetRowSpan, widgetColumnSpan);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    };

    public CalendarAppWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        // Find the widget id from the intent.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        seekBarOpacityValue = loadBackgroundOpacityPref(CalendarAppWidgetConfigureActivity.this, mAppWidgetId);
        darkBackground = loadDarkBackgroundPref(CalendarAppWidgetConfigureActivity.this, mAppWidgetId);

        CalendarAppWidgetConfigureBinding binding = CalendarAppWidgetConfigureBinding.inflate(getLayoutInflater());

        WindowHelper.setInsets(binding.getRoot());

        setContentView(binding.getRoot());

        calendarAppWidgetView = getLayoutInflater().inflate(R.layout.calendar_app_widget, binding.previewContainer, false);

        appWidgetManager = AppWidgetManager.getInstance(this);
        Bundle options = appWidgetManager.getAppWidgetOptions(mAppWidgetId);

        calendarAppWidgetRootView = calendarAppWidgetView.findViewById(android.R.id.background);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) calendarAppWidgetRootView.getLayoutParams();
        layoutParams.width = Math.min(ResourceHelper.getDP((int) options.get("appWidgetMaxWidth")) - 40, ResourceHelper.getScreenWidth() - 80);
        layoutParams.height = ResourceHelper.getDP((int) options.get("appWidgetMaxHeight") - 20);

        setCalendarAppWidgetRootViewBackground();

        binding.previewContainer.addView(calendarAppWidgetView);
        binding.previewContainer.setBackgroundResource(R.drawable.calendar_widget_preview_rounded_corners);

        darkBackgroundSwitch = binding.darkBackground;
        darkBackgroundSwitch.setChecked(darkBackground);
        darkBackgroundSwitch.setOnCheckedChangeListener(darkBackgroundListener);

        seekBarBackgroundOpacity = binding.backgroundOpacity;

        seekBarBackgroundOpacity.setMin(0);
        seekBarBackgroundOpacity.setMax(100);

        seekBarBackgroundOpacity.setProgress(seekBarOpacityValue, true);
        seekBarBackgroundOpacity.setOnSeekBarChangeListener(seekBarBackgroundOpacityListener);

        binding.btnApply.setOnClickListener(applyOnClickListener);
    }

    @Override
    protected void onDestroy() {
        seekBarBackgroundOpacity.setOnSeekBarChangeListener(null);
        darkBackgroundSwitch.setOnCheckedChangeListener(null);

        super.onDestroy();
    }

    private void setCalendarAppWidgetRootViewBackground() {
        int backgroundColor = getBackgroundColor(darkBackground, seekBarOpacityValue);
        PaintDrawable backgroundShape = new PaintDrawable(backgroundColor);
        backgroundShape.setCornerRadius(getResources().getDimension(android.R.dimen.system_app_widget_background_radius));
        calendarAppWidgetRootView.setBackground(backgroundShape);
    }

    @ColorInt
    static int getBackgroundColor(boolean dark, int opacityPercentage) {
        if (dark) {
            return Color.argb((int) (2.55 * opacityPercentage), 0, 0, 0);
        } else {
            return Color.argb((int) (2.55 * opacityPercentage), 255, 255, 255);
        }
    }

    static void saveBackgroundOpacityPref(Context context, int appWidgetId, int backgroundOpacity) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_PREFIX_OPACITY_KEY + appWidgetId, backgroundOpacity);
        prefs.apply();
    }

    static int loadBackgroundOpacityPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getInt(PREF_PREFIX_OPACITY_KEY + appWidgetId, 100);
    }

    static void deleteBackgroundOpacityPref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_OPACITY_KEY + appWidgetId);
        prefs.apply();
    }

    static void saveDarkBackgroundPref(Context context, int appWidgetId, boolean darkBackground) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putBoolean(PREF_PREFIX_DARK_KEY + appWidgetId, darkBackground);
        prefs.apply();
    }

    static boolean loadDarkBackgroundPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getBoolean(PREF_PREFIX_DARK_KEY + appWidgetId, true);
    }

    static void deleteDarkBackgroundPref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_DARK_KEY + appWidgetId);
        prefs.apply();
    }
}