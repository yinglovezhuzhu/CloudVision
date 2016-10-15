package com.xunda.cloudvision.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.ui.activity.ProductDetailActivity;

import java.util.Random;

/**
 * 图片Fragment，只显示一张图片
 * Created by yinglovezhuzhu@gmail.com on 2016/9/27.
 */

public class ImageFragment extends BaseFragment {

    /** 参数：图片路径（本地或者网络）-- String **/
    private static final String ARG_PATH = "arg_path";
    /** 参数：图片宽度（显示加载宽度）-- int **/
    private static final String ARG_WIDTH = "arg_width";
    /** 参数：图片高度（显示加载高度）-- int **/
    private static final String ARG_HEIGHT = "arg_height";
    /** 参数：图片在列表种的位置 **/
    private static final String ARG_POSITION = "arg_position";

    public static ImageFragment newInstance(String path, int position, int width, int height) {
        final ImageFragment fragment = new ImageFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_PATH, path);
        args.putInt(ARG_POSITION, position);
        args.putInt(ARG_WIDTH, width);
        args.putInt(ARG_HEIGHT, height);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_image, container, false);

        initView(contentView);

        return contentView;
    }

    private void initView(View contentView) {

        final Bundle args = getArguments();
        final ImageView imageView = (ImageView) contentView.findViewById(R.id.iv_image_fragment_img);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null == args) {
                    return;
                }
                Activity activity = getActivity();
                if(activity instanceof ProductDetailActivity) {
                    final int position = args.getInt(ARG_POSITION, 0);
                    ((ProductDetailActivity) activity).onPicClicked(position);
                }
            }
        });

        switch (new Random().nextInt(4)) {
            case 0:
                imageView.setImageResource(R.drawable.img_product1);
                break;
            case 1:
                imageView.setImageResource(R.drawable.img_product2);
                break;
            case 2:
                imageView.setImageResource(R.drawable.img_product3);
                break;
            case 3:
                imageView.setImageResource(R.drawable.img_product4);
                break;
            default:
                break;
        }
    }


}
