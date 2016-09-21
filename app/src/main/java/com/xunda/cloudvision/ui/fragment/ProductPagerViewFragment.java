package com.xunda.cloudvision.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.ui.adapter.ProductPagerViewAdapter;

/**
 * 产品浏览模式Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public class ProductPagerViewFragment extends BaseFragment {

    private ProductPagerViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_product_pager_view, container, false);

        initView(contentView);

        return contentView;
    }

    private void initView(View contentView) {
        final ViewPager viewPager = (ViewPager) contentView.findViewById(R.id.vp_product_pager_view);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.contentPadding_level0));
        mAdapter = new ProductPagerViewAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
    }
}
