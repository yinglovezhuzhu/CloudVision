package com.xunda.cloudvision.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.presenter.ProductSearchPresenter;
import com.xunda.cloudvision.ui.adapter.ProductListViewAdapter;
import com.xunda.cloudvision.view.IProductSearchView;

/**
 * 产品搜索Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/28.
 */

public class ProductSearchActivity extends BaseActivity implements IProductSearchView {

    private ProductSearchPresenter mProductSearchPresenter;

    private ProductListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_search);

        mProductSearchPresenter = new ProductSearchPresenter(this);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_product_search_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.btn_product_search:
                break;
            default:
                break;
        }
    }

    private void initView() {
        findViewById(R.id.btn_product_search).setOnClickListener(this);
        findViewById(R.id.ibtn_product_search_back).setOnClickListener(this);

        final GridView gvProduct = (GridView) findViewById(R.id.gv_product_search);
        int padding = getResources().getDimensionPixelSize(R.dimen.contentPadding_level4);
        int width = (getResources().getDisplayMetrics().widthPixels - 3 * padding) / 2;
        int height = width * 3 / 2;
        mAdapter = new ProductListViewAdapter(this, width, height);
        gvProduct.setAdapter(mAdapter);
        gvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ProductSearchActivity.this, ProductDetailActivity.class));
            }
        });
    }
}
