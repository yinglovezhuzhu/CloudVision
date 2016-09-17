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
    public void onNoticeSettingsChanged(boolean enabled) {
        mSharePrefHelper.saveBoolean(Config.SP_KEY_MAIN_NOTICE_ENABLED, enabled);
    }

    @Override
    public void onWeatherSettingsChanged(boolean enabled) {
        mSharePrefHelper.saveBoolean(Config.SP_KEY_MAIN_WEATHER_ENABLED, enabled);
    }

    @Override
    public boolean isNoticeEnabled() {
        return mSharePrefHelper.getBoolean(Config.SP_KEY_MAIN_NOTICE_ENABLED, true);
    }

    @Override
    public boolean isWeatherEnabled() {
        return mSharePrefHelper.getBoolean(Config.SP_KEY_MAIN_WEATHER_ENABLED, true);
    }
}
