package com.vrcvp.cloudvision.presenter;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.VoiceBean;
import com.vrcvp.cloudvision.bean.VoiceSearchResultBean;
import com.vrcvp.cloudvision.bean.resp.VoiceSearchResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.xfyun.XFOperation;
import com.vrcvp.cloudvision.xfyun.bean.XFSemantic;
import com.vrcvp.cloudvision.xfyun.bean.XFSemanticResp;
import com.vrcvp.cloudvision.xfyun.bean.XFSlots;
import com.vrcvp.cloudvision.xfyun.bean.XFSpeechResult;
import com.vrcvp.cloudvision.xfyun.bean.XFWordArrayBean;
import com.vrcvp.cloudvision.xfyun.bean.XFWordBean;
import com.vrcvp.cloudvision.model.IVoiceModel;
import com.vrcvp.cloudvision.model.VoiceModel;
import com.vrcvp.cloudvision.utils.LogUtils;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.utils.Utils;
import com.vrcvp.cloudvision.view.IVoiceView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 语音Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/17.
 */
public class VoicePresenter {

    private static final String QUERY_REGULAR_EXPRESSION = XFOperation.QUERY.name() + "_\\w*";

    private final IVoiceView mVoiceView;
    private final IVoiceModel mVoiceModel;

    private final String mStrAndroidStartWord;
    private final String mStrAndroidUnknownWhat;
    private final String mStrAndroidNotFound;
    private final String mStrSearching;
    private final String mStrSearchResult;
    private final String mStrSearchResultAbout;

    private SpeechSynthesizer mSpeechSynthesizer;
    private SpeechRecognizer mSpeechRecognizer;
    private TextUnderstander mTextUnderstander;

    private boolean mSpeechRecognizerInitialized = false;
    private boolean mSpeechSynthesizerInitialized = false;

    public VoicePresenter(Context context, IVoiceView voiceView) {
        this.mVoiceView = voiceView;
        this.mVoiceModel = new VoiceModel(context);
        mStrAndroidStartWord = context.getString(R.string.str_voice_android_start);
        mStrAndroidUnknownWhat = context.getString(R.string.str_voice_unknown_what_to_do);
        mStrAndroidNotFound = context.getString(R.string.str_voice_search_not_found);
        mStrSearching = context.getString(R.string.str_searching);
        mStrSearchResult = context.getString(R.string.str_search_result);
        mStrSearchResultAbout = context.getString(R.string.str_search_result_about);

        initEngine(context);

        mVoiceView.onNewVoiceData(new VoiceBean(VoiceBean.TYPE_ANDROID, mStrAndroidStartWord), IVoiceView.ACTION_NONE);
        startSpeak(mStrAndroidStartWord);
    }

    /**
     * 开始语音合成
     * @param words 合成语音的文字
     */
    public void startSpeak(String words) {
        if(null == mSpeechSynthesizer || !mSpeechRecognizerInitialized) {
            return;
        }
        mSpeechSynthesizer.startSpeaking(words, mSynthesizerListener);
    }

    /**
     * 停止语音合成
     */
    public void stopSpeak() {
        if(null == mSpeechSynthesizer || !mSpeechRecognizerInitialized) {
            return;
        }
        mSpeechSynthesizer.stopSpeaking();
    }

    /**
     * 开始语音识别
     */
    public void startSpeech() {
        if(null == mSpeechRecognizer || !mSpeechSynthesizerInitialized) {
            return;
        }
        stopSpeak();
        mSpeechRecognizer.startListening(mRecognizerListener);
    }

    /**
     * 结束语音识别
     */
    public void stopSpeech() {
        if(null == mSpeechRecognizer || !mSpeechSynthesizerInitialized) {
            return;
        }
        mSpeechRecognizer.stopListening();
    }



