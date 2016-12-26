package com.example.trungnguyen.androidwatch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.trungnguyen.androidwatch.OnSwitchAlarmChanged;
import com.example.trungnguyen.androidwatch.R;
import com.example.trungnguyen.androidwatch.models.AlarmTime;

import java.util.List;

/**
 * Created by Trung Nguyen on 12/18/2016.
 */
//public class AlarmClockAdapter extends ArrayAdapter<AlarmTime> {
//    List<AlarmTime> list;
//    AlarmViewHolder holder;
//    Context mContext;
//    int mPosition;
//
//    public interface onSwitchClicked {
//        void onSwitch();
//    }
//
//    public AlarmClockAdapter(Context context, int resource, List<AlarmTime> objects) {
//        super(context, resource, objects);
//        list = objects;
//        mContext = context;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        Log.d(TAG, position + "");
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_clock_item, parent, false);
//            holder = new AlarmViewHolder(convertView);
//            Log.d(TAG, "Lệnh trong if");
////            holder.tvAlarmTime = (TextView) convertView.findViewById(R.id.tvAlarmTime);
////            holder.tvAlarmContent = (TextView) convertView.findViewById(R.id.tvAlarmConent);
////            holder.swAlarm = (Switch) convertView.findViewById(R.id.swAlarm);
//            convertView.setTag(holder);
//        } else {
//            holder = (AlarmViewHolder) convertView.getTag();
//            Log.d(TAG, "Lệnh trong else");
//        }
//        holder.tvAlarmTime.setText(list.get(position).getTime());
//        holder.tvAlarmContent.setText(list.get(position).getContent());
//        holder.swAlarm.setChecked(list.get(position).isEnable());
//        return convertView;
//    }
//
//    public class AlarmViewHolder implements View.OnClickListener {
//        TextView tvAlarmTime, tvAlarmContent;
//        Switch swAlarm;
//
//        public AlarmViewHolder(View v) {
//            tvAlarmTime = (TextView) v.findViewById(R.id.tvAlarmTime);
//            tvAlarmContent = (TextView) v.findViewById(R.id.tvAlarmConent);
//            swAlarm = (Switch) v.findViewById(R.id.swAlarm);
//            swAlarm.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//        }
//    }
//}
public class AlarmClockAdapter extends RecyclerView.Adapter<AlarmClockAdapter.AlarmViewHolder> {
    public static final String TAG = AlarmClockAdapter.class.getSimpleName();
    private OnSwitchAlarmChanged mListener;
    private List<AlarmTime> mAlarms;
    private AlarmViewHolder mViewHolder;
    Context mContext;
    View mView;

    public AlarmClockAdapter(List<AlarmTime> alarms) {
        mAlarms = alarms;
    }

    @Override
    public AlarmClockAdapter.AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_clock_item, parent, false);
        mViewHolder = new AlarmViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(AlarmClockAdapter.AlarmViewHolder holder, int position) {
        Log.d(TAG, position + "");
        holder.bindAlarm(mAlarms.get(position));
    }

    public void SetOnSwitchAlarmChanged(OnSwitchAlarmChanged listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mAlarms.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView tvTime, tvContent;
        public Switch swAlarm;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tvAlarmTime);
            tvContent = (TextView) itemView.findViewById(R.id.tvAlarmConent);
            swAlarm = (Switch) itemView.findViewById(R.id.swAlarm);
            swAlarm.setOnClickListener(this);
        }

        public void bindAlarm(AlarmTime time) {
            Log.d(TAG, "bindAlarm");
            tvTime.setText(time.getTime());
            tvContent.setText(time.getContent());
            swAlarm.setChecked(time.isEnable());
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "IN ONCLICK: " + getAdapterPosition() + "");
            if (swAlarm.isChecked()) {
                mAlarms.get(getAdapterPosition()).setEnable(true);
                mListener.onSwitchChanged(getAdapterPosition(), view);
            } else {
                mAlarms.get(getAdapterPosition()).setEnable(false);
                mListener.onSwitchChanged(getAdapterPosition(), view);
            }
        }
    }
}
