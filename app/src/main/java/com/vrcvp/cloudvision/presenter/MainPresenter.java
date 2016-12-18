package com.vrcvp.cloudvision.presenter;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.vrcvp.cloudvision.bean.InfoBean;
import com.vrcvp.cloudvision.bean.NoticeBean;
import com.vrcvp.cloudvision.bean.UpdateInfo;
import com.vrcvp.cloudvision.bean.WeatherInfo;
import com.vrcvp.cloudvision.bean.resp.CheckUpdateResp;
import com.vrcvp.cloudvision.bean.resp.FindInfoResp;
import com.vrcvp.cloudvision.bean.resp.QueryAdvertiseResp;
import com.vrcvp.cloudvision.bean.resp.QueryNoticeResp;
import com.vrcvp.cloudvision.downloader.DownloadListener;
import com.vrcvp.cloudvision.downloader.Downloader;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.model.IMainModel;
import com.vrcvp.cloudvision.model.MainModel;
import com.vrcvp.cloudvision.utils.DataManager;
import com.vrcvp.cloudvision.utils.LogUtils;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.view.IMainView;

import java.io.File;

/**
 * Main主页面Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/16.
 */
public class MainPresenter implements Handler.Callback, BDLocationListener {

    private static final String TAG = "MainPresenter";

    /** 时间更新消息 **/
    private static final int MSG_TIME_UPDATE = 0x00;
    /** 公告更新消息 **/
    public static final int MSG_NOTICE_UPDATE = 0x01;

    /** 公告更新时间间隔 **/
    private static final int NOTICE_UPDATE_TIME = 1000 * 10;

    private Context mContext;
    private IMainView mMainView;
    private IMainModel mMainModel;

    private Downloader mApkDownloader;

    private Handler mHandler = new Handler(this);

    private LocationClient mLocationClient = null;
    private BDLocation mBDLocation;
    private WeatherInfo mWeatherInfo;

