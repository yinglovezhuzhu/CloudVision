package com.xunda.cloudvision.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
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
import android.widget.AbsListView;
import android.widget.TextView;

import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.AttrValueBean;
import com.xunda.cloudvision.bean.resp.QueryProductDetailResp;
import com.xunda.cloudvision.ui.adapter.ProductDetailAttrAdapter;
import com.xunda.cloudvision.ui.adapter.ProductDetailAttrItemAdapter;
import com.xunda.cloudvision.presenter.ProductDetailPresenter;
import com.xunda.cloudvision.ui.adapter.ProductDetailImgAdapter;
import com.xunda.cloudvision.ui.widget.NoScrollGridView;
import com.xunda.cloudvision.ui.widget.NoScrollListView;
import com.xunda.cloudvision.ui.widget.PageControlBar;
import com.xunda.cloudvision.utils.LogUtils;
import com.xunda.cloudvision.utils.NetworkManager;
import com.xunda.cloudvision.view.IProductDetailView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 产品详情页面
 * Created by yinglovezhuzhu@gmail.com on 2016/9/26.
 */

public class ProductDetailActivity extends BaseActivity implements IProductDetailView {

    private ProductDetailPresenter mProductDetailPresenter;

    private PageControlBar mPageIndicator;
    private ProductDetailImgAdapter mImgAdapter;

    private View mDetailLoadingView;
    private WebView mWebView;

    private ProductDetailAttrAdapter mAttrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

        mProductDetailPresenter = new ProductDetailPresenter(this, this);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_product_detail_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.ibtn_product_720_view_img:
                // 720度看图
                viewPic(0);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        findViewById(R.id.ibtn_product_detail_back).setOnClickListener(this);
        findViewById(R.id.ibtn_product_720_view_img).setOnClickListener(this);

        int dmWidth = getResources().getDisplayMetrics().widthPixels;
        int pageMargin = getResources().getDimensionPixelSize(R.dimen.product_detail_img_page_margin);
        ViewPager imgPager = (ViewPager) findViewById(R.id.vp_product_detail_img);
        ViewGroup.LayoutParams lp = imgPager.getLayoutParams();
        if(null == lp) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.WRAP_CONTENT);
        }
        lp.height = dmWidth * 2 / 3;
        imgPager.setLayoutParams(lp);
        // 调整缩放后的页面间距
        imgPager.setPageMargin(pageMargin);
        imgPager.setOffscreenPageLimit(3);
        mImgAdapter = new ProductDetailImgAdapter(getSupportFragmentManager());
        imgPager.setAdapter(mImgAdapter);

        mPageIndicator = (PageControlBar) findViewById(R.id.pb_product_detail_page_indicator);
        mPageIndicator.setPageCount(mImgAdapter.getCount());
        mPageIndicator.setCurrentPage(imgPager.getCurrentItem());

        imgPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPageIndicator.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imgPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            /** 最小缩放比例 **/
            private static final float PAGE_MIN_SCALE = 0.85f;
            /** 最小透明比例 **/
            private static final float PAGE_MIN_ALPHA = 0.85f;

            @Override
            public void transformPage(View page, float position) {
                if (position < -1) {
                    page.setAlpha(PAGE_MIN_ALPHA);
                    page.setScaleX(PAGE_MIN_SCALE);
                    page.setScaleY(PAGE_MIN_SCALE);
                } else if (position <= 1) {
                    float scaleFactor = Math.max(PAGE_MIN_SCALE, 1 - Math.abs(position));
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
//                    page.setAlpha(PAGE_MIN_ALPHA + (scaleFactor - PAGE_MIN_SCALE) / (1 - PAGE_MIN_SCALE) * (1 - PAGE_MIN_ALPHA));
                } else {
//                    page.setAlpha(PAGE_MIN_ALPHA);
                    page.setScaleX(PAGE_MIN_SCALE);
                    page.setScaleY(PAGE_MIN_SCALE);
                }
            }
        });

        final NoScrollListView lvAttr = (NoScrollListView) findViewById(R.id.lv_product_detail_attr);
        final TextView tvPrice = (TextView) findViewById(R.id.tv_product_detail_price);
        mAttrAdapter = new ProductDetailAttrAdapter(this);
        lvAttr.setAdapter(mAttrAdapter);

        List<AttrValueBean> colors = new ArrayList<>();
        colors.add(new AttrValueBean("颜色", "黄色"));
        colors.add(new AttrValueBean("颜色", "紫色"));
        colors.add(new AttrValueBean("颜色", "蓝色"));
        colors.add(new AttrValueBean("颜色", "白色"));

        mAttrAdapter.addAll(colors, false);

        List<AttrValueBean> sizes = new ArrayList<>();
        sizes.add(new AttrValueBean("尺寸", "S"));
        sizes.add(new AttrValueBean("尺寸", "M"));
        sizes.add(new AttrValueBean("尺寸", "L"));
        sizes.add(new AttrValueBean("尺寸", "XL"));
        sizes.add(new AttrValueBean("尺寸", "XXL"));
        sizes.add(new AttrValueBean("尺寸", "XXXL"));
        mAttrAdapter.addAll(sizes, true);

        tvPrice.setText(String.format(getString(R.string.str_price_format_with_currency), "100"));

        mAttrAdapter.setOnAttrCheckChangedListener(new ProductDetailAttrAdapter.OnAttrCheckChangedListener() {
            @Override
            public void onAttrCheckChanged(int position, int subPosition, boolean isChecked) {
                LogUtils.e("XXXX", "Attr Check Changed: \nposition-> " + position
                        + "\nsubPosition-> " + subPosition
                        + "\nisChecked-> " + isChecked);
                if(isChecked) {
                    boolean updatePrice = true;
                    for(int i = 0; i < mAttrAdapter.getCount(); i++) {
                        if(mAttrAdapter.getCheckedPosition(i) == -1) {
                            updatePrice = false;
                            break;
                        }
                    }
                    if(updatePrice) {
                        switch (subPosition) {
                            case 0:
                                tvPrice.setText(String.format(getString(R.string.str_price_format_with_currency), "100"));
                                break;
                            case 1:
                                tvPrice.setText(String.format(getString(R.string.str_price_format_with_currency), "120"));
                                break;
                            case 2:
                                tvPrice.setText(String.format(getString(R.string.str_price_format_with_currency), "150"));
                                break;
                            case 3:
                                tvPrice.setText(String.format(getString(R.string.str_price_format_with_currency), "200"));
                                break;
                            default:
                                tvPrice.setText(String.format(getString(R.string.str_price_format_with_currency), "220"));
                                break;
                        }
                    }
                }
            }
        });


        mDetailLoadingView = findViewById(R.id.ll_product_detail_loading);
        mWebView = (WebView) findViewById(R.id.wv_product_detail);
        settingWebView(mWebView);
        mWebView.loadUrl("https://www.baidu.com/");
    }

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {

    }

    @Override
    public void onQueryProductDetailResult(QueryProductDetailResp result) {

    }

    public void onPicClicked(int position) {
        viewPic(position);
    }

    private void viewPic(int position) {
        Intent intent = new Intent(this, Img720ViewActivity.class);
        intent.putExtra(Config.EXTRA_POSITION, position);
        startActivity(intent);
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
            super.onProgressChanged(view, newProgress);
        }

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            mmChildView = new WebView(ProductDetailActivity.this);
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
            mDetailLoadingView.setVisibility(View.GONE);
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
