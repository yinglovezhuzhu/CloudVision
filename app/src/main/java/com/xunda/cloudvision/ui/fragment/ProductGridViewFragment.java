package com.xunda.cloudvision.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.pullview.IPullView;
import com.opensource.pullview.OnLoadMoreListener;
import com.opensource.pullview.OnRefreshListener;
import com.opensource.pullview.PullListView;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.observer.ProductObserver;
import com.xunda.cloudvision.ui.activity.ProductActivity;
import com.xunda.cloudvision.ui.activity.ProductDetailActivity;
import com.xunda.cloudvision.ui.adapter.ProductGridViewAdapter;

/**
 * 产品网格模式Fragment（列表模式）
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public class ProductGridViewFragment extends BaseFragment {

    private PullListView mLvProduct;
    private ProductGridViewAdapter mAdapter;
    private ProductObserver mProductObserver = new ProductObserver() {
        @Override
        public void onQueryProductResult(boolean isRefresh, QueryProductResp result) {
            if(null != mLvProduct) {
                mLvProduct.refreshCompleted();
                // FIXME 是否可以加载更多根据加载分页结果决定
                mLvProduct.loadMoreCompleted(true);
            }
            if(isRefresh) {
                mAdapter.clear(true);
            }
            mAdapter.addAll(result.getProduct(), true);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = getResources().getDisplayMetrics().widthPixels / 2;
        int height = width * 4 / 3;
        mAdapter = new ProductGridViewAdapter(getActivity(), width, height, 2, 0);

        mAdapter.setOnProductItemClickListener(new ProductGridViewAdapter.OnProductItemClickListener() {
            @Override
            public void onItemClicked(int row, int column) {
                showShortToast("position-----" + row + " <> " + column);
                startActivity(new Intent(getActivity(), ProductDetailActivity.class));
            }
        });

        final Activity activity = getActivity();
        if(activity instanceof ProductActivity) {
            mAdapter.addAll(((ProductActivity) activity).getProductData(), true);
            ((ProductActivity) activity).registerProductObserver(mProductObserver);
        } else {
            throw new IllegalStateException("Only attach by " + ProductActivity.class.getName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_product_grid_view, container, false);

        initView(contentView);

        return contentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLvProduct = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        final Activity activity = getActivity();
        if(activity instanceof ProductActivity) {
            ((ProductActivity) activity).unregisterProductObserver(mProductObserver);
        } else {
            throw new IllegalStateException("Only attach by " + ProductActivity.class.getName());
        }
    }

    private void initView(View contentView) {

        mLvProduct = (PullListView) contentView.findViewById(R.id.lv_product_grid);
        mLvProduct.setAdapter(mAdapter);
        final Handler handler = new Handler();
        mLvProduct.setLoadMode(IPullView.LoadMode.PULL_TO_LOAD); // 设置为上拉加载更多（默认滑动到底部自动加载）
        mLvProduct.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 刷新数据
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Activity activity = getActivity();
                        if(activity instanceof ProductActivity) {
                            ((ProductActivity) activity).refresh();
                        } else {
                            throw new IllegalStateException("Only attach by " + ProductActivity.class.getName());
                        }
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
                        final Activity activity = getActivity();
                        if(activity instanceof ProductActivity) {
                            ((ProductActivity) activity).loadMore();
                        } else {
                            throw new IllegalStateException("Only attach by " + ProductActivity.class.getName());
                        }
                    }
                }, 3000);
            }
        });
    }

}
