package com.example.trungnguyen.androidwatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Trung Nguyen on 12/18/2016.
 */
public class AlarmClockAdapter extends ArrayAdapter<AlarmTime> {
    List<AlarmTime> list;

    public AlarmClockAdapter(Context context, int resource, List<AlarmTime> objects) {
        super(context, resource, objects);
        list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlarmViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_clock_item, parent, false);
            holder = new AlarmViewHolder();
            holder.tvAlarmTime = (TextView) convertView.findViewById(R.id.tvAlarmTime);
            holder.tvAlarmContent = (TextView) convertView.findViewById(R.id.tvAlarmConent);
            holder.swAlarm = (Switch) convertView.findViewById(R.id.swAlarm);
            convertView.setTag(holder);
        } else {
            holder = (AlarmViewHolder) convertView.getTag();
            holder.tvAlarmTime.setText(list.get(position).getTime());
            holder.tvAlarmContent.setText(list.get(position).getContent());
            holder.swAlarm.setChecked(list.get(position).isEnable);
        }
        return convertView;
    }

    public class AlarmViewHolder {
        TextView tvAlarmTime, tvAlarmContent;
        Switch swAlarm;
    }
}
