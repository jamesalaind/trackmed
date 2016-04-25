package com.jeonsoft.mobile.essbuddy.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by WEstrada on 4/25/2016.
 */
public class LocationTracker implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public enum LocationTrackerStatus {
        Connected,
        Disconnected,
        Failed,
        Suspended,
        PermissionDenied
    }
    public interface LocationTrackerCallbacks {
        void onLocationTrackerStatusChange(LocationTrackerStatus status);
    }

    private Context context;
    private Location location;
    private GoogleApiClient googleApiClient;

    public LocationTracker(Context context) {
        this.context = context;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void connect() {
        googleApiClient.connect();
    }

    public void disconnect() {
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
