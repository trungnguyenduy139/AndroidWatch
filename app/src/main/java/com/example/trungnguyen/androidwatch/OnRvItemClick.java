package com.example.trungnguyen.androidwatch;

import android.view.View;

/**
 * Created by Trung Nguyen on 12/21/2016.
 */
public interface OnRvItemClick {
    void onListAlarmSelected(int index, View view, int viewCode);
    void onRtClick(int index, boolean isChecked);

//    void onSwitchAlarmChanged(int index, View view, boolean isEnable);
}
