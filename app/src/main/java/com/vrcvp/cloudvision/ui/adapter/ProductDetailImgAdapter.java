package com.vrcvp.cloudvision.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vrcvp.cloudvision.ui.fragment.ImageFragment;
import com.vrcvp.cloudvision.utils.StringUtils;

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

    public void addAll(List<String> images, boolean notifyDataSetChanged) {
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
        final String img = mData.get(position);
        if(StringUtils.isEmpty(img)) {
            return null;
        }
        return ImageFragment.newInstance(img, position, 400, 400);
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
