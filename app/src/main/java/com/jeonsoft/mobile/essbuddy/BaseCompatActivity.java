package com.jeonsoft.mobile.essbuddy;

import android.support.v7.app.AppCompatActivity;

import com.jeonsoft.mobile.essbuddy.log.AppLogger;

/**
 * Created by whizk on 07/01/2016.
 */
public abstract class BaseCompatActivity extends AppCompatActivity {
    protected void logE(String msg) {
        AppLogger.logE(msg);
    }

    protected void logD(String msg) {
        AppLogger.logD(msg);
    }

    protected void logI(String msg) {
        AppLogger.logI(msg);
    }
}
