package com.vrcvp.cloudvision.bean;

/**
 * 讯飞语音返回的WebPage数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/10.
 */

public class XFWebPage {
    private String header;
    private String url;

    public String getHeader() {
        return header;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "XFWebPage{" +
                "header='" + header + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
