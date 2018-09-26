package com.luci.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;

import com.luci.AppPreferences;
import com.luci.R;
import com.luci.util.Constant;

public class SplashActivity extends BaseActivity {
    public static SplashActivity instance;
    public static final int REQUEST_PERMISSION = 0x111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        verifyPermissions();
    }

    private String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.INTERNET,
    };

    private void verifyPermissions() {
       int permission = ActivityCompat.checkSelfPermission(instance, Manifest.permission.WRITE_EXTERNAL_STORAGE);
       if (permission != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(
                   instance,
                   PERMISSIONS,
                   REQUEST_PERMISSION
           );

       } else {
           doNextStep();
       }
    }

    private void doNextStep() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isActiviated = AppPreferences.getBool(AppPreferences.KEY.ACTIVATED, false);
                if (isActiviated) {
                    finish();
                    Intent intent = new Intent(instance, MainActivity.class);
                    startActivity(intent);
                } else {
                    SharedPreferences sp = getSharedPreferences(Constant.USERINFO_ID, Context.MODE_PRIVATE );
                    boolean firstuse = sp.getBoolean( "firstuse", true );

                    Intent intent;

                    if (firstuse) {
//                        intent = new Intent(instance, RegisterActivity.class);
                        intent = new Intent(instance, LoginActivity.class);
                    } else {
                        intent = new Intent(instance, LoginActivity.class);
                    }

                    startActivity(intent);
                }
            }
        }, /*10000*/2000);
    }
}
