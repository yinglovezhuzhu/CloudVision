package com.vrcvp.cloudvision.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.bean.NoticeBean;
import com.vrcvp.cloudvision.bean.req.PageReq;
import com.vrcvp.cloudvision.bean.req.QueryHomeDataReq;
import com.vrcvp.cloudvision.bean.resp.QueryHomeDataResp;
import com.vrcvp.cloudvision.bean.resp.QueryNoticeResp;
import com.vrcvp.cloudvision.db.HttpCacheDBUtils;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.utils.DataManager;
import com.vrcvp.cloudvision.utils.NetworkManager;
import com.vrcvp.cloudvision.utils.SharedPrefHelper;
import com.vrcvp.cloudvision.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面Model
 * Created by yinglovezhuzhu@gmail.com on 2016/9/16.
 */
public class MainModel implements IMainModel {

    private Context mContext;
    private SharedPrefHelper mSharePrefHelper = null;
    private final List<NoticeBean> mNotices = new ArrayList<>();
    private int mCurrentNoticeIndex = 0;

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


    @Override
    public NoticeBean nextNotice() {
        if(!isNoticeEnabled() || mNotices.size() == 0) {
            return null;
        }
        if(mCurrentNoticeIndex >= mNotices.size()) {
            mCurrentNoticeIndex = 0;
        }
        return mNotices.get(mCurrentNoticeIndex++);
    }

    @Override
    public boolean isActivated() {
        // FIXME 修改为正确的判断是否激活的代码
        return !StringUtils.isEmpty(mSharePrefHelper.getString(Config.SP_KEY_ACTIVATE_CODE, null));
    }

    @Override
    public void queryHomeData(final HttpAsyncTask.Callback<QueryHomeDataResp> callback) {
        // FIXME 请求地址修改
        final String url = "main_advertise.json";
        final QueryHomeDataReq reqParam = new QueryHomeDataReq();
        reqParam.setEnterpriseId(DataManager.getInstance().getCorporateId());
        reqParam.setToken(DataManager.getInstance().getToken());
        final Gson gson = new Gson();
        // FIXME 请求标识修改
        final String key = gson.toJson(reqParam);
        if(NetworkManager.getInstance().isNetworkConnected()) {
            new HttpAsyncTask<QueryHomeDataResp>(mContext).execute(url, reqParam,
                    QueryHomeDataResp.class, new HttpAsyncTask.Callback<QueryHomeDataResp>() {
                public void onPreExecute() {
                    if(null != callback) {
                        callback.onPreExecute();
                    }
                }

                @Override
                public void onCanceled() {
                    if(null != callback) {
                        callback.onCanceled();
                    }
                }

                @Override
                public void onResult(QueryHomeDataResp result) {
                    // 保存接口请求缓存，只有在请求成功的时候才保存
                    if(HttpStatus.SC_OK == result.getHttpCode()) {
                        HttpCacheDBUtils.saveHttpCache(mContext, url, key, gson.toJson(result));
                    }

                    if(null != callback) {
                        callback.onResult(result);
                    }
                }
            });
        } else {
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

    @Override
    public void queryNotice(final HttpAsyncTask.Callback<QueryNoticeResp> callback) {
        // FIXME 请求地址修改
        final String url = "notice.json";
        final PageReq reqParam = new PageReq();
        reqParam.setToken(DataManager.getInstance().getToken());
        reqParam.setPageNo(1);
        final Gson gson = new Gson();
        // FIXME 请求标识修改
        final String key = gson.toJson(reqParam);
        if(NetworkManager.getInstance().isNetworkConnected()) {
            new HttpAsyncTask<QueryNoticeResp>(mContext).execute(url, reqParam,
                    QueryNoticeResp.class, new HttpAsyncTask.Callback<QueryNoticeResp>() {
                        public void onPreExecute() {
                            if(null != callback) {
                                callback.onPreExecute();
                            }
                        }

                        @Override
                        public void onCanceled() {
                            if(null != callback) {
                                callback.onCanceled();
                            }
                        }

                        @Override
                        public void onResult(QueryNoticeResp result) {
                            // 保存接口请求缓存，只有在请求成功的时候才保存
                            if(HttpStatus.SC_OK == result.getHttpCode()) {
                                HttpCacheDBUtils.saveHttpCache(mContext, url, key, gson.toJson(result));
                                final List<NoticeBean> notices = result.getNotices();
                                if(null != notices && !notices.isEmpty()) {
                                    mNotices.clear();
                                    mNotices.addAll(notices);
                                }
                            }
                            if(null != callback) {
                                callback.onResult(result);
                            }
                        }
                    });
        } else {
            QueryNoticeResp result;
            String data = HttpCacheDBUtils.getHttpCache(mContext, url, key);
            if(StringUtils.isEmpty(data)) {
                // 错误
                if(null != callback) {
                    result = new QueryNoticeResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                    callback.onResult(result);
                }
            } else {
                try {
                    // 解析数据
                    result = new Gson().fromJson(data, QueryNoticeResp.class);
                    if(null != result) {
                        final List<NoticeBean> notices = result.getNotices();
                        if(null != notices && !notices.isEmpty()) {
                            mNotices.clear();
                            mNotices.addAll(notices);
                        }
                    }
                    if(null != callback) {
                        callback.onResult(result);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    if(null != callback) {
                        result = new QueryNoticeResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                        callback.onResult(result);
                        // 解析错误的，删除掉记录
                        HttpCacheDBUtils.deleteHttpCache(mContext, url, key);
                    }
                }
            }
        }
    }
}
