package com.xunda.cloudvision.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.ui.fragment.WebViewFragment;
import com.xunda.cloudvision.utils.NetworkManager;

/**
 * 浏览网页Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/10/21.
 */

public class WebViewActivity extends BaseActivity {

    private ProgressBar mPbLoadProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);

        initView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_webview_activity_back:
                finish(RESULT_CANCELED, null);
                break;
            default:
                break;
        }
    }

    private void initView() {
        mPbLoadProgress = (ProgressBar) findViewById(R.id.pb_webview_activity_progress);
        final WebView webView = (WebView) findViewById(R.id.wv_webview_activity_web);
        settingWebView(webView);

        Intent intent = getIntent();

        if(null != intent) {
            Uri uri = intent.getData();
            if(null != uri) {
                webView.loadUrl(uri.toString());
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void settingWebView(WebView webView) {
        // Javascript enabled on webview，不加这个页面可能加载不出来
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // 根据网络状态来设定缓存模式
        if(NetworkManager.getInstance().isNetworkConnected()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 缓存模式：默认模式
        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY); // 缓存模式：只加载缓存
        }
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webView.setWebChromeClient(new CusWebChromeClient());
        webView.setWebViewClient(new CusWebViewClient());
    }

    /**
     * WebView 设置
     */
    private class CusWebChromeClient extends WebChromeClient {
        private WebView mmChildView = null;
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // 当progress小于100的时候显示，等于100的时候隐藏，一次请求多个url的时候不会出现卡顿现象
            mPbLoadProgress.setVisibility( newProgress < 100 ? View.VISIBLE : View.GONE);
            mPbLoadProgress.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            mmChildView = new WebView(WebViewActivity.this);
            settingWebView(mmChildView);
            view.addView(mmChildView);

            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(mmChildView);
            resultMsg.sendToTarget();

            return true;
        }

        @Override
        public void onCloseWindow(WebView window) {
            if (null != mmChildView) {
                mmChildView.setVisibility(View.GONE);
                window.removeView(mmChildView);
            }
        }
    }

    /**
     * WebView 设置
     */
    private class CusWebViewClient extends WebViewClient {
        //在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        //重写此方法可以让webview处理https请求。
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) {
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {

        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }
}
