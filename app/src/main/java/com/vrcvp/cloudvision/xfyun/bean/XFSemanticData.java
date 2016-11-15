package com.vrcvp.cloudvision.xfyun.bean;

/**
 * 讯飞语义结果data字段数据
 * Created by yinglovezhuzhu@gmail.com on 2016/11/14.
 */

public class XFSemanticData {
    private String header; // 否 导语部分
    private String headerTts; // 否 导语播报内容，若字段不存在，则 取值与header相同
    private Object result; //  是 结构化数据；如果没有查到数据， 此字段不返回。参照后续不同服务 的定义(JSON字符串对象)

    public String getHeader() {
        return header;
    }

    public String getHeaderTts() {
        return headerTts;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "XFSemanticData{" +
                "header='" + header + '\'' +
                ", headerTts='" + headerTts + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
