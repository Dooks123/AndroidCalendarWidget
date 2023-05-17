package com.dooks123.androidcalendarwidget.helpers;

import android.util.TypedValue;

import androidx.annotation.DimenRes;

import com.dooks123.androidcalendarwidget.MainApplication;

public class ResourceHelper {
    public static int getDimension(@DimenRes int resourceID) {
        return (int) MainApplication.getContext().getResources().getDimension(resourceID);
    }

    public static int getDP(float value) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, MainApplication.getContext().getResources().getDisplayMetrics()));
    }

    public static int getScreenWidth() {
        return MainApplication.getContext().getResources().getDisplayMetrics().widthPixels;
    }
}
