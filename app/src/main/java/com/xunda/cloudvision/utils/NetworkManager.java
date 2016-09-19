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

    private Context mAppContext;

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

    /**
     * 初始化
     * @param context Context对象
     */
    public void initialized(Context context) {
        if(!mInitialized) {
            mAppContext = context.getApplicationContext();
            mConnectivityManager = (ConnectivityManager) mAppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            mAppContext.registerReceiver(new NetworkReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            mCurrentNetwork = mConnectivityManager.getActiveNetworkInfo();
            mNetworkConnected = null != mCurrentNetwork && mCurrentNetwork.isConnected();
            mInitialized = true;
        }
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


    /**
     * 网络状态广播接收器
     */
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
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            mNetworkConnected = !noConnectivity;
            if(mNetworkConnected) {
                mCurrentNetwork = mConnectivityManager.getActiveNetworkInfo();
            } else {
                mCurrentNetwork = null;
                // 没有网络连接，直接返回
            }

            mNetworkObservable.notifyNetworkChanged(mNetworkConnected, mCurrentNetwork, lastNetwork);
        }
    }

}