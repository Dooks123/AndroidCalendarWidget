package com.dooks123.androidcalendarwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;

import com.dooks123.androidcalendarwidget.adapters.EventListAdapter;
import com.dooks123.androidcalendarwidget.databinding.CalendarAppWidgetConfigureBinding;
import com.dooks123.androidcalendarwidget.helpers.ResourceHelper;
import com.dooks123.androidcalendarwidget.helpers.WindowHelper;
import com.dooks123.androidcalendarwidget.prefs.CalendarAppSharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarAppWidgetConfigureActivity extends Activity {
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    int seekBarOpacityValue = 100;
    boolean darkBackground = true;
    boolean darkText = false;

    int widgetRowSpan = 1;
    int widgetColumnSpan = 2;

    int textColor = 0xff000000;

    AppWidgetManager appWidgetManager;

    SwitchCompat darkTextSwitch;
    SwitchCompat darkBackgroundSwitch;
    AppCompatSeekBar seekBarBackgroundOpacity;
    View calendarAppWidgetView;
    View calendarAppWidgetRootView;

    CompoundButton.OnCheckedChangeListener darkTextListener = (buttonView, isChecked) -> {
        darkText = isChecked;
        textColor = darkText ? 0xff000000 : 0xffffffff;
        setWidgetStyle();
    };

    CompoundButton.OnCheckedChangeListener darkBackgroundListener = (buttonView, isChecked) -> {
        darkBackground = isChecked;
        setWidgetStyle();
    };

    SeekBar.OnSeekBarChangeListener seekBarBackgroundOpacityListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            seekBarOpacityValue = progress;
            setWidgetStyle();
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
        CalendarAppSharedPreferences prefs = CalendarAppSharedPreferences.getInstance();
        prefs.setInt(CalendarAppSharedPreferences.KEY_BACKGROUND_OPACITY, mAppWidgetId, seekBarOpacityValue);
        prefs.setBoolean(CalendarAppSharedPreferences.KEY_BACKGROUND_DARK, mAppWidgetId, darkBackground);
        prefs.setBoolean(CalendarAppSharedPreferences.KEY_TEXT_DARK, mAppWidgetId, darkText);

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

        init();
    }

    private void init() {
        CalendarAppSharedPreferences prefs = CalendarAppSharedPreferences.getInstance();
        seekBarOpacityValue = prefs.getInt(CalendarAppSharedPreferences.KEY_BACKGROUND_OPACITY, mAppWidgetId, seekBarOpacityValue);
        darkBackground = prefs.getBoolean(CalendarAppSharedPreferences.KEY_BACKGROUND_DARK, mAppWidgetId, darkBackground);
        darkText = prefs.getBoolean(CalendarAppSharedPreferences.KEY_TEXT_DARK, mAppWidgetId, darkText);
        textColor = darkText ? 0xff000000 : 0xffffffff;

        CalendarAppWidgetConfigureBinding binding = CalendarAppWidgetConfigureBinding.inflate(getLayoutInflater());

        WindowHelper.setInsets(binding.getRoot());

        setContentView(binding.getRoot());

        calendarAppWidgetView = getLayoutInflater().inflate(R.layout.calendar_app_widget, binding.previewContainer, false);

        appWidgetManager = AppWidgetManager.getInstance(this);
        Bundle options = appWidgetManager.getAppWidgetOptions(mAppWidgetId);

        widgetRowSpan = (int) options.get("semAppWidgetRowSpan");
        widgetColumnSpan = (int) options.get("semAppWidgetColumnSpan");

        calendarAppWidgetRootView = calendarAppWidgetView.findViewById(android.R.id.background);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) calendarAppWidgetRootView.getLayoutParams();
        layoutParams.width = Math.min(ResourceHelper.getDP((int) options.get("appWidgetMaxWidth")) - 40, ResourceHelper.getScreenWidth() - 80);
        layoutParams.height = ResourceHelper.getDP((int) options.get("appWidgetMaxHeight") - 20);

        setWidgetStyle();

        binding.previewContainer.addView(calendarAppWidgetView);
        binding.previewContainer.setBackgroundResource(R.drawable.calendar_widget_preview_rounded_corners);

        darkTextSwitch = binding.darkText;
        darkTextSwitch.setChecked(darkText);
        darkTextSwitch.setOnCheckedChangeListener(darkTextListener);

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
        darkTextSwitch.setOnCheckedChangeListener(null);
        darkBackgroundSwitch.setOnCheckedChangeListener(null);

        super.onDestroy();
    }

    public void setWidgetStyle() {
        setContainer();
        setDateContainer();
        setEvents();
    }

    private void setContainer() {
        boolean large = widgetColumnSpan > 2;
        int dp8 = ResourceHelper.getDP(8);
        int dp20 = ResourceHelper.getDP(20);
        int paddingLeftRight = large ? dp20 : dp8;

        int backgroundColor = getBackgroundColor(darkBackground, seekBarOpacityValue);
        PaintDrawable backgroundShape = new PaintDrawable(backgroundColor);
        backgroundShape.setCornerRadius(getResources().getDimension(android.R.dimen.system_app_widget_background_radius));

        calendarAppWidgetRootView.setPadding(paddingLeftRight, 0, paddingLeftRight, 0);
        calendarAppWidgetRootView.setBackground(backgroundShape);
    }

    private void setDateContainer() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String day = dayFormat.format(calendar.getTime());
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        String month = monthFormat.format(calendar.getTime());

        TextView lblDay = calendarAppWidgetView.findViewById(R.id.lblDay);
        lblDay.setText(day);
        lblDay.setTextColor(textColor);

        TextView lblDate = calendarAppWidgetView.findViewById(R.id.lblDate);
        lblDate.setText(String.valueOf(date));
        lblDate.setTextColor(textColor);

        TextView lblMonth = calendarAppWidgetView.findViewById(R.id.lblMonth);
        lblMonth.setText(month);
        lblMonth.setTextColor(textColor);

        LinearLayout llDateContainer = calendarAppWidgetView.findViewById(R.id.dateContainer);

        boolean large = widgetColumnSpan > 2;
        int dp8 = ResourceHelper.getDP(8);
        int dp12 = ResourceHelper.getDP(12);

        LinearLayout.LayoutParams dateContainerLayoutParams = (LinearLayout.LayoutParams) llDateContainer.getLayoutParams();
        dateContainerLayoutParams.setMarginEnd(large ? dp12 : dp8);

        if (widgetRowSpan > 1) {
            dateContainerLayoutParams.height = ResourceHelper.getDP(130);
        } else {
            dateContainerLayoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
        }
    }

    private EventListAdapter eventsAdapter;

    private void setEvents() {
        ListView eventsListView = calendarAppWidgetView.findViewById(R.id.eventsListView);
        TextView noEvents = calendarAppWidgetView.findViewById(R.id.noEvents);
        noEvents.setTextColor(textColor);

        eventsListView.setEmptyView(noEvents);

        if (eventsAdapter == null) {
            eventsAdapter = new EventListAdapter(this);
            eventsAdapter.setItems(textColor);

            eventsListView.setAdapter(eventsAdapter);
        } else {
            eventsAdapter.setItems(textColor);
        }
    }

    @ColorInt
    static int getBackgroundColor(boolean dark, int opacityPercentage) {
        if (dark) {
            return Color.argb((int) (2.55 * opacityPercentage), 0, 0, 0);
        } else {
            return Color.argb((int) (2.55 * opacityPercentage), 255, 255, 255);
        }
    }
}