package com.lkn.a11509.democollection;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * onSaveInstance当中存储一个字符串，Activity被销毁并重建时，可以在onCreate和onRestoreInstanceState中获取之，
 * 二者区别是，onRestoreInstanceState一旦被调用，其参数Bundle savedInstanceState一定是有值的
 * onCreate 如果是Activity正常启动的话，Bundle savedInstanceState为null，需判空，官方建议使用onRestoreInstanceState
 */
public class LifeCircleActivity extends Activity {

    String TAG = LifeCircleActivity.this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_toolbar);
        if (savedInstanceState != null) {
            String test = savedInstanceState.getString("extra_test");
            Log.d(TAG,"[onCreate]restore extra_test:" + test);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState");
        outState.putString("extra_test","test");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String test = savedInstanceState.getString("extra_test");
        Log.d(TAG,"[onRestoreInstanceState]restore extra_test:" + test);
    }
}
