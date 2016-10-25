package com.vrcvp.cloudvision.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 错误页面提示
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public class ErrorPage extends LinearLayout {

    public ErrorPage(Context context) {
        super(context);
    }

    public ErrorPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ErrorPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public ErrorPage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
