package com.xunda.cloudvision.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Fragment基类，包含一些公共方法
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public class BaseFragment extends Fragment implements View.OnClickListener {


    @Override
    public void onClick(View v) {

    }

    /**
     * 显示一个短Toast
     * @param text
     */
    protected void showShortToast(CharSequence text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个短Toast
     * @param resId
     */
    protected void showShortToast(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长Toast
     * @param text
     */
    protected void showLongToast(CharSequence text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长Toast
     * @param resId
     */
    protected void showLongToast(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载图片
     * @param imageView 显示图片的ImageView
     * @param path 图片路径
     * @param width 目标显示宽度
     * @param height 目标显示高度
     * @param placeholder 加载占位图
     * @param error 加载错误占位图
     */
    protected void loadImage(ImageView imageView, String path, int width, int height,
                             int placeholder, int error) {
        Picasso.with(getActivity())
                .load(path)
                .resize(width, height)
                .placeholder(placeholder)
                .error(error)
                .into(imageView);
    }
}
