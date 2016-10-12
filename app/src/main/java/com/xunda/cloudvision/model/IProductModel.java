package com.xunda.cloudvision.model;

import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.http.HttpAsyncTask;

/**
 * 产品Model
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public interface IProductModel {

    void queryProduct(final HttpAsyncTask.Callback<QueryProductResp> callback);

}
