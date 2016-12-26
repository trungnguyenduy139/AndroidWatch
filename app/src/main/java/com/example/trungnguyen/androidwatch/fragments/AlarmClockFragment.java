package com.example.trungnguyen.androidwatch.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.trungnguyen.androidwatch.adapters.AlarmClockAdapter;
import com.example.trungnguyen.androidwatch.adapters.AlarmClockEditAdapter;
import com.example.trungnguyen.androidwatch.AlarmReceiver;
import com.example.trungnguyen.androidwatch.AlarmService;
import com.example.trungnguyen.androidwatch.OnRvItemClick;
import com.example.trungnguyen.androidwatch.OnSwitchAlarmChanged;
import com.example.trungnguyen.androidwatch.R;
import com.example.trungnguyen.androidwatch.SetupAlarmActivity;
import com.example.trungnguyen.androidwatch.helpers.ConvertTimeMode;
import com.example.trungnguyen.androidwatch.helpers.LastStatePreference;
import com.example.trungnguyen.androidwatch.models.AlarmTime;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Trung Nguyen on 12/18/2016.
 */
public class AlarmClockFragment extends Fragment
        implements OnRvItemClick, OnSwitchAlarmChanged {
    private static final String TAG = AlarmClockFragment.class.getSimpleName();
    public static final int REQUEST_CODE_1 = 99;
    public static final int REQUEST_CODE_2 = 100;
    public static final String SEND_REQUEST_CODE = "send_request_code";
    public static final String RINGTONE = "ringtone";
    List<AlarmTime> alarmTimes;
    AlarmClockAdapter adapter;
    RecyclerView rvAlarm;
    Activity mActivity;
    AlarmManager[] alarmManager;
    PendingIntent pendingIntent;
    private boolean isExitModeOpen = false;
    Menu mMenu;
    Calendar calendar;
    AlarmClockEditAdapter adapterEdit;
    int mEditPosition;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CALL ON ON CREATE");
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "CALL ON ON CREATE VIEW");
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.alarm_clock_fragment, container, false);
        addVariables();

