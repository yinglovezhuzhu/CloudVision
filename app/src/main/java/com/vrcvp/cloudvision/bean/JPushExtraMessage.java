package com.vrcvp.cloudvision.bean;

import com.google.gson.Gson;

/**
 * JPush消息Extra部分的extraMessage字段数据
 * Created by yinglovezhuzhu@gmail.com on 2016/11/21.
 */

public class JPushExtraMessage {
    private String currentTime;
    private String startTime;
    private String endTime;
    private String mac;

    public String getCurrentTime() {
        return currentTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getMac() {
        return mac;
    }

    @Override
    public String toString() {
        return "JPushExtraMessage{" +
                "currentTime='" + currentTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }

    /**
     * 将Json字符串解析成JPushExtraMessage对象，如果Json字符串有问题解析失败，返回null
     * @param jsonString json字符串
     * @return JPushExtraMessage对象
     */
    public static JPushExtraMessage fromJsonString(String jsonString) {
        try {
            return new Gson().fromJson(jsonString, JPushExtraMessage.class);
        } catch (Exception e) {
            // do nothing
        }
        return null;
    }

}
