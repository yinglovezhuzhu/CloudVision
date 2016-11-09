package com.vrcvp.cloudvision.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vrcvp.cloudvision.R;

/**
 * 错误页面提示
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public class TipPageView extends LinearLayout {

    private ImageView mIvIcon;
    private TextView mTvMessage;
    private TextView mTvMemo;

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

    /**
     * 设置消息
     * @param iconResId 图标资源id
     * @param textResId 文字资源id
     * @param textColorResId 文字颜色资源id
     */
    public void setTips(int iconResId, int textResId, int textColorResId) {
        mIvIcon.setImageResource(iconResId);
        mTvMessage.setText(textResId);
        mTvMessage.setTextColor(getResourceColor(textColorResId));
        mTvMemo.setVisibility(View.INVISIBLE);
        setOnClickListener(null);
    }

    /**
     * 设置消息
     * @param iconResId 图标资源id
     * @param textResId 文字资源id
     * @param textColorResId 文字颜色资源id
     * @param memeResId 备注文字资源id
     * @param listener 页面点击监听
     */
    public void setTips(int iconResId, int textResId, int textColorResId, int memeResId,
                        View.OnClickListener listener) {
        mIvIcon.setImageResource(iconResId);
        mTvMessage.setText(textResId);
        mTvMessage.setTextColor(getResourceColor(textColorResId));
        mTvMemo.setVisibility(View.VISIBLE);
        mTvMemo.setText(memeResId);
        this.setOnClickListener(listener);
    }

    private void initView(Context context) {
        inflate(context, R.layout.layout_tip_page, this);

        mIvIcon = (ImageView) findViewById(R.id.iv_tip_page_icon);
        mTvMessage = (TextView) findViewById(R.id.tv_tip_page_msg);
        mTvMemo = (TextView) findViewById(R.id.tv_tip_page_memo);

        this.setClickable(true);
    }

    private int getResourceColor(int colorResId) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return getResources().getColor(colorResId);
        } else {
            try {
                return getResources().getColor(colorResId, null);
            } catch (NoSuchMethodError e) {
                return getResources().getColor(colorResId);
            }
        }
    }
}
