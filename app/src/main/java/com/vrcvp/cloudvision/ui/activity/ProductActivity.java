package com.vrcvp.cloudvision.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.ProductBean;
import com.vrcvp.cloudvision.bean.VideoBean;
import com.vrcvp.cloudvision.bean.resp.QueryProductResp;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.observer.ProductObservable;
import com.vrcvp.cloudvision.observer.ProductObserver;
import com.vrcvp.cloudvision.presenter.ProductPresenter;
import com.vrcvp.cloudvision.ui.fragment.ProductGridViewFragment;
import com.vrcvp.cloudvision.ui.fragment.ProductListViewFragment;
import com.vrcvp.cloudvision.ui.fragment.ProductPagerViewFragment;
import com.vrcvp.cloudvision.ui.widget.TipPageView;
import com.vrcvp.cloudvision.view.IProductView;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品列表页面
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public class ProductActivity extends BaseActivity implements IProductView {

    private ProductPresenter mProductPresenter;
    private TipPageView mTipPageView;

    private final ProductObservable mProductObservable = new ProductObservable();
    private final List<ProductBean> mProductData = new ArrayList<>();
    private boolean mRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);

        mProductPresenter = new ProductPresenter(this, this);

        initView();

        queryProduct();
    }

    @Override
    protected void onDestroy() {
        mProductPresenter.onDestroy();
        cancelLoadingDialog();
        super.onDestroy();
//        mProductObservable.unregisterAll();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_product_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.btn_product_search:
                startActivity(new Intent(this, ProductSearchActivity.class));
                break;
            case R.id.tpv_product_list:
                queryProduct();
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
        cancelLoadingDialog();
    }

    @Override
    public void onQueryProductResult(QueryProductResp result) {
        if(null == result) {
            // 错误
            if(mProductPresenter.isLoadMore()) {
                showShortToast(R.string.str_no_more_data);
            } else {
                mTipPageView.setTips(R.drawable.ic_network_error, R.string.str_network_error,
                        R.color.colorTextGray, R.string.str_touch_to_refresh, this);
                mTipPageView.setVisibility(View.VISIBLE);
            }
        } else {
            switch (result.getHttpCode()) {
                case HttpStatus.SC_OK:
                    List<ProductBean> products = result.getData();
                    if(null == products || products.isEmpty()) {
                        // 请求成功，但是没有数据
                        if(mProductPresenter.isLoadMore()) {
                            showShortToast(R.string.str_no_more_data);
                        } else {
                            mTipPageView.setTips(R.drawable.ic_no_data, R.string.str_no_data,
                                    R.color.colorTextGray, R.string.str_touch_to_refresh, this);
                            mTipPageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if(mRefresh) {
                            mProductData.clear();
                        }
                        mProductData.addAll(products);
                        mTipPageView.setVisibility(View.GONE);
                    }
                    break;
                case HttpStatus.SC_NO_MORE_DATA:
                    showShortToast(R.string.str_no_more_data);
                    break;
                case HttpStatus.SC_CACHE_NOT_FOUND:
                    // 无网络，读取缓存错误或者没有缓存
                default:
                    // 错误
                    if(mProductPresenter.isLoadMore()) {
                        showShortToast(R.string.str_network_error);
                    } else {
                        mTipPageView.setTips(R.drawable.ic_network_error, R.string.str_network_error,
                                R.color.colorTextGray, R.string.str_touch_to_refresh, this);
                        mTipPageView.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
        mRefresh = false;
        cancelLoadingDialog();
        mProductObservable.notifyQueryProductResult(mRefresh, mProductPresenter.hasMore(), result);
    }

    /**
     * 注册查询产品观察者
     * @param observer 产品观察者
     */
    public void registerProductObserver(ProductObserver observer) {
        mProductObservable.registerObserver(observer);
    }

    /**
     * 反注册产品观察者
     * @param observer 产品观察者
     */
    public void unregisterProductObserver(ProductObserver observer) {
        mProductObservable.unregisterObserver(observer);
    }

    /**
     * 刷新
     */
    public void refresh() {
        mRefresh = true;
        mProductPresenter.queryProductFirstPage();
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        mRefresh = false;
        mProductPresenter.queryProductNextPage();
    }

    /**
     * 取消加载更多
     */
    public void cancelQueryProduct() {
        mProductPresenter.cancelQueryProduct();
    }

    public List<ProductBean> getProductData() {
        return mProductData;
    }

    private void initView() {
        final FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.fth_product_tabs);
        tabHost.setup(this, getSupportFragmentManager(), R.id.fl_product_container);

        addTabs(tabHost);

        findViewById(R.id.btn_product_back).setOnClickListener(this);
        findViewById(R.id.btn_product_search).setOnClickListener(this);

        final View listViewDivider = findViewById(R.id.view_product_list_view_mode_divider);
        final ToggleButton listViewModeSwitcher = (ToggleButton) findViewById(R.id.tbtn_product_list_view_mode);
        listViewModeSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tabHost.setCurrentTab(isChecked ? 0 : 1);
            }
        });

        final ToggleButton viewModeSwitcher = (ToggleButton) findViewById(R.id.tbtn_product_view_mode);
        viewModeSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tabHost.setCurrentTab(isChecked ? (listViewModeSwitcher.isChecked() ? 0 : 1) : 2);
                listViewModeSwitcher.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                listViewDivider.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        viewModeSwitcher.setChecked(true);
        mTipPageView = (TipPageView) findViewById(R.id.tpv_product_list);
    }

    /**
     * 添加标签
     * @param tabHost
     */
    private void addTabs(FragmentTabHost tabHost) {

        // GridView模式
        final Bundle gridArgs = new Bundle();
        tabHost.addTab(tabHost.newTabSpec("productGrid").setIndicator("productGrid"),
                ProductGridViewFragment.class, gridArgs);

        // 列表模式
        final Bundle listArgs = new Bundle();
        tabHost.addTab(tabHost.newTabSpec("productList").setIndicator("productList"),
                ProductListViewFragment.class, listArgs);

        // 浏览模式
        final Bundle pagerArgs = new Bundle();
        tabHost.addTab(tabHost.newTabSpec("productPager").setIndicator("productPager"),
                ProductPagerViewFragment.class, pagerArgs);


    }



    private void queryProduct() {
//        mTipPageView.setVisibility(View.GONE);
        showLoadingDialog(null, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mProductPresenter.cancelQueryProduct();
            }
        });
        mProductPresenter.queryProductFirstPage();
    }
}
