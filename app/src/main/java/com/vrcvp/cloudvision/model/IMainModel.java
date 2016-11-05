package com.vrcvp.cloudvision.model;

import com.vrcvp.cloudvision.bean.NoticeBean;
import com.vrcvp.cloudvision.bean.resp.QueryAdvertiseResp;
import com.vrcvp.cloudvision.bean.resp.QueryNoticeResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;

/**
 * MainModel接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/16.
 */
public interface IMainModel extends IModel {

    /**
     * 公告设置改变
     * @param disabled 是否关闭， true 关闭，false 开启
     */
    void onNoticeSettingsChanged(boolean disabled);

    /**
     * 天气设置改变
     * @param disabled 是否关闭， true 关闭， false 开启
     */
    void onWeatherSettingsChanged(boolean disabled);

    /**
     * 公告是否启用
     * @return true 启用， false 禁用（默认启用）
     */
    boolean isNoticeEnabled();

    /**
     * 天气是否启用
     * @return true 启用， false禁用（默认启用）
     */
    boolean isWeatherEnabled();

    /**
     * 下一条通知， 如果通知栏禁用或者没有通知，返回null
     */
    NoticeBean nextNotice();

    /**
     * 是否已激活
     * @return
     */
    boolean isActivated();

    /**
     * 查询首页数据
     * @param callback 回调
     */
    void queryAdvertise(final HttpAsyncTask.Callback<QueryAdvertiseResp> callback);

    /**
     * 取消查询首页广告数据的异步线程任务
     */
    void cancelQueryAdvertise();

    /**
     * 查询公告
     * @param callback 回调
     */
    void queryNotice(final HttpAsyncTask.Callback<QueryNoticeResp> callback);

    /**
     * 取消查询公告数据的异步线程任务
     */
    void cancelQueryNotice();

    /**
     * 登出操作
     */
    void logout();
}
