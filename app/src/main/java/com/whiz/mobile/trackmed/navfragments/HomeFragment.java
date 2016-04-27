package com.whiz.mobile.trackmed.navfragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whiz.mobile.trackmed.R;
import com.whiz.mobile.trackmed.location.LocationTracker;
import com.whiz.mobile.trackmed.utils.Delayer;

/**
 * Created by whizk on 07/01/2016.
 */
public class HomeFragment extends BaseFragment {
    private LocationTracker tracker;
    private TextView tvHello;

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvHello = ((TextView) view.findViewById(R.id.tvHello));
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
    public void onStart() {
        super.onStart();
        Delayer.delay(1, new Delayer.DelayCallback() {
            @Override
            public void afterDelay() {
                tracker = new LocationTracker(getActivity(), new LocationTracker.LocationTrackerCallbacks() {
                    @Override
                    public void onLocationTrackerStatusChange(LocationTracker.LocationTrackerStatus status) {
                        if (status == LocationTracker.LocationTrackerStatus.Connected) {
                            double latitude = tracker.getLongitude();
                            double longitude = tracker.getLatitude();
                            final String msg = status.toString() + ", coord: (" +
                                    String.valueOf(latitude) + ", " +
                                    String.valueOf(longitude) + ") -> " +
                                    tracker.getAddress();
                            tvHello.setText(msg);
                        }
                    }
                });
                new GetLocationAsync().execute();
            }
        });
    }

    class GetLocationAsync extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvHello.setText("Retrieving address...");
        }

        @Override
        protected String doInBackground(String... params) {
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
