package com.xunda.cloudvision.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xunda.cloudvision.bean.req.QueryProductReq;
import com.xunda.cloudvision.bean.resp.QueryProductDetailResp;
import com.xunda.cloudvision.db.HttpCacheDBUtils;
import com.xunda.cloudvision.http.HttpAsyncTask;
import com.xunda.cloudvision.http.HttpStatus;
import com.xunda.cloudvision.utils.NetworkManager;
import com.xunda.cloudvision.utils.StringUtils;

/**
 * 产品详情Model
 * Created by yinglovezhuzhu@gmail.com on 2016/10/12.
 */

public class ProductDetailModel implements IProductDetailModel {

    private Context mContext;

    public ProductDetailModel(Context context) {
        this.mContext = context;
    }

    @Override
    public void queryProductDetail(String productId, HttpAsyncTask.Callback<QueryProductDetailResp> callback) {
        // FIXME 请求地址修改
        final String url = "url";
        if(NetworkManager.getInstance().isNetworkConnected()) {
            QueryProductReq reqParam = new QueryProductReq();
            new HttpAsyncTask<QueryProductDetailResp>().execute("", reqParam, QueryProductDetailResp.class, callback);
        } else {
            // FIXME 请求标识修改
            final String key = "key";
            QueryProductDetailResp result;
            String data = HttpCacheDBUtils.getHttpCache(mContext, url, key);
            if(StringUtils.isEmpty(data)) {
                // 错误
                if(null != callback) {
                    result = new QueryProductDetailResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                    callback.onResult(result);
                }
            } else {
                try {
                    // 解析数据
                    result = new Gson().fromJson(data, QueryProductDetailResp.class);
                    if(null != callback) {
                        callback.onResult(result);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    if(null != callback) {
                        result = new QueryProductDetailResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                        callback.onResult(result);
                        // 解析错误的，删除掉记录
                        HttpCacheDBUtils.deleteHttpCache(mContext, url, key);
                    }
                }
            }
        }
    }
}
