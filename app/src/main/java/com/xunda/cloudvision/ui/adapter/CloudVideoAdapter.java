package com.xunda.cloudvision.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.ProductBean;
import com.xunda.cloudvision.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 云展视频列表适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class CloudVideoAdapter extends BaseAdapter {

    private Context mContext;
    private List<VideoBean> mData = new ArrayList<>();
    private int mWidth = 700;

    public CloudVideoAdapter(Context context, int width) {
        mContext = context;
        mWidth = width;
    }

    @Override
    public int getCount() {
//        return mData.size();
        return 20;
    }

    @Override
    public VideoBean getItem(int i) {
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
            view = View.inflate(mContext, R.layout.item_cloud_video, null);
            viewHolder.flContainer = (FrameLayout) view.findViewById(R.id.fl_item_cloud_video_img_container);
            viewHolder.ivImg = (ImageView) view.findViewById(R.id.iv_item_cloud_video_img);
            viewHolder.ivPlay = (ImageView) view.findViewById(R.id.iv_item_cloud_video_play);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tv_item_cloud_video_title);
            LayoutParams lp = viewHolder.flContainer.getLayoutParams();
            if(null == lp) {
                lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            }
            lp.height = mWidth * 4 / 7;
            viewHolder.flContainer.setLayoutParams(lp);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        return view;
    }


    private class ViewHolder {
        private FrameLayout flContainer;
        private ImageView ivImg;
        private ImageView ivPlay;
        private TextView tvTitle;
    }
}
