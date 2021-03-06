package com.lkn.a11509.democollection;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.IconHintView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RefreshCounterActivity extends BaseActivity {

    Animation animation;

    @BindView(R.id.counter)
    TextView tvCounter;
    @BindView(R.id.Button01)
    Button btnStart;
    @BindView(R.id.Button02)
    Button btnStop;
    @BindView(R.id.Button03)
    Button btnReset;

    private final Handler handler = new Handler();
    @BindView(R.id.red_pic)
    ImageView redPic;
    @BindView(R.id.view_pager)
    RollPagerView viewPager;
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
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_small);
        viewPager.setAdapter(new ImageNormalAdapter());
        viewPager.setHintView(new IconHintView(this,R.mipmap.point_focus,R.mipmap.point_normal));
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

    @OnClick(R.id.red_pic)
    public void onViewClicked() {
        redPic.startAnimation(animation);
    }

    private class ImageNormalAdapter extends StaticPagerAdapter {
        int[] imgs = new int[]{
                R.mipmap.img1,
                R.mipmap.img2,
                R.mipmap.img3,
                R.mipmap.img4,
                R.mipmap.img5,
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_START);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setImageResource(imgs[position]);
            return view;
        }


        @Override
        public int getCount() {
            return imgs.length;
        }
    }
}
