package com.xunda.cloudvision;

/**
 * 配置文件
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public class Config {

    /** SP文件名称：配置 **/
    public final static String SP_FILE_CONFIG = "sp_file_config";

    /** SP文件名称：缓存 **/
    public static final String SP_FILE_CACHE = "sp_file_cache";

    /** SP配置文件key：是否禁用用首页天气（boolean）true 禁用， false 启用 **/
    public static final String SP_KEY_MAIN_WEATHER_DISABLED = "disable_main_weather";
    /** SP配置文件key：是否禁用首页公告（boolean）true 禁用， false 启用 **/
    public static final String SP_KEY_MAIN_NOTICE_DISABLED = "disable_main_notice";
    /** SP配置文件key：激活码（String） **/
    public static final String SP_KEY_ACTIVATE_CODE = "activate_code";



    /** Intent传参Data的key **/
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_POSITION = "extra_position";
}
