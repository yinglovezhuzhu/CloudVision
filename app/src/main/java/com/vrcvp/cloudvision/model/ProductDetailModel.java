package com.vrcvp.cloudvision.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.bean.AttrValueBean;
import com.vrcvp.cloudvision.bean.req.QueryProductDetailReq;
import com.vrcvp.cloudvision.bean.req.QuerySkuPriceReq;
import com.vrcvp.cloudvision.bean.resp.QueryProductDetailResp;
import com.vrcvp.cloudvision.bean.resp.QuerySkuPriceResp;
import com.vrcvp.cloudvision.db.HttpCacheDBUtils;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.utils.DataManager;
import com.vrcvp.cloudvision.utils.NetworkManager;
import com.vrcvp.cloudvision.utils.StringUtils;

import java.util.Collection;

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
    public void queryProductDetail(String productId, final HttpAsyncTask.Callback<QueryProductDetailResp> callback) {
        final String url = Config.API_PRODUCT_DETAIL;
        final QueryProductDetailReq reqParam = new QueryProductDetailReq();
        reqParam.setProductId(productId);
        reqParam.setToken(DataManager.getInstance().getToken());
        final Gson gson = new Gson();
        final String key = gson.toJson(reqParam);
        if(NetworkManager.getInstance().isNetworkConnected()) {
            new HttpAsyncTask<QueryProductDetailResp>().execute(url, reqParam,
                    QueryProductDetailResp.class, new HttpAsyncTask.Callback<QueryProductDetailResp>() {
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
                        public void onResult(QueryProductDetailResp result) {
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

    @Override
    public void querySkuPrice(String productId, Collection<AttrValueBean> attrValueList,
                              final HttpAsyncTask.Callback<QuerySkuPriceResp> callback) {
        final String url = Config.API_PRODUCT_SKU_PRICE;
        final QuerySkuPriceReq reqParam = new QuerySkuPriceReq();
        reqParam.setProductId(productId);
        reqParam.addAttrValues(attrValueList);
        reqParam.setToken(DataManager.getInstance().getToken());
        final Gson gson = new Gson();
        final String key = gson.toJson(reqParam);
        if(NetworkManager.getInstance().isNetworkConnected()) {
            new HttpAsyncTask<QuerySkuPriceResp>().execute(url, reqParam,
                    QuerySkuPriceResp.class, new HttpAsyncTask.Callback<QuerySkuPriceResp>() {
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
                        public void onResult(QuerySkuPriceResp result) {
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
            QuerySkuPriceResp result;
            String data = HttpCacheDBUtils.getHttpCache(mContext, url, key);
            if(StringUtils.isEmpty(data)) {
                // 错误
                if(null != callback) {
                    result = new QuerySkuPriceResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                    callback.onResult(result);
                }
            } else {
                try {
                    // 解析数据
                    result = new Gson().fromJson(data, QuerySkuPriceResp.class);
                    if(null != callback) {
                        callback.onResult(result);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    if(null != callback) {
                        result = new QuerySkuPriceResp(HttpStatus.SC_CACHE_NOT_FOUND, "Cache not found");
                        callback.onResult(result);
                        // 解析错误的，删除掉记录
                        HttpCacheDBUtils.deleteHttpCache(mContext, url, key);
                    }
                }
            }
        }
    }
}
