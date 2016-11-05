package com.vrcvp.cloudvision.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.vrcvp.cloudvision.R;

/**
 * 错误页面提示
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public class ErrorPage extends LinearLayout {

    public ErrorPage(Context context) {
        super(context);
        initView(context);
    }

    public ErrorPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ErrorPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(21)
    public ErrorPage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.layout_error_page, this);


    }
}
