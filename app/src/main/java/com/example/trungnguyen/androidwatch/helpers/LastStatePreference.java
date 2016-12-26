package com.example.trungnguyen.androidwatch.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.trungnguyen.androidwatch.models.AlarmTime;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung Nguyen on 12/21/2016.
 */
public class LastStatePreference {

    private static final String TAG = LastStatePreference.class.getSimpleName();

    public static SharedPreferences getLastSongPreference(Context context) {
        return context.getSharedPreferences("Android Clock", Activity.MODE_PRIVATE);
    }

    public static void saveLastAlarmState(Context context, List<AlarmTime> times) {
        Log.d(TAG, "CALL SAVE PREFERENCE");
        SharedPreferences.Editor editor = getLastSongPreference(context).edit();
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        for (AlarmTime time : times) {
            sb.append(gson.toJson(time));
            sb.append("`");
        }
        editor.putString("JSON_ALARM_TIME", sb.toString());
        editor.commit();
    }

    public static List<AlarmTime> getLastAlarmState(Context context) {
        SharedPreferences mSharedPreferences = getLastSongPreference(context);
        String jsonString = mSharedPreferences.getString("JSON_ALARM_TIME", "");
        Gson gson = new Gson();
        List<AlarmTime> alarmTimes = new ArrayList<>();
        if (!jsonString.equals("")) {
            String alarmStringArray[] = jsonString.split("`"); // Không thể dùng "," hoặc ":"
            //Vì trong chuổi JsonString có xuất hiện 2 kí tự đó
            for (int i = 0; i < alarmStringArray.length; i++)
                alarmTimes.add(gson.fromJson(alarmStringArray[i], AlarmTime.class));
        }
        if (alarmTimes.size() == 0)
            return new ArrayList<AlarmTime>();
        else return alarmTimes;
    }
}
