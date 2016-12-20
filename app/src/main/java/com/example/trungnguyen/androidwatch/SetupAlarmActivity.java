package com.example.trungnguyen.androidwatch;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
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
    public static final String EDIT_ALARM_POSITION = "edit_alarm_position";
    LinearLayout soundPanel;
    ImageView btSubmit, btCancel;
    EditText etContent;
    TimePicker timePicker;
    TextView chosenSound;
    AlarmTime[] alarm;
    boolean isTimeChanged = false;
    int requestCode;
    int position;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_alarm);
        addControls();
        addEvents();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        requestCode = getIntent().getIntExtra(AlarmClockFragment.SEND_REQUEST_CODE, 0);
        alarm = new AlarmTime[1];
        Log.d(TAG, String.valueOf(timePicker.getCurrentHour()));
        Log.d(TAG, String.valueOf(timePicker.getCurrentMinute()));
        Slide slide = new Slide(Gravity.RIGHT);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        View decor = getWindow().getDecorView();
        int actionBarId = R.id.action_bar_container;
        slide.excludeTarget(decor.findViewById(actionBarId), true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(slide);
        if (RESULT_CODE == AlarmClockFragment.REQUEST_CODE_2) {
            position = getIntent().getIntExtra("POSITION", -1);
            alarm[0] = getIntent().getParcelableExtra("ALARM");
            String[] currentTime = convertTimeTo24HourMode();
            timePicker.setCurrentHour(Integer.valueOf(currentTime[0]));
            timePicker.setCurrentMinute(Integer.valueOf(currentTime[1]));
            etContent.setText(alarm[0].getContent());
        }
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
        isTimeChanged = true;
        // currentHour và currentMinute trong onTimeChanged luôn trả về giá trị thời gian
        // ở mode 24h vì thế ta cần convert sang mode 12h với SimpleDateFormat
        Log.d(TAG, "TIME CHANGE " + currentHour + " " + currentMinute);
        String mode12HourTime = convertTimeTo12HourMode(currentHour, currentMinute);
        alarm[0] = new AlarmTime(mode12HourTime, true, etContent.getText().toString());
    }

    private String convertTimeTo12HourMode(int currentHour, int currentMinute) {
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

    private String[] convertTimeTo24HourMode() {
        SimpleDateFormat format12Hour = new SimpleDateFormat("h:mm a");
        String mode12HourTime = alarm[position].getTime();
        Date date = null;
        try {
            date = format12Hour.parse(mode12HourTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format24Hour = new SimpleDateFormat("hh:mm");
        String[] currentTime = new String[2];
        currentTime = format24Hour.format(date).split(":");
        //format24Hour.format(date) sẻ trả về kiểu String dạng ví dụ "10:50"
        //ta cần split để lấy đc hai chuỗi "10" và "50"
        return currentTime;
    }

    @Override
    public void onClick(View view) {
        if (view == btSubmit) {
            if (!isTimeChanged) {
                String mode12HourTime = convertTimeTo12HourMode(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                alarm[0] = new AlarmTime(mode12HourTime, true, etContent.getText().toString());
            }
            Intent intent = new Intent();
            intent.putExtra(SETUP_ALARM, alarm[0]);
            intent.putExtra(EDIT_ALARM_POSITION, position);
            setResult(RESULT_CODE, intent);
            onBackPressed();
        } else if (view == btCancel) {
            onBackPressed();
        }
    }
}
