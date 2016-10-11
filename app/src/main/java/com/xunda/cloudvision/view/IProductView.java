package com.xunda.cloudvision.view;

import com.xunda.cloudvision.bean.resp.QueryProductDetailResp;

/**
 * 产品列表视图接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public interface IProductView {


    /**
     * 异步执行前
     *
     * @param key 关键字，用来区分是哪个异步线程
     */
    void onPreExecute(String key);

    /**
     * 取消异步任务
     *
     * @param key 关键字，用来区分是哪个异步线程
     */
    void onCanceled(String key);

    /**
     * 查询产品结果
     * @param result
     */
    void onQueryProductResult(QueryProductDetailResp result);
}
