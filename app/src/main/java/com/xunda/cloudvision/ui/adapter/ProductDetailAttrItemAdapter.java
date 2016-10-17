package com.xunda.cloudvision.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.AttrValueBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 产品详情属性适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/10/13.
 */

public class ProductDetailAttrItemAdapter extends BaseAdapter {

    public static final int POSITION_INVALID = -1;

    private Context mContext;
    private List<Boolean> mChecked = new ArrayList<>();
    private List<AttrValueBean> mData = new ArrayList<>();
    private int mLastCheckedPosition = POSITION_INVALID;

    private OnCheckChangedListener mOnCheckChangedListener;

    public ProductDetailAttrItemAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * 加入数据
     * @param data
     * @param notifyDataSetChanged
     */
    public void addAll(List<AttrValueBean> data, boolean notifyDataSetChanged) {
        if(null == data || data.isEmpty()) {
            return;
        }
        mData.addAll(data);
        for(int i = 0;i < data.size(); i++) {
            mChecked.add(false);
        }
        if(notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public void clear(boolean notifyDataSetChanged) {
        mData.clear();
        mChecked.clear();
        if(notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public int getCheckedPosition() {
        return mLastCheckedPosition;
    }

    public void clearChoice() {
        if(mLastCheckedPosition < mChecked.size()) {
            mChecked.set(mLastCheckedPosition, false);
        }
        mLastCheckedPosition = POSITION_INVALID;
        notifyDataSetChanged();
    }

    public void setOnChechChangedListener(OnCheckChangedListener listener) {
        this.mOnCheckChangedListener = listener;
    }

    @Override
    public int getCount() {
        return mData.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(null == convertView) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_product_detail_attr_item, null);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_item_product_detail_attr_item_value_name);
            viewHolder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked(position);
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final boolean checked = position < mChecked.size() ? mChecked.get(position) : false;
        viewHolder.tvName.setBackgroundResource(checked ?
                R.drawable.shape_bg_item_product_detail_attr_pressed : R.drawable.selector_bg_item_product_detail_attr);
        viewHolder.tvName.setText(getItem(position).getAttrName());
        return convertView;
    }

    private void onItemClicked(int position) {
        if(mLastCheckedPosition == position) {
            final boolean checked = mChecked.get(mLastCheckedPosition);
            mChecked.set(mLastCheckedPosition, !checked);
            mLastCheckedPosition = checked ? POSITION_INVALID : position;
        } else {
            if(POSITION_INVALID != mLastCheckedPosition && position < mChecked.size()) {
                mChecked.set(mLastCheckedPosition, false);
            }
            if(position >= mChecked.size() && position < mData.size()) {
                for(int i = mChecked.size(); i < mData.size(); i++) {
                    mChecked.add(position == i);
                }
            } else {
                mChecked.set(position, true);
            }
            mLastCheckedPosition = position;
        }
        if(null != mOnCheckChangedListener) {
            mOnCheckChangedListener.onCheckChanged(mLastCheckedPosition);
        }
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView tvName;
    }

    public interface OnCheckChangedListener {

        /**
         * 选中状态发生改变
         * @param checkedPosition 选中position，当取值为 {@linkplain #POSITION_INVALID} = -1时，表示没有选中任何项
         */
        void onCheckChanged(int checkedPosition);
    }
}
