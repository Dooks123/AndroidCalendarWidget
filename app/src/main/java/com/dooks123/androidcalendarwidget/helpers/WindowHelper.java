package com.dooks123.androidcalendarwidget.helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Insets;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.ColorInt;

import com.dooks123.androidcalendarwidget.MainApplication;

public class WindowHelper {
    public static void setWindow(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        setWindowStatusNav(window, 0xFF018786, 0xFF018786);
        window.setNavigationBarDividerColor(0xFF018786);
    }

    public static void setWindowStatusNav(
            Window window,
            @ColorInt int statusBarColor,
            @ColorInt int navbarColor
    ) {
        window.setStatusBarColor(statusBarColor);
        window.setNavigationBarColor(navbarColor);
    }

    public static void setWindowNoStatus(Window window) {
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setNavigationBarColor(Color.BLACK);
    }

    public static void setInsets(final View rootView) {
        rootView.setOnApplyWindowInsetsListener((v, insets) -> {
            Insets in = insets.getInsets(WindowInsets.Type.systemBars());
            rootView.setPadding(in.left, in.top, in.right, in.bottom);
            return insets;
        });
    }

    public static void keyboardHide(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        //Find the currently focused view, so we can grab the correct window token from it.
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void keyboardHide(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void keyboardShow(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }

        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void keyboardShow(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static int getScreenWidth() {
        return MainApplication.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return MainApplication.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenSmallestDimension() {
        int width = getScreenWidth();
        int height = getScreenHeight();
        return Math.min(width, height);
    }
}