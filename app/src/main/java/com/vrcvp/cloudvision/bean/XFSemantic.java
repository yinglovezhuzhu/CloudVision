package com.vrcvp.cloudvision.bean;

/**
 * 讯飞语义semantic实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/10.
 */

public class XFSemantic {
    private String normalPrompt; // 否 获取到数据时的导语文字，可为空
    private String normalPromptTts; //否 获取到数据时的导语播报内容，若字段不存 在，则取值与normalPrompt相同
    private String noDataPrompt; // 否 获取不到数据时的导语文字,可以为空,如当 slots就返回一个url的时候
    private String noDataPromptTts; // 否 获取不到数据时的导语播报内容，若字段不 存在，则取值与noDataPrompt相同 slots Object 是 参照后续不同服务的定义
    private XFSlots slots; // 参照后续不同服务的定义

    public String getNormalPrompt() {
        return normalPrompt;
    }

    public String getNormalPromptTts() {
        return normalPromptTts;
    }

    public String getNoDataPrompt() {
        return noDataPrompt;
    }

    public String getNoDataPromptTts() {
        return noDataPromptTts;
    }

    public XFSlots getSlots() {
        return slots;
    }

    @Override
    public String toString() {
        return "XFSemantic{" +
                "normalPrompt='" + normalPrompt + '\'' +
                ", normalPromptTts='" + normalPromptTts + '\'' +
                ", noDataPrompt='" + noDataPrompt + '\'' +
                ", noDataPromptTts='" + noDataPromptTts + '\'' +
                ", slots=" + slots +
                '}';
    }
}
