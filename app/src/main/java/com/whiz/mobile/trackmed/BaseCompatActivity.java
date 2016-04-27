/**
 * Certificate and Credentials
 * Client ID: 1032140503624-tj8v66lmgt6ri780c7noikcvlesg7lsg.apps.googleusercontent.com
 * MD5:  99:33:B2:8A:18:C5:BC:18:09:E8:C8:75:14:94:3E:DE
   SHA1: A1:B5:EF:37:79:D9:13:4F:DA:CB:CD:D5:74:08:A5:53:CA:E0:4B:44
 */
package com.whiz.mobile.trackmed;

import android.support.v7.app.AppCompatActivity;

import com.whiz.mobile.trackmed.log.AppLogger;

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
