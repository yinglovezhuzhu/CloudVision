package com.vrcvp.cloudvision.xfyun;

/**
 * 讯飞操作类型
 * Created by yinglovezhuzhu@gmail.com on 2016/11/15.
 */

public enum  XFOperation {

    /** 打开应用(缺省) **/
    LAUNCH,
    /** 卸载应用 **/
    UNINSTALL,
    /** 下载应用 **/
    DOWNLOAD,
    /** 安装应用 **/
    INSTALL,
    /** 搜索 **/
    QUERY,
    /** 退出或关闭应用 **/
    EXIT,
    /** 直接返回答案 **/
    ANSWER,
    /** 查询时间 **/
    QUERY_TIME,
    /** 预订 **/
    BOOK,
    /** 查询路线 **/
    ROUTE,
    /** 定位我的位置 **/
    POSITION,
    /** 播放歌曲/视频 **/
    PLAY, 
    /** 搜索歌曲 **/
    SEARCH, 
    /** 查看日程 **/
    VIEW, 
    /** 创建日程 **/
    CREATE, 
    /** 翻译 **/
    TRANSLATION, 
    /** 电视节目播放信息查询 **/
    QUERY_PROGRAM, 
    /** 电视台的节目信息查询 **/
    QUERY_TV, 
    /** 查询视频信息（暂不支持） **/
    QUERY_VIDEOINFO,
    /** 打开网站 **/
    OPEN, 
    /** 发布微博 **/
    PUBLISH, 
    /** 发送私信 **/
    SEND, 
    /** 转发微博 **/
    FORWARD, 
    /** 评论微博 **/
    COMMENT, 
    /** 拨打电话 **/
    CALL,
    /** 表示朗读 **/
    SYNTH;
}
