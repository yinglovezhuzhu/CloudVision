package com.vrcvp.cloudvision;

import android.app.Application;

import com.vrcvp.cloudvision.db.WeatherDBHelper;
import com.vrcvp.cloudvision.utils.DataManager;
import com.vrcvp.cloudvision.utils.LogUtils;
import com.vrcvp.cloudvision.utils.NetworkManager;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 程序Application
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public class CVApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        WeatherDBHelper.copyDatabaseFileIfNeeded(this);
        NetworkManager.getInstance().initialized(this);
        DataManager.getInstance().initialize(this);
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
        JPushInterface.setAlias(this, "AAAAAAAAAAAAAAAAA", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                LogUtils.e("XXXXXXX", i + "<>" + s );
            }
        });
    }
}
