package com.vrcvp.cloudvision.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 语音对话内容实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/9/18.
 */
public class VoiceBean {
    /** 类型：机器人 **/
    public static final int TYPE_ANDROID = 1;
    /** 类型：人 **/
    public static final int TYPE_HUMAN = 2;
    /** 类型：搜索结果 **/
    public static final int TYPE_SEARCH_RESULT = 3;

    private int type;
    private String text;
    // 语音搜索结果，只有搜索结果才有
    private final List<VoiceSearchResultBean> searchResult = new ArrayList<>();

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

    public void addSearchResult(Collection<VoiceSearchResultBean> result) {
        this.searchResult.addAll(result);
    }

    public List<VoiceSearchResultBean> getSearchResult() {
        return searchResult;
    }

    public VoiceSearchResultBean getSearchResultData(int position) {
        if(searchResult.isEmpty() || position >= searchResult.size()) {
            return null;
        }
        return searchResult.get(position);
    }

    @Override
    public String toString() {
        return "VoiceBean{" +
                "type=" + type +
                ", text='" + text + '\'' +
                ", searchResult=" + searchResult +
                '}';
    }
}
