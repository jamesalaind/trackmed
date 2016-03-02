package com.jeonsoft.mobile.essbuddy.navfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeonsoft.mobile.essbuddy.R;

/**
 * Created by whizk on 03/02/2016.
 */
public class MissingPageFragment extends BaseFragment {
    public MissingPageFragment() {
        setShowFloatingActionButton(false);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_404, container, false);
    }
}
