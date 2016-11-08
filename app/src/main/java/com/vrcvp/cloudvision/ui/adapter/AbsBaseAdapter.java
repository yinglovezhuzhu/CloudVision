package com.vrcvp.cloudvision.ui.adapter;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vrcvp.cloudvision.BuildConfig;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.utils.StringUtils;

/**
 * 继承BaseAdapter的适配器抽象类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/23.
 */

public abstract class AbsBaseAdapter extends BaseAdapter {



    /**
     * 加载图片
     * @param path 图片路径
     * @param placeholder 加载占位图
     * @param error 加载错误占位图
     * @param imageView 显示图片的ImageView
     */
    protected void loadImage(Context context, String path, int placeholder, int error, ImageView imageView) {
        if( null == imageView) {
            return;
        }
        if(StringUtils.isEmpty(path)) {
            imageView.setImageResource(placeholder);
            return;
        }
        Picasso.with(context)
                .load(path)
                .placeholder(placeholder)
                .error(error)
                .into(imageView);
    }

    /**
     * 加载图片并且显示
     * @param path 图片地址
     * @param imageView 显示图片的ImageView控件
     */
    protected void loadImage(Context context, String path, ImageView imageView) {
        loadImage(context, path, R.drawable.default_img, R.drawable.default_img, imageView);
    }
}
