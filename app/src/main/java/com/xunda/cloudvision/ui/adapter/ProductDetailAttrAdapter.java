package com.xunda.cloudvision.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.AttrValueBean;
import com.xunda.cloudvision.ui.widget.NoScrollGridView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 产品详情属性适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/10/13.
 */

public class ProductDetailAttrAdapter extends BaseAdapter {

    private Context mContext;
    private Map<String, List<AttrValueBean>> mData = new HashMap<>();

    public ProductDetailAttrAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * 加入数据
     * @param data
     * @param notifyDataSetChanged
     */
    public void addAll(Collection<AttrValueBean> data, boolean notifyDataSetChanged) {
        if(null == data || data.isEmpty()) {
            return;
        }
        String attrName;
        for (AttrValueBean attr : data) {
            attrName = attr.getAttrName();
            if(mData.containsKey(attrName)) {
                mData.get(attrName).add(attr);
            } else {
                List<AttrValueBean> attrs = new ArrayList<>();
                attrs.add(attr);
                mData.put(attrName, attrs);
            }
        }
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
        return mData.size();
    }

    @Override
    public List<AttrValueBean> getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(null == convertView) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_product_detail_attr, null);
            viewHolder.tvAttrName = (TextView) convertView.findViewById(R.id.tv_item_product_detail_attr_name);
            viewHolder.gvAttrValueName = (NoScrollGridView) convertView.findViewById(R.id.gv_item_product_detail_attr_value);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Object key = mData.keySet().toArray()[position];

        viewHolder.tvAttrName.setText(key.toString());
        ProductDetailAttrItemAdapter adapter = new ProductDetailAttrItemAdapter(mContext);
        viewHolder.gvAttrValueName.setAdapter(adapter);
        adapter.addAll(mData.get(key), true);

        return convertView;
    }


    private static class ViewHolder {
        TextView tvAttrName;
        NoScrollGridView gvAttrValueName;
    }
}
