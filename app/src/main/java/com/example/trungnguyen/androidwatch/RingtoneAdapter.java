package com.example.trungnguyen.androidwatch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Trung Nguyen on 12/24/2016.
 */

public class RingtoneAdapter extends RecyclerView.Adapter<RingtoneAdapter.RingtoneViewHolder> {
    private static final String TAG = RingtoneAdapter.class.getSimpleName();
    OnRvItemClick mLis;
    Context mContext;
    List<Ringtone> mRingtones;
    RingtoneViewHolder mViewHolder;

    public RingtoneAdapter(List<Ringtone> ringtones, Context context) {
        Log.d(TAG, "CALL RINGTONE ADAPTER");
        mRingtones = ringtones;
        mContext = context;
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
        Log.d(TAG, "CALL onBindViewHolder");
        holder.bindRingtone(mRingtones.get(position));
    }

    @Override
    public int getItemCount() {
        // Must return some number greater than 0;
        return mRingtones.size();
    }


    public void SetOnRingtoneClick(OnRvItemClick listener) {
        mLis = listener;
    }

    public class RingtoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RadioButton radioButton;
        TextView tv;

        public RingtoneViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "Constructor viewholder");
            radioButton = (RadioButton) itemView.findViewById(R.id.radioRingtone);
            tv = (TextView) itemView.findViewById(R.id.tvRingtoneName);
            radioButton.setOnClickListener(this);
        }

        public void bindRingtone(Ringtone index) {
            Log.d(TAG, "bindRingtone");
            tv.setText(index.getName());
        }

        @Override
        public void onClick(View view) {
            mLis.onRtClick(getAdapterPosition());
        }
    }
}
