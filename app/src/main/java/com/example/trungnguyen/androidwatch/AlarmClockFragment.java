package com.example.trungnguyen.androidwatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung Nguyen on 12/18/2016.
 */
public class AlarmClockFragment extends Fragment {
    List<AlarmTime> alarmTimes;
    AlarmClockAdapter adapter;
    ListView lvAlarm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_clock_fragment, container, false);
        alarmTimes = new ArrayList<>();
        lvAlarm = (ListView) mView.findViewById(R.id.lvAlarm);
        adapter = new AlarmClockAdapter(getActivity(), R.layout.alarm_clock_item, alarmTimes);
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.alarm_clock_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting_alarm_clock:
                Toast.makeText(getActivity(), "Alarm clock fragment setting menu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_new_alarm_clock:
                Toast.makeText(getActivity(), "Alarm Clock fragment add new menu", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
