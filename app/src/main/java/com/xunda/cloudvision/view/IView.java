package com.xunda.cloudvision.view;

/**
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public interface IView {
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
}
