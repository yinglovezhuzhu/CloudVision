package com.vrcvp.cloudvision.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.utils.NetworkManager;
import com.vrcvp.cloudvision.utils.StringUtils;

/**
 * WebView Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public class WebViewFragment extends BaseFragment {

    private ProgressBar mPbLoadProgress;

    private String mData;
    private String mUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if(null != args) {
            if(args.containsKey(Config.EXTRA_URL)) {
                mUrl = args.getString(Config.EXTRA_URL);
            } else if(args.containsKey(Config.EXTRA_DATA)) {
                mData = args.getString(Config.EXTRA_DATA);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_webview, container, false);
        initView(contentView);
        return contentView;
    }

    private void initView(View contentView) {
        mPbLoadProgress = (ProgressBar) contentView.findViewById(R.id.pb_webview_fragment_progress);
        final WebView webView = (WebView) contentView.findViewById(R.id.wv_webview_fragment_web);
        settingWebView(webView);

        if(!StringUtils.isEmpty(mUrl)) {
            webView.loadUrl(mUrl);
        } else if(!StringUtils.isEmpty(mData)) {
            webView.loadData(mData, "text/html;charset=UTF-8", "UTF-8");
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
            mmChildView = new WebView(getActivity());
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
