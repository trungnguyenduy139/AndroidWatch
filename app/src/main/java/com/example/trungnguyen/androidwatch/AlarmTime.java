package com.example.trungnguyen.androidwatch;

/**
 * Created by Trung Nguyen on 12/18/2016.
 */
public class AlarmTime {
    String mTime;
    String mContent;
    boolean isEnable;

    public AlarmTime() {

    }

    public AlarmTime(String time, boolean isEnable, String content) {
        this.mTime = time;
        this.isEnable = isEnable;
        this.mContent = content;
    }

    public String getTime() {
        return mTime;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public String getContent() {
        return mContent;
    }

    public void setTime(String time) {
        this.mTime = time;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void setContent(String content) {
        this.mContent = content;
    }
}
