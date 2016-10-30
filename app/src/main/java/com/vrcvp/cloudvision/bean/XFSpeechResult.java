package com.vrcvp.cloudvision.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by yinglovezhuzhu@gmail.com on 2016/10/30.
 */

public class XFSpeechResult {
    private int sn;
    private boolean ls;
    private int bg;
    private int ed;
    private List<XFWordArrayBean> ws = new ArrayList<>();

    public int getSn() {
        return sn;
    }

    public boolean isLs() {
        return ls;
    }

    public int getBg() {
        return bg;
    }

    public int getEd() {
        return ed;
    }

    public List<XFWordArrayBean> getWs() {
        return ws;
    }

    @Override
    public String toString() {
        return "XFSpeechResult{" +
                "sn=" + sn +
                ", ls=" + ls +
                ", bg=" + bg +
                ", ed=" + ed +
                ", ws=" + ws +
                '}';
    }
}
