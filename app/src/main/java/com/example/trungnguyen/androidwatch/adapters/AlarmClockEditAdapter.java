package com.example.trungnguyen.androidwatch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trungnguyen.androidwatch.OnRvItemClick;
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
public class AlarmClockEditAdapter extends RecyclerView.Adapter<AlarmClockEditAdapter.AlarmViewHolder> {

    public static final String TAG = AlarmClockAdapter.class.getSimpleName();
    private List<AlarmTime> mAlarms;
    private AlarmViewHolder mViewHolder;
    private OnRvItemClick mListener;
    Context mContext;
    //Tạo 1 file Interface rồi khai báo Interface trong rv adapter
    //Trong hàm onClick trong viewholder khai báo mListener.onListAlarmSelected
    //Và khai báo 1 hàm SetOnRvClickTenner
//    public void SetOnRvClickListener(OnRvItemClick listener) {
//        mListener = listener;
//    }
    //Trong fragment or activity implements Interface đó và khai báo, Rv.SetOnRvClickListener(this)
    //Override abtract method onListAlarmSelected trong fragment or activity
    //Interface dc override ở đâu thì cần khai báo với Activity or Context của Activity or Fragment đó(dùng getActivity() cho Fragment)

    //CÓ 2 cách sử dụng, 1 là khai báo hàm SetOnRvClick trong adapter
    // 2 là trong hàm tạo adapter thêm 1 biến context để khai báo cho interface

    public AlarmClockEditAdapter(List<AlarmTime> alarms) { //Cách 2 khai báo thêm 1 biến trong Hàm tạo adapter
        mAlarms = alarms;
//        mContext = context;
//        mListener = (OnRvItemClick) mContext;
    }

    @Override
    public AlarmClockEditAdapter.AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_clock_edit_item, parent, false);
        mViewHolder = new AlarmViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(AlarmClockEditAdapter.AlarmViewHolder holder, int position) {
        Log.d(TAG, position + "");
        holder.bindAlarm(mAlarms.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlarms.size();
    }

    //Trong android thường sử dụng cách tạo hàm SetOnClick cho việc xử lí các sự kiện click lên các View

    public void SetOnRvClickListener(OnRvItemClick listener) {
        mListener = listener;
        if (mListener == null)
            Log.d(TAG, "NULL");
        else Log.d(TAG, "KO NULL");
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView tvTime, tvContent;
        public ImageView btEdit, btDel;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tvAlarmTime);
            tvContent = (TextView) itemView.findViewById(R.id.tvAlarmConent);
            btEdit = (ImageView) itemView.findViewById(R.id.btEdit);
            btDel = (ImageView) itemView.findViewById(R.id.btDel);
            btEdit.setOnClickListener(this);
            btDel.setOnClickListener(this);
        }

        public void bindAlarm(AlarmTime time) {
            tvTime.setText(time.getTime());
            tvContent.setText(time.getContent());
        }

        @Override
        public void onClick(View view) {
            //Đặt view code cho để biết ta click vào view nào trong
            //nếu nếu ta click vào btDel ta sẻ gọi hàm với viewCode = 0
            //còn nếu ta click vào btEdit ta sẻ gọi hàm với viewCode = 1
            Log.d(TAG, "CALL ON CLICK EDIT ADAPTER");
            if (view == btDel) {
                mListener.onListAlarmSelected(getAdapterPosition(), view, 0);
            } else if (view == btEdit)
                mListener.onListAlarmSelected(getAdapterPosition(), view, 1);
        }
    }
}
