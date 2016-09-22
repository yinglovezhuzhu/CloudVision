package com.xunda.cloudvision.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.ui.adapter.ProductListViewAdapter;

/**
 * 产品列表模式Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public class ProductListViewFragment extends BaseFragment {

    private ProductListViewAdapter mAdapter;

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

        final GridView gvProduct = (GridView) contentView.findViewById(R.id.gv_product_list_view);
        gvProduct.setAdapter(mAdapter);

    }

}
