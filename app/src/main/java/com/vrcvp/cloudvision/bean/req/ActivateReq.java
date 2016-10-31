package com.vrcvp.cloudvision.bean.req;

/**
 * 激活接口入参实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class ActivateReq {
    private String activateCode;	// 激活码
    private String equipmentNo;	// 机器码
    private String mac; // 网卡物理地址

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "ActivateReq{" +
                "activateCode='" + activateCode + '\'' +
                ", equipmentNo='" + equipmentNo + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }
}
