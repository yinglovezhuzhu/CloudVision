package com.vrcvp.cloudvision.bean;

/**
 * 广告数据实体类（首页）
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class AdvertiseBean {
    /** 广告类型：1-图片 **/
    public static final int TYPE_IMAGE = 1;
    /** 广告类型：2-视频 **/
    public static final int TYPE_VIDEO = 2;
    /** 广告类型：3-产品 **/
    public static final int TYPE_PRODUCT = 3;
    /** 广告类型：4-企业 **/
    public static final int TYPE_CORPORATE = 4;
    /** 广告类型：5-外部链接 **/
    public static final int TYPE_OUTER_LINK = 5;

    private String id;	// 广告id
    private int type;	// 广告类型
    private String relationId;	// 关联ID
    private String orderWeight;	// 序号
    private String url;	// url
    private String outLink;	// 外部链接

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getOrderWeight() {
        return orderWeight;
    }

    public void setOrderWeight(String orderWeight) {
        this.orderWeight = orderWeight;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOutLink() {
        return outLink;
    }

    public void setOutLink(String outLink) {
        this.outLink = outLink;
    }

    @Override
    public String toString() {
        return "AdvertiseBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", relationId='" + relationId + '\'' +
                ", orderWeight='" + orderWeight + '\'' +
                ", url='" + url + '\'' +
                ", outLink='" + outLink + '\'' +
                '}';
    }
}