    /**
     * 语音合成（文字转语音）监听
     */
    private SynthesizerListener mSynthesizerListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };


    /**
     * 语音识别（语音转文字）监听
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener(){

        //听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；
        //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
        //关于解析Json的代码可参见MscDemo中JsonParser类；
        //isLast等于true时会话结束。
        private final StringBuilder mmResultString = new StringBuilder();
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {
            // 清除上一次数据
            mmResultString.delete(0, mmResultString.length());
        }

        //结束录音
        public void onEndOfSpeech() {

        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean isLast) {
            mmResultString.append(parseResult(recognizerResult));
            if(isLast) {
                final String resultString = mmResultString.toString();
                if(StringUtils.isEmpty(resultString)) {
                    return;
                }

                // 清除就的搜索记录
                mVoiceView.clearListView(true);

                mVoiceView.onNewVoiceData(new VoiceBean(VoiceBean.TYPE_HUMAN, resultString), IVoiceView.ACTION_NONE);

                if(mTextUnderstander.isUnderstanding()) {
                    mTextUnderstander.cancel();
                }
                // 开始语义识别并搜索
                onNewAndroidItem(mStrSearching, false);
                mTextUnderstander.understandText(mmResultString.toString(), mTextUnderstanderListener);
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            // TODO 语音输入错误
            LogUtils.e("VOICE", speechError.getErrorDescription());
        }

        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {}
    };

    /**
     * 初始化引擎
     * @param context Context
     */
    private void initEngine(Context context) {
        final String appId = Utils.getStringMetaData(context, "XFYUN_APPID");
        if(StringUtils.isEmpty(appId)) {
            mSpeechRecognizerInitialized = false;
            mSpeechSynthesizerInitialized = false;
            mVoiceView.onXFEngineError(IVoiceView.ERROR_APPID_INVALID, "讯飞语音APP_ID为空");
            return;
        }

        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=" + appId);

        initSpeechSynthesizer(context);

        initSpeechRecognizer(context);

        initTextUnderstander(context);

    }

    /**
     * 初始化语音合成（文字转语音）引擎
     * @param context Context对象
     */
    private void initSpeechSynthesizer(Context context) {
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mSpeechSynthesizer= SpeechSynthesizer.createSynthesizer(context, new InitListener() {
            @Override
            public void onInit(int i) {
                if(ErrorCode.SUCCESS == i) {
                    mSpeechSynthesizerInitialized = true;
                } else {
                    mSpeechSynthesizerInitialized = false;
                    mVoiceView.onXFEngineError(IVoiceView.ERROR_SPEECH_SYNTHESIZER_INI_FAILED, "讯飞语音合成引擎初始化失败");
                }
            }
        });
        mSpeechRecognizerInitialized = true;
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mSpeechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mSpeechSynthesizer.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mSpeechSynthesizer.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mSpeechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        //如果不需要保存合成音频，注释该行代码
//        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
        //3.开始合成
//        mSpeechSynthesizer.startSpeaking("科大讯飞，让世界聆听我们的声音", mSynthesizerListener);
    }

    /**
     * 初始化语音识别（语音转文字）引擎
     * @param context Context对象
     */
    private void initSpeechRecognizer(Context context) {
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mSpeechRecognizer= SpeechRecognizer.createRecognizer(context, new InitListener() {
            @Override
            public void onInit(int i) {
                if(ErrorCode.SUCCESS == i) {
                    mSpeechRecognizerInitialized = true;
                } else {
                    mSpeechRecognizerInitialized = false;
                    mVoiceView.onXFEngineError(IVoiceView.ERROR_SPEECH_RECOGNIZER_INI_FAILED, "讯飞语音识别引擎初始化失败");
                }
            }
        });

        mSpeechSynthesizerInitialized = true;
        //2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        mSpeechRecognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        mSpeechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mSpeechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin ");
        //3.开始听写   mIat.startListening(mRecognizerListener);
        //听写监听器
    }

    /**
     * 解析语音识别结果
     * @param result 语音识别结果
     * @return 语音识别结果文字
     */
    private String parseResult(RecognizerResult result) {
        if(null == result) {
            return "";
        }
        final String resultString = result.getResultString();
        if(StringUtils.isEmpty(resultString)) {
            return "";
        }

        XFSpeechResult speechResult = null;
        try {
            speechResult = new Gson().fromJson(resultString, XFSpeechResult.class);
        } catch (JsonSyntaxException e) {
            // do nothing
        }
        if(null == speechResult) {
            return "";
        }
        XFWordBean wordBean;
        StringBuilder textSb = new StringBuilder();
        for (XFWordArrayBean arrayBean : speechResult.getWs()) {
            // 这里只拿第一个结果，不做多个识别
            wordBean = arrayBean.getWordAt(0);
            if(null != wordBean) {
                textSb.append(wordBean.getW());
            }
        }
        return textSb.toString();
    }

    private void initTextUnderstander(Context context) {
        //创建文本语义理解对象
        mTextUnderstander = TextUnderstander.createTextUnderstander(context,  null);
        mTextUnderstander.setParameter(SpeechConstant.DOMAIN, "iat");
        mTextUnderstander.setParameter(SpeechConstant.NLP_VERSION, "2.0");
        //开始语义理解
//        mTextUnderstander.understandText("科大讯飞", mTextUnderstanderListener);
    }

    //初始化监听器
    private TextUnderstanderListener mTextUnderstanderListener = new TextUnderstanderListener(){

        private Gson mmGson = new Gson();

        //语义结果回调
        public void onResult(UnderstanderResult result){
            final String resultString = result.getResultString();
            LogUtils.e("XFVoice", resultString);
            if(StringUtils.isEmpty(resultString)) {
                mVoiceView.updateLastAndroid(mStrAndroidNotFound);
                return;
            }
            try {
                final XFSemanticResp bean = mmGson.fromJson(resultString, XFSemanticResp.class);
                // 处理讯飞语义识别结果
                handleTextUnderstanderResult(bean);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                mVoiceView.updateLastAndroid(mStrAndroidNotFound);
            }
        }
        //语义错误回调
        public void onError(SpeechError error) {
            onNewAndroidItem(mStrAndroidNotFound, false);
        }
    };

    /**
     * 处理讯飞语义识别结果
     * @param bean 语义识别结果
     */
    private void handleTextUnderstanderResult(XFSemanticResp bean) {
        if(null == bean) {
            mVoiceView.updateLastAndroid(mStrAndroidNotFound);
            return;
        }
        if(XFSemanticResp.RC_SUCCESS == bean.getRc()) {
            final Map<String, XFSemanticResp> resultMap = new HashMap<>();
            resultMap.put(bean.getOperation() + "_" + bean.getService(), bean);
            List<XFSemanticResp> moreResult = bean.getMoreResults();
            if(null != moreResult) {
                for (XFSemanticResp result : moreResult) {
                    if(XFSemanticResp.RC_SUCCESS == result.getRc()) {
                        resultMap.put(result.getOperation() + "_" + result.getService(), result);
                    }
                }
            }

            final Set<String> resultKeySet = resultMap.keySet();

            if(handleSearch(resultMap, resultKeySet)) {
                return;
            }
            // TODO 处理其他请求，比如打开网页等等
            mVoiceView.updateLastAndroid(mStrAndroidNotFound);

        } else {
            mVoiceView.updateLastAndroid(mStrAndroidNotFound);
        }
    }

    /**
     * 处理查询
     * @param resultMap 语义结果Map
     * @param resultKeySet 语义结果Map keySet
     * @return 是否已处理
     */
    private boolean handleSearch(final Map<String, XFSemanticResp> resultMap, final Set<String> resultKeySet) {
        String keywords = null;
        for (String key : resultKeySet) {
            if(key.matches(QUERY_REGULAR_EXPRESSION)) {
                final XFSemantic semantic = resultMap.get(key).getSemantic();
                if(null == semantic) {
                    continue;
                }
                final XFSlots slots = semantic.getSlots();
                if(null == slots) {
                    continue;
                }
                keywords = slots.getKeywords();
                if(!StringUtils.isEmpty(keywords)) {
                    break;
                }
            }
        }

        if(StringUtils.isEmpty(keywords)) {
            return false;
        }
        search(keywords);
        return true;
    }

    /**
     * 请求后台搜索企业数据
     * @param keywords 关键字
     */
    private void search(final String keywords) {
        // 请求后台搜索
        mVoiceModel.searchVoiceRequest(keywords, 1, new HttpAsyncTask.Callback<VoiceSearchResp>() {
            @Override
            public void onPreExecute() {
                mVoiceView.onPreExecute("voice_search");
            }

            @Override
            public void onCanceled() {
                mVoiceView.onCanceled("voice_search");
            }

            @Override
            public void onResult(VoiceSearchResp result) {
                if (null == result) {
                    // 没有找到内容
                    mVoiceView.updateLastAndroid(mStrAndroidNotFound);
                    return;
                }
                switch (result.getHttpCode()) {
                    case HttpStatus.SC_OK:
                        final List<VoiceSearchResultBean> datas = result.getData();
                        if (null == datas || datas.isEmpty()) {
                            // 没有找到内容
                            mVoiceView.updateLastAndroid(mStrAndroidNotFound);
                            return;
                        }
//                        mVoiceView.updateLastAndroid(mStrSearchResult);
                        mVoiceView.updateLastAndroid(String.format(mStrSearchResultAbout, keywords));
                        mVoiceView.onVoiceSearchResult(datas);
                        final VoiceBean bean = new VoiceBean(VoiceBean.TYPE_SEARCH_RESULT, null);
                        bean.addSearchResult(datas);
                        mVoiceView.onNewVoiceData(bean, IVoiceView.ACTION_NONE);
                        break;
                    default:
                        // 没有找到内容
                        mVoiceView.updateLastAndroid(mStrAndroidNotFound);
                        break;
                }
            }
        });
    }

    private void onNewAndroidItem(String text, boolean speek) {
        mVoiceView.onNewVoiceData(new VoiceBean(VoiceBean.TYPE_ANDROID, text), IVoiceView.ACTION_NONE);
        if(speek) {
            startSpeak(mStrAndroidUnknownWhat);
        }
    }

}
