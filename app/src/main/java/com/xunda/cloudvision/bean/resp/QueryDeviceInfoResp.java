package com.xunda.cloudvision.bean.resp;

/**
 * 查询广告机信息接口返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryDeviceInfoResp extends BaseResp {
    private String openTime; //　开机时间
    private String closeTime; // 关机时间

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    @Override
    public String toString() {
        return "QueryDeviceInfoResp{" +
                "openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                "} " + super.toString();
    }
}
