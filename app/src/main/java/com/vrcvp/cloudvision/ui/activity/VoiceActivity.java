package com.vrcvp.cloudvision.ui.activity;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.AdvertiseBean;
import com.vrcvp.cloudvision.bean.VoiceBean;
import com.vrcvp.cloudvision.bean.VoiceSearchResultBean;
import com.vrcvp.cloudvision.bean.resp.VoiceSearchResp;
import com.vrcvp.cloudvision.presenter.VoicePresenter;
import com.vrcvp.cloudvision.ui.adapter.VoiceAdapter;
import com.vrcvp.cloudvision.utils.LogUtils;
import com.vrcvp.cloudvision.view.IVoiceView;

import java.util.List;

/**
 * 语音Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/17.
 */
public class VoiceActivity extends BaseActivity implements IVoiceView {

    private VoicePresenter mVoicePresenter;
    private ListView mLvVoice;
    private VoiceAdapter mAdapter;

    private AnimationDrawable mWaveAnimDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voice);

        initView();

        mVoicePresenter = new VoicePresenter(this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_voice_close:
                finish(RESULT_CANCELED, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onXFEngineError(int code, String message) {

    }

    @Override
    public void onNewVoiceData(VoiceBean bean, int action) {
        if(null == bean) {
            return;
        }
        mAdapter.add(bean, true);
        mLvVoice.setSelection(mAdapter.getCount());
        switch (action) {
//            case ACTION_SPEAK:
//                break;
            default:
                break;
        }
    }

    @Override
    public void showLoadingDialog() {
        showLoadingDialog(null, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    @Override
    public void onPreExecute(String key) {
        cancelLoadingDialog();
    }

    @Override
    public void onCanceled(String key) {

    }

    @Override
    public void onVoiceSearchResult(List<VoiceSearchResultBean> result) {

    }

    @Override
    public void clearListView(boolean keepFirst) {
        mAdapter.clear(true, true);
    }

    @Override
    public void updateLastAndroid(String text) {
        mAdapter.updateLastAndroid(text, true);
    }

    @Override
    public void viewWebURL(String url) {

    }

    private void initView() {
        findViewById(R.id.ibtn_voice_close).setOnClickListener(this);

        mLvVoice = (ListView) findViewById(R.id.lv_voice_list);
        mAdapter = new VoiceAdapter(this);
        mAdapter.setOnSubItemClickListener(new VoiceAdapter.OnSubItemClickListener() {
            @Override
            public void onSubItemClickListener(int position, int subPosition) {
                final VoiceBean voiceBean = mAdapter.getItem(position);
                if(null == voiceBean) {
                    return;
                }
                final VoiceSearchResultBean searchResultBean = voiceBean.getSearchResultData(subPosition);
                if(null == searchResultBean) {
                    return;
                }
                // FIXME 改为跳转
                switch (searchResultBean.getType()) {
                    case AdvertiseBean.TYPE_IMAGE:
                        openWebView(searchResultBean.getOutLink());
                        break;
                    case AdvertiseBean.TYPE_VIDEO:
//                        playVideo(mData.getOutLink());
                        playVideo(searchResultBean.getUrl(), searchResultBean.getContent());
                        break;
                    case AdvertiseBean.TYPE_PRODUCT:
                        openWebView(searchResultBean.getOutLink());
                        break;
                    case AdvertiseBean.TYPE_CORPORATE:
                        openWebView(searchResultBean.getOutLink());
                        break;
                    case AdvertiseBean.TYPE_OUTER_LINK:
                        openWebView(searchResultBean.getOutLink());
                        break;
                    default:
                        break;
                }
            }
        });
        mLvVoice.setAdapter(mAdapter);

        // FIXME 改为动画
        final ImageView ivVoiceWave = (ImageView) findViewById(R.id.iv_voice_voice_wave);
        mWaveAnimDrawable = (AnimationDrawable) ivVoiceWave.getDrawable();
        if(null == mWaveAnimDrawable) {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                mWaveAnimDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_voice_wave);
            } else {
                try {
                    mWaveAnimDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_voice_wave, null);
                } catch (Exception e) {
                    mWaveAnimDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_voice_wave);
                }
            }
            ivVoiceWave.setImageDrawable(mWaveAnimDrawable);
        }

        final ImageButton btnRecord = (ImageButton) findViewById(R.id.ibtn_voice_record);
        btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mWaveAnimDrawable.start();
                        mVoicePresenter.startSpeech();
                        break;
                    case MotionEvent.ACTION_UP:
                        mWaveAnimDrawable.stop();
                        mVoicePresenter.stopSpeech();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