    public MainPresenter(Context context, IMainView view) {
        mContext = context;
        mMainView = view;
        mMainModel = new MainModel(mContext);
        mLocationClient = new LocationClient(mContext.getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(this);
    }

    /**
     * Activity回到前台
     */
    public void onCreate() {
        mMainView.onTimeUpdate(StringUtils.formatTimeMillis(System.currentTimeMillis()));
        mMainView.onNoticeUpdate(mMainModel.nextNotice());

        updateTime();

        initLocation();

        if(mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        } else {
            mLocationClient.start();
        }

        checkUpdate();
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
        if(mLocationClient.isStarted()) {
            mLocationClient.stop();
        }

        cancelFindInfo();
        cancelQueryAdvertise();
        cancelQueryNotice();
        cancelCheckUpdate();
        cancelDownloadApk();
    }

    /**
     * 公告设置改变
     * @param disabled 是否关闭， true 关闭，false 开启
     */
    public void onNoticeSettingsChanged(boolean disabled) {
        mMainModel.onNoticeSettingsChanged(disabled);
        mMainView.onNoticeSettingsChanged(disabled);
        updateNotice();
    }

    /**
     * 天气设置改变
     * @param disabled 是否关闭， true 关闭， false 开启
     */
    public void onWeatherSettingsChanged(boolean disabled) {
        mMainModel.onWeatherSettingsChanged(disabled);
        mMainView.onWeatherSettingsChanged(disabled);
        if(!disabled) {
            if(null != mWeatherInfo) {
                mMainView.onWeatherUpdate(mWeatherInfo);
            }
            if(null != mBDLocation) {
                mMainView.onBDLocationUpdate(mBDLocation);
                mMainModel.queryCityWeather(mBDLocation.getCity(), new HttpAsyncTask.Callback<WeatherInfo>() {
                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void onCanceled() {

                    }

                    @Override
                    public void onResult(WeatherInfo result) {
                        mWeatherInfo = result;
                        mMainView.onWeatherUpdate(result);
                    }
                });
            }
            updateTime();

        }
    }

    /**
     * 公告是否关闭
     * @return true 关闭， false 启用（默认启用）
     */
    public boolean isNoticeDisabled() {
        return mMainModel.isNoticeDisabled();
    }

    /**
     * 天气是否禁用
     * @return true 禁用， false启用（默认启用）
     */
    public boolean isWeatherDisabled() {
        return mMainModel.isWeatherDisabled();
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case MSG_TIME_UPDATE:
                mMainView.onTimeUpdate(StringUtils.formatTimeMillis(System.currentTimeMillis()));
                mHandler.sendEmptyMessageDelayed(MSG_TIME_UPDATE, 1000);
                return true;
            case MSG_NOTICE_UPDATE:
                final NoticeBean notice = mMainModel.nextNotice();
                if(null != notice) {
                    mMainView.onNoticeUpdate(notice);
                    mHandler.sendEmptyMessageDelayed(MSG_NOTICE_UPDATE, NOTICE_UPDATE_TIME);
                }
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
     * 激活成功
     */
    public void onActivateSuccess() {
        findInfo();
        queryAdvertise();
        queryNotice();
    }

    /**
     * 下载apk文件
     * @param updateInfo 更新信息
     */
    public void downloadApk(final UpdateInfo updateInfo) {
        if(null == updateInfo || StringUtils.isEmpty(updateInfo.getDownloadUrl())) {
            mMainView.onDownloadApkError(updateInfo, HttpStatus.SC_BAD_REQUEST, "下载链接为空");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                File saveDir = mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                if(null == saveDir) {
                    saveDir = mContext.getFilesDir();
                }
                if(null == saveDir) {
                    saveDir = Environment.getExternalStorageDirectory();
                }

                mApkDownloader = new Downloader(mContext, updateInfo.getDownloadUrl(), false, saveDir, null);
                try {
                    mApkDownloader.download(".apk", new DownloadListener() {
                        @Override
                        public void onProgressUpdate(final int downloadedSize, final int totalSize) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mMainView.onDownloadApkProgressUpdate(updateInfo, downloadedSize, totalSize);
                                }
                            });
                        }

                        @Override
                        public void onError(final int code, final String message) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mMainView.onDownloadApkError(updateInfo, code, message);
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mMainView.onDownloadApkError(updateInfo, HttpStatus.SC_EXPECTATION_FAILED, "下载apk文件出现异常");
                        }
                    });
                }
                if(mApkDownloader.isFinished()) {
                    final File savedFile = mApkDownloader.getSavedFile();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mMainView.onDownloadApkFinished(updateInfo, savedFile.getPath());
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 取消下载文件
     */
    public void cancelDownloadApk() {
        if(null != mApkDownloader && !mApkDownloader.isStop()) {
            mApkDownloader.stop();
        }
    }

    /**
     * 登出
     */
    public void logout() {
        mMainModel.logout();
        DataManager.getInstance().logout();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        //Receive Location
        mMainView.onBDLocationUpdate(bdLocation);
        if(null != bdLocation && !StringUtils.isEmpty(bdLocation.getCity())) {
            LogUtils.d(TAG, "百度定位成功，城市-" + bdLocation.getCity());
            mMainModel.queryCityWeather(bdLocation.getCity(), new HttpAsyncTask.Callback<WeatherInfo>() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void onCanceled() {

                }

                @Override
                public void onResult(WeatherInfo result) {
                    mWeatherInfo = result;
                    mMainView.onWeatherUpdate(result);
                }
            });
            mBDLocation = bdLocation;
//            mLocationClient.stop();
        }
    }

    /**
     * 初始化百度定位
     */
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 查询首页广告
     */
    private void queryAdvertise() {
        mMainModel.queryAdvertise(new HttpAsyncTask.Callback<QueryAdvertiseResp>() {
            @Override
            public void onPreExecute() {
                mMainView.onPreExecute(null);
            }

            @Override
            public void onCanceled() {
                mMainView.onCanceled(null);
            }

            @Override
            public void onResult(QueryAdvertiseResp result) {
                mMainView.onQueryAdvertiseResult(result);
            }
        });
    }

    /**
     * 取消查询广告异步任务
     */
    private void cancelQueryAdvertise() {
        mMainModel.cancelQueryAdvertise();
    }

    /**
     * 查询公告数据
     */
    private void queryNotice() {
        mMainModel.queryNotice(new HttpAsyncTask.Callback<QueryNoticeResp>() {
            @Override
            public void onPreExecute() {
                mMainView.onPreExecute(null);
            }

            @Override
            public void onCanceled() {
                mMainView.onCanceled(null);
            }

            @Override
            public void onResult(QueryNoticeResp result) {
                if(null != result) {
                    updateNotice();
                }
                mMainView.onQueryNoticeResult(result);
            }
        });
    }

    /**
     * 取消查询公告异步任务
     */
    private void cancelQueryNotice() {
        mMainModel.cancelQueryNotice();
    }

    /**
     * 查找广告机信息
     */
    private void findInfo() {
        mMainModel.findInfo(new HttpAsyncTask.Callback<FindInfoResp>() {
            @Override
            public void onPreExecute() {
                mMainView.onPreExecute(null);
            }

            @Override
            public void onCanceled() {
                mMainView.onCanceled(null);
            }

            @Override
            public void onResult(FindInfoResp result) {
                if(null != result) {
                    InfoBean data = result.getData();
                    if(null != data) {
                        DataManager.getInstance().updateAndroidName(data.getAndroidName());
                        DataManager.getInstance().updateAndroidGender(data.getVoiceSex());
                    }
                }
                mMainView.onFindInfoResult(result);
            }
        });
    }

    /**
     * 取消查找广告机信息
     */
    private void cancelFindInfo() {
        mMainModel.cancelFindInfo();
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        mMainModel.checkUpdate(new HttpAsyncTask.Callback<CheckUpdateResp>() {
            @Override
            public void onPreExecute() {
                mMainView.onPreExecute(null);
            }

            @Override
            public void onCanceled() {
                mMainView.onCanceled(null);
            }

            @Override
            public void onResult(CheckUpdateResp result) {
                mMainView.onCheckUpdateResult(result);
            }
        });
    }

    /**
     * 取消检查更新任务
     */
    private void cancelCheckUpdate() {
        mMainModel.cancelCheckUpdate();
    }

    /**
     * 更新时间
     */
    private void updateTime() {
        if(null != mHandler) {
            if(mHandler.hasMessages(MSG_TIME_UPDATE)) {
                mHandler.removeMessages(MSG_TIME_UPDATE);
            }
            if(!isWeatherDisabled()) {
                // 延迟一秒
                mHandler.sendEmptyMessage(MSG_TIME_UPDATE);
            }
        }
    }

    /**
     * 开始轮播公告
     */
    private void updateNotice() {
        if(null != mHandler) {
            if(mHandler.hasMessages(MSG_NOTICE_UPDATE)) {
                mHandler.removeMessages(MSG_NOTICE_UPDATE);
            }
            if(!isNoticeDisabled()) {
                mHandler.sendEmptyMessage(MSG_NOTICE_UPDATE);
            }
        }
    }
}
