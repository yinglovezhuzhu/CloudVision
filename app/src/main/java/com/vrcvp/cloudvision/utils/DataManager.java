package com.vrcvp.cloudvision.utils;

import android.content.Context;

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

    private String mDeviceNo;
    private ActivateResp mActivateData;
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

        getDeviceNo();

        getActivateData();
    }

    /**
     * 获取设备编号
     * @return 设备编号
     */
    public String getDeviceNo() {
        if(!mInitialized) {
            return null;
        }
        if(StringUtils.isEmpty(mDeviceNo)) {
            mDeviceNo = mSharedPrefHelper.getString(SP_KEY_DEVICE_NO, "");
            if(StringUtils.isEmpty(mDeviceNo)) {
                // FIXME 获取设备编号
                mDeviceNo = "ABCDEFG";
                mSharedPrefHelper.saveString(SP_KEY_DEVICE_NO, mDeviceNo);
            }
        }
        return mDeviceNo;
    }

    /**
     * 获取激活数据
     * @return 激活数据
     */
    public ActivateResp getActivateData() {
        if(!mInitialized) {
            return null;
        }
        if(null == mActivateData) {
            try {
                mActivateData = mGson.fromJson(mSharedPrefHelper.getString(SP_KEY_ACTIVATE_DATA, ""), ActivateResp.class);
            } catch (Exception e) {
                // do nothing
            }
        }
        return mActivateData;
    }

    /**
     * 更新激活数据
     * @param data 激活数据
     * @return 是否更新成功
     */
    public boolean updateActivateData(ActivateResp data) {
        if(!mInitialized || null == data) {
            return false;
        }
        updateToken(data.getToken());
        mActivateData = data;
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
     * 设备注销
     */
    public void logout() {
        // 清除激活数据
        mSharedPrefHelper.remove(SP_KEY_ACTIVATE_DATA);
        // 清除Token
        mSharedPrefHelper.remove(SP_KEY_TOKEN);

        mActivateData = null;
        mToken = null;
        mCorporateInfo = null;
    }

}
