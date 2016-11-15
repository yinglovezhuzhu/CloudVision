package com.vrcvp.cloudvision.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.VoiceBean;
import com.vrcvp.cloudvision.bean.resp.VoiceSearchResp;
import com.vrcvp.cloudvision.presenter.VoicePresenter;
import com.vrcvp.cloudvision.ui.adapter.VoiceAdapter;
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
    public void onNewVoiceData(int type, String text, int action) {
        mAdapter.add(new VoiceBean(type, text), true);
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
    public void onVoiceSearchResult(List<VoiceSearchResp.VoiceSearchData> result) {

    }

    @Override
    public void viewWebURL(String url) {

    }

    private void initView() {
        findViewById(R.id.ibtn_voice_close).setOnClickListener(this);

        mLvVoice = (ListView) findViewById(R.id.lv_voice_list);
        mAdapter = new VoiceAdapter(this);
        mLvVoice.setAdapter(mAdapter);

        final ImageButton btnRecord = (ImageButton) findViewById(R.id.ibtn_voice_record);
        btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mVoicePresenter.startSpeech();
                        break;
                    case MotionEvent.ACTION_UP:
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
