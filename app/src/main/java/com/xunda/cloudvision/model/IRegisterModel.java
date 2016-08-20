package com.xunda.cloudvision.model;

import com.xunda.cloudvision.bean.resp.BaseResp;

/**
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public interface IRegisterModel {

    public void register(String code);

    /**
     * 异步执行前
     */
    public void onPreExecute();

    /**
     * 取消异步任务
     */
    public void onCanceled();

    /**
     * 异步任务返回结果
     * @param result 结果数据
     */
    public void onResult(BaseResp result);
}
