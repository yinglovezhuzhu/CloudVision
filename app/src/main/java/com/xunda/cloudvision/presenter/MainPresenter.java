package com.xunda.cloudvision.presenter;

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

    private static final int MSG_TIME_UPDATE = 0x00;

    private IMainView mMainView;
    private IMainModel mMainModel;

    private Handler mHandler = new Handler(this);

    public MainPresenter(IMainView view) {
        mMainView = view;
        mMainModel = new MainModel();
    }

    public void onPause() {

    }

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
