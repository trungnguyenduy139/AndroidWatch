package com.example.trungnguyen.androidwatch.fragments;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trungnguyen.androidwatch.OnRvItemClick;
import com.example.trungnguyen.androidwatch.R;
import com.example.trungnguyen.androidwatch.SetupAlarmActivity;
import com.example.trungnguyen.androidwatch.adapters.RingtoneAdapter;
import com.example.trungnguyen.androidwatch.models.Ringtone;

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
    String ringtoneName;
    RingtoneAdapter adapterRingtone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Dialog call");
        View view = inflater.inflate(R.layout.fragment_rington, container);
        lvRingtone = (RecyclerView) view.findViewById(R.id.lvRingtone);
        ringtoneList = new ArrayList<Ringtone>();
        ringtoneList.add(new Ringtone("Alarm 1", R.raw.alarm1, false));
        ringtoneList.add(new Ringtone("Alarm 2", R.raw.alarm2, false));
        ringtoneList.add(new Ringtone("Alarm 3", R.raw.alarm3, false));
        ringtoneList.add(new Ringtone("Alarm 4", R.raw.alarm4, false));
        ringtoneList.add(new Ringtone("Alarm 5", R.raw.alarm_sound, false));
        Log.d(TAG, ringtoneList.size() + "");
        if (getArguments() != null)
            adapterRingtone = new RingtoneAdapter(ringtoneList, getArguments().getInt(SetupAlarmActivity.ID));
        else
            adapterRingtone = new RingtoneAdapter(ringtoneList, -1);
        adapterRingtone.SetOnRingtoneClick(RingtoneDialog.this);
        lvRingtone.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        lvRingtone.setAdapter(adapterRingtone);
        Log.d(TAG, "Dialog call 2");
        return view;
    }

    @Override
    public void onListAlarmSelected(int index, View view, int viewCode) {

    }

    @Override
    public void onRtClick(int index, boolean isChecked) {
        Log.d(TAG, "onRtClick");
        ringtoneID = ringtoneList.get(index).getId();
        ringtoneName = ringtoneList.get(index).getName();
        SetupAlarmActivity.getRingtoneID(ringtoneID, ringtoneName);
    }
}
