package com.example.trungnguyen.androidwatch;

/**
 * Created by Trung Nguyen on 12/24/2016.
 */
public class Ringtone {
    String name;
    int id;

    public Ringtone(String name, int id) {
        this.name = name;
        this.id = id;
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
