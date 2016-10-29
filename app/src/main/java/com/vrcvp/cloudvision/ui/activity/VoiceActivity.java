package com.vrcvp.cloudvision.ui.activity;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.speech.RecognizerResult;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.ImageBean;
import com.vrcvp.cloudvision.bean.VoiceBean;
import com.vrcvp.cloudvision.ui.adapter.VoiceAdapter;
import com.vrcvp.cloudvision.utils.LogUtils;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.utils.Utils;
import com.vrcvp.cloudvision.view.IVoiceView;

/**
 * 语音Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/17.
 */
public class VoiceActivity extends BaseActivity implements IVoiceView {

    private VoiceAdapter mAdapter;
    private SpeechRecognizer mSpeechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voice);

        initView();

        initXF();
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
    public void onNewVoiceData(int type, String text, int action) {
        mAdapter.add(new VoiceBean(type, text), true);
        switch (action) {
            default:
                break;
        }
    }

    private void initView() {
        findViewById(R.id.ibtn_voice_close).setOnClickListener(this);

        final ListView lvVoice = (ListView) findViewById(R.id.lv_voice_list);
        mAdapter = new VoiceAdapter(this);
        lvVoice.setAdapter(mAdapter);

        final ImageButton btnRecord = (ImageButton) findViewById(R.id.ibtn_voice_record);
        btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(null != mSpeechRecognizer) {
                            mSpeechRecognizer.startListening(mRecoListener);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(null != mSpeechRecognizer) {
                            mSpeechRecognizer.stopListening();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        mAdapter.add(new VoiceBean(VoiceBean.TYPE_ANDROID, "我有什么可以帮到您？"), false);
        mAdapter.add(new VoiceBean(VoiceBean.TYPE_HUMAN, "我查找沙发产品"), false);
    }

    private void initXF() {
        final String appId = Utils.getStringMetaData(this, "XFYUN_APPID");
        if(StringUtils.isEmpty(appId)) {
            LogUtils.e(TAG, "讯飞语音APPId为空，讯飞语音初始化失败!");
            finish(RESULT_CANCELED, null);
            // FIXME 讯飞语音APPId为空，讯飞语音初始化失败!
            return;
        }
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" + appId);


        init();
    }

    private void init() {
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mSpeechRecognizer= SpeechRecognizer.createRecognizer(this, null);
//2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        mSpeechRecognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        mSpeechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mSpeechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin ");
//3.开始听写   mIat.startListening(mRecoListener);
//听写监听器

        }

    private RecognizerListener mRecoListener = new RecognizerListener(){

        //听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；
//一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
//关于解析Json的代码可参见MscDemo中JsonParser类；
//isLast等于true时会话结束。

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {

        }

        //结束录音
        public void onEndOfSpeech() {}

        @Override
        public void onResult(com.iflytek.cloud.RecognizerResult recognizerResult, boolean b) {
            Log.d("Result:",recognizerResult.getResultString ());
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {}
    };
}
