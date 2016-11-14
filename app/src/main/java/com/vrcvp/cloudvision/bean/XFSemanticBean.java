package com.vrcvp.cloudvision.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 讯飞语义返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/10.
 */

public class XFSemanticBean {
    private int rc;
    private XFWebPage webPage;
    private XFSemantic semantic;
    private XFAnswer answer;
    private String operation;
    private String service;
    private String data;
    private String text;
    private List<XFSemanticBean> moreResults;

    public int getRc() {
        return rc;
    }

    public XFWebPage getWebPage() {
        return webPage;
    }

    public XFSemantic getSemantic() {
        return semantic;
    }

    public XFAnswer getAnswer() {
        return answer;
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

    public List<XFSemanticBean> getMoreResults() {
        return moreResults;
    }

    @Override
    public String toString() {
        return "XFSemanticBean{" +
                "rc=" + rc +
                ", webPage=" + webPage +
                ", semantic=" + semantic +
                ", answer=" + answer +
                ", operation='" + operation + '\'' +
                ", service='" + service + '\'' +
                ", data='" + data + '\'' +
                ", text='" + text + '\'' +
                ", moreResults=" + moreResults +
                '}';
    }
}
