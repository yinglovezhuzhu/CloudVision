package com.xunda.cloudvision.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.bean.req.ActivateReq;
import com.xunda.cloudvision.bean.req.QueryHomeDataReq;
import com.xunda.cloudvision.bean.resp.ActivateResp;
import com.xunda.cloudvision.bean.resp.BaseResp;
import com.xunda.cloudvision.bean.resp.QueryCorporateResp;
import com.xunda.cloudvision.bean.resp.QueryHomeDataResp;
import com.xunda.cloudvision.db.HttpCacheDBUtils;
import com.xunda.cloudvision.http.HttpAsyncTask;
import com.xunda.cloudvision.http.HttpStatus;
import com.xunda.cloudvision.utils.NetworkManager;
import com.xunda.cloudvision.utils.SharedPrefHelper;
import com.xunda.cloudvision.utils.StringUtils;

/**
 * 主页面Model
 * Created by yinglovezhuzhu@gmail.com on 2016/9/16.
 */
public class MainModel implements IMainModel {

    private Context mContext;
    private SharedPrefHelper mSharePrefHelper = null;

    public MainModel(Context context) {
        this.mContext = context;
        mSharePrefHelper = SharedPrefHelper.newInstance(context, Config.SP_FILE_CONFIG);
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

    @Override
    public void queryHomeData(HttpAsyncTask.Callback<QueryHomeDataResp> callback) {
        // FIXME 请求地址修改
        final String url = "url";
        if(NetworkManager.getInstance().isNetworkConnected()) {
            QueryHomeDataReq reqParam = new QueryHomeDataReq();
            new HttpAsyncTask<QueryHomeDataResp>().execute(url, reqParam, QueryHomeDataResp.class, callback);
        } else {
            // FIXME 请求标识修改
            final String key = "key";
            QueryHomeDataResp result;
            String data = HttpCacheDBUtils.getHttpCache(mContext, url, key);
            if(StringUtils.isEmpty(data)) {
                // 错误
                if(null != callback) {
                    result = new QueryHomeDataResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                    callback.onResult(result);
                }
            } else {
                try {
                    // 解析数据
                    result = new Gson().fromJson(data, QueryHomeDataResp.class);
                    if(null != callback) {
                        callback.onResult(result);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    if(null != callback) {
                        result = new QueryHomeDataResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                        callback.onResult(result);
                        // 解析错误的，删除掉记录
                        HttpCacheDBUtils.deleteHttpCache(mContext, url, key);
                    }
                }
            }
        }
    }
}
