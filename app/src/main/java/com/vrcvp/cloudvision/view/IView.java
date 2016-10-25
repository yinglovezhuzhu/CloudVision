package com.vrcvp.cloudvision.view;

/**
 * View接口基类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/12.
 */

public interface IView {

    /**
     * 异步执行前
     *
     * @param key 关键字，用来区分是哪个异步线程（只有一个可以传null）
     */
    void onPreExecute(String key);

    /**
     * 取消异步任务
     *
     * @param key 关键字，用来区分是哪个异步线程（只有一个可以传null）
     */
    void onCanceled(String key);
}
