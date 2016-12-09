package com.vrcvp.cloudvision.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.opensource.pullview.IPullView;
import com.opensource.pullview.OnLoadMoreListener;
import com.opensource.pullview.OnRefreshListener;
import com.opensource.pullview.PullListView;
import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.ProductBean;
import com.vrcvp.cloudvision.bean.resp.QueryProductResp;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.presenter.ProductSearchPresenter;
import com.vrcvp.cloudvision.ui.adapter.ProductGridViewAdapter;
import com.vrcvp.cloudvision.ui.widget.TipPageView;
import com.vrcvp.cloudvision.view.IProductSearchView;

import java.util.List;

/**
 * 产品搜索Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/28.
 */

public class ProductSearchActivity extends BaseActivity implements IProductSearchView {

    private ProductSearchPresenter mProductSearchPresenter;

    private EditText mEtKeyword;
    private TipPageView mTipPageView;

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
    protected void onDestroy() {
        mProductSearchPresenter.onDestroy();
        cancelLoadingDialog();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_product_search_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.btn_product_search:
                search();
                break;
            case R.id.tpv_product_search:
                search();
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
        cancelLoadingDialog();
        showShortToast(R.string.str_input_keyword);
    }

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {
        cancelLoadingDialog();
    }

    @Override
    public void onSearchProductResult(QueryProductResp result) {
        mLvProduct.refreshCompleted();
        // 根据分页数据是否能加载更多
        mLvProduct.loadMoreCompleted(mProductSearchPresenter.hasMore());
        if(null == result) {
            // 错误
            if(mProductSearchPresenter.isLoadMore()) {
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
                        if(mProductSearchPresenter.isLoadMore()) {
                            showShortToast(R.string.str_no_more_data);
                        } else {
                            mTipPageView.setTips(R.drawable.ic_no_data, R.string.str_no_data,
                                    R.color.colorTextOrange);
                            mTipPageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mAdapter.addAll(products, true);
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
                    if(mProductSearchPresenter.isLoadMore()) {
                        showShortToast(R.string.str_network_error);
                    } else {
                        mTipPageView.setTips(R.drawable.ic_network_error, R.string.str_network_error,
                                R.color.colorTextGray, R.string.str_touch_to_refresh, this);
                        mTipPageView.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
        cancelLoadingDialog();
    }

    private void initView() {
        mEtKeyword = (EditText) findViewById(R.id.et_product_search_keyword);
        mTipPageView = (TipPageView) findViewById(R.id.tpv_product_search);
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
                final ProductBean product = mAdapter.getData(row, column);
                if(null == product) {
                    return;
                }
                Intent intent = new Intent(ProductSearchActivity.this, ProductDetailActivity.class);
                intent.putExtra(Config.EXTRA_DATA, product);
                startActivity(intent);
            }
        });
        mLvProduct.setLoadMode(IPullView.LoadMode.PULL_TO_LOAD); // 设置为上拉加载更多（默认滑动到底部自动加载）
        mLvProduct.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                mAdapter.clear(true);
                mProductSearchPresenter.search();
            }
        });
        mLvProduct.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // 加载下一页
                mProductSearchPresenter.nextPage();
            }
        });
        mEtKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(EditorInfo.IME_ACTION_SEARCH == actionId) {
                    hideSoftInputFromWindow(mEtKeyword);
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 搜索
     */
    private void search() {
        hideSoftInputFromWindow(mEtKeyword);
        showLoadingDialog(null, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mProductSearchPresenter.cancelLoadDataTask();
            }
        });
        mAdapter.clear(true);
        mProductSearchPresenter.search();
    }

}
