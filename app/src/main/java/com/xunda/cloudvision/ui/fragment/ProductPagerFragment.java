package com.xunda.cloudvision.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.ProductBean;
import com.xunda.cloudvision.ui.activity.ProductDetailActivity;

import java.util.Random;

/**
 * 产品浏览模式信息Pager页面Fragment
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
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击事件
                startActivity(new Intent(getActivity(), ProductDetailActivity.class));
            }
        });
        contentView.requestFocus();
        initView(contentView);
        return contentView;
    }

    private void initView(View contentView) {

        final ImageView ivImg = (ImageView) contentView.findViewById(R.id.iv_product_pager_img);



        final TextView tvDesc = (TextView) contentView.findViewById(R.id.tv_product_pager_desc);

        final TextView tvCurrency = (TextView) contentView.findViewById(R.id.tv_product_pager_currency);

        final TextView tvPrice = (TextView) contentView.findViewById(R.id.tv_product_pager_price);

        switch (new Random().nextInt(4)) {
            case 0:
                ivImg.setImageResource(R.drawable.img_product1);
                tvPrice.setText("100");
                break;
            case 1:
                ivImg.setImageResource(R.drawable.img_product2);
                tvPrice.setText("120");
                break;
            case 2:
                ivImg.setImageResource(R.drawable.img_product3);
                tvPrice.setText("150");
                break;
            case 3:
                ivImg.setImageResource(R.drawable.img_product4);
                tvPrice.setText("200");
                break;
            default:
                break;
        }
    }
}
