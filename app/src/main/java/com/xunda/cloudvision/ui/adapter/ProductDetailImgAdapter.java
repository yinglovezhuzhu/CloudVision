package com.xunda.cloudvision.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xunda.cloudvision.bean.ImageBean;
import com.xunda.cloudvision.bean.ProductBean;
import com.xunda.cloudvision.ui.fragment.ImageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品详情图片Pager适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/27.
 */

public class ProductDetailImgAdapter extends FragmentStatePagerAdapter {

    private List<ImageBean> mData = new ArrayList<>();

    public ProductDetailImgAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addAll(List<ImageBean> images, boolean notifyDataSetChanged) {
        if(null == images || images.isEmpty()) {
            return;
        }
        mData.addAll(images);
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
        final ImageBean img = mData.get(position);
        if(null == img) {
            return null;
        }
        return ImageFragment.newInstance(img.getImgUrl(), position, 400, 400);
//        return ImageFragment.newInstance(null, position, 400, 400);
    }

    @Override
    public int getCount() {
        return mData.size();
//        return 20;
    }
}
