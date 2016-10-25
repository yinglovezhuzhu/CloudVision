package com.vrcvp.cloudvision.bean;

/**
 * 云展视频数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class VideoBean {
    private String id;
    private String name;
    private String orderWeight;
    private String imageUrl;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrderWeight() {
        return orderWeight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", orderWeight='" + orderWeight + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
