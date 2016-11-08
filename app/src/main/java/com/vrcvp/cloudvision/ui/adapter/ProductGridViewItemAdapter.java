package com.vrcvp.cloudvision.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.ProductBean;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 产品列表浏览模式适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/22.
 */

class ProductGridViewItemAdapter extends AbsBaseAdapter {

    private final Context mContext;
    private final List<ProductBean> mData = new ArrayList<>();
    private int mWidth = 200;
    private int mHeight = 430;
    private int mNumColumns = 2;
    private final String mPriceFormat;

    ProductGridViewItemAdapter(Context context, int width, int height,
                               int numColumns, ProductBean... products) {
        mContext = context;
        this.mWidth = width;
        this.mHeight = height;
        this.mNumColumns = numColumns;
        this.mPriceFormat = mContext.getResources().getString(R.string.str_price_format_with_currency);
        if(null != products) {
            for( ProductBean product : products) {
                if(null == product) {
                    continue;
                }
                this.mData.add(product);
            }
        }
    }

    public void addAll(Collection<ProductBean> products, boolean notifyDataSetChanged) {
        if(null == products || products.isEmpty()) {
            return;
        }
        mData.addAll(products);
        if(notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public void clear(boolean notifyDataSetChanged) {
        mData.clear();
        if(notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mData.size() < mNumColumns ? mData.size() : mNumColumns;
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
            view = View.inflate(mContext, R.layout.item_product_grid_view_item, null);
            viewHolder.ivImg = (ImageView) view.findViewById(R.id.iv_item_product_grid_view_img);
            viewHolder.tvDesc = (TextView) view.findViewById(R.id.tv_item_product_grid_view_desc);
            viewHolder.tvPrice = (TextView) view.findViewById(R.id.tv_item_product_grid_view_price);
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

        final ProductBean product = getItem(i);

        loadImage(mContext, product.getImageUrl(), R.drawable.default_img2, R.drawable.default_img2, viewHolder.ivImg);
        viewHolder.tvDesc.setText(product.getName());
        viewHolder.tvPrice.setText(String.format(mPriceFormat, product.getPrice()));

        return view;
    }


    private class ViewHolder {
        ImageView ivImg;
        TextView tvDesc;
        TextView tvPrice;
    }
}
