package com.xunda.cloudvision.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.xunda.cloudvision.bean.resp.QueryHomeDataResp;
import com.xunda.cloudvision.http.HttpAsyncTask;
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

    /** 公告更新时间间隔 **/
    private static final int NOTICE_UPDATE_TIME = 1000 * 6;

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
    public void onDestroy() {
        if(null != mHandler) {
            if(mHandler.hasMessages(MSG_TIME_UPDATE)) {
                mHandler.removeMessages(MSG_TIME_UPDATE);
            }

            if(mHandler.hasMessages(MSG_NOTICE_UPDATE)) {
                mHandler.removeMessages(MSG_NOTICE_UPDATE);
            }
        }
    }

    /**
     * Activity回到前台
     */
    public void onCreate() {
        mMainView.onTimeUpdate(StringUtils.formatTimeMillis(System.currentTimeMillis()));
        mMainView.onNoticeUpdate(mMainModel.nextNotice());
        if(null != mHandler) {
            if(mHandler.hasMessages(MSG_TIME_UPDATE)) {
                mHandler.removeMessages(MSG_TIME_UPDATE);
            }
            // 延迟一秒
            mHandler.sendEmptyMessageDelayed(MSG_TIME_UPDATE, 1000);

            if(mHandler.hasMessages(MSG_NOTICE_UPDATE)) {
                mHandler.removeMessages(MSG_NOTICE_UPDATE);
            }
            mHandler.sendEmptyMessageDelayed(MSG_NOTICE_UPDATE, NOTICE_UPDATE_TIME);
        }


    }

    /**
     * 公告设置改变
     * @param disabled 是否关闭， true 关闭，false 开启
     */
    public void onNoticeSettingsChanged(boolean disabled) {
        mMainModel.onNoticeSettingsChanged(disabled);
        mMainView.onNoticeSettingsChanged(disabled);
    }

    /**
     * 天气设置改变
     * @param disabled 是否关闭， true 关闭， false 开启
     */
    public void onWeatherSettingsChanged(boolean disabled) {
        mMainModel.onWeatherSettingsChanged(disabled);
        mMainView.onWeatherSettingsChanged(disabled);
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
            case MSG_NOTICE_UPDATE:
                mMainView.onNoticeUpdate(mMainModel.nextNotice());
                mHandler.sendEmptyMessageDelayed(MSG_NOTICE_UPDATE, NOTICE_UPDATE_TIME);
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 是否已经激活
     * @return 是否已经激活， true 已激活， false 未激活
     */
    public boolean isActivated() {
        return mMainModel.isActivated();
    }

    /**
     * 查询首页广告
     */
    public void queryHomeAdvertise() {
        mMainModel.queryHomeData(new HttpAsyncTask.Callback<QueryHomeDataResp>() {
            @Override
            public void onPreExecute() {
                mMainView.onPreExecute(null);
            }

            @Override
            public void onCanceled() {
                mMainView.onCanceled(null);
            }

            @Override
            public void onResult(QueryHomeDataResp result) {
                mMainView.onQueryAdvertiseResult(result);
            }
        });
    }
}
