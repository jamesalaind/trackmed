package com.whiz.mobile.trackmed.data.model;

import android.location.Location;

import com.google.android.gms.location.Geofence;

/**
 * Created by WEstrada on 4/27/2016.
 */
public class Doctor extends Person implements ITrackable {
    private String trackingId;
    private Location location;
    private float radiusInMeters;
    private int transtionType;
    private long expiration;

    public Doctor(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String getTrackingId() {
        return trackingId;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public float getRadiusInMeters() {
        return radiusInMeters;
    }

    @Override
    public void setTrackingId(String id) {
        this.trackingId = id;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void setRadiusInMeters(float radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }

    @Override
    public long getExpiration() {
        return expiration;
    }

    @Override
    public int getTransitionType() {
        return transtionType;
    }

    @Override
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    @Override
    public void setTransitionType(int transitionType) {
        this.transtionType = transitionType;
    }

    @Override
    public Geofence toGeofence() {
        return new Geofence.Builder()
                .setCircularRegion(getLocation().getLatitude(), getLocation().getLongitude(), getRadiusInMeters())
                .setExpirationDuration(getExpiration())
                .setRequestId(getTrackingId())
                .setTransitionTypes(getTransitionType())
                .build();
    }
}
