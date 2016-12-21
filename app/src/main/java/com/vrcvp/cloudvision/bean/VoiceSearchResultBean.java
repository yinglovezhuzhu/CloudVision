package com.vrcvp.cloudvision.bean;

/**
 * 语音搜索结果数据对象实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/16.
 */

public class VoiceSearchResultBean {
    private String id;
    private String name;
    private String content;
    private String action;
    private int type;
    private String outLink;
    private String url;
    private int sex;
    private String robotName;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getAction() {
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
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", action='" + action + '\'' +
                ", type=" + type +
                ", outLink='" + outLink + '\'' +
                ", url='" + url + '\'' +
                ", sex=" + sex +
                ", robotName='" + robotName + '\'' +
                '}';
    }
}
