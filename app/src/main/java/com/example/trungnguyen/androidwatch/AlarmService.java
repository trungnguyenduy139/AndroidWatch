package com.example.trungnguyen.androidwatch;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by Trung Nguyen on 12/23/2016.
 */
public class AlarmService extends Service {
    public static final String ALARM_FILTER = "alarm_filter";
    MediaPlayer alarmPlayer;
    IntentFilter filter;

    @Override
    public void onCreate() {
        super.onCreate();
        alarmPlayer = new MediaPlayer();
        filter = new IntentFilter(ALARM_FILTER);
        registerReceiver(receiver, filter);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarmPlayer = MediaPlayer.create(this, intent.getIntExtra(AlarmReceiver.RINGTONE1, R.raw.alarm2));
        alarmPlayer.setLooping(true);
        alarmPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(!intent.getBooleanExtra("STOP_ALARM", false)){
                alarmPlayer.stop();
                stopSelf();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
