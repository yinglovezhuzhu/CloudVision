package com.vrcvp.cloudvision.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.vrcvp.cloudvision.bean.VoiceBean;
import com.vrcvp.cloudvision.ui.widget.VoiceItemView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 语音对话内容适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/18.
 */
public class VoiceAdapter extends BaseAdapter {

    private Context mContext;
    private List<VoiceBean> mData = new ArrayList<>();

    public VoiceAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * 增加一个数据
     * @param bean 数据
     * @param notifyDataSetChanged 是否刷新页面
     */
    public void add(VoiceBean bean, boolean notifyDataSetChanged) {
        if(null == bean) {
            return;
        }
        mData.add(bean);
        if(notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    /**
     * 增加数据
     * @param beans 数据
     * @param notifyDataSetChanged 是否刷新页面
     */
    public void addAll(Collection<VoiceBean> beans, boolean notifyDataSetChanged) {
        if(null == beans || beans.isEmpty()) {
            return;
        }
        mData.addAll(beans);
        if(notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    /**
     * 清除所有数据
     * @param notifyDataSetChanged 是否刷新页面
     */
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
    public VoiceBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VoiceItemView itemView = null;
        if(null == convertView) {
            itemView = new VoiceItemView(mContext);
            convertView = itemView;
        } else {
            itemView = (VoiceItemView) convertView;
        }
        itemView.setData(getItem(position));
        return convertView;
    }
}
