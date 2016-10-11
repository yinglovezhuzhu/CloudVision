package com.xunda.cloudvision.model;

import android.content.Context;

import com.xunda.cloudvision.bean.req.QueryCorporateReq;
import com.xunda.cloudvision.bean.req.QueryProductReq;
import com.xunda.cloudvision.bean.resp.QueryCorporateResp;
import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.http.HttpAsyncTask;

/**
 * Corporate Model class
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public class CorporateModel implements ICorporateModel {

    private Context mContext;

    public CorporateModel(Context context) {
        this.mContext = context;
    }

    @Override
    public void queryRecommendedProduct(final HttpAsyncTask.Callback<QueryProductResp> callback) {
        QueryProductReq reqParam = new QueryProductReq();
        new HttpAsyncTask<QueryProductResp>().execute("", reqParam, QueryProductResp.class, callback);
    }

    @Override
    public void queryCorporateInfo(final HttpAsyncTask.Callback<QueryCorporateResp> callback) {
        QueryCorporateReq reqParam = new QueryCorporateReq();
        new HttpAsyncTask<QueryCorporateResp>().execute("", reqParam, QueryCorporateResp.class, callback);
    }
}
