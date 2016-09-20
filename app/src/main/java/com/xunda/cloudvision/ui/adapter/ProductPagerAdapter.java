package com.xunda.cloudvision.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xunda.cloudvision.bean.ProductBean;
import com.xunda.cloudvision.ui.fragment.ProductPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐产品ViewPager适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/20.
 */
public class ProductPagerAdapter extends FragmentStatePagerAdapter {

    private List<ProductBean> mData = new ArrayList<>();

    public ProductPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addAll(List<ProductBean> banners, boolean notifyDataSetChanged) {
        if(null == banners || banners.isEmpty()) {
            return;
        }
        mData.addAll(banners);
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
//        return ProductPagerFragment.newInstance(getItemData(position));
        return ProductPagerFragment.newInstance(null);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
//        return mData.size();
        return 20;
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
//        return 0.8f;
    }
}
