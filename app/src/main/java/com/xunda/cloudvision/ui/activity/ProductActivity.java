package com.xunda.cloudvision.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.presenter.ProductPresenter;
import com.xunda.cloudvision.ui.fragment.ProductListViewFragment;
import com.xunda.cloudvision.ui.fragment.ProductPagerViewFragment;
import com.xunda.cloudvision.view.IProductView;

/**
 * 产品列表页面
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public class ProductActivity extends BaseActivity implements IProductView {

    private ProductPresenter mProductPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);

        mProductPresenter = new ProductPresenter(this);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_product_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.btn_product_search:
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

    private void initView() {
        final FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.fth_product_tabs);
        tabHost.setup(this, getSupportFragmentManager(), R.id.fl_product_container);

        addTabs(tabHost);

        findViewById(R.id.btn_product_back).setOnClickListener(this);
        findViewById(R.id.btn_product_search).setOnClickListener(this);
        final ToggleButton viewModeSwitcher = (ToggleButton) findViewById(R.id.tbtn_product_view_mode);
        viewModeSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tabHost.setCurrentTab(isChecked ? 0 : 1);
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
        final Bundle honorArgs = new Bundle();
        tabHost.addTab(tabHost.newTabSpec("honorTab").setIndicator("honorTab"),
                ProductListViewFragment.class, honorArgs);

        // 浏览模式
        final Bundle cultureArgs = new Bundle();
        tabHost.addTab(tabHost.newTabSpec("cultureTab").setIndicator("honorTab"),
                ProductPagerViewFragment.class, cultureArgs);


    }
}
