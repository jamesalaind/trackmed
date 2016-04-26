package com.jeonsoft.mobile.essbuddy.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

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
    private LocationTrackerCallbacks callback;

    public double getLatitude() {
        return location.getLatitude();
    }

    public double getLongitude() {
        return location.getLongitude();
    }

    public Location getLocation() {
        return location;
    }

    public String getAddress() {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(getLatitude(), getLongitude(), 1);
            Address address = addresses.get(0);
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                sb.append(
                        address.getAddressLine(i) + ", ");
            }
            return sb.toString().trim().substring(0, sb.toString().trim().length() - 1);
        } catch (IOException ex) {
            return ex.getMessage();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public LocationTracker(Context context, LocationTrackerCallbacks callback) {
        this.context = context;
        this.callback = callback;
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
        callback.onLocationTrackerStatusChange(LocationTrackerStatus.Disconnected);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            callback.onLocationTrackerStatusChange(LocationTrackerStatus.PermissionDenied);
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null)
            callback.onLocationTrackerStatusChange(LocationTrackerStatus.Connected);
        else
            callback.onLocationTrackerStatusChange(LocationTrackerStatus.Failed);
    }

    @Override
    public void onConnectionSuspended(int i) {
        callback.onLocationTrackerStatusChange(LocationTrackerStatus.Suspended);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        callback.onLocationTrackerStatusChange(LocationTrackerStatus.Failed);
    }
}
