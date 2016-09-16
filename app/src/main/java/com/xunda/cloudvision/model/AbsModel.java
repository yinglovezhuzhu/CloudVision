package com.xunda.cloudvision.model;

import com.xunda.cloudvision.bean.resp.BaseResp;

/**
 * Model抽象方法
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public abstract class AbsModel {

    /**
     * 异步执行前
     * @param key 关键字，用来区分是哪个异步线程
     */
    public abstract void onPreExecute(String key);

    /**
     * 取消异步任务
     * @param key 关键字，用来区分是哪个异步线程
     */
    public abstract void onCanceled(String key);

    /**
     * 异步任务返回结果
     * @param key 关键字，用来区分是哪个异步线程
     * @param result 结果数据
     */
    public abstract void onResult(String key, BaseResp result);
}
