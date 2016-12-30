package com.vrcvp.cloudvision.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 重启app服务
 * Created by yinglovezhuzhu@gmail.com on 2016/12/30.
 */

public class RestartService extends Service {

    public static final String EXTRA_DELAY_TIME = "extra_delay_time";

    public static final String EXTRA_PACKAGE_NAME = "extra_package_name";

    private final  Handler mHandler = new Handler();

    public RestartService() {
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        final long delay = intent.getLongExtra(EXTRA_DELAY_TIME, 1000);
        final String packageName = intent.getStringExtra(EXTRA_PACKAGE_NAME);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
                startActivity(LaunchIntent);
                RestartService.this.stopSelf();
            }
        }, delay);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
