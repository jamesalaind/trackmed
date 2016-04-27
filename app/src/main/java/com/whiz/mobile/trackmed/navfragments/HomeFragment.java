package com.whiz.mobile.trackmed.navfragments;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.google.android.gms.maps.model.LatLng;
import com.whiz.mobile.trackmed.R;
import com.whiz.mobile.trackmed.location.LocationTracker;
import com.whiz.mobile.trackmed.utils.Delayer;

/**
 * Created by whizk on 07/01/2016.
 */
public class HomeFragment extends BaseFragment {
    private LocationTracker tracker;
    private TextView tvHello;
    private CircularProgressButton cpb;

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvHello = ((TextView) view.findViewById(R.id.tvHello));
        cpb = (CircularProgressButton) view.findViewById(R.id.btnWithText);
        cpb.setIndeterminateProgressMode(true); // turn on indeterminate progress
        cpb.setProgress(50);
    }

    @Override
    public void onStop() {
        super.onStop();
        new Thread(new Runnable() {
            @Override
            public void run() {
                tracker.disconnect();
            }
        }).start();
    }

    @Override
    public void onReady() {
        new GetLocationAsync().execute();
    }

    class GetLocationAsync extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvHello.setText("Retrieving address...");
        }

        @Override
        protected String doInBackground(String... params) {
            tracker = new LocationTracker(getActivity(), new LocationTracker.LocationTrackerCallbacks() {
                @Override
                public void onLocationTrackerStatusChange(final LocationTracker.LocationTrackerStatus status) {
                    if (status == LocationTracker.LocationTrackerStatus.Connected) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                double latitude = tracker.getLongitude();
                                double longitude = tracker.getLatitude();
                                final String msg = status.toString() + ", coord: (" +
                                        String.valueOf(latitude) + ", " +
                                        String.valueOf(longitude) + ") -> " +
                                        tracker.getAddress();
                                if (tracker != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvHello.setText(msg);
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
                }
            }, new LocationTracker.OnLocationTrackChange() {
                @Override
                public void onAddMarker(LatLng coord, Location location) {
                    Toast.makeText(getActivity(), "Tracked new location: " +
                                    String.valueOf(coord.latitude) + "," + String.valueOf(coord.longitude),
                            Toast.LENGTH_SHORT).show();
                }
            });
            tracker.connect();
            return "";
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
}
