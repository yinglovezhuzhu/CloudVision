package com.xunda.cloudvision.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.bean.req.ActivateReq;
import com.xunda.cloudvision.bean.resp.ActivateResp;
import com.xunda.cloudvision.db.HttpCacheDBUtils;
import com.xunda.cloudvision.http.HttpAsyncTask;
import com.xunda.cloudvision.http.HttpStatus;
import com.xunda.cloudvision.utils.DataManager;
import com.xunda.cloudvision.utils.NetworkManager;
import com.xunda.cloudvision.utils.SharedPrefHelper;
import com.xunda.cloudvision.utils.StringUtils;

/**
 * 激活页面Model
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public class ActivateModel implements IActivateModel {

    private Context mContext;
    private SharedPrefHelper mSharePrefHelper = null;

    public ActivateModel(Context context) {
        this.mContext = context;
        mSharePrefHelper = SharedPrefHelper.newInstance(context, Config.SP_FILE_CONFIG);
    }

    @Override
    public void activate(String code, final HttpAsyncTask.Callback<ActivateResp> callback) {
        // FIXME 请求地址修改
        final String url = "activate.json";
        final ActivateReq reqParam = new ActivateReq();
        reqParam.setEquipmentNo(DataManager.getInstance().getDeviceNo());
        reqParam.setActivateCode(code);
        final Gson gson = new Gson();
        // FIXME 请求标识修改
        final String key = gson.toJson(reqParam);
        if(NetworkManager.getInstance().isNetworkConnected()) {
            new HttpAsyncTask<ActivateResp>(mContext).execute(url, reqParam, ActivateResp.class, new HttpAsyncTask.Callback<ActivateResp>() {
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
                public void onResult(ActivateResp result) {
                    // 保存接口请求缓存，只有在请求成功的时候才保存
                    if(HttpStatus.SC_OK == result.getHttpCode()) {
                        DataManager.getInstance().updateActivateData(result);
                        HttpCacheDBUtils.saveHttpCache(mContext, url, key, gson.toJson(result));
                    }

                    if(null != callback) {
                        callback.onResult(result);
                    }
                }
            });
        } else {
            ActivateResp result;
            String data = HttpCacheDBUtils.getHttpCache(mContext, url, key);
            if(StringUtils.isEmpty(data)) {
                // 错误
                if(null != callback) {
                    result = new ActivateResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                    callback.onResult(result);
                }
            } else {
                try {
                    // 解析数据
                    result = gson.fromJson(data, ActivateResp.class);
                    if(null != callback) {
                        callback.onResult(result);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    if(null != callback) {
                        result = new ActivateResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                        callback.onResult(result);
                        // 解析错误的，删除掉记录
                        HttpCacheDBUtils.deleteHttpCache(mContext, url, key);
                    }
                }
            }
        }
    }
}
