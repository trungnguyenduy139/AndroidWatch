package com.example.trungnguyen.androidwatch;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung Nguyen on 12/24/2016.
 */
public class RingtoneDialog extends DialogFragment
        implements OnRvItemClick {
    private static final String TAG = RingtoneDialog.class.getSimpleName();
    RecyclerView lvRingtone;
    List<Ringtone> ringtoneList;
    int ringtoneID;
    RingtoneAdapter adapterRingtone;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Dialog call" );
        View view = inflater.inflate(R.layout.fragment_rington, container);
        lvRingtone = (RecyclerView) view.findViewById(R.id.lvRingtone);
        ringtoneList = new ArrayList<Ringtone>();
        ringtoneList.add(new Ringtone("Alarm 1", R.raw.alarm1));
        ringtoneList.add(new Ringtone("Alarm 2", R.raw.alarm2));
        ringtoneList.add(new Ringtone("Alarm 3", R.raw.alarm3));
        ringtoneList.add(new Ringtone("Alarm 4", R.raw.alarm4));
        ringtoneList.add(new Ringtone("Alarm Sound", R.raw.alarm_sound));
        Log.d(TAG, ringtoneList.size()+"");
        adapterRingtone = new RingtoneAdapter(ringtoneList, getActivity());
        adapterRingtone.SetOnRingtoneClick(RingtoneDialog.this);
        lvRingtone.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        lvRingtone.setAdapter(adapterRingtone);
        Log.d(TAG, "Dialog call 2" );
        return view;
    }

    @Override
    public void onListAlarmSelected(int index, View view, int viewCode) {

    }

    @Override
    public void onRtClick(int index) {
        ringtoneID = ringtoneList.get(index).getId();
        SetupAlarmActivity.getRingtoneID(ringtoneID);
    }
}
