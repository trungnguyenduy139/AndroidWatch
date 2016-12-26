package com.example.trungnguyen.androidwatch.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.trungnguyen.androidwatch.OnRvItemClick;
import com.example.trungnguyen.androidwatch.R;
import com.example.trungnguyen.androidwatch.models.Ringtone;

import java.util.List;

/**
 * Created by Trung Nguyen on 12/24/2016.
 */

public class RingtoneAdapter extends RecyclerView.Adapter<RingtoneAdapter.RingtoneViewHolder> {
    private static final String TAG = RingtoneAdapter.class.getSimpleName();
    OnRvItemClick mListener;
    private int mAlarmID;
    private List<Ringtone> mRingtones;
    private RingtoneViewHolder mViewHolder;
    private int selectedPosition = -1;

    public RingtoneAdapter(List<Ringtone> ringtones, int alarmID) {
        Log.d(TAG, "CALL RINGTONE ADAPTER");
        mRingtones = ringtones;
        mAlarmID = alarmID;
        Log.d(TAG, mRingtones.size() + "");
    }

    @Override
    public RingtoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "CALL onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rington_item, parent, false);
        mViewHolder = new RingtoneViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RingtoneViewHolder holder, int position) {
        Log.d(TAG, "CALL onBindViewHolder" + position);
        holder.bindRingtone(mRingtones.get(position));
    }

    @Override
    public int getItemCount() {
        // Must return some number greater than 0;
        return mRingtones.size();
    }


    public void SetOnRingtoneClick(OnRvItemClick listener) {
        mListener = listener;
    }

    public class RingtoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CheckBox checkBox;
        private TextView tv;

        @TargetApi(Build.VERSION_CODES.M)
        public RingtoneViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "Constructor view holder");
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            tv = (TextView) itemView.findViewById(R.id.tvRingtoneName);
            checkBox.setOnClickListener(this);
        }

        public void bindRingtone(Ringtone index) {
            Log.d(TAG, "bindRingtone");
            tv.setText(index.getName());
            checkBox.setTag(getAdapterPosition());
            if((mAlarmID == index.getId())) {
                checkBox.setChecked(true);
                mAlarmID = -1;
            }
            else if ((getAdapterPosition() == selectedPosition)) {
                checkBox.setChecked(true);
            } else checkBox.setChecked(false);
//            checkBox.setOnClickListener(onStateChangeListener(checkBox, getAdapterPosition()));
        }

        @Override
        public void onClick(View view) {
            if (checkBox.isChecked()) {
                selectedPosition = getAdapterPosition();
            } else {
                selectedPosition = -1;
            }
            mListener.onRtClick(getAdapterPosition(), checkBox.isChecked());
            notifyDataSetChanged();
        }

//        private View.OnClickListener onStateChangeListener(final CheckBox checkBox, final int position) {
//            mListener.onRtClick(getAdapterPosition(), checkBox.isChecked());
//            return new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (checkBox.isChecked()) {
//                        selectedPosition = position;
//                    } else {
//                        selectedPosition = -1;
//                    }
//                    notifyDataSetChanged();
//                }
//            };
//        }

//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//        }
    }
}
