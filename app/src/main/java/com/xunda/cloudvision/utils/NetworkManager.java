package com.xunda.cloudvision.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xunda.cloudvision.observer.NetworkObservable;
import com.xunda.cloudvision.observer.NetworkObserver;

/**
 * 网络状态管理类
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public class NetworkManager {

    private Context mContext;

    private ConnectivityManager mConnectivityManager;

    private final NetworkObservable mNetworkObservable = new NetworkObservable();

    private boolean mNetworkConnected = false;

    private NetworkInfo mCurrentNetwork = null;

    private boolean mInitialized = false;

    private static NetworkManager mInstance = null;

    private NetworkManager() {

    }

    public static NetworkManager getInstance() {
        synchronized (NetworkManager.class) {
            if(null == mInstance) {
                mInstance = new NetworkManager();
            }
            return mInstance;
        }
    }

    public void initialized(Context context) {
        if(!mInitialized) {
            mContext = context.getApplicationContext();
            mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            mContext.registerReceiver(new NetworkReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            mInitialized = true;
        }

        // 每次初始化都重新初始化网络状态，因为在android 7.0 API 24的时候程序关闭后（单例依旧存在），程序无法接收
        // 网络状态广播
        mCurrentNetwork = mConnectivityManager.getActiveNetworkInfo();
        mNetworkConnected = null != mCurrentNetwork && mCurrentNetwork.isConnected();
    }

    /**
     * 网络是否连接
     * @return
     */
    public boolean isNetworkConnected() {
        return mNetworkConnected;
    }

    /**
     * 获取当前网络信息
     * @return 当前网络信息，如果有网络连接，则为null
     */
    public NetworkInfo getCurrentNetwork() {
        return mCurrentNetwork;
    }

    /**
     * 重新初始化网络状态，因为在android 7.0 API 24的时候程序关闭后（单例依旧存在），程序无法接收网络状态广播
     */
    public void updateNetworkState() {
        if(!mInitialized) {
            return;
        }
        mCurrentNetwork = mConnectivityManager.getActiveNetworkInfo();
        mNetworkConnected = null != mCurrentNetwork && mCurrentNetwork.isConnected();
    }

    /**
     * 注册一个网络状态观察者
     * @param observer
     */
    public void registerNetworkObserver(NetworkObserver observer) {
        synchronized (mNetworkObservable) {
            mNetworkObservable.registerObserver(observer);
        }
    }

    /**
     * 反注册一个网络状态观察者
     * @param observer
     */
    public void unregisterNetworkObserver(NetworkObserver observer) {
        synchronized (mNetworkObservable) {
            mNetworkObservable.unregisterObserver(observer);
        }
    }

    /**
     * 反注册所有的观察者，建议这个只在退出程序时做清理用
     */
    public void unregisterAllObservers() {
        synchronized (mNetworkObservable) {
            mNetworkObservable.unregisterAll();
        }
    }

    private class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(null == intent) {
                return;
            }
            if(!ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                return;
            }

            NetworkInfo lastNetwork = mCurrentNetwork;
            mCurrentNetwork = mConnectivityManager.getActiveNetworkInfo();
            mNetworkConnected = null != mCurrentNetwork && mCurrentNetwork.isConnected();

            mNetworkObservable.notifyNetworkChanged(mNetworkConnected, mCurrentNetwork, lastNetwork);
        }
    }

}