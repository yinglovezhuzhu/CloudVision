package com.xunda.cloudvision.bean.resp;

import com.xunda.cloudvision.bean.ProductBean;
import com.xunda.cloudvision.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询视频接口返回数据实体类（查询视频接口，视频搜索接口）
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryVideoResp extends BaseResp{

    private List<VideoBean> video = new ArrayList<>();

    public List<VideoBean> getVideo() {
        return video;
    }

    @Override
    public String toString() {
        return "QueryVideoResp{" +
                "video=" + video +
                "} " + super.toString();
    }
}
