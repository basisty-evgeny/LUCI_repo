package com.luci;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

public class LuciApp extends MultiDexApplication {
    public static Context mContext;
    public static String mPackageName;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();

        // window size
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        AppGlobals.SCREEN_WIDTH = display.getWidth();
        AppGlobals.SCREEN_HEIGHT = display.getHeight();

        // preference
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        AppPreferences.initialize(pref);

        // get package name
        mPackageName = mContext.getPackageName();
    }

    public static Context getContext() {
        return mContext;
    }

    public static String getAppPackageName() {
        if (TextUtils.isEmpty(mPackageName))
            return "";

        return mPackageName;
    }
}