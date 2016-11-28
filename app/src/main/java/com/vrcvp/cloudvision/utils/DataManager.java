package com.vrcvp.cloudvision.utils;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;

import com.baidu.location.Poi;
import com.google.gson.Gson;
import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.bean.CorporateBean;
import com.vrcvp.cloudvision.bean.resp.ActivateResp;

/**
 * 设备数据管理（设备硬件、软件数据）
 * Created by yinglovezhuzhu@gmail.com on 2016/10/21.
 */

public class DataManager {

    private static final String SP_KEY_DEVICE_NO = "device_no";
    private static final String SP_KEY_ACTIVATE_DATA = "activate_data";
    private static final String SP_KEY_TOKEN = "token";
    private static final String SP_KEY_CORPORATE_INFO = "corporate_info";

    private static DataManager mInstance = null;

    private final Gson mGson = new Gson();

    private boolean mInitialized = false;
    private SharedPrefHelper mSharedPrefHelper;

    private ActivateResp.ActivateRespData mActivateData;
    private CorporateBean mCorporateInfo;
    private String mToken;

    public static DataManager getInstance() {
        synchronized (DataManager.class) {
            if(null == mInstance) {
                mInstance = new DataManager();
            }
        }
        return mInstance;
    }

    private DataManager() {

    }

    /**
     * 初始化
     * @param context Context 对象
     */
    public void initialize(Context context) {
        if(mInitialized) {
            return;
        }
        mSharedPrefHelper = SharedPrefHelper.newInstance(context, Config.SP_FILE_CACHE);
        mInitialized = true;

        getActivateData();

        getToken();
    }

    /**
     * 获取激活数据
     * @return 激活数据
     */
    public ActivateResp.ActivateRespData getActivateData() {
        if(!mInitialized) {
            return null;
        }
        if(null == mActivateData) {
            try {
                mActivateData = mGson.fromJson(mSharedPrefHelper.getString(SP_KEY_ACTIVATE_DATA, ""), ActivateResp.ActivateRespData.class);
            } catch (Exception e) {
                // do nothing
            }
        }
        return mActivateData;
    }

    /**
     * 更新激活数据
     * @param respData 激活数据
     * @return 是否更新成功
     */
    public boolean updateActivateData(ActivateResp respData) {
        if(!mInitialized || null == respData) {
            return false;
        }
        ActivateResp.ActivateRespData data = respData.getData();
        if(null == data) {
            return false;
        }
        mActivateData = data;
        updateToken(data.getToken());
        return mSharedPrefHelper.saveString(SP_KEY_ACTIVATE_DATA, mGson.toJson(data));
    }

    /**
     * 更新token缓存
     * @param token token
     * @return 是否更新成功
     */
    public boolean updateToken(String token) {
        if(!mInitialized || StringUtils.isEmpty(token)) {
            return false;
        }
        mToken = token;
        return mSharedPrefHelper.saveString(SP_KEY_TOKEN, token);
    }

    /**
     * 获取Token
     * @return token
     */
    public String getToken() {
        if(!mInitialized) {
            return null;
        }
        if(StringUtils.isEmpty(mToken)) {
            mToken = mSharedPrefHelper.getString(SP_KEY_TOKEN, "");
        }
        return mToken;
    }

    /**
     * 获取企业id
     * @return 企业id
     */
    public String getCorporateId() {
        if(!mInitialized || null == mActivateData) {
            return null;
        }
        return mActivateData.getEnterpriseId();
    }

    /**
     * 更细企业数据
     * @param corporate 企业简介数据
     * @return 是否保存成功
     */
    public boolean updateCorporateInfo(CorporateBean corporate) {
        if(!mInitialized || null == corporate) {
            return false;
        }
        mCorporateInfo = corporate;
        return mSharedPrefHelper.saveString(SP_KEY_CORPORATE_INFO, mGson.toJson(corporate));
    }

    /**
     * 获取当前企业数据
     */
    public CorporateBean getCorporateInfo() {
        if(!mInitialized) {
            return null;
        }
        if(null == mCorporateInfo) {
            try {
                mCorporateInfo = mGson.fromJson(mSharedPrefHelper.getString(SP_KEY_CORPORATE_INFO, ""), CorporateBean.class);
            } catch (Exception e) {
                // do nothing
            }
        }
        return mCorporateInfo;
    }

