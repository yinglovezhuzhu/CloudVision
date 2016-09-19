package com.xunda.cloudvision.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunda.cloudvision.R;

/**
 * 公司主页Tab项
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public class CompanyTabItem extends LinearLayout {

    private TextView mTvText;

    public CompanyTabItem(Context context) {
        super(context);
        initView(context);
    }

    public CompanyTabItem(Context context, int resId) {
        this(context);
        setText(resId);
    }

    public CompanyTabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CompanyTabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(21)
    public CompanyTabItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    /**
     * 设置文字
     * @param text
     */
    public void setText(CharSequence text) {
        mTvText.setText(text);
    }

    /**
     * 设置文字
     * @param resId
     */
    public void setText(int resId) {
        mTvText.setText(resId);
    }

    private void initView(Context context) {
        inflate(context, R.layout.item_company_home_tab_item, this);

        mTvText = (TextView) findViewById(R.id.tv_company_home_tab_text);
    }
}
