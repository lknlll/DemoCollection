package com.lkn.a11509.democollection;

import android.os.Bundle;
import android.widget.SeekBar;

import com.dingmouren.paletteimageview.PaletteImageView;

import butterknife.BindView;

public class PaletteImageViewActivity extends BaseActivity {

    @BindView(R.id.palette1)
    PaletteImageView paletteImageView1;
    @BindView(R.id.palette2)
    PaletteImageView paletteImageView2;
    @BindView(R.id.palette3)
    PaletteImageView paletteImageView3;
    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_palette_imageview);
    }

    @Override
    protected void setUpView() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                paletteImageView1.setCornerRadius(progress);
                paletteImageView2.setCornerRadius(progress);
                paletteImageView3.setCornerRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }
}
