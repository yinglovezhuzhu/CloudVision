package com.vrcvp.cloudvision.ui.adapter;

import android.content.Context;
import android.speech.tts.Voice;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.vrcvp.cloudvision.bean.VoiceBean;
import com.vrcvp.cloudvision.bean.VoiceSearchResultBean;
import com.vrcvp.cloudvision.ui.widget.VoiceItemView;
import com.vrcvp.cloudvision.ui.widget.VoiceSearchResultItemView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 语音搜索结果数据列表适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/18.
 */
public class VoiceSearchResultAdapter extends BaseAdapter {

    private Context mContext;
    private List<VoiceSearchResultBean> mData = new ArrayList<>();
    private AdapterView.OnItemClickListener mItemClickListener;

    public VoiceSearchResultAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * 增加一个数据
     * @param bean 数据
     * @param notifyDataSetChanged 是否刷新页面
     */
    public void add(VoiceSearchResultBean bean, boolean notifyDataSetChanged) {
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
    public void addAll(Collection<VoiceSearchResultBean> beans, boolean notifyDataSetChanged) {
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
    public VoiceSearchResultBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VoiceSearchResultItemView itemView;
        if(null == convertView) {
            itemView = new VoiceSearchResultItemView(mContext);
            convertView = itemView;
        } else {
            itemView = (VoiceSearchResultItemView) convertView;
        }
        itemView.setData(getItem(position));
        return convertView;
    }
}
