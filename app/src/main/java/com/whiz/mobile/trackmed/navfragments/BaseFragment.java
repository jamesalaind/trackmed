package com.whiz.mobile.trackmed.navfragments;

import android.support.v4.app.Fragment;

import com.whiz.mobile.trackmed.log.AppLogger;

/**
 * Created by whizk on 07/01/2016.
 */
public abstract class BaseFragment extends Fragment {
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

    private boolean showFloatingActionButton;
}
