package com.lkn.a11509.democollection;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class RefreshCounterActivity extends BaseActivity {

    @BindView(R.id.counter)
    TextView tvCounter;
    @BindView(R.id.Button01)
    Button btnStart;
    @BindView(R.id.Button02)
    Button btnStop;
    @BindView(R.id.Button03)
    Button btnReset;

    private final Handler handler = new Handler();
    private long count = 0;
    private boolean run = false;

    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            if (run) {
                handler.postDelayed(this, 1000);
                count++;
            }
            tvCounter.setText("Count: " + count);
        }
    };

    private void updateButton() {
        btnStart.setEnabled(!run);
        btnStop.setEnabled(run);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_refresh_counter);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.Button01, R.id.Button02, R.id.Button03})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Button01:
                run = true;
                updateButton();
                handler.postDelayed(task, 1000);
                break;
            case R.id.Button02:
                run = false;
                updateButton();
                handler.post(task);
                break;
            case R.id.Button03:
                count = 0;
                run = false;
                updateButton();
                handler.post(task);
                break;
        }
    }
}
