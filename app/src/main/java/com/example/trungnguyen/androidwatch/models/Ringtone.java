package com.example.trungnguyen.androidwatch.models;

import java.io.Serializable;

/**
 * Created by Trung Nguyen on 12/24/2016.
 */
public class Ringtone implements Serializable {
    String name;
    int id;
    boolean isChecked;

    public Ringtone(String name, int id , boolean checked) {
        this.name = name;
        this.id = id;
        this.isChecked = checked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
