package com.lkn.a11509.democollection;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.werb.permissionschecker.PermissionChecker;

import butterknife.BindView;
import butterknife.OnClick;

public class WertVideoActivity extends BaseActivity {

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private PermissionChecker permissionChecker;

    @BindView(R.id.btn_video)
    Button video;
    @BindView(R.id.btn_video_lin)
    Button videoLin;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_werb_video);
    }

    @Override
    protected void setUpView() {
        permissionChecker = new PermissionChecker(this); // initialize，must need
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_video)
    public void onViewClicked() {
        if (permissionChecker.isLackPermissions(PERMISSIONS)) {
            permissionChecker.requestPermissions();
        } else {
            startVideo();
        }
    }
    @OnClick(R.id.btn_video_lin)
    public void onVideoLinClicked() {
        if (permissionChecker.isLackPermissions(PERMISSIONS)) {
            permissionChecker.requestPermissions();
        } else {
            startVideoLin();
        }
    }
    private void startVideoLin(){
        Intent intent = new Intent();
        intent.setClass(WertVideoActivity.this,LinRecordActivity.class);
        startActivity(intent);
    }
    private void startVideo(){
        Intent intent = new Intent();
        intent.setClass(WertVideoActivity.this,VideoRecorderActivity.class);
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionChecker.PERMISSION_REQUEST_CODE:
                if (permissionChecker.hasAllPermissionsGranted(grantResults)) {
                    startVideo();
                } else {
                    permissionChecker.showDialog();
                }
                break;
        }
    }
}
