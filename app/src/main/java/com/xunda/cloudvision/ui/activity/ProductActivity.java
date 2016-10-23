package com.xunda.cloudvision.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.ProductBean;
import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.http.HttpStatus;
import com.xunda.cloudvision.observer.ProductObservable;
import com.xunda.cloudvision.observer.ProductObserver;
import com.xunda.cloudvision.presenter.ProductPresenter;
import com.xunda.cloudvision.ui.fragment.ProductGridViewFragment;
import com.xunda.cloudvision.ui.fragment.ProductListViewFragment;
import com.xunda.cloudvision.ui.fragment.ProductPagerViewFragment;
import com.xunda.cloudvision.view.IProductView;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品列表页面
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public class ProductActivity extends BaseActivity implements IProductView {

    private ProductPresenter mProductPresenter;

    private final ProductObservable mProductObservable = new ProductObservable();
    private final List<ProductBean> mProductData = new ArrayList<>();
    private boolean mRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);

        mProductPresenter = new ProductPresenter(this, this);

        initView();

        mProductPresenter.queryProduct();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProductObservable.unregisterAll();
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
    public void onQueryProductResult(QueryProductResp result) {
        if(null == result) {

        } else {
            switch (result.getHttpCode()) {
                case HttpStatus.SC_OK:
                    List<ProductBean> products = result.getProduct();
                    if(null == products || products.isEmpty()) {
                        // TODO 错误
                    } else {
                        if(mRefresh) {
                            mProductData.clear();
                        }
                        mProductData.addAll(products);
                        mProductObservable.notifyQueryProductResult(mRefresh, result);
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
        mRefresh = false;
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

    public void refresh() {
        mRefresh = true;
        mProductPresenter.queryProduct();
    }

    public void loadMore() {
        mRefresh = false;
        mProductPresenter.queryProduct();
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
                tabHost.setCurrentTab(isChecked ? 0 : 2);
            }
        });

        final ToggleButton viewModeSwitcher = (ToggleButton) findViewById(R.id.tbtn_product_view_mode);
        viewModeSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tabHost.setCurrentTab(isChecked ? (listViewModeSwitcher.isChecked() ? 0 : 2) : 1);
                listViewModeSwitcher.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                listViewDivider.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        viewModeSwitcher.setChecked(true);
    }

    /**
     * 添加标签
     * @param tabHost
     */
    private void addTabs(FragmentTabHost tabHost) {

        // 列表模式
        final Bundle listArgs = new Bundle();
        tabHost.addTab(tabHost.newTabSpec("productList").setIndicator("productList"),
                ProductListViewFragment.class, listArgs);

        // 浏览模式
        final Bundle pagerArgs = new Bundle();
        tabHost.addTab(tabHost.newTabSpec("productPager").setIndicator("productPager"),
                ProductPagerViewFragment.class, pagerArgs);

        // GridView模式
        final Bundle gridArgs = new Bundle();
        tabHost.addTab(tabHost.newTabSpec("productGrid").setIndicator("productGrid"),
                ProductGridViewFragment.class, gridArgs);


    }
}
