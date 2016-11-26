package com.vrcvp.cloudvision.bean;

/**
 * 软件更新相关信息
 * Created by yinglovezhuzhu@gmail.com on 2016/11/26.
 */

public class UpdateInfo {
    /** 更新类型：普通更新 **/
    public static final int TYPE_NORMAL_UPDATE = 1;
    /** 更新类型：强制更新 **/
    public static final int TYPE_FORCE_UPDATE = 2;

    private int updateType;
    private String version;
    private String versionInt;
    private String downloadUrl;
    private String remark;

    public int getUpdateType() {
        return updateType;
    }

    public String getVersion() {
        return version;
    }

    public String getVersionInt() {
        return versionInt;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "updateType=" + updateType +
                ", version='" + version + '\'' +
                ", versionInt='" + versionInt + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
