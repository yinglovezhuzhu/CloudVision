package com.vrcvp.cloudvision.bean;

/**
 * 讯飞语义回答
 * Created by yinglovezhuzhu@gmail.com on 2016/11/14.
 */

public class XFAnswer {
    /** 语义回答内容类型：text数据 **/
    public static final String TYPE_TEXT = "T";
    /** 语义回答内容类型：url数据 **/
    public static final String TYPE_URL = "U";
    /** 语义回答内容类型：text+url数据 **/
    public static final String TYPE_TEXT_URL = "TU";
    /** 语义回答内容类型：image+text数据 **/
    public static final String TYPE_IMAGE_TEXT = "IT";
    /** 语义回答内容类型：image+text+url数据 **/
    public static final String TYPE_IMAGE_TEXT_URL = "ITU";

    private String type; // 是 显示的类型，通过这个类型，可 以确定数据的返回内容和客户 端的显示内容： T：text数据 U：url数据 TU：text+url数据 IT：image+text数据 ITU：image+text+url数据
    private String text; // 是 通用的文字显示，属于text数据
    private String imgUrl; // 否 图片的链接地址，属于image数据
    private String imgDesc; // 否 图片的描述文字
    private String url; // 否 url链接
    private String urlDesc; // 否 url链接的描述文字

    public String getText() {
        return text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlDesc() {
        return urlDesc;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "XFAnswer{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", imgDesc='" + imgDesc + '\'' +
                ", url='" + url + '\'' +
                ", urlDesc='" + urlDesc + '\'' +
                '}';
    }
}
