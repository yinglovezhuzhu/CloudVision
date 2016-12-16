package com.vrcvp.cloudvision.bean.resp;

import com.vrcvp.cloudvision.Config;

/**
 * 激活返回数据
 * Created by yinglovezhuzhu@gmail.com on 2016/10/9.
 */

public class ActivateResp extends BaseResp<ActivateResp.ActivateRespData> {

    public ActivateResp() {
    }

    public ActivateResp(int httpCode, String msg) {
        super(httpCode, msg);
    }


    @Override
    public String toString() {
        return "ActivateResp{} " + super.toString();
    }

    public static class ActivateRespData {
        private String enterpriseId; // 所属企业id
        private String activateTime; // 激活时间	yyyy-MM-dd
        private String endTime; // 激活码截止使用时间	yyyy-MM-dd
        private String token;
        private int voiceSex = Config.GENDER_FEMALE; // 机器人性别
        private String androidName; // 机器人名称

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

        public int getVoiceSex() {
            return voiceSex;
        }

        public String getAndroidName() {
            return androidName;
        }

        public void setVoiceSex(int voiceSex) {
            this.voiceSex = voiceSex;
        }

        public void setAndroidName(String androidName) {
            this.androidName = androidName;
        }

        @Override
        public String toString() {
            return "ActivateRespData{" +
                    "enterpriseId='" + enterpriseId + '\'' +
                    ", activateTime='" + activateTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", token='" + token + '\'' +
                    ", voiceSex=" + voiceSex +
                    ", androidName='" + androidName + '\'' +
                    '}';
        }
    }
}
