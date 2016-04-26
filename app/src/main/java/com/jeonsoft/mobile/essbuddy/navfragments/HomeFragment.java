package com.jeonsoft.mobile.essbuddy.navfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeonsoft.mobile.essbuddy.R;
import com.jeonsoft.mobile.essbuddy.location.LocationTracker;

/**
 * Created by whizk on 07/01/2016.
 */
public class HomeFragment extends BaseFragment {
    private LocationTracker tracker;

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tracker = new LocationTracker(getActivity(), new LocationTracker.LocationTrackerCallbacks() {
            @Override
            public void onLocationTrackerStatusChange(LocationTracker.LocationTrackerStatus status) {
                double latitude = tracker.getLongitude();
                double longitude = tracker.getLatitude();
                String msg = status.toString() + ", coord: (" +
                        String.valueOf(latitude) + ", " +
                        String.valueOf(longitude) + ") -> " +
                        tracker.getAddress();
                ((TextView) view.findViewById(R.id.tvHello)).setText(msg);
                Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
            }
        });
        tracker.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        tracker.disconnect();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
}
