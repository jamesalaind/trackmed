package com.whiz.mobile.trackmed.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by WEstrada on 4/25/2016.
 */
public class LocationTracker implements GoogleApiClient.ConnectionCallbacks,
        LocationListener,
        GoogleApiClient.OnConnectionFailedListener {

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

    public interface OnLocationTrackChange {
        void onAddMarker(LatLng coord, Location location);
    }

    private static final long INTERVAL = 1000 * 60 * 1; //1 minute
    private static final long FASTEST_INTERVAL = 1000 * 60 * 1; //1 minute

    private Context context;
    private Location location;
    private GoogleApiClient googleApiClient;
    private LocationTrackerCallbacks callback;
    private OnLocationTrackChange locationTrackChange;

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
                sb.append(address.getAddressLine(i) + ", ");
            }
            return sb.toString().trim().substring(0, sb.toString().trim().length() - 1);
        } catch (IOException ex) {
            return ex.getMessage();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public LocationTracker(Context context, LocationTrackerCallbacks callback, OnLocationTrackChange locationTrackChange) {
        this.context = context;
        this.callback = callback;
        this.locationTrackChange = locationTrackChange;
        createLocationRequest();
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
        stopLocationUpdates();
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
        if (location != null) {
            callback.onLocationTrackerStatusChange(LocationTrackerStatus.Connected);
            if (trackLocation) {
                startLocationUpdates();
            }
        } else
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

    private LocationRequest locationRequest;
    private String mLastUpdateTime;
    private boolean trackLocation = true;

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdates() {
        PendingResult<Status> pendingResult =
                LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    public static String getProviderName(LocationManager locationManager) {
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        return locationManager.getBestProvider(criteria, true);
    }

    private void stopLocationUpdates() {
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient, this);
        }
    }

    public static boolean isGooglePlayServicesAvailable(Context context) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status,
                    context instanceof Activity ? ((Activity) context) : null, 0).show();

            return false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        addLocationMarker();
    }

    private void addLocationMarker() {
        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        long atTime = location.getTime();
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));
        if (trackLocation) {
            locationTrackChange.onAddMarker(currentLatLng, location);
        }
    }
}
