package com.vrcvp.cloudvision.model;

import com.vrcvp.cloudvision.bean.resp.QueryProductResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;

/**
 * 产品Model
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public interface IProductModel {

    /**
     * 查询产品列表
     * @param pageNo 页码
     * @param callback 回调
     */
    void queryProduct(int pageNo, final HttpAsyncTask.Callback<QueryProductResp> callback);

    /**
     * 取消查询产品列表异步线程任务
     */
    void cancelQueryProduct();

}
