package com.vrcvp.cloudvision.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
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
     * @param iconResId
     * @param textResId
     * @param textColorResId
     */
    public void setTips(int iconResId, int textResId, int textColorResId) {
        mIvIcon.setImageResource(iconResId);
        mTvMessage.setText(textResId);
        mTvMessage.setTextColor(getResourceColor(textColorResId));
    }

    private void initView(Context context) {
        inflate(context, R.layout.layout_tip_page, this);

        mIvIcon = (ImageView) findViewById(R.id.iv_tip_page_icon);
        mTvMessage = (TextView) findViewById(R.id.tv_tip_page_msg);

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
