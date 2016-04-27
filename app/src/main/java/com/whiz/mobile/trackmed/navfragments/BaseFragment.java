package com.whiz.mobile.trackmed.navfragments;

import android.support.v4.app.Fragment;

import com.whiz.mobile.trackmed.log.AppLogger;
import com.whiz.mobile.trackmed.utils.Delayer;

/**
 * Created by whizk on 07/01/2016.
 */
public abstract class BaseFragment extends Fragment {
    protected int secondsDelayBeforeReady = 1;

    public BaseFragment() {
        setShowFloatingActionButton(true);
    }

    public void logE(String msg) {
        AppLogger.logE(msg);
    }

    public boolean isShowFloatingActionButton() {
        return showFloatingActionButton;
    }

    public void setShowFloatingActionButton(boolean showFloatingActionButton) {
        this.showFloatingActionButton = showFloatingActionButton;
    }

    @Override
    public void onStart() {
        super.onStart();
        Delayer.delay(secondsDelayBeforeReady, new Delayer.DelayCallback() {
            @Override
            public void afterDelay() {
                onReady();
            }
        });
    }

    public void onReady()
    {

    }

    private boolean showFloatingActionButton;
}
