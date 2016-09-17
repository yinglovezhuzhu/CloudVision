package com.xunda.cloudvision.model;

import com.xunda.cloudvision.bean.resp.BaseResp;

/**
 * Model接口
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public interface IModel {

    /**
     * 异步执行前
     * @param key 关键字，用来区分是哪个异步线程
     */
    void onPreExecute(String key);

    /**
     * 取消异步任务
     * @param key 关键字，用来区分是哪个异步线程
     */
    void onCanceled(String key);

    /**
     * 异步任务返回结果
     * @param key 关键字，用来区分是哪个异步线程
     * @param result 结果数据
     */
    void onResult(String key, BaseResp result);
}
