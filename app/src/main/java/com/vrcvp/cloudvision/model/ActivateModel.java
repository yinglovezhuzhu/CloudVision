package com.vrcvp.cloudvision.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.bean.req.ActivateReq;
import com.vrcvp.cloudvision.bean.resp.ActivateResp;
import com.vrcvp.cloudvision.db.HttpCacheDBUtils;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.utils.DataManager;
import com.vrcvp.cloudvision.utils.NetworkManager;
import com.vrcvp.cloudvision.utils.SharedPrefHelper;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.utils.Utils;

/**
 * 激活页面Model
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public class ActivateModel implements IActivateModel {

    private Context mContext;
    private HttpAsyncTask<ActivateResp> mActivateTask;

    public ActivateModel(Context context) {
        this.mContext = context;
    }

    @Override
    public void activate(String code, final HttpAsyncTask.Callback<ActivateResp> callback) {
        final String url = Config.API_ACTIVATE;
        final ActivateReq reqParam = new ActivateReq();
        reqParam.setActivateCode(code);
        reqParam.setEquipmentNo(Utils.getDeviceId(mContext));
        reqParam.setMac(Utils.getMac(mContext));
        final Gson gson = new Gson();
        final String key = gson.toJson(reqParam);
        if(NetworkManager.getInstance().isNetworkConnected()) {
            mActivateTask = new HttpAsyncTask<>();
            mActivateTask.execute(url, reqParam, ActivateResp.class, new HttpAsyncTask.Callback<ActivateResp>() {
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

    @Override
    public void cancelActivate() {
        if(null != mActivateTask) {
            mActivateTask.cancel();
        }
    }
}
