package com.vrcvp.cloudvision.bean;

/**
 * 讯飞语音文字实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/30.
 */

public class XFWordBean {
    private int sc;
    private String w;

    public int getSc() {
        return sc;
    }

    public String getW() {
        return w;
    }

    @Override
    public String toString() {
        return "XFWordBean{" +
                "sc=" + sc +
                ", w='" + w + '\'' +
                '}';
    }
}
