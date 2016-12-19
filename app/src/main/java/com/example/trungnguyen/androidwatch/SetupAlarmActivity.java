package com.example.trungnguyen.androidwatch;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    private String getTimeMode(int hour) {
        //Long version
//        Calendar calendar = Calendar.getInstance();
//        if (calendar.get(Calendar.AM_PM) == Calendar.AM)
//            return "AM";
//        else return "PM";

        // Sort version
        return Calendar.getInstance().get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
//        if (hour > 12)
//            return "PM";
//        else return "AM";
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
        // currentHour và currentMinute trong onTimeChanged lun trả về giá trị thời gian
        // ở mode 24h vì thế ta cần convert sang mode 12h với SimpleDateFormat
        Log.d(TAG, "TIME CHANGE " + currentHour + " " + currentMinute);
        String mode12HourTime = formatTime(currentHour, currentMinute);
        alarm[0] = new AlarmTime(mode12HourTime, true, etContent.getText().toString());
    }

    private String formatTime(int currentHour, int currentMinute) {
        SimpleDateFormat format24Hour = new SimpleDateFormat("hh:mm");
        String mode24HourTime = String.valueOf(currentHour) + ":" + String.valueOf(currentMinute);
        Date date = null;
        try {
            date = format24Hour.parse(mode24HourTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format12Hour = new SimpleDateFormat("h:mm a");
        return format12Hour.format(date);
    }

    @Override
    public void onClick(View view) {
        if (view == btSubmit) {
            if (!isTimeChange) {
                String mode12HourTime = formatTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                alarm[0] = new AlarmTime(mode12HourTime, true, etContent.getText().toString());
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
