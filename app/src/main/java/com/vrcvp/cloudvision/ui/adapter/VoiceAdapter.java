package com.vrcvp.cloudvision.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private OnSubItemClickListener mSubItemClickListener;

    public VoiceAdapter(Context context) {
        this.mContext = context;
    }

    public void setOnSubItemClickListener(OnSubItemClickListener listener) {
        this.mSubItemClickListener = listener;
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

    /**
     * 清除所有数据
     * @param keepFirst 是否保留第一个
     * @param notifyDataSetChanged 是否刷新页面
     */
    public void clear(boolean keepFirst, boolean notifyDataSetChanged) {
        VoiceBean first = null;
        if(keepFirst && !mData.isEmpty()) {
            first = mData.get(0);
        }
        mData.clear();
        if(null != first) {
            mData.add(first);
        }
        if(notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    /**
     * 更新最后一个机器人类型的数据
     * @param text 显示文字
     * @param notifyDataSetChanged 是否刷新页面
     */
    public void updateLastAndroid(String text, boolean notifyDataSetChanged) {
        if(mData.isEmpty()) {
            return;
        }
        VoiceBean last = mData.get(mData.size() - 1);
        if(VoiceBean.TYPE_ANDROID == last.getType()) {
            last.setText(text);
            if(notifyDataSetChanged) {
                notifyDataSetChanged();
            }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        VoiceItemView itemView;
        if(null == convertView) {
            itemView = new VoiceItemView(mContext);
            convertView = itemView;
        } else {
            itemView = (VoiceItemView) convertView;
        }
        final VoiceBean data = getItem(position);
        itemView.setData(data);
        if(VoiceBean.TYPE_SEARCH_RESULT == data.getType()) {
            itemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int subPosition, long id) {
                    if(null != mSubItemClickListener) {
                        mSubItemClickListener.onSubItemClickListener(position, subPosition);
                    }
                }
            });
        }
        return convertView;
    }

    public interface OnSubItemClickListener {
        void onSubItemClickListener(int position, int subPosition);
    }
}
