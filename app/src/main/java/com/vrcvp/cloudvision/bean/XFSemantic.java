package com.vrcvp.cloudvision.bean;

/**
 * 讯飞语义semantic实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/10.
 */

public class XFSemantic {

    private XFSlots slots;

    public XFSlots getSlots() {
        return slots;
    }

    @Override
    public String toString() {
        return "XFSemantic{" +
                "slots=" + slots +
                '}';
    }
}
