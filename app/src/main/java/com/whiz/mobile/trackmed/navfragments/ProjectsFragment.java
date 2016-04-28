package com.whiz.mobile.trackmed.navfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.whiz.mobile.trackmed.R;
import com.whiz.mobile.trackmed.data.SqliteHelper;

/**
 * Created by james on 3/2/2016.
 */
public class ProjectsFragment extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

    private Button button;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = (Button) view.findViewById(R.id.button);
    }

    @Override
    public void onReady() {
        super.onReady();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqliteHelper helper = new SqliteHelper(getContext());
            }
        });
    }
}
