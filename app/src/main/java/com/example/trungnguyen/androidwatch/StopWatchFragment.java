package com.example.trungnguyen.androidwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Trung Nguyen on 12/17/2016.
 */
public class StopWatchFragment extends Fragment {
    private static final String TAG = StopWatchFragment.class.getSimpleName();
    Button btStart, btStop, btReset, btLap;
    ListView lvStopWatch;
    TextView tvTimer, tvLapTimer;
    private int mSeconds = 0;
    private int mJiffy = 0;
    boolean isRunning;
    private Handler handler;
    private String time;
    ArrayList<String> lapTimeList;
    private String lapTime;
    ArrayAdapter adapter;
    int mLapJiffy = 0;
    int lapIndex = 1;
    boolean isLapButtonClicked = false;
    Handler lapHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.stop_watch_fragment, container, false);
        addControls(mView);
        addVariables();
        addClickEvents();
        //12/18/2016 nếu bạn gọi hàm runTimer trong Start button process,
        // mỗi lần click start button 1 post mới sẻ đc gọi, và sẻ có nhiều post chạy song song với nhau
        runHoursTimer();
        ;
        runTimer();
        runLapTimer();
        return mView;
    }

    private void addVariables() {
        btStop.setVisibility(View.INVISIBLE);
        btReset.setVisibility(View.INVISIBLE);
        lapTimeList = new ArrayList<>();
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, lapTimeList);
        lvStopWatch.setAdapter(adapter);
    }

    private void addClickEvents() {
        // TODO: Start button clicked 
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProcess();
            }
        });
        // TODO: Stop button clicked 
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopProcess();
            }
        });
        // TODO: Reset button clicked 
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetProcess();
            }
        });
        // TODO: Lap button clicked
        btLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lapProcess();
            }
        });
    }

    private void lapProcess() {
        if (lapTimeList.size() == 0) {
            lapTimeList.add("Lap " + String.valueOf(lapIndex++) + " : " + time);
            adapter.notifyDataSetChanged();
        } else {
            lapTimeList.add("Lap " + String.valueOf(lapIndex++) + " : " + lapTime);
            adapter.notifyDataSetChanged();
        }
        mLapJiffy = 0;
        isLapButtonClicked = true;
    }

    private void resetProcess() {
        btStop.setVisibility(View.INVISIBLE);
        btStart.setVisibility(View.VISIBLE);
        btReset.setVisibility(View.INVISIBLE);
        btLap.setVisibility(View.VISIBLE);
        isRunning = false;
        mSeconds = 0;
        mJiffy = 0;
        mLapJiffy = 0;
        lapTimeList.clear();
        adapter.notifyDataSetChanged();
        Log.d(TAG, "CALL RESET PROCESS");
    }

    private void stopProcess() {
        btReset.setVisibility(View.VISIBLE);
        btLap.setVisibility(View.INVISIBLE);
        isRunning = false;
        Log.d(TAG, "CALL STOP PROCESS");
    }

    private void startProcess() {
        btStart.setVisibility(View.INVISIBLE);
        btStop.setVisibility(View.VISIBLE);
        isRunning = true;
        Log.d(TAG, "CALL START PROCESS");
    }

    private void addControls(View v) {
        btReset = (Button) v.findViewById(R.id.btReset);
        btStart = (Button) v.findViewById(R.id.btStart);
        btStop = (Button) v.findViewById(R.id.btStop);
        btLap = (Button) v.findViewById(R.id.btLap);
        lvStopWatch = (ListView) v.findViewById(R.id.lvStopWatch);
        tvTimer = (TextView) v.findViewById(R.id.tvTimer);
        tvLapTimer = (TextView) v.findViewById(R.id.tvLapTimer);
    }

    public void runHoursTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Call run handler");
                int hours = mSeconds / 3600;
                int minutes = (mSeconds % 3600) / 60;
                int secs = mSeconds % 60;
                time = String.format("%d:%02d:%02d", hours, minutes, secs);
                tvTimer.setText(time);
                if (isRunning)
                    mSeconds++;
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void runTimer() {
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Call run handler");
                int minutes = mJiffy / 6000;
                int seconds = (mJiffy % 6000) / 100;
                int jiffy = mJiffy % 100;
                time = String.format("%02d:%02d:%02d", minutes, seconds, jiffy);
                tvTimer.setText(time);
                if (isRunning)
                    mJiffy++;
                handler.postDelayed(this, 10);
            }
        });
    }

    private void runLapTimer() {
        lapHandler = new Handler();
        lapHandler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = mLapJiffy / 6000;
                int seconds = (mLapJiffy % 6000) / 100;
                int jiffy = mLapJiffy % 100;
                lapTime = String.format("%02d:%02d:%02d", minutes, seconds, jiffy);
                tvLapTimer.setText(lapTime);
                if (isRunning && isLapButtonClicked)
                    mLapJiffy++;
                lapHandler.postDelayed(this, 10);
            }
        });
    }
}
