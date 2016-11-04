package com.vrcvp.cloudvision.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.VoiceBean;

/**
 * 语音对话列表Item视图
 * Created by yinglovezhuzhu@gmail.com on 2016/9/18.
 */
public class VoiceItemView extends LinearLayout {

    private View mViewAndroid;
    private View mViewHuman;
    private TextView mTvAndroid;
    private TextView mTvHuman;

    public VoiceItemView(Context context) {
        super(context);
        initView(context);
    }

    public VoiceItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VoiceItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(21)
    public VoiceItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void setData(VoiceBean bean) {
        if(null == bean) {
            return;
        }
        switch (bean.getType()) {
            case VoiceBean.TYPE_ANDROID:
                mViewAndroid.setVisibility(View.VISIBLE);
                mViewHuman.setVisibility(View.GONE);
                mTvAndroid.setText(bean.getText());
                break;
            case VoiceBean.TYPE_HUMAN:
                mViewAndroid.setVisibility(View.GONE);
                mViewHuman.setVisibility(View.VISIBLE);
                mTvHuman.setText(bean.getText());
                break;
            default:
                break;
        }
    }

    private void initView(Context context) {
        inflate(context, R.layout.item_voice, this);

        mViewAndroid = findViewById(R.id.ll_item_voice_android_content);
        mViewHuman = findViewById(R.id.ll_item_voice_human_content);
        mTvAndroid = (TextView) findViewById(R.id.tv_item_voice_android);
        mTvHuman = (TextView) findViewById(R.id.tv_item_voice_human);
    }
}
