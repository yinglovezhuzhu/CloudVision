package com.vrcvp.cloudvision.bean;

import com.vrcvp.cloudvision.Config;

/**
 * 广告机信息
 * Created by yinglovezhuzhu@gmail.com on 2016/11/23.
 */

public class InfoBean {
    private String openTime;
    private String closeTime;
    private int voiceSex = Config.GENDER_FEMALE; // 机器人性别
    private String androidName; // 机器人名称

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public int getVoiceSex() {
        return voiceSex;
    }

    public String getAndroidName() {
        return androidName;
    }

    @Override
    public String toString() {
        return "InfoBean{" +
                "openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", voiceSex=" + voiceSex +
                ", androidName='" + androidName + '\'' +
                '}';
    }
}
