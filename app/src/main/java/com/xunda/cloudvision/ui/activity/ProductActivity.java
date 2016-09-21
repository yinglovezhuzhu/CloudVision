package com.xunda.cloudvision.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.presenter.ProductPresenter;
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
        findViewById(R.id.btn_product_back).setOnClickListener(this);
    }
}
