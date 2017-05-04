package com.lkn.a11509.democollection;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lkn.a11509.democollection.Adapter.TestAdapter;
import com.lkn.a11509.democollection.Bean.DataBean;
import com.lkn.a11509.democollection.Bean.MenuItem;
import com.lkn.a11509.democollection.Fragment.BottomMenuFragment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import butterknife.OnLongClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.buk_tv)
    TextView bukTv;
    @BindView(R.id.buk_btn)
    Button bukBtn;
    @BindView(R.id.buk_lv)
    ListView bukLv;

    private List<DataBean> data;

    @Override
    protected void setUpContentView() {
        //layout处右击或者Alt + Insert Generate ButterKnife Injections
        //if this option is missing,try Invalidate caches/ restart
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setUpView() {
        initListViewData();
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt > Build.VERSION_CODES.HONEYCOMB) {// api11
            ClipboardManager copy = (ClipboardManager) MainActivity.this
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            copy.setText(getDeviceInfo(this));
            Toast.makeText(MainActivity.this, "成功复制到粘贴板",
                    Toast.LENGTH_SHORT).show();
        } else if (sdkInt <= Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager copyq = (android.text.ClipboardManager) MainActivity.this
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            copyq.setText(getDeviceInfo(this));
            Toast.makeText(MainActivity.this, "成功复制到粘贴板",
                    Toast.LENGTH_SHORT).show();
        }
        Log.v("deviceInfo",getDeviceInfo(this));
        Log.v("screenInfo",getWindowsSize());
    }

    @Override
    protected void setUpTitle(int titleResId) {
        super.setUpTitle(R.string.app_name);
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }
    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getWindowsSize(){
        //Android获得屏幕的宽和高
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = screenWidth = display.getWidth();
        int screenHeight = screenHeight = display.getHeight();
        String size = screenHeight +"x"+screenWidth;
        return size;
    }

    private void initListViewData(){
        data = new ArrayList<>();
        for (int i = 0; i<6;i++){
            DataBean bean = new DataBean();
            bean.setTitle(getResources().getString(R.string.app_name)+i);
            bean.setContent(getResources().getString(R.string.app_name));
            data.add(bean);
        }
        bukLv.setAdapter(new TestAdapter(this,data));
    }

    @OnClick({R.id.buk_tv, R.id.buk_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buk_tv:
                BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();

                List<MenuItem> menuItemList = new ArrayList<MenuItem>();
                MenuItem menuItem1 = new MenuItem();
                menuItem1.setText("Hello World");
                menuItem1.setStyle(MenuItem.MenuItemStyle.COMMON);
                MenuItem menuItem2 = new MenuItem();
                menuItem2.setText("Menu Btn 2");
                MenuItem menuItem3 = new MenuItem();
                menuItem3.setText("点击！");
                menuItem3.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem3) {
                    @Override
                    public void onClickMenuItem(View v, MenuItem menuItem) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,FlowActivity.class);
                        startActivity(intent);
                        Log.i("", "onClickMenuItem: ");
                    }
                });
                menuItemList.add(menuItem1);
                menuItemList.add(menuItem2);
                menuItemList.add(menuItem3);

                bottomMenuFragment.setMenuItems(menuItemList);

                bottomMenuFragment.show(getSupportFragmentManager(), "BottomMenuFragment");
                break;
            case R.id.buk_btn:
                Toast.makeText(this, R.id.buk_btn+"", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //注意：这个方法返回boolean类型
    @OnLongClick({R.id.buk_tv})
    public boolean onLongClick(View view){
        switch (view.getId()){
            case R.id.buk_tv:
                //返回被调用方法名
                Toast.makeText(this, new Throwable().getStackTrace()[0].getMethodName(), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @OnItemClick({R.id.buk_lv})
    public void onItemClick(int position){
        switch (position) {
            case 0:
                Toast.makeText(this, getString(R.string.main_activity_position)
                        + position + getString(R.string.module_name_recycler), Toast.LENGTH_SHORT).show();
                gotoActivity(MainActivity.this,RecyclerDemoActivity.class,null,false);
                break;
            case 1:
                Toast.makeText(this, getString(R.string.main_activity_position)
                        + position + getString(R.string.title_activity_refresh_demo), Toast.LENGTH_SHORT).show();
                gotoActivity(MainActivity.this,DefineLoadWithRefreshActivity.class,null,false);
                break;
            case 2:
                Toast.makeText(this, getString(R.string.main_activity_position)
                        + position + getString(R.string.title_activity_life_circle_demo), Toast.LENGTH_SHORT).show();
                gotoActivity(MainActivity.this,LifeCircleActivity.class,null,false);
                break;
            case 3:
                Toast.makeText(this, getString(R.string.main_activity_position)
                        + position + getString(R.string.title_refresh_counter), Toast.LENGTH_SHORT).show();
                gotoActivity(MainActivity.this,RefreshCounterActivity.class,null,false);
                break;
        }
    }

    //注意：这个方法返回boolean类型
    @OnItemLongClick({R.id.buk_lv})
    public boolean onItemLongClick(View view){
        Toast.makeText(this, "你要不要再按久点！", Toast.LENGTH_SHORT).show();
        return true;
    }
}
