package com.vrcvp.cloudvision.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.bean.NoticeBean;
import com.vrcvp.cloudvision.bean.WeatherInfo;
import com.vrcvp.cloudvision.bean.req.CheckUpdateReq;
import com.vrcvp.cloudvision.bean.req.FindInfoReq;
import com.vrcvp.cloudvision.bean.req.QueryAdvertiseReq;
import com.vrcvp.cloudvision.bean.req.QueryNoticeReq;
import com.vrcvp.cloudvision.bean.resp.CheckUpdateResp;
import com.vrcvp.cloudvision.bean.resp.FindInfoResp;
import com.vrcvp.cloudvision.bean.resp.QueryAdvertiseResp;
import com.vrcvp.cloudvision.bean.resp.QueryNoticeResp;
import com.vrcvp.cloudvision.bean.resp.QueryWeatherResp;
import com.vrcvp.cloudvision.db.HttpCacheDBUtils;
import com.vrcvp.cloudvision.db.WeatherDBHelper;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.utils.DataManager;
import com.vrcvp.cloudvision.utils.NetworkManager;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面Model
 * Created by yinglovezhuzhu@gmail.com on 2016/9/16.
 */
public class MainModel implements IMainModel {

    private Context mContext;
    private final List<NoticeBean> mNotices = new ArrayList<>();
    private int mCurrentNoticeIndex = 0;

    private HttpAsyncTask<QueryAdvertiseResp> mQueryAdvertiseTask;
    private HttpAsyncTask<QueryNoticeResp> mQueryNoticeTask;
    private HttpAsyncTask<QueryWeatherResp> mQueryWeatherTask;
    private HttpAsyncTask<FindInfoResp> mFindInfoTask;
    private HttpAsyncTask<CheckUpdateResp> mCheckUpdateTask;

    public MainModel(Context context) {
        this.mContext = context;
        DataManager.getInstance().initialize(context);
    }

    @Override
    public void onNoticeSettingsChanged(boolean disabled) {
        DataManager.getInstance().onNoticeSettingsChanged(disabled);
    }

    @Override
    public void onWeatherSettingsChanged(boolean disabled) {
        DataManager.getInstance().onWeatherSettingsChanged(disabled);
    }

    @Override
    public boolean isNoticeDisabled() {
        return DataManager.getInstance().isNoticeDisabled();
    }

    @Override
    public boolean isWeatherDisabled() {
        return DataManager.getInstance().isWeatherDisabled();
    }


    @Override
    public NoticeBean nextNotice() {
        if(isNoticeDisabled() || mNotices.size() == 0) {
            return null;
        }
        if(mCurrentNoticeIndex >= mNotices.size()) {
            mCurrentNoticeIndex = 0;
        }
        return mNotices.get(mCurrentNoticeIndex++);
    }

    @Override
    public boolean isActivated() {
        return DataManager.getInstance().isActivated();
    }

