package com.vrcvp.cloudvision;

import android.app.Application;

import com.vrcvp.cloudvision.db.WeatherDBHelper;
import com.vrcvp.cloudvision.utils.DataManager;
import com.vrcvp.cloudvision.utils.LogUtils;
import com.vrcvp.cloudvision.utils.NetworkManager;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.utils.Utils;

import java.security.NoSuchAlgorithmException;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 程序Application
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public class CVApplication extends Application {

    private static final String TAG = "CVApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        WeatherDBHelper.copyDatabaseFileIfNeeded(this);
        NetworkManager.getInstance().initialized(this);
        DataManager.getInstance().initialize(this);
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);

        setJPushAlias(1);
    }

    /**
     * 设置极光推送JPush的别名（Alias）
     * @param times 次数
     */
    private void setJPushAlias(final int times) {
        if(DataManager.getInstance().getJPushAliasSetResult() || times > 4) {
            // 失败后重试3次（共4次），重试3次后不再重试
            return;
        }

        // 用ClientID当做alias
        final String alias = Utils.getClientId(this);

        JPushInterface.setAlias(this, alias, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if(0 == i) {
                    DataManager.getInstance().saveJPushAliasSetResult(true);
                    LogUtils.e(TAG, "JPush alias set success: " + s);
                } else {
                    // 失败后重试3次（共4次）
                    LogUtils.e(TAG, "JPush alias set failed: " + s);
                    setJPushAlias(times + 1);
                }
            }
        });
    }
}
