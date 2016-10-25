package com.vrcvp.cloudvision;

import android.app.Application;

import com.vrcvp.cloudvision.utils.DataManager;
import com.vrcvp.cloudvision.utils.NetworkManager;

/**
 * 程序Application
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public class CVApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NetworkManager.getInstance().initialized(this);
        DataManager.getInstance().initialize(this);
    }
}
