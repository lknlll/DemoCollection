package com.lkn.a11509.democollection;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class LinRecordActivity extends BaseActivity {

    @BindView(R.id.main_surface_view)
    SurfaceView mSurfaceView;
    @BindView(R.id.bt_start_pause)
    Button btStartPause;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.after_pause_view)
    LinearLayout afterPauseLayout;
    private MediaUtils mediaUtils;
    boolean isRecording = false;
    private int mProgress;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    progressBar.setProgress(mProgress);
                    if (mediaUtils.isRecording()) {
                        mProgress = mProgress + 1;
                        sendMessageDelayed(handler.obtainMessage(0), 100);
                    }
                    break;
            }
        }
    };

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_lin_record);
    }

    @Override
    protected void setUpView() {
        mediaUtils = new MediaUtils(this);
        mediaUtils.setRecorderType(MediaUtils.MEDIA_VIDEO);
        mediaUtils.setTargetDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
        mediaUtils.setTargetName(UUID.randomUUID() + ".mp4");
        mediaUtils.setSurfaceView(mSurfaceView);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    private void startView(){
        progressBar.setVisibility(View.VISIBLE);
        afterPauseLayout.setVisibility(View.GONE);
        mProgress = 0;
        handler.removeMessages(0);
        handler.sendMessage(handler.obtainMessage(0));
    }

    private void stopView(){
        progressBar.setVisibility(View.GONE);
        afterPauseLayout.setVisibility(View.VISIBLE);
        mProgress = 0;
        handler.removeMessages(0);
    }
    @OnClick(R.id.bt_start_pause)
    public void onStartPauseClicked() {
        isRecording = !isRecording;
        if (isRecording) {
            mediaUtils.record();
            startView();
        }else {
            mediaUtils.stopRecordSave();
            stopView();
        }
    }
}
