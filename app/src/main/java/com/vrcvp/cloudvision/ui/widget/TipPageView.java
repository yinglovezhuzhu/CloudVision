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
public class TipPageView extends LinearLayout {

    public TipPageView(Context context) {
        super(context);
        initView(context);
    }

    public TipPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TipPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(21)
    public TipPageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.layout_tip_page, this);


    }
}
