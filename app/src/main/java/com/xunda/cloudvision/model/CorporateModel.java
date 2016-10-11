package com.xunda.cloudvision.model;

import android.content.Context;

import com.xunda.cloudvision.bean.req.QueryProductReq;
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
        new HttpAsyncTask<QueryProductResp>().execute("", reqParam,
                QueryProductResp.class, new HttpAsyncTask.Callback<QueryProductResp>() {
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
            public void onResult(QueryProductResp result) {
                if(null != callback) {
                    callback.onResult(result);
                }
            }
        });
    }
}
