package com.xunda.cloudvision.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xunda.cloudvision.bean.ProductBean;
import com.xunda.cloudvision.ui.fragment.Img720ViewFragment;
import com.xunda.cloudvision.ui.fragment.ProductPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐产品ViewPager适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/20.
 */
public class Img720ViewAdapter extends FragmentStatePagerAdapter {

    private List<String> mData = new ArrayList<>();

    public Img720ViewAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addAll(List<String> data, boolean notifyDataSetChanged) {
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


    public String getItemData(int position) {
        return mData.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return Img720ViewFragment.newInstance(null);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
//        return mData.size();
        return 5;
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
//        return 0.8f;
    }
}