//        AlarmTime[] lastState;
//        if (savedInstanceState != null && savedInstanceState.getParcelableArray(ALARM_TIME_LAST_STATE) != null) {
//            Log.d(TAG, "saveInstanceState không null");
//            lastState = (AlarmTime[]) savedInstanceState.getParcelableArray(ALARM_TIME_LAST_STATE);
//            for (AlarmTime alarm : lastState) {
//                alarmTimes.add(alarm);
//            }
//        } else {
//            Log.d(TAG, "saveInstanceState null");
//        }
        rvAlarm = (RecyclerView) mView.findViewById(R.id.rvAlarm);
        adapter = new AlarmClockAdapter(alarmTimes);
        adapter.SetOnSwitchAlarmChanged(AlarmClockFragment.this);
        rvAlarm.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        rvAlarm.setLayoutManager(layoutManager);
        rvAlarm.setHasFixedSize(true);
        return mView;
    }

    private void addVariables() {
        mActivity = getActivity();
        calendar = Calendar.getInstance();
        alarmTimes = LastStatePreference.getLastAlarmState(getActivity());
        alarmManager = new AlarmManager[24];
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.alarm_clock_menu, menu);
        mMenu = menu;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_alarm_clock:
                Intent iAddAlarm = new Intent(mActivity, SetupAlarmActivity.class);
                iAddAlarm.putExtra(SEND_REQUEST_CODE, REQUEST_CODE_1);
                // TODO: 12/19/2016 Ko thể gọi mActivity.startActivityForResult trong fragment, vì sẻ ko gọi đc hàm onActivityResult
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                        (getActivity());
                startActivityForResult(iAddAlarm, REQUEST_CODE_1, options.toBundle());
                break;
            case R.id.edit_alarm_clock:
                if (!isExitModeOpen) {
                    isExitModeOpen = true;
                    mMenu.findItem(R.id.add_new_alarm_clock).setVisible(false);
                    adapterEdit = new AlarmClockEditAdapter(alarmTimes);
                    adapterEdit.SetOnRvClickListener(AlarmClockFragment.this);
                    item.setTitle("Exit Edit Mode");
                    rvAlarm.setAdapter(adapterEdit);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
                    rvAlarm.setLayoutManager(layoutManager);
                    rvAlarm.setHasFixedSize(true);
                } else {
                    isExitModeOpen = false;
                    mMenu.findItem(R.id.add_new_alarm_clock).setVisible(true);
                    item.setTitle("Edit Mode");
                    rvAlarm.setAdapter(adapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
                    rvAlarm.setLayoutManager(layoutManager);
                    rvAlarm.setHasFixedSize(true);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        AlarmTime[] alarmTimeLastState = new AlarmTime[alarmTimes.size()];
//        Log.d(TAG, alarmTimes.size() + "");
//        if (alarmTimes.size() > 0) {
//            for (int i = 0; i < alarmTimes.size(); i++) {
//                alarmTimeLastState[i] = new AlarmTime(
//                        alarmTimes.get(i).getTime(),
//                        alarmTimes.get(i).isEnable(),
//                        alarmTimes.get(i).getContent()
//                );
//            }
//            outState.putParcelableArray(ALARM_TIME_LAST_STATE, alarmTimeLastState);
//        }
//        super.onSaveInstanceState(outState);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Request code: " + requestCode + "");
        Log.d(TAG, "CALL ON ActivityResult");
        if (resultCode == SetupAlarmActivity.RESULT_CODE) {
            if (requestCode == REQUEST_CODE_1) {
                Log.d(TAG, "CALL result");
                Log.d(TAG, "CALL IN REQUEST 1");
                if (data.getParcelableExtra(SetupAlarmActivity.SETUP_ALARM) != null) {
                    alarmTimes.add((AlarmTime) data.getParcelableExtra(SetupAlarmActivity.SETUP_ALARM));
                    Log.d(TAG, alarmTimes.size() + "");
                    adapter.notifyDataSetChanged();

                }
            } else if (requestCode == REQUEST_CODE_2) {
                if (data.getParcelableExtra(SetupAlarmActivity.SETUP_ALARM) != null) {
                    alarmTimes.set(mEditPosition,
                            (AlarmTime) data.getParcelableExtra(SetupAlarmActivity.SETUP_ALARM));
                    Log.d(TAG, "POSITION UPDATED " + data.getIntExtra(SetupAlarmActivity.EDIT_ALARM_POSITION, -1) + "");
                    adapterEdit.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "CALL ON STOP");
        LastStatePreference.saveLastAlarmState(getActivity(), alarmTimes);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onListAlarmSelected(int index, View view, int viewCode) {
        if (viewCode == 0) {
            alarmTimes.remove(index);
            adapterEdit.notifyDataSetChanged();
        } else if (viewCode == 1) {
            Intent iAddAlarm = new Intent(mActivity, SetupAlarmActivity.class);
            iAddAlarm.putExtra(SEND_REQUEST_CODE, REQUEST_CODE_2);
            mEditPosition = index;
            iAddAlarm.putExtra("ALARM", alarmTimes.get(index));
            // TODO: 12/19/2016 Ko thể gọi mActivity.startActivityForResult trong fragment, vì sẻ ko gọi đc hàm onActivityResult
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                    (getActivity());
            startActivityForResult(iAddAlarm, REQUEST_CODE_2, options.toBundle());
        }
    }

    @Override
    public void onRtClick(int index, boolean isChecked) {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSwitchChanged(int index, View view) {
        if (alarmTimes.get(index).isEnable()) {
            alarmManager[index] = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intentAlarmReceiver = new Intent(getActivity(), AlarmReceiver.class);
            intentAlarmReceiver.putExtra(RINGTONE, alarmTimes.get(index).getRingtone().getId());
            Log.d(TAG, alarmTimes.get(index).getRingtone().getId() + "");
            String[] currentTime = ConvertTimeMode.convertTo24HourMode(alarmTimes.get(index).getTime());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(currentTime[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(currentTime[1]));
            //format24Hour.format(date) sẻ trả về kiểu String dạng ví dụ "10:50"
            //ta cần split với dấu ":" để lấy đc hai chuỗi "10" và "50"
            //Create pending intent that delays the intent until the specified calendar time
//            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(currentTime[0]));
//            calendar.set(Calendar.MINUTE, Integer.parseInt(currentTime[1]));
            pendingIntent = PendingIntent.getBroadcast(
                    getActivity(), index, intentAlarmReceiver, 0);
            //Set the alarm manager
            alarmManager[index].set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (!alarmTimes.get(index).isEnable() && alarmManager[index] != null) {
            alarmManager[index].cancel(pendingIntent);
            Intent stopAlarmIntent = new Intent(AlarmService.ALARM_FILTER);
            stopAlarmIntent.putExtra("STOP_ALARM", alarmTimes.get(index).isEnable());
            getActivity().sendBroadcast(stopAlarmIntent);
        }
    }
}
