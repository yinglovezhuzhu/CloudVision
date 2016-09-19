package com.xunda.cloudvision;

import android.app.Application;

import com.xunda.cloudvision.utils.NetworkManager;

/**
 * 程序Application
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public class CVApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NetworkManager.getInstance().initialized(this);
    }
}
