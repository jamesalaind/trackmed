package com.jeonsoft.mobile.essbuddy.navfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeonsoft.mobile.essbuddy.R;

/**
 * Created by whizk on 03/02/2016.
 */
public class WorkSchedulesFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_work_schedules, container, false);
    }
}
