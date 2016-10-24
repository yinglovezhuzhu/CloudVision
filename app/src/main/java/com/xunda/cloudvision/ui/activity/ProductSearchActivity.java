package com.xunda.cloudvision.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.opensource.pullview.IPullView;
import com.opensource.pullview.OnLoadMoreListener;
import com.opensource.pullview.OnRefreshListener;
import com.opensource.pullview.PullListView;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.ProductBean;
import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.http.HttpStatus;
import com.xunda.cloudvision.presenter.ProductSearchPresenter;
import com.xunda.cloudvision.ui.adapter.ProductGridViewAdapter;
import com.xunda.cloudvision.view.IProductSearchView;

import java.util.List;

/**
 * 产品搜索Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/28.
 */

public class ProductSearchActivity extends BaseActivity implements IProductSearchView {

    private ProductSearchPresenter mProductSearchPresenter;

    private EditText mEtKeyword;

    private PullListView mLvProduct;
    private ProductGridViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_search);

        mProductSearchPresenter = new ProductSearchPresenter(this, this);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_product_search_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.btn_product_search:
                mProductSearchPresenter.search();
                hideSoftInputFromWindow(mEtKeyword);
                break;
            default:
                break;
        }
    }

    @Override
    public String getKeyword() {
        if(null == mEtKeyword) {
            return null;
        }
        return mEtKeyword.getText().toString().trim();
    }

    @Override
    public void onKeywordEmptyError() {
        showShortToast(R.string.str_input_keyword);
    }

    private void initView() {
        mEtKeyword = (EditText) findViewById(R.id.et_product_search_keyword);
        findViewById(R.id.btn_product_search).setOnClickListener(this);
        findViewById(R.id.ibtn_product_search_back).setOnClickListener(this);

        mLvProduct = (PullListView) findViewById(R.id.lv_product_search);
        int padding = getResources().getDimensionPixelSize(R.dimen.contentPadding_level4);
        int width = (getResources().getDisplayMetrics().widthPixels - 3 * padding) / 2;
        int height = width * 3 / 2;
        mAdapter = new ProductGridViewAdapter(this, width, height, 2, padding);
        mLvProduct.setAdapter(mAdapter);
        mAdapter.setOnProductItemClickListener(new ProductGridViewAdapter.OnProductItemClickListener() {
            @Override
            public void onItemClicked(int row, int column) {
                startActivity(new Intent(ProductSearchActivity.this, ProductDetailActivity.class));
            }
        });
        final Handler handler = new Handler();
        mLvProduct.setLoadMode(IPullView.LoadMode.PULL_TO_LOAD); // 设置为上拉加载更多（默认滑动到底部自动加载）
        mLvProduct.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 刷新数据
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLvProduct.refreshCompleted();
                    }
                }, 3000);
            }
        });
        mLvProduct.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // TODO 加载下一页
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLvProduct.loadMoreCompleted(true);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {

    }

    @Override
    public void onSearchProductResult(QueryProductResp result) {
        mLvProduct.refreshCompleted();
        // FIXME 根据分页数据是否能加载更多
        mLvProduct.loadMoreCompleted(true);
        if(null == result) {

        } else {
            switch (result.getHttpCode()) {
                case HttpStatus.SC_OK:
                    List<ProductBean> products = result.getProduct();
                    if(null == products || products.isEmpty()) {
                        // TODO 错误
                    } else {
                        mAdapter.addAll(products, true);
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

}
