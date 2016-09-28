package com.xunda.cloudvision.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.ProductBean;
import com.xunda.cloudvision.ui.activity.ProductDetailActivity;

/**
 * 推荐产品信息Pager页面Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/9/20.
 */
public class RecommendedProductPagerFragment extends BaseFragment {

    public static RecommendedProductPagerFragment newInstance(ProductBean product) {
        RecommendedProductPagerFragment fragment = new RecommendedProductPagerFragment();
        if(null != product) {
            Bundle args = new Bundle();
            args.putParcelable(Config.EXTRA_DATA, product);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_recommended_product_pager, container, false);
        initView(contentView);
        return contentView;
    }

    private void initView(View contentView) {
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击事件
                startActivity(new Intent(getActivity(), ProductDetailActivity.class));
            }
        });
    }
}
