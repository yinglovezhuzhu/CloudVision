package com.vrcvp.cloudvision.bean;

/**
 * 讯飞语义返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/10.
 */

public class XFSemanticBean {
    private XFWebPage webPage;
    private XFSemantic semantic;
    private int rc;
    private String operation;
    private String service;
    private String data;
    private String text;

    public XFWebPage getWebPage() {
        return webPage;
    }

    public XFSemantic getSemantic() {
        return semantic;
    }

    public int getRc() {
        return rc;
    }

    public String getOperation() {
        return operation;
    }

    public String getService() {
        return service;
    }

    public String getData() {
        return data;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "XFSemanticBean{" +
                "webPage=" + webPage +
                ", semantic=" + semantic +
                ", rc=" + rc +
                ", operation='" + operation + '\'' +
                ", service='" + service + '\'' +
                ", data='" + data + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
