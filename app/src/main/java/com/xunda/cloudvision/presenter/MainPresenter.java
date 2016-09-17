package com.xunda.cloudvision.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.xunda.cloudvision.model.IMainModel;
import com.xunda.cloudvision.model.MainModel;
import com.xunda.cloudvision.utils.StringUtils;
import com.xunda.cloudvision.view.IMainView;

/**
 * Main主页面Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/16.
 */
public class MainPresenter implements Handler.Callback {

    /** 时间更新消息 **/
    private static final int MSG_TIME_UPDATE = 0x00;
    /** 公告更新消息 **/
    public static final int MSG_NOTICE_UPDATE = 0x01;

    private Context mContext;
    private IMainView mMainView;
    private IMainModel mMainModel;

    private Handler mHandler = new Handler(this);

    public MainPresenter(Context context, IMainView view) {
        mContext = context;
        mMainView = view;
        mMainModel = new MainModel(mContext);
    }

    /**
     * Activity放到后台
     */
    public void onPause() {
        if(null != mHandler) {
            if(mHandler.hasMessages(MSG_TIME_UPDATE)) {
                mHandler.removeMessages(MSG_TIME_UPDATE);
            }
        }
    }

    /**
     * Activity回到前台
     */
    public void onResume() {
        mMainView.onTimeUpdate(StringUtils.formatTimeMillis(System.currentTimeMillis()));
        if(null != mHandler) {
            if(mHandler.hasMessages(MSG_TIME_UPDATE)) {
                mHandler.removeMessages(MSG_TIME_UPDATE);
            }
            // 延迟一秒
            mHandler.sendEmptyMessageDelayed(MSG_TIME_UPDATE, 1000);
        }
    }

    /**
     * 公告设置改变
     * @param enabled 是否开启， true 开启，false关闭
     */
    public void onNoticeSettingsChanged(boolean enabled) {
        mMainModel.onNoticeSettingsChanged(enabled);
    }

    /**
     * 天气设置改变
     * @param enabled 是否开启， true开启， false关闭
     */
    public void onWeatherSettingsChanged(boolean enabled) {
        mMainModel.onWeatherSettingsChanged(enabled);
    }

    /**
     * 公告是否启用
     * @return true 启用， false 禁用（默认启用）
     */
    public boolean isNoticeEnabled() {
        return mMainModel.isNoticeEnabled();
    }

    /**
     * 天气是否启用
     * @return true 启用， false禁用（默认启用）
     */
    public boolean isWeatherEnabled() {
        return mMainModel.isWeatherEnabled();
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case MSG_TIME_UPDATE:
                mMainView.onTimeUpdate(StringUtils.formatTimeMillis(System.currentTimeMillis()));
                mHandler.sendEmptyMessageDelayed(MSG_TIME_UPDATE, 1000);
                return true;
            default:
                break;
        }
        return false;
    }
}
