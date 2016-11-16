package com.vrcvp.cloudvision.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.VoiceBean;
import com.vrcvp.cloudvision.ui.adapter.VoiceSearchResultAdapter;

/**
 * 语音对话列表Item视图
 * Created by yinglovezhuzhu@gmail.com on 2016/9/18.
 */
public class VoiceItemView extends LinearLayout {

    private View mViewAndroid;
    private View mViewHuman;
    private View mViewSearchResult;
    private TextView mTvAndroid;
    private TextView mTvHuman;
    private NoScrollListView mLvResult;
    private AdapterView.OnItemClickListener mItemClickListener;

    private VoiceSearchResultAdapter mAdapter;

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
                mViewSearchResult.setVisibility(View.GONE);
                mTvAndroid.setText(bean.getText());
                break;
            case VoiceBean.TYPE_HUMAN:
                mViewAndroid.setVisibility(View.GONE);
                mViewHuman.setVisibility(View.VISIBLE);
                mViewSearchResult.setVisibility(View.GONE);
                mTvHuman.setText(bean.getText());
                break;
            case VoiceBean.TYPE_SEARCH_RESULT:
                mViewAndroid.setVisibility(View.GONE);
                mViewHuman.setVisibility(View.GONE);
                mViewSearchResult.setVisibility(View.VISIBLE);
                mAdapter.clear(false);
                mAdapter.addAll(bean.getSearchResult(), true);
                break;
            default:
                break;
        }
    }

    /**
     * 设置点击监听
     * @param listener
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    private void initView(Context context) {
        inflate(context, R.layout.item_voice, this);

        mViewAndroid = findViewById(R.id.ll_item_voice_android_content);
        mViewHuman = findViewById(R.id.ll_item_voice_human_content);
        mViewSearchResult = findViewById(R.id.ll_item_voice_search_result_content);
        mTvAndroid = (TextView) findViewById(R.id.tv_item_voice_android);
        mTvHuman = (TextView) findViewById(R.id.tv_item_voice_human);
        mLvResult = (NoScrollListView) findViewById(R.id.nlv_item_voice_android_result);

        mAdapter = new VoiceSearchResultAdapter(context);
        mLvResult.setAdapter(mAdapter);
        mLvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(null != mItemClickListener) {
                    mItemClickListener.onItemClick(parent, view, position, id);
                }
            }
        });
    }
}
