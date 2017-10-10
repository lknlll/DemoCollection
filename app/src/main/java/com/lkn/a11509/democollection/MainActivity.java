package com.lkn.a11509.democollection;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
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
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.zhl.userguideview.UserGuideView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.guideView)
    UserGuideView guideView;

    private List<DataBean> data;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "back key caught", Toast.LENGTH_SHORT).show();
            // 在这里，拦截或者监听Android系统的返回键事件。
            // return将拦截。
            // 不做任何处理则默认交由Android系统处理。
        }

        return false;
    }

    @Override
    protected void setUpContentView() {
        //layout处右击或者Alt + Insert Generate ButterKnife Injections
        //if this option is missing,try Invalidate caches/ restart
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setUpView() {
//        单一高亮View的设置方法
//        guideView.setHighLightView(bukBtn);
//        多个高亮View顺序展示
        View[] target = new View[]{bukTv,bukBtn,bukLv};
        guideView.setHightLightView(target);
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
        Log.v("deviceInfo", getDeviceInfo(this));
        Log.v("screenInfo", getWindowsSize());
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
            JSONObject json = new JSONObject();
            TelephonyManager tm = (TelephonyManager) context
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
                device_id = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getWindowsSize() {
        //Android获得屏幕的宽和高
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = screenWidth = display.getWidth();
        int screenHeight = screenHeight = display.getHeight();
        String size = screenHeight + "x" + screenWidth;
        return size;
    }

    private void initListViewData() {
        data = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            DataBean bean = new DataBean();
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    bean.setTitle(getResources().getString(R.string.app_name) + i);
                    bean.setContent(getResources().getString(R.string.app_name));
                    break;
                case 5:
                    bean.setTitle("WerbVideo");
                    bean.setContent("https://github.com/Werb/MediaUtils");
                    break;
                case 6:
                    bean.setTitle("PaletteImageViewActivity");
                    bean.setContent("提取图片的主要颜色作为图片阴影");
                    break;
                case 7:
                    bean.setTitle("VectAlignActivity");
                    bean.setContent("两张静态图片合并成SVG");
                    break;
                case 8:
                    bean.setTitle("RecordActivity");
                    bean.setContent("录音");
                    break;
                case 9:
                    bean.setTitle("PagingRecycler");
                    bean.setContent("列表页");
                    break;
                case 10:
                    bean.setTitle("LJWebView");
                    bean.setContent("LJWebView");
                    break;
            }
            data.add(bean);
        }
        bukLv.setAdapter(new TestAdapter(this, data));
    }

    @OnClick({R.id.buk_tv, R.id.buk_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buk_tv:
                BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();

                List<MenuItem> menuItemList = new ArrayList<MenuItem>();
                MenuItem menuItem1 = new MenuItem();
                menuItem1.setText("checkPermission");
                menuItem1.setStyle(MenuItem.MenuItemStyle.COMMON);
                menuItem1.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem1) {
                    @Override
                    public void onClickMenuItem(View v, MenuItem menuItem) {
                        Log.i("", "onClickMenuItem: " + menuItem.getText());
                        PackageManager pm = getPackageManager();
                        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                                pm.checkPermission("android.permission.RECORD_AUDIO", "com.lkn.a11509.democollection"));
                        if (permission) {
                            Toast.makeText(MainActivity.this, "有录音权限", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "木有录音权限", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            PackageInfo pack = pm.getPackageInfo("com.lkn.a11509.democollection", PackageManager.GET_PERMISSIONS);
                            String[] permissionStrings = pack.requestedPermissions;
                            String permissionReq = "";
                            for (String permissionString : permissionStrings) {
                                permissionReq = permissionReq + permissionString + "\n";
                            }
                            Toast.makeText(MainActivity.this, "权限清单--->" + permissionReq, Toast.LENGTH_SHORT).show();
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                MenuItem menuItem2 = new MenuItem();
                menuItem2.setText("去权限管理");
                menuItem2.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem2) {
                    @Override
                    public void onClickMenuItem(View v, MenuItem menuItem) {
                        if (Build.MANUFACTURER.equals("Xiaomi")) {
                            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                            intent.setComponent(componentName);
                            intent.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);
                            startActivity(intent);
                        } else if (Build.MANUFACTURER.equals("Meizu")) {
                            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                            startActivity(intent);
                        }
                        Log.i("", "onClickMenuItem: ");
                    }
                });
                MenuItem menuItem3 = new MenuItem();
                menuItem3.setText("点击！");
                menuItem3.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem3) {
                    @Override
                    public void onClickMenuItem(View v, MenuItem menuItem) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, FlowActivity.class);
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
                Toast.makeText(this, R.id.buk_btn + "", Toast.LENGTH_SHORT).show();
                //Launch recording Video
                PictureSelector.create(MainActivity.this)
                        .openCamera(PictureMimeType.ofVideo())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
        }
    }

    //注意：这个方法返回boolean类型
    @OnLongClick({R.id.buk_tv})
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.buk_tv:
                //返回被调用方法名
                Toast.makeText(this, new Throwable().getStackTrace()[0].getMethodName(), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @OnItemClick({R.id.buk_lv})
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                Toast.makeText(this, getString(R.string.main_activity_position)
                        + position + getString(R.string.module_name_recycler), Toast.LENGTH_SHORT).show();
                gotoActivity(MainActivity.this, RecyclerDemoActivity.class, null, false);
                break;
            case 1:
                Toast.makeText(this, getString(R.string.main_activity_position)
                        + position + getString(R.string.title_activity_refresh_demo), Toast.LENGTH_SHORT).show();
                gotoActivity(MainActivity.this, DefineLoadWithRefreshActivity.class, null, false);
                break;
            case 2:
                Toast.makeText(this, getString(R.string.main_activity_position)
                        + position + getString(R.string.title_activity_life_circle_demo), Toast.LENGTH_SHORT).show();
                gotoActivity(MainActivity.this, LifeCircleActivity.class, null, false);
                break;
            case 3:
                Toast.makeText(this, getString(R.string.main_activity_position)
                        + position + getString(R.string.title_refresh_counter), Toast.LENGTH_SHORT).show();
                gotoActivity(MainActivity.this, RefreshCounterActivity.class, null, false);
                break;
            case 5:
                gotoActivity(MainActivity.this, WertVideoActivity.class, null, false);
                break;
            case 6:
                gotoActivity(MainActivity.this, PaletteImageViewActivity.class, null, false);
                break;
            case 7:
                gotoActivity(MainActivity.this, VectAlignActivity.class, null, false);
                break;
            case 8:
                gotoActivity(MainActivity.this, RecordActivity.class, null, false);
                break;
            case 9:
                gotoActivity(MainActivity.this, PagingRecyclerActivity.class, null, false);
                break;
            case 10:
                gotoActivity(MainActivity.this, LJActivity.class, null, false);
                break;
        }
    }

    //注意：这个方法返回boolean类型
    @OnItemLongClick({R.id.buk_lv})
    public boolean onItemLongClick(View view) {
        Toast.makeText(this, "你要不要再按久点！", Toast.LENGTH_SHORT).show();
        return true;
    }
}
