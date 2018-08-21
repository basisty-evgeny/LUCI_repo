package com.luci.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.luci.Engine.MyMediaRecorder;
import com.luci.R;
import com.luci.util.FileUtil;

import java.io.File;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity implements
        View.OnClickListener {
    public static MainActivity instance;

    // UI
    TextView txt_server;
    ImageView img_start;
    View view_status;

    ImageView prog_background;
    ImageView prog_out;
    ImageView prog_in;

    RangeSeekBar rsb_left;
    RangeSeekBar rsb_right;
    ImageView img_headphone;
    ImageView img_mic;

    // Data
    boolean isStart = false;
    boolean isEnabledHeadPhone = true;
    boolean isEnabledMic = true;

    MyMediaRecorder mRecorder;
    private Thread thread;
    private boolean isThreadRun = true;

    private float mDecibelSize = 0;
    private int mProgBackWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_main);

        txt_server = findViewById(R.id.txt_server);
        img_start = findViewById(R.id.img_start);
        view_status = findViewById(R.id.view_status);

        prog_background = findViewById(R.id.prog_background);
        prog_background.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                prog_background.removeOnLayoutChangeListener(this);
                mProgBackWidth = right - left;
            }
        });
        prog_out = findViewById(R.id.prog_out);
        prog_in = findViewById(R.id.prog_in);

        rsb_left = findViewById(R.id.rsb_left);
        rsb_left.setValue(0);
        rsb_left.setIndicatorText("0 dB");
        rsb_left.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                rsb_left.setIndicatorText(String.format(Locale.getDefault(), "%.0fdB", leftValue));
            }
            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) { }
            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) { }
        });

        rsb_right = findViewById(R.id.rsb_right);
        rsb_right.setValue(0);
        rsb_right.setIndicatorText("0 dB");
        rsb_right.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                rsb_right.setIndicatorText(String.format(Locale.getDefault(), "%.0fdB", -leftValue));
            }
            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) { }
            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) { }
        });

        img_headphone = findViewById(R.id.img_headphone);
        img_mic = findViewById(R.id.img_mic);

        img_start.setOnClickListener(this);
        img_headphone.setOnClickListener(this);
        findViewById(R.id.img_setting).setOnClickListener(this);
        img_mic.setOnClickListener(this);

        mRecorder = new MyMediaRecorder();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_start: {
                startStopBroadcast();
            }
            break;

            case R.id.img_headphone: {
                enabledHeadPhone();
            }
            break;

            case R.id.img_setting: {
                Intent intent = new Intent(instance, SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_up, R.anim.none);
            }
            break;

            case R.id.img_mic: {
                enableMic();
            }
            break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        File file = FileUtil.createFile("temp.amr");
        if (file != null) {
            mRecorder.setMyRecAudioFile(file);
        } else {
            Toasty.warning(instance, "Can not create record file", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRecorder.delete(); //Stop recording and delete the recording file
        thread = null;
    }

    private void startStopBroadcast() {
        isStart = !isStart;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                img_start.setImageResource(R.drawable.mic_yellow);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txt_server.setCompoundDrawablesWithIntrinsicBounds(isStart ? R.drawable.ic_broadcast_black : R.drawable.ic_broadcast, 0, 0, 0);
                        img_start.setImageResource(isStart ? R.drawable.mic_red : R.drawable.mic_grey);
                        view_status.setBackgroundResource(isStart ? R.drawable.panel_dot_green : R.drawable.panel_dot_grey);

                        if (isStart) {
                            startListenAudio();
                            Toasty.success(instance, "Started!!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            stopListenAudio();
                            Toasty.warning(instance, "Stoped!!", Toast.LENGTH_LONG).show();
                        }

                    }
                }, 1000);
            }
        }, 1000);
    }

    private void enabledHeadPhone() {
        isEnabledHeadPhone = !isEnabledHeadPhone;
        img_headphone.setImageResource(isEnabledHeadPhone ? R.drawable.ic_headphone : R.drawable.ic_headphone_disable);
    }

    private void enableMic() {
        isEnabledMic = !isEnabledMic;
        img_mic.setImageResource(isEnabledMic ? R.drawable.ic_mic : R.drawable.ic_mic_headphone);
    }

    private void startListenAudio() {
        mRecorder.startRecorder();
        isThreadRun = true;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isThreadRun) {
                    try {
                        float volume = mRecorder.getMaxAmplitude();  //Get the sound pressure value
                        if(volume > 0 && volume < 1000000) {
                            Log.e("Decibel", String.format("%f", volume));
                            mDecibelSize = 20 * (float)(Math.log10(volume));
                            // Update with thread
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                        Thread.sleep(300);

                    } catch (InterruptedException e) {
                        e.printStackTrace();

                        isThreadRun = false;
                        break;
                    }
                }
            }
        });
        thread.start();
    }

    private void stopListenAudio() {
        mRecorder.stopRecording();

        if (thread != null)
            thread.interrupt();

        mDecibelSize = 0;
        updateData();
    }

    private void updateData() {
       ViewGroup.LayoutParams lp =  prog_out.getLayoutParams();
       lp.width = (int)(mProgBackWidth * mDecibelSize / 120);
       prog_out.setLayoutParams(lp);
    }

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what == 1){
                updateData();
            }
        }
    };
}