    /**
     * 保存公告设置
     * @param disabled 是否关闭， true 关闭， false 开启
     * @return 是否保存成功
     */
    public boolean onNoticeSettingsChanged(boolean disabled) {
        if(!mInitialized) {
            return false;
        }
        return mSharedPrefHelper.saveBoolean(Config.SP_KEY_MAIN_NOTICE_DISABLED_PREFIX + getCorporateId(), disabled);
    }

    /**
     * 保存首页顶部栏天气设置
     * @param disabled 是否关闭， true 关闭， false 开启
     * @return 是否保存成功
     */
    public boolean onWeatherSettingsChanged(boolean disabled) {
        return mSharedPrefHelper.saveBoolean(Config.SP_KEY_MAIN_WEATHER_DISABLED_PREFIX + getCorporateId(), disabled);
    }

    /**
     * 首页公告是否关闭
     * @return true 关闭， false 开启
     */
    public boolean isNoticeDisabled() {

        return mSharedPrefHelper.getBoolean(Config.SP_KEY_MAIN_NOTICE_DISABLED_PREFIX + getCorporateId(), false);
    }

    /**
     *  首页顶部栏天气是否关闭
     * @return true 关闭， false 开启
     */
    public boolean isWeatherDisabled() {
        return mSharedPrefHelper.getBoolean(Config.SP_KEY_MAIN_WEATHER_DISABLED_PREFIX + getCorporateId(), false);
    }

    /**
     * 是否已激活
     * @return true 已经激活， false 未激活
     */
    public boolean isActivated() {
        return null != mActivateData && !StringUtils.isEmpty(getToken());
    }


    /**
     * 保存首页菜单按钮位置
     * @param position 位置
     * @return 是否保存成功
     */
    public boolean saveMainMenuPosition(Point position) {
        return mSharedPrefHelper.saveInt(Config.SP_KEY_MAIN_MENU_POSITION_X_PREFIX + getCorporateId(), position.x)
                && mSharedPrefHelper.saveInt(Config.SP_KEY_MAIN_MENU_POSITION_Y_PREFIX + getCorporateId(), position.y);
    }

    /**
     * 获取首页菜单按钮位置
     * @param frame 显示区域
     * @return 首页菜单按钮位置
     */
    public Point getMainMenuPosition(Rect frame) {
        final Point position = new Point(0, 0);
        if(mInitialized) {
            position.set(mSharedPrefHelper.getInt(Config.SP_KEY_MAIN_MENU_POSITION_X_PREFIX
                    + getCorporateId(), 0),
                    mSharedPrefHelper.getInt(Config.SP_KEY_MAIN_MENU_POSITION_Y_PREFIX
                            + getCorporateId(), (frame.bottom - frame.top) / 3));
        } else {
            position.set(0, (frame.bottom - frame.top) / 3);
        }
        return position;
    }

    /**
     * 保存JPush别名设置结果
     * @param success 是否成功， true 成功； false 事变
     * @return 是否保存成功
     */
    public boolean saveJPushAliasSetResult(boolean success) {
        if(!mInitialized) {
            return false;
        }
        return mSharedPrefHelper.saveBoolean(Config.SP_KEY_JPUSH_ALIAS_SET_SUCCESS, success);
    }

    /**
     * 读取JPush别名设置结果
     * @return
     */
    public boolean getJPushAliasSetResult() {
        if(!mInitialized) {
            return false;
        }
        return mSharedPrefHelper.getBoolean(Config.SP_KEY_JPUSH_ALIAS_SET_SUCCESS, false);
    }

    /**
     * 设备注销
     */
    public void logout() {
        // 清除激活数据
        mSharedPrefHelper.remove(SP_KEY_ACTIVATE_DATA);
        // 清除Token
        mSharedPrefHelper.remove(SP_KEY_TOKEN);
        // 清除alias
        mSharedPrefHelper.remove(Config.SP_KEY_JPUSH_ALIAS_SET_SUCCESS);

        mActivateData = null;
        mToken = null;
        mCorporateInfo = null;
    }

}
