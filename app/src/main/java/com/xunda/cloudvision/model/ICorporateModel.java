package com.xunda.cloudvision.model;

import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.http.HttpAsyncTask;

/**
 * Corporate Model interface
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public interface ICorporateModel {

    void queryRecommendedProduct(HttpAsyncTask.Callback<QueryProductResp> callback);
}
