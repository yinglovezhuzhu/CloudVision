package com.vrcvp.cloudvision.xfyun.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 讯飞语音文字结果列表实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/30.
 */

public class XFWordArrayBean {
    private int bg;
    private List<XFWordBean> cw = new ArrayList<>();

    public int getBg() {
        return bg;
    }

    public List<XFWordBean> getCw() {
        return cw;
    }

    public XFWordBean getWordAt(int position) {
        if(cw.isEmpty() || position >= cw.size()) {
            return null;
        }
        return cw.get(position);
    }

    @Override
    public String toString() {
        return "XFWordArrayBean{" +
                "bg=" + bg +
                ", cw=" + cw +
                '}';
    }
}
