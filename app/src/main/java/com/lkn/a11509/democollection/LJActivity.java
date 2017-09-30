package com.lkn.a11509.democollection;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LJActivity extends Activity {

    WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lj);
        mWebview = (WebView) findViewById(R.id.wv_lj);
        mWebview.setNetworkAvailable(true);
        //声明WebSettings子类
        WebSettings webSettings = mWebview.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //支持插件
        webSettings.setPluginState(WebSettings.PluginState.ON);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        // 开启缓存
        webSettings.setAppCacheEnabled(true);// 应用可以有缓存
        webSettings.setDomStorageEnabled(true);// 设置可以使用localStorage
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 优先使用缓存
        webSettings.setAppCacheMaxSize(50 * 1024 * 1024);// 缓存最多可以有10M
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }*/
        mWebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    return false;
                }

                try{

                    Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                    startActivity( intent );

                }catch(Exception e){
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mWebview.loadUrl("javascript:(function() { var videos = document.getElementsByTagName('video'); for(var i=0;i<videos.length;i++){videos[i].play();}})()");
            }
        });
        mWebview.setWebChromeClient(new WebChromeClient() {
            private View myView = null;
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                ViewGroup parent = (ViewGroup) mWebview.getParent();
                parent.removeView(mWebview);

                // 设置背景色为黑色
                view.setBackgroundColor(LJActivity.this.getResources().getColor(R.color.black));
                parent.addView(view);
                myView = view;

                setFullScreen();
            }
            // 退出全屏
            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                if (myView != null) {

                    ViewGroup parent = (ViewGroup) myView.getParent();
                    parent.removeView(myView);
                    parent.addView(mWebview);
                    myView = null;

//                    titleBar.setVisibility(View.VISIBLE);
                    quitFullScreen();
                }
            }
        });

        mWebview.loadUrl("http://51uuid.lancygroup.com/home.php/addon/WxSite/WxSite/index/shop_id/68264bdb65b97eeae6788aa3348e553c");
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText("http://51uuid.lancygroup.com/home.php/addon/WxSite/WxSite/index/shop_id/68264bdb65b97eeae6788aa3348e553c");
//        mWebview.loadUrl("http://ovarofyes.bkt.clouddn.com/01.mp4");
//        mWebview.loadUrl("https://51uuid.lancygroup.com:8099/home.php/addon/WxSite/WxSite/index/shop_id/68264bdb65b97eeae6788aa3348e553c");

    }
    /**
     * 设置全屏
     */
    private void setFullScreen() {
        // 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 退出全屏
     */
    private void quitFullScreen() {
        // 声明当前屏幕状态的参数并获取
        final WindowManager.LayoutParams attrs = this.getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setAttributes(attrs);
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
