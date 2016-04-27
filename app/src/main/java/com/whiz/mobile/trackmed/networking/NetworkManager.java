package com.whiz.mobile.trackmed.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by whizk on 01/20/2016.
 */
public final class NetworkManager {
    public int getActiveNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.isConnected())
                return networkInfo.getType();
        }
        return -1;
    }
}
