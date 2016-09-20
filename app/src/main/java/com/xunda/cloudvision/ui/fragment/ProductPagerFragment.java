package com.xunda.cloudvision.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.ProductBean;

/**
 * 推荐产品信息Pager页面Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/9/20.
 */
public class ProductPagerFragment extends BaseFragment {

    public static ProductPagerFragment newInstance(ProductBean product) {
        ProductPagerFragment fragment = new ProductPagerFragment();
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
        View contentView = inflater.inflate(R.layout.fragment_product_pager, container, false);
        return contentView;
    }
}
