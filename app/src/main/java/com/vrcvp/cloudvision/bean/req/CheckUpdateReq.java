package com.vrcvp.cloudvision.bean.req;

/**
 * 检查更新请求入参实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/26.
 */

public class CheckUpdateReq {

    private int versionCode;

    public CheckUpdateReq() {

    }

    public CheckUpdateReq(int versionCode) {
        this.versionCode = versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    public String toString() {
        return "CheckUpdateReq{" +
                "versionCode=" + versionCode +
                '}';
    }
}
