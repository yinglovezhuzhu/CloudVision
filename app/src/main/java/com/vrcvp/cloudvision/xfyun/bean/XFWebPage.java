package com.vrcvp.cloudvision.xfyun.bean;

/**
 * 讯飞语音返回的WebPage数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/10.
 */

public class XFWebPage {
    private String header; // 否 导语部分
    private String headerTts; // 否 导语播报内容，若字段不存在，则 取值与header相同
    private String url; // 是 对data进行UI展示的链接

    public String getHeader() {
        return header;
    }

    public String getHeaderTts() {
        return headerTts;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "XFWebPage{" +
                "header='" + header + '\'' +
                ", headerTts='" + headerTts + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}