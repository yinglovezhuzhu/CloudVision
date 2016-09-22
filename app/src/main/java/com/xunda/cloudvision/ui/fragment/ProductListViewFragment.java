package com.xunda.cloudvision.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xunda.cloudvision.R;

/**
 * 产品列表模式Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public class ProductListViewFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_product_list_view, container, false);

        initView(contentView);

        return contentView;
    }

    private void initView(View contentView) {

    }

}
