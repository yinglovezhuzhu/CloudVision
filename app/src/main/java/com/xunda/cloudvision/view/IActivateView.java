package com.xunda.cloudvision.view;

import com.xunda.cloudvision.bean.resp.ActivateResp;

/**
 * 激活View接口
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public interface IActivateView {

    public String getCodeText();

    public void onCodeEmpty();


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

    void onActivateResult(ActivateResp result);

}
