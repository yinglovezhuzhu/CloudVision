package com.vrcvp.cloudvision;

/**
 * 配置文件
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public class Config {

    /** SP文件名称：配置 **/
    public static final String SP_FILE_CONFIG = "sp_file_config";
    /** SP配置文件key：设备id **/
    public static final String SP_KEY_CLIENT_ID = "client_id";

    /** SP文件名称：缓存 **/
    public static final String SP_FILE_CACHE = "sp_file_cache";

    /** SP配置缓存文件key：是否禁用用首页天气（boolean）true 禁用， false 启用 **/
    public static final String SP_KEY_MAIN_WEATHER_DISABLED_PREFIX = "disable_main_weather_";
    /** SP配置缓存文件key：是否禁用首页公告（boolean）true 禁用， false 启用 **/
    public static final String SP_KEY_MAIN_NOTICE_DISABLED_PREFIX = "disable_main_notice_";
    /** SP配置缓存文件key：首页菜单按钮位置X坐标 **/
    public static final String SP_KEY_MAIN_MENU_POSITION_X_PREFIX = "main_menu_position_x_";
    /** SP配置缓存文件key：首页菜单按钮位置Y坐标 **/
    public static final String SP_KEY_MAIN_MENU_POSITION_Y_PREFIX = "main_menu_position_y_";
    /** SP配置缓存文件key：激活码（String） **/
    public static final String SP_KEY_ACTIVATE_CODE = "activate_code";


    /** HTTP请求Host **/
    public static final String HTTP_HOST = BuildConfig.HTTP_HOST;
    /** 接口字--激活 **/
    public static final String API_ACTIVATE = HTTP_HOST + "/activate/check";
    /** 首页广告 **/
    public static final String API_ADVERTISE_LIST = HTTP_HOST + "/advertise/list";
    /** 首页公告 **/
    public static final String API_NOTICE_LIST = HTTP_HOST + "/notice/list";
    /** 企业信息 **/
    public static final String API_CORPORATE_INFO = HTTP_HOST + "/enterprise/one";



    /** Intent传参Data的key **/
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_POSITION = "extra_position";
}
