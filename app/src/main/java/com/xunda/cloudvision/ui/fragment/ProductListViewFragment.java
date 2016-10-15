package com.xunda.cloudvision.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.opensource.pullview.IPullView;
import com.opensource.pullview.OnLoadMoreListener;
import com.opensource.pullview.OnRefreshListener;
import com.opensource.pullview.PullListView;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.ui.activity.ProductDetailActivity;
import com.xunda.cloudvision.ui.adapter.ProductListViewAdapter;

/**
 * 产品列表模式Fragment（浏览模式）
 * Created by yinglovezhuzhu@gmail.com on 2016/10/15.
 */

public class ProductListViewFragment extends BaseFragment {

    public ProductListViewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ProductListViewAdapter(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_product_list_view, container, false);

        initView(contentView);

        return contentView;
    }

    private void initView(View contentView) {

        final PullListView lvProduct = (PullListView) contentView.findViewById(R.id.lv_product_list);
        lvProduct.setAdapter(mAdapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), ProductDetailActivity.class));
            }
        });
        final Handler handler = new Handler();
        lvProduct.setLoadMode(IPullView.LoadMode.PULL_TO_LOAD); // 设置为上拉加载更多（默认滑动到底部自动加载）
        lvProduct.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 刷新数据
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvProduct.refreshCompleted();
                    }
                }, 3000);
            }
        });
        lvProduct.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // TODO 加载下一页
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvProduct.loadMoreCompleted(true);
                    }
                }, 3000);
            }
        });
    }
}
