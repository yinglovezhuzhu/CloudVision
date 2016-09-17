package com.xunda.cloudvision.view;

/**
 * Main页面视图接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/16.
 */
public interface IMainView {

    /**
     * 时间更新
     * @param time 时间字符串
     */
    public void onTimeUpdate(String time);

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

}
