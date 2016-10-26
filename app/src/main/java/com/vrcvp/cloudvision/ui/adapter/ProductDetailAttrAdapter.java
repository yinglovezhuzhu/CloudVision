package com.vrcvp.cloudvision.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.AttrBean;
import com.vrcvp.cloudvision.bean.AttrValueBean;
import com.vrcvp.cloudvision.ui.widget.NoScrollGridView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品详情属性适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/10/13.
 */

public class ProductDetailAttrAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<AttrBean> mData = new ArrayList<>();
    private final Map<String, ProductDetailAttrItemAdapter> mAdapters = new HashMap<>();
    private final String mLabelFormat;

    private OnAttrCheckChangedListener mOnAttrCheckChangedListener = null;

    public ProductDetailAttrAdapter(Context context) {
        this.mContext = context;
        this.mLabelFormat = context.getResources().getString(R.string.str_label_format);
    }

    /**
     * 加入数据
     * @param data 属性数据
     * @param notifyDataSetChanged 是否更新UI
     */
    public void add(AttrBean data, boolean notifyDataSetChanged) {
        if(null == data) {
            return;
        }
        mData.add(data);
        if(notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    /**
     * 加入数据
     * @param data 属性数据
     * @param notifyDataSetChanged 是否更新UI
     */
    public void addAll(Collection<AttrBean> data, boolean notifyDataSetChanged) {
        if(null == data || data.isEmpty()) {
            return;
        }
        mData.addAll(data);
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

    public void setOnAttrCheckChangedListener(OnAttrCheckChangedListener listener) {
        this.mOnAttrCheckChangedListener = listener;
    }

    /**
     * 获取某个属性选中的值索引
     * @param position 属性索引
     * @return 选中的值索引， -1表示没有选中任何值
     */
    public int getCheckedPosition(int position) {
        if(mData.isEmpty() || position >= mData.size()) {
            return -1;
        }
        return mAdapters.get(mData.get(position).getAttrName()).getCheckedPosition();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public AttrBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null == convertView) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_product_detail_attr, null);
            viewHolder.tvAttrName = (TextView) convertView.findViewById(R.id.tv_item_product_detail_attr_name);
            viewHolder.gvAttrValueName = (NoScrollGridView) convertView.findViewById(R.id.gv_item_product_detail_attr_value);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final AttrBean attr = getItem(position);
        final String attrName = attr.getAttrName();


        viewHolder.tvAttrName.setText(String.format(mLabelFormat, attrName));

        final ProductDetailAttrItemAdapter adapter;
        if(mAdapters.containsKey(attr.getAttrName())) {
            adapter = mAdapters.get(attrName);
        } else {
            adapter = new ProductDetailAttrItemAdapter(mContext);
            adapter.addAll(attr.getValues(), true);
            adapter.setOnCheckChangedListener(new ProductDetailAttrItemAdapter.OnCheckChangedListener() {
                @Override
                public void onCheckChanged(int subPosition, boolean isChecked) {
                    if(null != mOnAttrCheckChangedListener) {
                        mOnAttrCheckChangedListener.onAttrCheckChanged(position, subPosition, isChecked);
                    }
                }
            });
            mAdapters.put(attrName, adapter);
        }
        viewHolder.gvAttrValueName.setAdapter(adapter);

        return convertView;
    }


    private static class ViewHolder {
        TextView tvAttrName;
        NoScrollGridView gvAttrValueName;
    }

    public interface OnAttrCheckChangedListener {
        /**
         * 属性选中状态发生变化
         * @param position 属性索引
         * @param subPosition 属性值索引
         * @param isChecked 是否选中，true 选中， false没选中
         */
        void onAttrCheckChanged(int position, int subPosition, boolean isChecked);
    }
}
