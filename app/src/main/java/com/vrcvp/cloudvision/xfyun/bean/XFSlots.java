package com.vrcvp.cloudvision.xfyun.bean;

/**
 * 讯飞语义slots实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/10.
 */

public class XFSlots {
    private String keywords;
    private String name;
    private String url;
    private String code;
    private String category;

    public String getKeywords() {
        return keywords;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getCode() {
        return code;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "XFSlots{" +
                "keywords='" + keywords + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", code='" + code + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
