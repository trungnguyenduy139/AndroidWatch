package com.example.trungnguyen.androidwatch;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung Nguyen on 12/18/2016.
 */
public class AlarmClockFragment extends Fragment {
    private static final String ALARM_TIME_LAST_STATE = "alarm_time_last_state";
    private static final String TAG = AlarmClockFragment.class.getSimpleName();
    private static final int REQUEST_CODE_1 = 99;
    private static final int REQUEST_CODE_2 = 100;
    List<AlarmTime> alarmTimes;
    AlarmClockAdapter adapter;
    RecyclerView rvAlarm;
    Activity mActivity;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.alarm_clock_fragment, container, false);
        mActivity = getActivity();
        alarmTimes = new ArrayList<>();
        AlarmTime[] lastState;
        if (savedInstanceState != null && savedInstanceState.getParcelableArray(ALARM_TIME_LAST_STATE) != null) {
            Log.d(TAG, "saveInstanceState không null");
            lastState = (AlarmTime[]) savedInstanceState.getParcelableArray(ALARM_TIME_LAST_STATE);
            for (AlarmTime alarm : lastState) {
                alarmTimes.add(alarm);
            }
        } else {
            Log.d(TAG, "saveInstanceState null");
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
            alarmTimes.add(new AlarmTime("9:40 PM", false, "Gọi Tao Dậy Đi Học"));
        }
        rvAlarm = (RecyclerView) mView.findViewById(R.id.rvAlarm);
        adapter = new AlarmClockAdapter(alarmTimes);
        rvAlarm.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        rvAlarm.setLayoutManager(layoutManager);
        rvAlarm.setHasFixedSize(true);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.alarm_clock_menu, menu);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_alarm_clock:
                Intent iAddAlarm = new Intent(mActivity, SetupAlarmActivity.class);
                // TODO: 12/19/2016 Ko thể gọi mActivity.startActivityForResult trong fragment, vì sẻ ko gọi đc hàm onActivityResult
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                        (getActivity());
                startActivityForResult(iAddAlarm, REQUEST_CODE_1, options.toBundle());
                break;
            case R.id.edit_alarm_clock:
                AlarmClockEditAdapter adapterEdit = new AlarmClockEditAdapter(alarmTimes, new AlarmClockEditAdapter.OnRvItemClick() {
                    @Override
                    public void onListAlarmSelected(int index, View view, int viewCode) {
                        if (viewCode == 0) {
                            alarmTimes.remove(index);
                        }
                    }
                });
                rvAlarm.setAdapter(adapterEdit);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
                rvAlarm.setLayoutManager(layoutManager);
                rvAlarm.setHasFixedSize(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        AlarmTime[] alarmTimeLastState = new AlarmTime[alarmTimes.size()];
        Log.d(TAG, alarmTimes.size() + "");
        if (alarmTimes.size() > 0) {
            for (int i = 0; i < alarmTimes.size(); i++) {
                alarmTimeLastState[i] = new AlarmTime(
                        alarmTimes.get(i).getTime(),
                        alarmTimes.get(i).isEnable(),
                        alarmTimes.get(i).getContent()
                );
            }
            outState.putParcelableArray(ALARM_TIME_LAST_STATE, alarmTimeLastState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Request code: " + requestCode + "");
        if (requestCode == REQUEST_CODE_1 && resultCode == SetupAlarmActivity.RESULT_CODE) {
            Log.d(TAG, "CALL result");
            Log.d(TAG, "CALL IN REQUEST 1");
            if (data.getParcelableExtra(SetupAlarmActivity.SETUP_ALARM) != null) {
                alarmTimes.add((AlarmTime) data.getParcelableExtra(SetupAlarmActivity.SETUP_ALARM));
                Log.d(TAG, alarmTimes.size() + "");
                adapter.notifyDataSetChanged();
            }
        }
    }
}
