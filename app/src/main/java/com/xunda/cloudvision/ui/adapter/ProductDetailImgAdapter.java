package com.xunda.cloudvision.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xunda.cloudvision.ui.fragment.ImageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品详情图片Pager适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/27.
 */

public class ProductDetailImgAdapter extends FragmentStatePagerAdapter {

    private List<String> mData = new ArrayList<>();

    public ProductDetailImgAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addAll(List<String> urls, boolean notifyDataSetChanged) {
        if(null == urls || urls.isEmpty()) {
            return;
        }
        mData.addAll(urls);
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

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(null, 400, 400);
    }

    @Override
    public int getCount() {
//        return mData.size();
        return 20;
    }
}
