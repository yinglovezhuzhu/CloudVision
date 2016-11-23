package com.vrcvp.cloudvision.bean;

/**
 * 广告机信息
 * Created by yinglovezhuzhu@gmail.com on 2016/11/23.
 */

public class InfoBean {
    private String openTime;
    private String closeTime;

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    @Override
    public String toString() {
        return "InfoBean{" +
                "openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                '}';
    }
}
