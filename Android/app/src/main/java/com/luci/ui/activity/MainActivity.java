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
import com.luci.util.Constant;
import com.luci.util.FileUtil;

import java.io.File;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import ice.caster.android.shout.Config;
import ice.caster.android.shout.Encoder;

public class MainActivity extends BaseActivity implements
        View.OnClickListener {
    public static MainActivity instance;

    // Encoding
    Encoder encoder;

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

        txt_server = (TextView) findViewById(R.id.txt_server);
        img_start = (ImageView) findViewById(R.id.img_start);
        view_status = findViewById(R.id.view_status);

        prog_background = (ImageView) findViewById(R.id.prog_background);
        prog_background.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                prog_background.removeOnLayoutChangeListener(this);
                mProgBackWidth = right - left;
            }
        });
        prog_out = (ImageView) findViewById(R.id.prog_out);
        prog_in = (ImageView) findViewById(R.id.prog_in);

        rsb_left = (RangeSeekBar) findViewById(R.id.rsb_left);
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

        rsb_right = (RangeSeekBar) findViewById(R.id.rsb_right);
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

        img_headphone = (ImageView) findViewById(R.id.img_headphone);
        img_mic = (ImageView) findViewById(R.id.img_mic);

        img_start.setOnClickListener(this);
        img_headphone.setOnClickListener(this);
        findViewById(R.id.img_setting).setOnClickListener(this);
        img_mic.setOnClickListener(this);

        mRecorder = new MyMediaRecorder();


        encoder = new Encoder(
                new Config().host(Constant.ICE_HOST).port(Constant.ICE_PORT).mount(Constant.ICE_MOUNT)
                        .username(Constant.ICE_USER).password(Constant.ICE_PASS).sampleRate(8000), this);


        /**
         * This is short-hand setter and handler should be static class
         */
        encoder.setHandle(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Encoder.MSG_REC_STARTED:
                        Toasty.success(instance, "Started!!", Toast.LENGTH_LONG).show();
                        break;
                    case Encoder.MSG_REC_STOPPED:
                        Toasty.warning(instance, "Stoped!!", Toast.LENGTH_LONG).show();
                        break;
                    case Encoder.MSG_ERROR_GET_MIN_BUFFERSIZE:
                        Toasty.error(instance, "Error get min buffer size!", Toast.LENGTH_LONG).show();
                        startStopBroadcast();
                        break;
                    case Encoder.MSG_ERROR_REC_START:
                        Toasty.error(instance, "Error rec start!", Toast.LENGTH_LONG).show();
                        startStopBroadcast();
                        break;
                    case Encoder.MSG_ERROR_AUDIO_RECORD:
                        Toasty.error(instance, "Error audio record!", Toast.LENGTH_LONG).show();
                        startStopBroadcast();
                        break;
                    case Encoder.MSG_ERROR_AUDIO_ENCODE:
                        Toasty.error(instance, "Error audio encode!", Toast.LENGTH_LONG).show();
                        startStopBroadcast();
                        break;
                    case Encoder.MSG_ERROR_STREAM_INIT:
                        Toasty.error(instance, "Error stream init!", Toast.LENGTH_LONG).show();
                        startStopBroadcast();
                        break;
                    default:
                        break;
                }
            }
        });

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
                        }
                        else {
                            stopListenAudio();
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
        encoder.start();
    }

    private void stopListenAudio() {
        encoder.stop();
    }

    private void updateData() {
        if (!isStart)
            mDecibelSize = 0;

       ViewGroup.LayoutParams lp =  prog_out.getLayoutParams();
       lp.width = (int)(mProgBackWidth * mDecibelSize / 120);
       prog_out.setLayoutParams(lp);
    }

    public void updateData(float val) {
        mDecibelSize = val;
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
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
