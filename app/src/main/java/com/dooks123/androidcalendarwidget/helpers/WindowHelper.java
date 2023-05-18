package com.dooks123.androidcalendarwidget.helpers;

import android.graphics.Color;
import android.graphics.Insets;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

public class WindowHelper {
    public static void setWindow(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        setWindowStatusNav(window, Color.BLACK, Color.BLACK);
        window.setNavigationBarDividerColor(Color.BLACK);
    }

    public static void setWindowStatusNav(
            Window window,
            @ColorInt int statusBarColor,
            @ColorInt int navbarColor
    ) {
        window.setStatusBarColor(statusBarColor);
        window.setNavigationBarColor(navbarColor);
    }

    public static void setInsets(final View rootView) {
        rootView.setOnApplyWindowInsetsListener((v, insets) -> {
            Insets in = insets.getInsets(WindowInsets.Type.systemBars());
            rootView.setPadding(in.left, in.top, in.right, in.bottom);
            return insets;
        });
    }
}