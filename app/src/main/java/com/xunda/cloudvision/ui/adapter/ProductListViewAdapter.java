package com.xunda.cloudvision.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.ProductBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 产品列表浏览模式适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/22.
 */

public class ProductListViewAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<ProductBean> mData = new ArrayList<>();
    private final String mPriceFormat;

    public ProductListViewAdapter(Context context) {
        this.mContext = context;
        this.mPriceFormat = mContext.getResources().getString(R.string.str_price_format_with_currency);
    }

    @Override
    public int getCount() {
        // FIXME 真是数据的时候改为注释部分
//        return mData.size();
        return 20;
    }

    @Override
    public ProductBean getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(null == view) {
            viewHolder = new ViewHolder();
            view = View.inflate(mContext, R.layout.item_product_list_view, null);
            viewHolder.ivImg = (ImageView) view.findViewById(R.id.iv_item_product_list_view_img);
            viewHolder.tvDesc = (TextView) view.findViewById(R.id.tv_item_product_list_view_desc);
            viewHolder.tvPrice = (TextView) view.findViewById(R.id.tv_item_product_list_view_price);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        switch (new Random().nextInt(4)) {
            case 0:
                viewHolder.ivImg.setImageResource(R.drawable.img_product1);
                viewHolder.tvPrice.setText(String.format(mPriceFormat, "100"));
                break;
            case 1:
                viewHolder.ivImg.setImageResource(R.drawable.img_product2);
                viewHolder.tvPrice.setText(String.format(mPriceFormat, "120"));
                break;
            case 2:
                viewHolder.ivImg.setImageResource(R.drawable.img_product3);
                viewHolder.tvPrice.setText(String.format(mPriceFormat, "150"));
                break;
            case 3:
                viewHolder.ivImg.setImageResource(R.drawable.img_product4);
                viewHolder.tvPrice.setText(String.format(mPriceFormat, "200"));
                break;
            default:
                break;
        }
        return view;
    }


    private class ViewHolder {
        ImageView ivImg;
        TextView tvDesc;
        TextView tvPrice;
    }
}
