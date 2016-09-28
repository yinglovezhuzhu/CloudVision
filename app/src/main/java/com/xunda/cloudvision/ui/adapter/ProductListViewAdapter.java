package com.xunda.cloudvision.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.ProductBean;


import java.util.ArrayList;
import java.util.List;

/**
 * 产品列表浏览模式适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/22.
 */

public class ProductListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<ProductBean> mData = new ArrayList<>();
    private int mWidth = 200;
    private int mHeight = 430;

    public ProductListViewAdapter(Context context, int width, int height) {
        mContext = context;
        this.mWidth = width;
        this.mHeight = height;
    }

    @Override
    public int getCount() {
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
            viewHolder.tvCurrency = (TextView) view.findViewById(R.id.tv_item_product_list_view_currency);
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            if(null == lp) {
                lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            }
            lp.width = mWidth;
            lp.height = mHeight;
            view.setLayoutParams(lp);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        return view;
    }


    private class ViewHolder {
        private ImageView ivImg;
        private TextView tvDesc;
        private TextView tvPrice;
        private TextView tvCurrency;
    }
}
