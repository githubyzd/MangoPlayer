package com.mango.player.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mango.player.R;
import com.mango.player.util.ApplicationConstant;
import com.mango.player.util.ExceptionUtil;
import com.mango.player.util.LogUtil;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WebActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        App.addActivity(this);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        WebSettings ws = webview.getSettings();
        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        if (Build.VERSION.SDK_INT >= 21) {
            ws.setMixedContentMode(0);
        }
        ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。

        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);

        ws.setDomStorageEnabled(true);
        ws.setSupportMultipleWindows(true);// 新加
        webview.setWebViewClient(new MyWebViewClient());
        webview.setWebChromeClient(new WebChromeClient());
        webview.setDownloadListener(new MyDownloadListener());

        webview.loadUrl(url);
    }

    private void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra(ApplicationConstant.URL);

        if (url == null || url.isEmpty()) {
            ExceptionUtil.illegaArgument("url is null or is empty");
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            LogUtil.logByD("url：" + s);
            if (s.startsWith("http:") || s.startsWith("https:")) {

                return false;
            }

            try {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));

                startActivity(intent);

            } catch (Exception e) {

            }

            return true;
        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
        }


    }

    private class MyDownloadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
            LogUtil.logByD("ldm", url);
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    @OnClick(R.id.back)
    void back() {
        webview.goBack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webview.resumeTimers();
        webview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webview.onPause();
        webview.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        webview.destroy();
        webview = null;
        super.onDestroy();
        App.removeActivity(this);
    }
}
