package com.example.trungnguyen.androidwatch.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Trung Nguyen on 12/18/2016.
 */
public class AlarmTime implements Parcelable {
    private String mTime;
    private String mContent;
    private boolean isEnable;
    private Ringtone ringtone;
    public AlarmTime() {

    }

    public Ringtone getRingtone() {
        return ringtone;
    }

    public void setRingtone(Ringtone ringtone) {
        this.ringtone = ringtone;
    }

    public AlarmTime(String time, boolean isEnable, String content, Ringtone ring) {
        this.mTime = time;
        this.isEnable = isEnable;
        this.mContent = content;
        this.ringtone = ring;
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

    @Override
    public int describeContents() {
        return 0; // ignore
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTime);
        dest.writeString(mContent);
        dest.writeInt(isEnable ? 1 : 0);
        dest.writeSerializable(ringtone); // Must implements Serializable for this line will work fine
    }

    private AlarmTime(Parcel in) {
        mTime = in.readString();
        mContent = in.readString();
        isEnable = in.readInt() != 0;
        ringtone = (Ringtone) in.readSerializable();
    }

    public static final Creator<AlarmTime> CREATOR = new Creator<AlarmTime>() {
        @Override
        public AlarmTime createFromParcel(Parcel source) {
            return new AlarmTime(source);
        }

        @Override
        public AlarmTime[] newArray(int size) {
            return new AlarmTime[size];
        }
    };
}
