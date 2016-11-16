package com.vrcvp.cloudvision.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vrcvp.cloudvision.BuildConfig;
import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.ui.activity.VideoActivity;
import com.vrcvp.cloudvision.ui.activity.VideoPlayerActivity;
import com.vrcvp.cloudvision.ui.activity.WebViewActivity;
import com.vrcvp.cloudvision.utils.StringUtils;

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
     * @param path 图片路径
     * @param placeholder 加载占位图
     * @param error 加载错误占位图
     * @param imageView 显示图片的ImageView
     */
    protected void loadImage(String path, int placeholder, int error, ImageView imageView) {
        if( null == imageView) {
            return;
        }
        if(StringUtils.isEmpty(path)) {
            imageView.setImageResource(placeholder);
            return;
        }
        Picasso.with(getActivity())
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
    protected void loadImage(String path, ImageView imageView) {
        loadImage(path, R.drawable.default_img, R.drawable.default_img, imageView);
    }

    /**
     * 打开网页
     * @param url
     */
    protected void openWebView(String url) {
        if(StringUtils.isEmpty(url)) {
            return;
        }
        Intent i = new Intent(getActivity(), WebViewActivity.class);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    /**
     * 播放视频
     * @param videoUrl 视频url地址
     * @param name 视频名称
     */
    protected void playVideo(String videoUrl, String name) {
        if(StringUtils.isEmpty(videoUrl)) {
            return;
        }
        Intent i = new Intent(getActivity(), VideoPlayerActivity.class);
        i.setData(Uri.parse(videoUrl));
        i.putExtra(Config.EXTRA_TITLE_STR, name);
        startActivity(i);
    }
}
