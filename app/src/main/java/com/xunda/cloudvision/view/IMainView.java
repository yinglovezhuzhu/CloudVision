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
     * @param disabled 是否关闭， true 关闭，false 开启
     */
    void onNoticeSettingsChanged(boolean disabled);

    /**
     * 天气设置改变
     * @param disabled 是否关闭， true 关闭， false 开启
     */
    void onWeatherSettingsChanged(boolean disabled);

    /**
     * 公告数据更新
     * @param notice 公告内容
     */
    void onNoticeUpdate(String notice);

}
