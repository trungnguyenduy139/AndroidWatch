package com.example.trungnguyen.androidwatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.trungnguyen.androidwatch.fragments.AlarmClockFragment;

/**
 * Created by Trung Nguyen on 12/21/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = AlarmReceiver.class.getSimpleName();
    public static final String RINGTONE1 = "ringtone1" ;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);
        Log.d(TAG, intent.getIntExtra(AlarmClockFragment.RINGTONE, R.raw.alarm1)+" "+ R.raw.alarm1+" "+ R.raw.alarm_sound);
        intentService.putExtra(RINGTONE1, intent.getIntExtra(AlarmClockFragment.RINGTONE, R.raw.alarm1));
        context.startService(intentService);
        Log.d(TAG, "We are in the receiver");
    }
}
