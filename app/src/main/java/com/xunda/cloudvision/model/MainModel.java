package com.xunda.cloudvision.model;

import android.content.Context;

import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.bean.resp.BaseResp;
import com.xunda.cloudvision.utils.SharedPrefHelper;

/**
 * 主页面Model
 * Created by yinglovezhuzhu@gmail.com on 2016/9/16.
 */
public class MainModel implements IMainModel {

    private SharedPrefHelper mSharePrefHelper = null;



    public MainModel(Context context) {
        mSharePrefHelper = SharedPrefHelper.newInstance(context, Config.SP_FILE_CONFIG);
    }

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {

    }

    @Override
    public void onResult(String key, BaseResp result) {

    }

    @Override
    public void onNoticeSettingsChanged(boolean disabled) {
        mSharePrefHelper.saveBoolean(Config.SP_KEY_MAIN_NOTICE_DISABLED, disabled);
    }

    @Override
    public void onWeatherSettingsChanged(boolean disabled) {
        mSharePrefHelper.saveBoolean(Config.SP_KEY_MAIN_WEATHER_DISABLED, disabled);
    }

    @Override
    public boolean isNoticeEnabled() {
        return !mSharePrefHelper.getBoolean(Config.SP_KEY_MAIN_NOTICE_DISABLED, false);
    }

    @Override
    public boolean isWeatherEnabled() {
        return !mSharePrefHelper.getBoolean(Config.SP_KEY_MAIN_WEATHER_DISABLED, false);
    }

    private String [] mNotice = new String [] {
            "This is notice one",
            "This is notice two",
            "This is notice three",
            "This is notice four",
            "This is notice five",
    };
    private int mIndex = 0;

    @Override
    public String nextNotice() {
        if(!isNoticeEnabled() || mNotice.length == 0) {
            return null;
        }
        if(mIndex >= mNotice.length) {
            mIndex = 0;
        }
        return mNotice[mIndex++];
    }
}
