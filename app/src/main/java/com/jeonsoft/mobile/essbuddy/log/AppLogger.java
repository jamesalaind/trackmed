package com.jeonsoft.mobile.essbuddy.log;

import android.util.Log;

/**
 * Created by whizk on 07/01/2016.
 */
public final class AppLogger {
    public static final String LOG_NAME_TAG = "essbuddy";

    public static void logE(String msg) {
        Log.e(LOG_NAME_TAG, msg);
    }

    public static void logD(String msg) {
        Log.d(LOG_NAME_TAG, msg);
    }

    public static void logI(String msg) {
        Log.i(LOG_NAME_TAG, msg);
    }
}
