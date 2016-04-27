package com.whiz.mobile.trackmed.data.model;

import android.location.Location;

import com.google.android.gms.location.Geofence;

/**
 * Created by WEstrada on 4/27/2016.
 */
public interface ITrackable {
    String getTrackingId();
    Location getLocation();
    float getRadiusInMeters();
    long getExpiration();
    int getTransitionType();
    void setTrackingId(String id);
    void setLocation(Location location);
    void setRadiusInMeters(float radiusInMeters);
    void setExpiration(long expiration);
    void setTransitionType(int transitionType);
    Geofence toGeofence();
}
