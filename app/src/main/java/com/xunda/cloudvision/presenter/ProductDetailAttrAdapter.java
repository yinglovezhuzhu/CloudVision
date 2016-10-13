package com.xunda.cloudvision.presenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.AttrValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品详情属性适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/10/13.
 */

public class ProductDetailAttrAdapter extends BaseAdapter {

    private Context mContext;
    private List<AttrValueBean> mData = new ArrayList<>();

    public ProductDetailAttrAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 3;
//        return mData.size();
    }

    @Override
    public AttrValueBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(null == convertView) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_product_detail_attr, null);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_item_product_detail_attr_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView tvName;
    }
}
