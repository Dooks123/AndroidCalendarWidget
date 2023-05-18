package com.dooks123.androidcalendarwidget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dooks123.androidcalendarwidget.helpers.WindowHelper;

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

            if (!allGranted) {
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
}