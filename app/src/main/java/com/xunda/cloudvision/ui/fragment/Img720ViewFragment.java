package com.xunda.cloudvision.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.widget.ZoomableImageView;
import com.squareup.picasso.Picasso;
import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.R;

/**
 * 720看图Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class Img720ViewFragment extends BaseFragment {

    /**
     * 新建一个实例
     * @param path 图片路径
     * @return Img720ViewFragment实例对象
     */
    public static Img720ViewFragment newInstance(String path) {
        Img720ViewFragment fragment = new Img720ViewFragment();
        Bundle args = new Bundle();
        args.putString(Config.EXTRA_DATA, path);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_720_view_img, container, false);

        initView(contentView);

        return contentView;
    }

    private void initView(View contentView) {
        final ZoomableImageView imageView = (ZoomableImageView) contentView.findViewById(R.id.iv_img_720_view_img);

        Bundle args = getArguments();
        if(null != args && args.containsKey(Config.EXTRA_DATA)) {
            final String path = args.getString(Config.EXTRA_DATA);
            // FIXME 显示图片
        }

        Picasso.with(getActivity())
                .load(R.drawable.bg_activate)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(imageView);
    }
}
