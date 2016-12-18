package com.example.trungnguyen.androidwatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Trung Nguyen on 12/18/2016.
 */
public class AlarmClockFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_clock_fragment, container, false);
        return mView;
    }
}
