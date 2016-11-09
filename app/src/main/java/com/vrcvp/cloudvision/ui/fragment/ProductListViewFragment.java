package com.vrcvp.cloudvision.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.opensource.pullview.IPullView;
import com.opensource.pullview.OnLoadMoreListener;
import com.opensource.pullview.OnRefreshListener;
import com.opensource.pullview.PullListView;
import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.ProductBean;
import com.vrcvp.cloudvision.bean.resp.QueryProductResp;
import com.vrcvp.cloudvision.observer.ProductObserver;
import com.vrcvp.cloudvision.ui.activity.ProductActivity;
import com.vrcvp.cloudvision.ui.activity.ProductDetailActivity;
import com.vrcvp.cloudvision.ui.adapter.ProductListViewAdapter;

/**
 * 产品列表模式Fragment（列表模式）
 * Created by yinglovezhuzhu@gmail.com on 2016/10/15.
 */

public class ProductListViewFragment extends BaseFragment {

    private PullListView mLvProduct;
    private ProductListViewAdapter mAdapter;
    private ProductActivity mAttachedActivity;

    private ProductObserver mProductObserver = new ProductObserver() {
        @Override
        public void onQueryProductResult(boolean isRefresh, boolean hasMore, QueryProductResp result) {
            if(isRefresh) {
                mAdapter.clear(true);
            }
            mAdapter.addAll(result.getData(), true);
            if(null != mLvProduct) {
                mLvProduct.refreshCompleted();
                // 是否可以加载更多根据加载分页结果决定
                mLvProduct.loadMoreCompleted(hasMore);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ProductListViewAdapter(getActivity());

        final Activity activity = getActivity();
        if(activity instanceof ProductActivity) {
            mAttachedActivity = (ProductActivity) activity;
            mAdapter.addAll(mAttachedActivity.getProductData(), true);
            mAttachedActivity.registerProductObserver(mProductObserver);
        } else {
            throw new IllegalStateException("Only attach by " + ProductActivity.class.getName());
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_product_list_view, container, false);

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
        mAttachedActivity.unregisterProductObserver(mProductObserver);
    }

    private void initView(View contentView) {

        mLvProduct = (PullListView) contentView.findViewById(R.id.lv_product_list);
        mLvProduct.setAdapter(mAdapter);
        mLvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ProductBean product = mAdapter.getItem(position);
                if(null == product) {
                    return;
                }
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra(Config.EXTRA_DATA, product);
                startActivity(intent);
            }
        });

        mLvProduct.setLoadMode(IPullView.LoadMode.PULL_TO_LOAD); // 设置为上拉加载更多（默认滑动到底部自动加载）
        mLvProduct.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                mAttachedActivity.refresh();
            }
        });
        mLvProduct.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // 加载下一页
                mAttachedActivity.loadMore();
            }
        });
    }
}
