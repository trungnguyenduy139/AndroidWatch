package com.example.trungnguyen.androidwatch;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.Calendar;

public class SetupAlarmActivity extends AppCompatActivity
        implements TimePicker.OnTimeChangedListener, View.OnClickListener {
    private static final String TAG = SetupAlarmActivity.class.getSimpleName();
    public static final String SETUP_ALARM = "setup_alarm";
    public static final int RESULT_CODE = 200;
    LinearLayout soundPanel;
    ImageView btSubmit, btCancel;
    EditText etContent;
    TimePicker timePicker;
    TextView chosenSound;
    AlarmTime[] alarm;
    boolean isTimeChange = false;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_alarm);
        addControls();
        addEvents();
        alarm = new AlarmTime[1];
        Log.d(TAG, String.valueOf(timePicker.getCurrentHour()));
        Log.d(TAG, String.valueOf(timePicker.getCurrentMinute()));
    }

    private void addEvents() {
        btSubmit.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        timePicker.setOnTimeChangedListener(this);
    }

    private String getTimeMode() {
        String timeMode = "";
        Calendar datetime = Calendar.getInstance();

        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            timeMode = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            timeMode = "PM";
        return timeMode;
    }

    private void addControls() {
        btSubmit = (ImageView) findViewById(R.id.btSubmit);
        btCancel = (ImageView) findViewById(R.id.btCancel);
        etContent = (EditText) findViewById(R.id.etContent);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        chosenSound = (TextView) findViewById(R.id.tvChosenSound);
        soundPanel = (LinearLayout) findViewById(R.id.soundPanel);
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int currentHour, int currentMinute) {
        isTimeChange = true;
        Log.d(TAG, "TIME CHANGE " + currentHour + " " + currentMinute);
        String timeMode = getTimeMode();
        String strTime = String.format("%d:%02d " + timeMode, currentHour, currentMinute);
        alarm[0] = new AlarmTime(strTime, true, etContent.getText().toString());
    }

    @Override
    public void onClick(View view) {
        if (view == btSubmit) {
            if (!isTimeChange) {
                String timeMode = getTimeMode();
                String strTime = String.format("%d:%02d " + timeMode, timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                alarm[0] = new AlarmTime(strTime, true, etContent.getText().toString());
            }
            Intent intent = new Intent();
            intent.putExtra(SETUP_ALARM, alarm[0]);
            setResult(RESULT_CODE, intent);
            finish();
        } else if (view == btCancel) {
            onBackPressed();
        }
    }
}
