package com.vrcvp.cloudvision.bean;

/**
 * 语音对话内容实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/9/18.
 */
public class VoiceBean {
    /** 类型：机器人 **/
    public static final int TYPE_ANDROID = 1;
    /** 类型：人 **/
    public static final int TYPE_HUMAN = 2;

    private int type;
    private String text;

    public VoiceBean(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "VoiceBean{" +
                "type=" + type +
                ", text='" + text + '\'' +
                '}';
    }
}
