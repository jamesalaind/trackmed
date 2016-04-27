package com.whiz.mobile.trackmed.navfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whiz.mobile.trackmed.R;

import java.util.ArrayList;

/**
 * Created by acerpc on 1/1/2016.
 */
public class FilingsFragment extends BaseFragment {
    private RecyclerView lvPersonalInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_tab_personal_info, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvPersonalInfo = (RecyclerView) view.findViewById(R.id.lvPersonalInfo);
        lvPersonalInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<PersonalInfo> infos = new ArrayList<>();

        for(int i = 0; i < 200; i++) {
            infos.add(new PersonalInfo("List item ".concat(String.valueOf(i))));
        }

        lvPersonalInfo.setAdapter(new PersonalInfoAdapter(infos));
    }

    private class PersonalInfo {
        public String name;
        public PersonalInfo(String name) {
            this.name = name;
        }
    }

    private class PersonalInfoHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public PersonalInfoHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void bind(PersonalInfo personalInfo) {
            tv.setText(personalInfo.name);
        }
    }

    private class PersonalInfoAdapter extends RecyclerView.Adapter<PersonalInfoHolder> {
        private ArrayList<PersonalInfo> infos;

        public PersonalInfoAdapter(ArrayList<PersonalInfo> infos) {
            this.infos = infos;
        }
        @Override
        public PersonalInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new PersonalInfoHolder(view);
        }

        @Override
        public void onBindViewHolder(PersonalInfoHolder holder, int position) {
            PersonalInfo h = infos.get(position);
            holder.bind(h);
        }

        @Override
        public int getItemCount() {
            return infos.size();
        }
    }
}
