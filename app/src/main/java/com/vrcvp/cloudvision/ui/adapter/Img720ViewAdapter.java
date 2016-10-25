package com.vrcvp.cloudvision.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vrcvp.cloudvision.bean.ImageBean;
import com.vrcvp.cloudvision.ui.fragment.Img720ViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐产品ViewPager适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/20.
 */
public class Img720ViewAdapter extends FragmentStatePagerAdapter {

    private List<ImageBean> mData = new ArrayList<>();

    public Img720ViewAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addAll(List<ImageBean> data, boolean notifyDataSetChanged) {
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


    public ImageBean getItemData(int position) {
        return mData.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return Img720ViewFragment.newInstance(getItemData(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }
}
