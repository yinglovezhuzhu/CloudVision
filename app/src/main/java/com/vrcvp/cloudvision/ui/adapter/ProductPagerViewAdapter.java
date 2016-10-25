package com.vrcvp.cloudvision.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vrcvp.cloudvision.bean.ProductBean;
import com.vrcvp.cloudvision.ui.fragment.ProductPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐产品ViewPager适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/20.
 */
public class ProductPagerViewAdapter extends FragmentStatePagerAdapter {

    private final List<ProductBean> mData = new ArrayList<>();

    public ProductPagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addAll(List<ProductBean> data, boolean notifyDataSetChanged) {
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


    public ProductBean getItemData(int position) {
        return mData.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return ProductPagerFragment.newInstance(getItemData(position));
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
