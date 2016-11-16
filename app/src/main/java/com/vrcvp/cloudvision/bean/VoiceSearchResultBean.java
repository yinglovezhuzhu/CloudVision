package com.vrcvp.cloudvision.bean;

/**
 * 语音搜索结果数据对象实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/16.
 */

public class VoiceSearchResultBean {
    private String id;
    private String content;
    private int action;
    private int type;
    private String outLink;
    private String url;
    private int sex;
    private String robotName;

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getAction() {
        return action;
    }

    public int getType() {
        return type;
    }

    public String getOutLink() {
        return outLink;
    }

    public String getUrl() {
        return url;
    }

    public int getSex() {
        return sex;
    }

    public String getRobotName() {
        return robotName;
    }

    @Override
    public String toString() {
        return "VoiceSearchResultBean{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", action=" + action +
                ", type=" + type +
                ", outLink='" + outLink + '\'' +
                ", url='" + url + '\'' +
                ", sex=" + sex +
                ", robotName='" + robotName + '\'' +
                '}';
    }
}
