package com.xunda.cloudvision.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xunda.cloudvision.bean.req.QueryVideoReq;
import com.xunda.cloudvision.bean.resp.QueryVideoResp;
import com.xunda.cloudvision.db.HttpCacheDBUtils;
import com.xunda.cloudvision.http.HttpAsyncTask;
import com.xunda.cloudvision.http.HttpStatus;
import com.xunda.cloudvision.utils.NetworkManager;
import com.xunda.cloudvision.utils.StringUtils;

/**
 * 云展视频Model
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class VideoModel implements IVideoModel {

    private Context mContext;

    public VideoModel(Context context) {
        this.mContext = context;
    }

    @Override
    public void queryVideo(HttpAsyncTask.Callback<QueryVideoResp> callback) {

        // FIXME 请求地址修改
        final String url = "url";
        if(NetworkManager.getInstance().isNetworkConnected()) {
            QueryVideoReq reqParam = new QueryVideoReq();
            new HttpAsyncTask<QueryVideoResp>().execute("", reqParam, QueryVideoResp.class, callback);
        } else {
            // FIXME 请求标识修改
            final String key = "key";
            QueryVideoResp result;
            String data = HttpCacheDBUtils.getHttpCache(mContext, url, key);
            if(StringUtils.isEmpty(data)) {
                // 错误
                if(null != callback) {
                    result = new QueryVideoResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                    callback.onResult(result);
                }
            } else {
                try {
                    // 解析数据
                    result = new Gson().fromJson(data, QueryVideoResp.class);
                    if(null != callback) {
                        callback.onResult(result);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    if(null != callback) {
                        result = new QueryVideoResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                        callback.onResult(result);
                        // 解析错误的，删除掉记录
                        HttpCacheDBUtils.deleteHttpCache(mContext, url, key);
                    }
                }
            }
        }
    }
}
