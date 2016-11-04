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


//    /** HTTP请求Host **/
//    public static final String HTTP_HOST = BuildConfig.HTTP_HOST;
    /** 接口字--激活 **/
    public static final String API_ACTIVATE = BuildConfig.HTTP_HOST + "/activate/check";
    /** 首页广告 **/
    public static final String API_ADVERTISE_LIST = BuildConfig.HTTP_HOST + "/advertise/list";
    /** 首页公告 **/
    public static final String API_NOTICE_LIST = BuildConfig.HTTP_HOST + "/notice/list";
    /** 企业信息 **/
    public static final String API_CORPORATE_INFO = BuildConfig.HTTP_HOST + "/enterprise/one";
    /** 推荐产品 **/
    public static final String API_RECOMMENDED_PRODUCT = BuildConfig.HTTP_HOST + "/recommendProdInfo/queryRecommendProdInfo";
    /** 产品列表 **/
    public static final String API_PRODUCT_LIST = BuildConfig.HTTP_HOST + "/product/queryProductList";
    /** 产品搜索 **/
    public static final String API_PRODUCT_SEARCH = BuildConfig.HTTP_HOST + "/productSearch/productSearch";
    /** 产品详情 **/
    public static final String API_PRODUCT_DETAIL = BuildConfig.HTTP_HOST + "/productDetail/queryProductDetail";
    /** 产品SKU价格查询 **/
    public static final String API_PRODUCT_SKU_PRICE = BuildConfig.HTTP_HOST + "/product/findPrice";
    /** 推荐视频 **/
    public static final String API_RECOMMENDED_VIDEO = BuildConfig.HTTP_HOST + "/video/recommend";
    /** 视频列表 **/
    public static final String API_VIDEO_LIST = BuildConfig.HTTP_HOST + "/video/cloud";
    /** 视频搜索 **/
    public static final String API_VIDEO_SEARCH = BuildConfig.HTTP_HOST + "/video/list";



    /** Intent传参Data的key **/
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_TITLE_STR = "extra_title_str";
    public static final String EXTRA_TITLE_RES = "extra_title_res";
    public static final String EXTRA_URL = "extra_url";
}
