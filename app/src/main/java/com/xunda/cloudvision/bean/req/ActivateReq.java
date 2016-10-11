package com.xunda.cloudvision.bean.req;

/**
 * 激活接口入参实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class ActivateReq extends BaseReq {
    private String activateCode;	// 激活码
    private String equipmentNo;	// 机器码

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    @Override
    public String toString() {
        return "ActivateReq{" +
                "activateCode='" + activateCode + '\'' +
                ", equipmentNo='" + equipmentNo + '\'' +
                "} " + super.toString();
    }
}
