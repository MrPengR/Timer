package com.pry.testtimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Timer mTimer1;
    private TimerTask mTask1;
    private static TextView mTvTime1;

    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        private MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        mTvTime1.setText(getTime());
                        break;
                    case 2:
                        break;
                }
            }
        }
    }

    private static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return format.format(new Date());
    }

    private final MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        mTvTime1 = findViewById(R.id.tv_time1);
        CardView cvStart1 = findViewById(R.id.cv_start1);
        CardView cvStop1 = findViewById(R.id.cv_stop1);

        cvStart1.setOnClickListener(this);
        cvStop1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_start1:
                if (mTimer1 == null && mTask1 == null) {
                    mTimer1 = new Timer();
                    mTask1 = new TimerTask() {
                        @Override
                        public void run() {
                            Message message = mHandler.obtainMessage(1);
                            mHandler.sendMessage(message);
                        }
                    };
                    mTimer1.schedule(mTask1, 0, 1000);
                }
                break;
            case R.id.cv_stop1:
                if (mTimer1 != null) {
                    mTimer1.cancel();
                    mTimer1 = null;
                }
                if (mTask1 != null) {
                    mTask1.cancel();
                    mTask1 = null;
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!mTask1.cancel()) {
            mTask1.cancel();
            mTimer1.cancel();
            mTimer1 = null;
        }
    }

//    private class MyThread extends Thread {
//        private boolean stop = false;
//
//        @Override
//        public void run() {
//            while (!stop) {
//                try {
//                    Mess
//                }
//            }
//        }
//    }
}