    @Override
    public void queryAdvertise(final HttpAsyncTask.Callback<QueryAdvertiseResp> callback) {
        final String url = Config.API_ADVERTISE_LIST;
        final QueryAdvertiseReq reqParam = new QueryAdvertiseReq();
        reqParam.setEnterpriseId(DataManager.getInstance().getCorporateId());
        reqParam.setToken(DataManager.getInstance().getToken());
        reqParam.setPageNo(1);
        final Gson gson = new Gson();
        final String key = gson.toJson(reqParam);
        if(NetworkManager.getInstance().isNetworkConnected()) {
            mQueryAdvertiseTask = new HttpAsyncTask<>();
            mQueryAdvertiseTask.doPost(url, reqParam,
                    QueryAdvertiseResp.class, new HttpAsyncTask.Callback<QueryAdvertiseResp>() {
                public void onPreExecute() {
                    if(null != callback) {
                        callback.onPreExecute();
                    }
                    mQueryAdvertiseTask = null;
                }

                @Override
                public void onCanceled() {
                    if(null != callback) {
                        callback.onCanceled();
                    }
                    mQueryAdvertiseTask = null;
                }

                @Override
                public void onResult(QueryAdvertiseResp result) {
                    // 保存接口请求缓存，只有在请求成功的时候才保存
                    if(HttpStatus.SC_OK == result.getHttpCode()) {
                        HttpCacheDBUtils.saveHttpCache(mContext, url, key, gson.toJson(result));
                    }

                    if(null != callback) {
                        callback.onResult(result);
                    }
                    mQueryAdvertiseTask = null;
                }
            });
        } else {
            QueryAdvertiseResp result;
            String data = HttpCacheDBUtils.getHttpCache(mContext, url, key);
            if(StringUtils.isEmpty(data)) {
                // 错误
                if(null != callback) {
                    result = new QueryAdvertiseResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                    callback.onResult(result);
                }
            } else {
                try {
                    // 解析数据
                    result = new Gson().fromJson(data, QueryAdvertiseResp.class);
                    if(null != callback) {
                        callback.onResult(result);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    if(null != callback) {
                        result = new QueryAdvertiseResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                        callback.onResult(result);
                        // 解析错误的，删除掉记录
                        HttpCacheDBUtils.deleteHttpCache(mContext, url, key);
                    }
                }
            }
        }
    }

    @Override
    public void cancelQueryAdvertise() {
        if(null != mQueryAdvertiseTask) {
            mQueryAdvertiseTask.cancel();
        }
    }

    @Override
    public void queryNotice(final HttpAsyncTask.Callback<QueryNoticeResp> callback) {
        final String url = Config.API_NOTICE_LIST;
        final QueryNoticeReq reqParam = new QueryNoticeReq();
        reqParam.setToken(DataManager.getInstance().getToken());
        reqParam.setEnterpriseId(DataManager.getInstance().getCorporateId());
        reqParam.setPageNo(1);
        final Gson gson = new Gson();
        final String key = gson.toJson(reqParam);
        if(NetworkManager.getInstance().isNetworkConnected()) {
            mQueryNoticeTask = new HttpAsyncTask<>();
            mQueryNoticeTask.doPost(url, reqParam,
                    QueryNoticeResp.class, new HttpAsyncTask.Callback<QueryNoticeResp>() {
                        public void onPreExecute() {
                            if(null != callback) {
                                callback.onPreExecute();
                            }
                            mQueryNoticeTask = null;
                        }

                        @Override
                        public void onCanceled() {
                            if(null != callback) {
                                callback.onCanceled();
                            }
                            mQueryNoticeTask = null;
                        }

                        @Override
                        public void onResult(QueryNoticeResp result) {
                            // 保存接口请求缓存，只有在请求成功的时候才保存
                            if(HttpStatus.SC_OK == result.getHttpCode()) {
                                HttpCacheDBUtils.saveHttpCache(mContext, url, key, gson.toJson(result));
                                final List<NoticeBean> notices = result.getData();
                                if(null != notices && !notices.isEmpty()) {
                                    mNotices.clear();
                                    mNotices.addAll(notices);
                                }
                            }
                            if(null != callback) {
                                callback.onResult(result);
                            }
                            mQueryNoticeTask = null;
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
                        final List<NoticeBean> notices = result.getData();
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

    @Override
    public void cancelQueryNotice() {
        if(null != mQueryNoticeTask) {
            mQueryNoticeTask.cancel();
        }
    }

    @Override
    public void queryCityWeather(String cityName, final HttpAsyncTask.Callback<WeatherInfo> callback) {
        final String cityCode = WeatherDBHelper.getCityCode(mContext, cityName);
        if(StringUtils.isEmpty(cityCode)) {
            if(null != callback) {
                callback.onResult(null);
            }
            return;
        }
        mQueryWeatherTask = new HttpAsyncTask<>();
        mQueryWeatherTask.doGet(Config.API_WEATHER + cityCode + ".html", null, null,
                QueryWeatherResp.class, new HttpAsyncTask.Callback<QueryWeatherResp>() {
            @Override
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
            public void onResult(QueryWeatherResp result) {
                if(null != callback) {
                    callback.onResult(null == result ? null : result.getWeatherinfo());
                }
            }
        });
    }

    @Override
    public void cancelQueryWeather() {
        if(null != mQueryWeatherTask) {
            mQueryWeatherTask.cancel();
        }
    }

    @Override
    public void findInfo(final HttpAsyncTask.Callback<FindInfoResp> callback) {
        final String url = Config.API_FIND_INFO;
        final FindInfoReq reqParam = new FindInfoReq();
        reqParam.setEnterpriseId(DataManager.getInstance().getCorporateId());
        reqParam.setToken(DataManager.getInstance().getToken());
        final Gson gson = new Gson();
        final String key = gson.toJson(reqParam);
        if(NetworkManager.getInstance().isNetworkConnected()) {
            mFindInfoTask = new HttpAsyncTask<>();
            mFindInfoTask.doPost(url, reqParam,
                    FindInfoResp.class, new HttpAsyncTask.Callback<FindInfoResp>() {
                        public void onPreExecute() {
                            if(null != callback) {
                                callback.onPreExecute();
                            }
                            mFindInfoTask = null;
                        }

                        @Override
                        public void onCanceled() {
                            if(null != callback) {
                                callback.onCanceled();
                            }
                            mFindInfoTask = null;
                        }

                        @Override
                        public void onResult(FindInfoResp result) {
                            // 保存接口请求缓存，只有在请求成功的时候才保存
                            if(HttpStatus.SC_OK == result.getHttpCode()) {
                                HttpCacheDBUtils.saveHttpCache(mContext, url, key, gson.toJson(result));
                            }

                            if(null != callback) {
                                callback.onResult(result);
                            }
                            mFindInfoTask = null;
                        }
                    });
        } else {
            FindInfoResp result;
            String data = HttpCacheDBUtils.getHttpCache(mContext, url, key);
            if(StringUtils.isEmpty(data)) {
                // 错误
                if(null != callback) {
                    result = new FindInfoResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                    callback.onResult(result);
                }
            } else {
                try {
                    // 解析数据
                    result = new Gson().fromJson(data, FindInfoResp.class);
                    if(null != callback) {
                        callback.onResult(result);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    if(null != callback) {
                        result = new FindInfoResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                        callback.onResult(result);
                        // 解析错误的，删除掉记录
                        HttpCacheDBUtils.deleteHttpCache(mContext, url, key);
                    }
                }
            }
        }
    }

    @Override
    public void cancelFindInfo() {
        if(null != mFindInfoTask) {
            mFindInfoTask.cancel();
        }
    }

    @Override
    public void checkUpdate(final HttpAsyncTask.Callback<CheckUpdateResp> callback) {
        final String url = Config.API_CHECK_UPDATE;
        final CheckUpdateReq reqParam = new CheckUpdateReq(Utils.getVersionCode(mContext));
        if(NetworkManager.getInstance().isNetworkConnected()) {
            mCheckUpdateTask = new HttpAsyncTask<>();
            mCheckUpdateTask.doPost(url, reqParam,
                    CheckUpdateResp.class, new HttpAsyncTask.Callback<CheckUpdateResp>() {
                        public void onPreExecute() {
                            if(null != callback) {
                                callback.onPreExecute();
                            }
                            mCheckUpdateTask = null;
                        }

                        @Override
                        public void onCanceled() {
                            if(null != callback) {
                                callback.onCanceled();
                            }
                            mCheckUpdateTask = null;
                        }

                        @Override
                        public void onResult(CheckUpdateResp result) {
                            if(null != callback) {
                                callback.onResult(result);
                            }
                            mCheckUpdateTask = null;
                        }
                    });
        }
    }

    @Override
    public void cancelCheckUpdate() {
        if(null != mCheckUpdateTask) {
            mCheckUpdateTask.cancel();
        }
    }

    @Override
    public void logout() {
        cancelQueryAdvertise();
        cancelQueryNotice();
        mNotices.clear();
    }
}
