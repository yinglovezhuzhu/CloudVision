package com.vrcvp.cloudvision.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
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

import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.AttrBean;
import com.vrcvp.cloudvision.bean.AttrValueBean;
import com.vrcvp.cloudvision.bean.ImageBean;
import com.vrcvp.cloudvision.bean.ProductBean;
import com.vrcvp.cloudvision.bean.resp.QueryProductDetailResp;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.ui.adapter.ProductDetailAttrAdapter;
import com.vrcvp.cloudvision.presenter.ProductDetailPresenter;
import com.vrcvp.cloudvision.ui.adapter.ProductDetailImgAdapter;
import com.vrcvp.cloudvision.ui.widget.NoScrollListView;
import com.vrcvp.cloudvision.ui.widget.PageControlBar;
import com.vrcvp.cloudvision.utils.LogUtils;
import com.vrcvp.cloudvision.utils.NetworkManager;
import com.vrcvp.cloudvision.view.IProductDetailView;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品详情页面
 * Created by yinglovezhuzhu@gmail.com on 2016/9/26.
 */

public class ProductDetailActivity extends BaseActivity implements IProductDetailView {

    private ProductDetailPresenter mProductDetailPresenter;

    private ViewPager mImgPager;
    private PageControlBar mPageIndicator;
    private ProductDetailImgAdapter mImgAdapter;

    private View mDetailLoadingView;
    private WebView mWebView;

    private ProductDetailAttrAdapter mAttrAdapter;

    private ProductBean mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

        mProductDetailPresenter = new ProductDetailPresenter(this, this);

        if(!initData()) {
            finish(RESULT_CANCELED, null);
            return;
        }

        initView();

        mProductDetailPresenter.queryProductDetail(mProduct.getId());
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

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {

    }

    @Override
    public void onQueryProductDetailResult(QueryProductDetailResp result) {
        if(null == result) {

        } else {
            switch (result.getHttpCode()) {
                case HttpStatus.SC_OK:
                    final ProductBean product = result.getProduct();
                    if(null == product) {
                        // TODO 错误
                    } else {
                        // TODO 设置数据
                        mProduct = product;
                        mImgAdapter.addAll(product.getDetailImages(), true);
                        mPageIndicator.setPageCount(mImgAdapter.getCount());
                        mPageIndicator.setCurrentPage(0);
                        mImgPager.setAdapter(mImgAdapter);

                    }
                    break;
                case HttpStatus.SC_CACHE_NOT_FOUND:
                    // TODO 无网络，读取缓存错误
                    break;
                default:
                    // TODO 错误
                    break;
            }
        }
    }

    public void onPicClicked(int position) {
        viewPic(position);
    }

    /**
     * 初始化数据，处理传递过来的数据
     * @return
     */
    private boolean initData() {
        Intent intent = getIntent();
        if(null == intent) {
            return false;
        }
        mProduct = intent.getParcelableExtra(Config.EXTRA_DATA);
        return null != mProduct;
    }

    /**
     * 初始化视图
     */
    private void initView() {

        final TextView tvProductName = (TextView) findViewById(R.id.tv_product_detail_name);

        findViewById(R.id.ibtn_product_detail_back).setOnClickListener(this);
        findViewById(R.id.ibtn_product_720_view_img).setOnClickListener(this);

        int dmWidth = getResources().getDisplayMetrics().widthPixels;
        int pageMargin = getResources().getDimensionPixelSize(R.dimen.product_detail_img_page_margin);
        mImgPager = (ViewPager) findViewById(R.id.vp_product_detail_img);
        ViewGroup.LayoutParams lp = mImgPager.getLayoutParams();
        if(null == lp) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.WRAP_CONTENT);
        }
        lp.height = dmWidth * 2 / 3;
        mImgPager.setLayoutParams(lp);
        // 调整缩放后的页面间距
        mImgPager.setPageMargin(pageMargin);
        mImgPager.setOffscreenPageLimit(3);
        mImgAdapter = new ProductDetailImgAdapter(getSupportFragmentManager());
        mImgPager.setAdapter(mImgAdapter);

        mPageIndicator = (PageControlBar) findViewById(R.id.pb_product_detail_page_indicator);

        mImgPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        mImgPager.setPageTransformer(false, new ViewPager.PageTransformer() {
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

        AttrBean attrColor = new AttrBean("颜色");
        attrColor.addAttrValue(new AttrValueBean("黄色"));
        attrColor.addAttrValue(new AttrValueBean("紫色"));
        attrColor.addAttrValue(new AttrValueBean("蓝色"));
        attrColor.addAttrValue(new AttrValueBean("白色"));

        mAttrAdapter.add(attrColor, false);

        AttrBean attrSize = new AttrBean("尺寸");
        attrSize.addAttrValue(new AttrValueBean("S"));
        attrSize.addAttrValue(new AttrValueBean("M"));
        attrSize.addAttrValue(new AttrValueBean("L"));
        attrSize.addAttrValue(new AttrValueBean("XL"));
        attrSize.addAttrValue(new AttrValueBean("XXL"));
        attrSize.addAttrValue(new AttrValueBean("XXXL"));
        mAttrAdapter.add(attrSize, true);

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

        tvProductName.setText(mProduct.getName());



        mWebView.loadUrl("https://www.baidu.com/");
    }

    private void viewPic(int position) {
        final List<ImageBean> images = mProduct.getDetailImages();
        if(null == images || images.isEmpty()) {
            return;
        }
        Intent intent = new Intent(this, Img720ViewActivity.class);
        intent.putParcelableArrayListExtra(Config.EXTRA_DATA, new ArrayList<Parcelable>(images));
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
