package com.whiz.mobile.trackmed.net;

import android.content.Context;
import android.net.ConnectivityManager;

public final class ConnectivityHelper {

	// ===========================================================
	// Private static methods
	// ===========================================================

	private static ConnectivityManager getConnectivityManager(final Context context) {
		if (context == null) throw new NullPointerException("context");
		return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
	}

	// ===========================================================
	// Public static methods
	// ===========================================================

    public static boolean isConnected(final Context context, boolean wifiOnly) {
        ConnectivityManager cm = getConnectivityManager(context);
        if (wifiOnly) {
            if (isWifi(context))
                return cm.getActiveNetworkInfo() == null ? false : cm.getActiveNetworkInfo().isConnected();
            return false;
        }
        return cm.getActiveNetworkInfo() == null ? false : cm.getActiveNetworkInfo().isConnected();
    }

	public static boolean isWifi(final Context context) {
		ConnectivityManager cm = getConnectivityManager(context);
		return cm.getActiveNetworkInfo() == null ? false : cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
	}

	public static String getType(final Context context) {
		ConnectivityManager cm = getConnectivityManager(context);
		return cm.getActiveNetworkInfo() == null ? null : cm.getActiveNetworkInfo().getTypeName();
	}

	public static String getStatus(final Context context, final boolean wifiOnly) {
		return isConnected(context, wifiOnly) ?
			"Connected" :"Not connected";
	}


	// ===========================================================
	// Private constructor
	// ===========================================================

	private ConnectivityHelper() {}
}
