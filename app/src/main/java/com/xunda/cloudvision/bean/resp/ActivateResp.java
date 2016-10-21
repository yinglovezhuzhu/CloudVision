package com.xunda.cloudvision.bean.resp;

/**
 * 激活返回数据
 * Created by yinglovezhuzhu@gmail.com on 2016/10/9.
 */

public class ActivateResp extends BaseResp {
    private String enterpriseId; // 所属企业id
    private String activateTime; // 激活时间	yyyy-MM-dd
    private String endTime; // 激活码截止使用时间	yyyy-MM-dd
    private String token;

    public ActivateResp() {
    }

    public ActivateResp(int httpCode, String msg) {
        super(httpCode, msg);
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public String getActivateTime() {
        return activateTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "ActivateResp{" +
                "enterpriseId='" + enterpriseId + '\'' +
                ", activateTime='" + activateTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", token='" + token + '\'' +
                "} " + super.toString();
    }
}
