package com.vrcvp.cloudvision.bean;

/**
 * 讯飞语义回答
 * Created by yinglovezhuzhu@gmail.com on 2016/11/14.
 */

public class XFAnswer {
    private String type;
    private String text;

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "XFAnswer{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
