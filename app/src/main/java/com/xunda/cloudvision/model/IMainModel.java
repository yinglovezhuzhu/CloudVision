package com.xunda.cloudvision.model;

/**
 * MainModel接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/16.
 */
public interface IMainModel extends IModel {

    /**
     * 公告设置改变
     * @param enabled 是否开启， true 开启，false关闭
     */
    void onNoticeSettingsChanged(boolean enabled);

    /**
     * 天气设置改变
     * @param enabled 是否开启， true开启， false关闭
     */
    void onWeatherSettingsChanged(boolean enabled);

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
}
