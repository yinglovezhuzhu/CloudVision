package com.vrcvp.cloudvision.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.AdvertiseBean;
import com.vrcvp.cloudvision.bean.InfoBean;
import com.vrcvp.cloudvision.bean.NoticeBean;
import com.vrcvp.cloudvision.bean.UpdateInfo;
import com.vrcvp.cloudvision.bean.WeatherInfo;
import com.vrcvp.cloudvision.bean.resp.CheckUpdateResp;
import com.vrcvp.cloudvision.bean.resp.FindInfoResp;
import com.vrcvp.cloudvision.bean.resp.QueryAdvertiseResp;
import com.vrcvp.cloudvision.bean.resp.QueryNoticeResp;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.presenter.MainPresenter;
import com.vrcvp.cloudvision.service.RestartService;
import com.vrcvp.cloudvision.ui.fragment.MainAdFragment;
import com.vrcvp.cloudvision.utils.DataManager;
import com.vrcvp.cloudvision.utils.LogUtils;
import com.vrcvp.cloudvision.utils.NetworkManager;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.utils.Utils;
import com.vrcvp.cloudvision.view.IMainView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * 主界面Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public class MainActivity extends BaseActivity implements IMainView {

    private static final List<String> DANGEROUS_PERMISSIONS = new ArrayList<>();
    static {
        DANGEROUS_PERMISSIONS.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.ACCESS_FINE_LOCATION);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.READ_PHONE_STATE);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            try {
                DANGEROUS_PERMISSIONS.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            } catch (Exception e) {
                // do nothing
            }
        }
        DANGEROUS_PERMISSIONS.add(Manifest.permission.WRITE_SETTINGS);
    }
    private View mViewTopBar;
    private TextView mTvTopBarTime;
    private TextView mTvTopBarCity;
    private TextView mTvTopBarWeather;
    private ImageView mIvTopBarWeatherIcon;

    private View mViewBottomBar;
    private TextView mTvNotice;

    private CheckBox mCbVoice;
    private CheckBox mCbSetting;

    private View mMenuItemView;
    private ImageButton mIBtnMenu;
    private PopupWindow mPwSettingMenu;

    private MainPresenter mMainPresenter;

    private Animation mNoticeEnterAnim;
    private Animation mNoticeExitAnim;

    private MainAdFragment mAdOne;
    private MainAdFragment mAdTwo;
    private MainAdFragment mAdThree;

    private AlertDialog mDownloadApkDialog;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            checkSelfPermission();
        }

        mMainPresenter = new MainPresenter(this, this);
        mNoticeEnterAnim = AnimationUtils.loadAnimation(this, R.anim.anim_main_menu_notice_enter);
        mNoticeExitAnim = AnimationUtils.loadAnimation(this, R.anim.anim_main_menu_notice_exit);

        initView();

        if(null != mMainPresenter) {
            mMainPresenter.onCreate();
        }

        if(mMainPresenter.isActivated()) {
            mMainPresenter.onActivateSuccess();
        } else {
            resetAndActivate();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkManager.getInstance().updateNetworkState();
    }

    @Override
    protected void onDestroy() {
        if(null != mMainPresenter) {
            mMainPresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_VOICE_PAGE:
                mCbVoice.setChecked(false);
                if(RESULT_UNAUTHORIZED == resultCode) {
                    // 需要登录
                    resetAndActivate();
                }
                break;
            case RC_CORPORATE_PAGE:
                if(RESULT_UNAUTHORIZED == resultCode) {
                    // 需要登录
                    resetAndActivate();
                }
                break;
            case RC_ACTIVATE_PAGE:
                if(RESULT_OK == resultCode) {
                    mMainPresenter.onActivateSuccess();
                } else {
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 && RC_REQUEST_PERMISSIONS == requestCode) {
            if(permissions.length > 0 && grantResults.length > 0 && permissions.length == grantResults.length ) {
                for (int result : grantResults) {
                    if(PackageManager.PERMISSION_DENIED == result) {
                        showPermissionSettingDialog();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_menu_home:
                // 企业首页
                startActivityForResult(new Intent(this, CorporateActivity.class), RC_CORPORATE_PAGE);
                break;
            case R.id.btn_main_menu_setting_logout:
//                mMainPresenter.logout();
//                startActivity(new Intent(this, ActivateActivity.class));
                finish();
                break;
            case R.id.btn_main_menu_setting_switch:
                switchAccount();
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeUpdate(String time) {
        mTvTopBarTime.setText(time);
    }

    @Override
    public void onBDLocationUpdate(BDLocation bdLocation) {
        if(null == bdLocation) {
            mTvTopBarCity.setVisibility(View.GONE);
            return;
        }
        mTvTopBarCity.setVisibility(View.VISIBLE);
        mTvTopBarCity.setText(bdLocation.getCity());
    }

    @Override
    public void onWeatherUpdate(WeatherInfo weatherInfo) {
        if(null == weatherInfo) {
            mTvTopBarWeather.setVisibility(View.GONE);
            return;
        }
        mTvTopBarWeather.setVisibility(View.VISIBLE);
        mTvTopBarWeather.setText(String.format(getString(R.string.str_weather_format),
                weatherInfo.getWeather(), weatherInfo.getTemp1(), weatherInfo.getTemp2()));
    }

    @Override
    public void onNoticeSettingsChanged(boolean disabled) {
        if(null != mViewBottomBar) {
            mViewBottomBar.setVisibility(disabled ? View.GONE : View.VISIBLE);
        }
        if(null != mAdOne) {
            mAdOne.resizeVideoView();
        }
        if(null != mAdTwo) {
            mAdTwo.resizeVideoView();
        }
        if(null != mAdThree) {
            mAdThree.resizeVideoView();
        }
    }

    @Override
    public void onWeatherSettingsChanged(boolean disabled) {
        if(null != mViewTopBar) {
            mViewTopBar.setVisibility(disabled ? View.GONE : View.VISIBLE);
        }
        if(null != mAdOne) {
            mAdOne.resizeVideoView();
        }
        if(null != mAdTwo) {
            mAdTwo.resizeVideoView();
        }
        if(null != mAdThree) {
            mAdThree.resizeVideoView();
        }
    }

    @Override
    public void onNoticeUpdate(final NoticeBean notice) {
        if(null == notice) {
            return;
        }
        if(StringUtils.isEmpty(mTvNotice.getText().toString().trim())) {
            mTvNotice.setText(notice.getName());
        } else {
            mNoticeExitAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mTvNotice.setText(notice.getName());
                    mTvNotice.startAnimation(mNoticeEnterAnim);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mTvNotice.startAnimation(mNoticeExitAnim);
        }
    }

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {

    }

    @Override
    public void onQueryAdvertiseResult(QueryAdvertiseResp result) {
        if(null == result) {

            return;
        }
        switch (result.getHttpCode()) {
            case HttpStatus.SC_OK:
                List<AdvertiseBean> advertises = result.getData();
                if(null == advertises || advertises.isEmpty()) {
                    // TODO 错误
                } else {
                    final AdvertiseBean advertiseOne = advertises.get(0);
                    if(null != mAdOne) {
                        mAdOne.setData(advertiseOne);
                    }

                    if(advertises.size() > 1) {
                        final AdvertiseBean advertiseTwo = advertises.get(1);
                        if(null != mAdTwo) {
                            mAdTwo.setData(advertiseTwo);
                        }
                    }

                    if(advertises.size() > 2) {
                        final AdvertiseBean advertiseThree = advertises.get(2);
                        if(null != mAdThree) {
                            mAdThree.setData(advertiseThree);
                        }
                    }
                }
                break;
            case HttpStatus.SC_UNAUTHORIZED:
                // 需要登录
                resetAndActivate();
                break;
            case HttpStatus.SC_CACHE_NOT_FOUND:
                // TODO 无网络，读取缓存错误
                break;
            default:
                // TODO 错误
                break;
        }
    }

    @Override
    public void onQueryNoticeResult(QueryNoticeResp result) {
        if(null == result) {
            return;
        }
        switch (result.getHttpCode()) {
            case HttpStatus.SC_UNAUTHORIZED:
                resetAndActivate();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFindInfoResult(FindInfoResp result) {
        if(null == result) {
            return;
        }
        switch (result.getHttpCode()) {
            case HttpStatus.SC_OK:
                final InfoBean infoBean = result.getData();
                if(null == infoBean) {
                    return;
                }
                Date endDate = Utils.parseTime(infoBean.getCloseTime(), Config.DATE_FORMAT_YMDHMS);
                if(null == endDate) {
                    return;
                }
                final long timeLimit = endDate.getTime() - result.getTimestamp();
                if(timeLimit < 0) {
                    LogUtils.e(TAG, "激活码已经过期");
                    // 激活码过期
                    activateCodeTimeout();
                    return;
                }

                LogUtils.d(TAG, "激活码剩余时间：" + Utils.printTime(this, timeLimit));
                showActivateCodeTimeLimit(timeLimit);
                break;
            case HttpStatus.SC_UNAUTHORIZED:
                // 需要登录
                resetAndActivate();
                break;
            case HttpStatus.SC_CACHE_NOT_FOUND:
                // TODO 无网络，读取缓存错误
                break;
            default:
                // TODO 错误
                break;
        }
    }

    @Override
    public void onCheckUpdateResult(CheckUpdateResp result) {
        // 处理检查更新结果
        if(null == result || !(HttpStatus.SC_OK == result.getHttpCode())) {
            LogUtils.e(TAG, "检查更新出现错误");
            return;
        }
        final UpdateInfo updateInfo = result.getData();
        if(null == updateInfo) {
            LogUtils.d(TAG, "没有发现新版本");
            return;
        }
        // FIXME 打包的时候去掉注释
        showUpdateDialog(updateInfo);
    }

    @Override
    public void onDownloadApkProgressUpdate(UpdateInfo updateInfo, int downloadedSize, int totalSize) {
        final int progress = downloadedSize * 100 / totalSize;
        if(null != mTvProgress) {
            mTvProgress.setText(String.format(Locale.getDefault(), getString(R.string.str_progress_text_format), progress));
        }
        if(null != mPbProgress) {
            mPbProgress.setProgress(progress);
        }
    }

    @Override
    public void onDownloadApkError(final UpdateInfo updateInfo, int code, String message) {
        LogUtils.e(TAG, "apk下载失败：\ncode: " + code + "\nmessage: " + message);
        dismissDownloadApkDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.str_download_failed);
        builder.setMessage(R.string.str_download_failed_with_error);
        builder.setPositiveButton(R.string.str_retry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDownloadDialog(updateInfo);
            }
        });
        if(UpdateInfo.TYPE_FORCE_UPDATE == updateInfo.getUpdateType()) {
            // 强制更新，取消下载退出程序
            builder.setNegativeButton(R.string.str_exit, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reset();
                    finish(RESULT_CANCELED, null);
                }
            });
        } else {
            builder.setNegativeButton(R.string.str_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        builder.show();
    }

    @Override
    public void onDownloadApkFinished(UpdateInfo updateInfo, String path) {
        LogUtils.d(TAG, "apk下载完成：" + path);
        dismissDownloadApkDialog();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.str_tips);
        builder.setMessage(R.string.str_installing_apk);
        if(UpdateInfo.TYPE_FORCE_UPDATE == updateInfo.getUpdateType()) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    builder.setCancelable(false);
                    builder.setMessage(R.string.str_update_finished);
                    builder.setPositiveButton(R.string.str_restart_app, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            restartApp();
                        }
                    });
                    builder.show();
                }
            }, 1000 * 30);
        } else {
            builder.setPositiveButton(R.string.str_install_background, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        builder.show();

        Utils.smdtSilentInstallApk(this, path);
    }

    /**
     * 初始化视图
     */
    private void initView() {

        mViewTopBar = findViewById(R.id.rl_main_top_bar);
        mTvTopBarTime = (TextView) findViewById(R.id.tv_main_top_bar_time);
        mTvTopBarCity = (TextView) findViewById(R.id.tv_main_top_bar_city);
        mTvTopBarWeather = (TextView) findViewById(R.id.tv_main_top_bar_weather);
        mIvTopBarWeatherIcon = (ImageView) findViewById(R.id.iv_main_top_bar_weather_icon);

        mViewBottomBar = findViewById(R.id.ll_main_bottom_bar);
        mTvNotice = (TextView) findViewById(R.id.tv_main_notice);

        mCbVoice = (CheckBox) findViewById(R.id.cb_main_menu_vice);
        mCbSetting = (CheckBox) findViewById(R.id.cb_main_menu_setting);

        mViewTopBar.setVisibility(mMainPresenter.isWeatherDisabled() ? View.GONE : View.VISIBLE);
        mViewBottomBar.setVisibility(mMainPresenter.isNoticeDisabled() ? View.GONE : View.VISIBLE);

        mMenuItemView = findViewById(R.id.ll_main_menu_items);
        mIBtnMenu = (ImageButton) findViewById(R.id.ibtn_main_menu);
        final Animation menuItemShowAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_main_menu_items_show);
        final Animation menuItemHideAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_main_menu_items_hide);

        mIBtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(View.VISIBLE == mMenuItemView.getVisibility()) {
                    mMenuItemView.startAnimation(menuItemHideAnimation);
                    mIBtnMenu.setImageResource(R.drawable.ic_main_menu_normal);
                } else {
                    mMenuItemView.startAnimation(menuItemShowAnimation);
                    mMenuItemView.setVisibility(View.VISIBLE);
                    mIBtnMenu.setImageResource(R.drawable.ic_main_menu_pressed);
                }
                mCbVoice.setChecked(false);
                mCbSetting.setChecked(false);
            }
        });
        final View menuBar = findViewById(R.id.ll_main_menu_bar);
        final FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) menuBar.getLayoutParams();
        final Rect contentFrame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(contentFrame);
        final int contentWidth = contentFrame.right - contentFrame.left;
        final int contentHeight = contentFrame.bottom - contentFrame.top;
        // 设置初始位置
        final Point position = DataManager.getInstance().getMainMenuPosition(contentFrame);
        lp.leftMargin = position.x;
        lp.topMargin = position.y;
        menuBar.setLayoutParams(lp);
        mIBtnMenu.setOnTouchListener(new View.OnTouchListener() {
            float startX;
            float startY;
            float lastX;
            float lastY;
            boolean start;
            int width = 0;
            int height = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(View.VISIBLE == mMenuItemView.getVisibility()) {
                    mMenuItemView.setVisibility(View.INVISIBLE);
                    mIBtnMenu.setImageResource(R.drawable.ic_main_menu_normal);
                    mIBtnMenu.setBackgroundResource(R.drawable.layer_list_bg_main_menu);
                    return true;
                }
                mIBtnMenu.setImageResource(R.drawable.ic_main_menu_normal);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getRawX();
                        startY = event.getRawY();
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        width = mIBtnMenu.getWidth();
                        height = mIBtnMenu.getHeight();
                        start = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (start && Math.abs(event.getRawX() - startX) < 5 && Math.abs(event.getRawY() - startY) < 5) {
                            start = false;
                            return false;
                        }
                        lp.leftMargin = lp.leftMargin + (int)(event.getRawX() - lastX);
                        lp.topMargin = lp.topMargin + (int)(event.getRawY() - lastY);
                        lp.leftMargin = lp.leftMargin < 0 ? 0 : lp.leftMargin;
                        lp.topMargin = lp.topMargin < 0 ? 0 : lp.topMargin;
                        lp.leftMargin = (lp.leftMargin + width) > contentWidth ? (contentWidth - width) : lp.leftMargin;
                        lp.topMargin = (lp.topMargin + height) > contentHeight ? (lp.topMargin - height) : lp.topMargin;
                        DataManager.getInstance().saveMainMenuPosition(new Point(lp.leftMargin, lp.topMargin));
                        menuBar.setLayoutParams(lp);
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        mIBtnMenu.setImageResource(R.drawable.ic_main_menu_normal);
                        return Math.abs(event.getRawX() - startX) > 5 || Math.abs(event.getRawY() - startY) > 5;
                    default:
                        break;
                }
                return false;
            }
        });
        findViewById(R.id.btn_main_menu_home).setOnClickListener(this);
        menuItemShowAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIBtnMenu.setBackgroundResource(R.drawable.layer_list_bg_main_menu_on_open);
            }

            @Override
            public void onAnimationEnd(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        menuItemHideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                mMenuItemView.setVisibility(View.INVISIBLE);
                mIBtnMenu.setBackgroundResource(R.drawable.layer_list_bg_main_menu);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                switch (compoundButton.getId()) {
                    case R.id.cb_main_menu_vice:
                        if(checked) {
                            mCbSetting.setChecked(false);
                            startActivityForResult(new Intent(MainActivity.this, VoiceActivity.class),
                                    RC_VOICE_PAGE);
                        }
                        break;
                    case R.id.cb_main_menu_setting:
                        if(checked) {
                            mCbVoice.setChecked(false);
                            Rect rect = new Rect();
                            mCbSetting.getGlobalVisibleRect(rect);
                            mPwSettingMenu.showAsDropDown(mCbSetting, rect.right - rect.left, -(rect.bottom - rect.top));
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        mCbVoice.setOnCheckedChangeListener(onCheckedChangeListener);
        mCbSetting.setOnCheckedChangeListener(onCheckedChangeListener);

        initSettingsSubItems();

        mAdOne = (MainAdFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main_add_one);
        mAdTwo = (MainAdFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main_add_two);
        mAdThree = (MainAdFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main_add_three);

        mViewTopBar.setVisibility(mMainPresenter.isWeatherDisabled() ? View.GONE : View.VISIBLE);
        mViewBottomBar.setVisibility(mMainPresenter.isWeatherDisabled() ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置子菜单初始化
     */
    private void initSettingsSubItems() {
        final View settingMenuContentView = View.inflate(this, R.layout.layout_main_setting_items, null);
        mPwSettingMenu = new PopupWindow(settingMenuContentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPwSettingMenu.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPwSettingMenu.setOutsideTouchable(true);
        mPwSettingMenu.setAnimationStyle(R.style.MainMenuSubItemAnimStyle);
        mPwSettingMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mCbSetting.setChecked(false);
            }
        });
        final CheckBox cbNoticeSettings = (CheckBox) settingMenuContentView.findViewById(R.id.cb_main_menu_setting_notice);
        final CheckBox cbWeatherSettings = (CheckBox) settingMenuContentView.findViewById(R.id.cb_main_menu_setting_weather);
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                switch (compoundButton.getId()) {
                    case R.id.cb_main_menu_setting_notice:
                        mMainPresenter.onNoticeSettingsChanged(checked);
                        break;
                    case R.id.cb_main_menu_setting_weather:
                        mMainPresenter.onWeatherSettingsChanged(checked);
                        break;
                    default:
                        break;
                }
            }
        };
        cbNoticeSettings.setOnCheckedChangeListener(onCheckedChangeListener);
        cbWeatherSettings.setOnCheckedChangeListener(onCheckedChangeListener);
        cbNoticeSettings.setChecked(mMainPresenter.isNoticeDisabled());
        cbWeatherSettings.setChecked(mMainPresenter.isWeatherDisabled());

        settingMenuContentView.findViewById(R.id.btn_main_menu_setting_logout).setOnClickListener(this);
        settingMenuContentView.findViewById(R.id.btn_main_menu_setting_switch).setOnClickListener(this);
    }

    /**
     * 权限是否已经授权
     * @param permission 权限
     * @return 是否已经授权
     */
    private boolean isPermissionGranted(String permission) {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, permission);
    }

    /**
     * 自检权限
     */
    private void checkSelfPermission() {
        final Set<String> needRequestPermissions = new HashSet<>();
        for (String permission : DANGEROUS_PERMISSIONS) {
            if(isPermissionGranted(permission)) {
                // 权限已授权
                continue;
            }
            needRequestPermissions.add(permission);
        }

        // 权限未授权
        if(needRequestPermissions.isEmpty()) {
            return;
        }
        ActivityCompat.requestPermissions(this, needRequestPermissions.toArray(new String []{}), RC_REQUEST_PERMISSIONS);
    }

    private void showPermissionSettingDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.str_need_request_permission)
                .setNegativeButton(R.string.str_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkSelfPermission();
                            }
                        })
                .setPositiveButton(R.string.str_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                .create()
                .show();
    }

    /**
     * 切换账号
     */
    private void switchAccount() {
        resetAndActivate();
//                finish();
    }

    /**
     * 重置
     */
    private void reset() {
        // 清除数据和重置UI
        mAdOne.clearData();
        mAdTwo.clearData();
        mAdThree.clearData();
        mPwSettingMenu.dismiss();
        mMenuItemView.setVisibility(View.INVISIBLE);
        mIBtnMenu.setImageResource(R.drawable.ic_main_menu_normal);
        mIBtnMenu.setBackgroundResource(R.drawable.layer_list_bg_main_menu);
    }

    /**
     * 重置并且跳转到激活页面
     */
    private void resetAndActivate() {

        reset();

        mMainPresenter.logout();

        // 跳转
        Intent intent = new Intent(this, ActivateActivity.class);
        startActivityForResult(intent, RC_ACTIVATE_PAGE);
    }

    /**
     * 激活码过期了
     */
    private void activateCodeTimeout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.str_warning)
                .setMessage(R.string.str_activate_code_time_out)
                .setPositiveButton(R.string.str_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 激活码过期，退出需要重新激活
                        switchAccount();
                    }
                })
                .show();
    }

    /**
     * 显示激活码剩余时间
     */
    private void showActivateCodeTimeLimit(long timeLimit) {
        if(timeLimit <= 0) {
            return;
        }
        if(timeLimit > 1000 * 60 * 60 * 24 * 3) {
            // 大于3天不提示
            return;
        }
        final String timeLimitStr = Utils.printTime(this, timeLimit);
        new AlertDialog.Builder(this)
                .setTitle(R.string.str_tips)
                .setMessage(String.format(Locale.getDefault(), getString(R.string.str_activate_code_time_limit_format), timeLimitStr))
                .setPositiveButton(R.string.str_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    /**
     * 显示更新对话框
     * @param updateInfo 更新信息
     */
    private void showUpdateDialog(final UpdateInfo updateInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.str_found_new_version);
        String message = String.format(Locale.getDefault(), getString(R.string.str_version_name_format), updateInfo.getVersion());
        message += "\n";
        message += updateInfo.getRemark();
        builder.setMessage(message);
        builder.setPositiveButton(R.string.str_update_now, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 下载文件
                showDownloadDialog(updateInfo);
            }
        });
        if(UpdateInfo.TYPE_FORCE_UPDATE == updateInfo.getUpdateType()) {
            builder.setNegativeButton(R.string.str_exit, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reset();
                    finish(RESULT_CANCELED, null);
                }
            });
//        } else if(UpdateInfo.TYPE_NORMAL_UPDATE == updateInfo.getUpdateType()) {
        } else {
            builder.setNegativeButton(R.string.str_update_later, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        builder.show();
    }

    private TextView mTvProgress;
    private ProgressBar mPbProgress;

    /**
     * 显示下载对话框
     * @param updateInfo 更新信息
     */
    private void showDownloadDialog(final UpdateInfo updateInfo) {
        final View downloadView = View.inflate(this, R.layout.layout_download, null);
        mTvProgress = (TextView) downloadView.findViewById(R.id.tv_download_progress);
        mPbProgress = (ProgressBar) downloadView.findViewById(R.id.pb_download_progress);
        mTvProgress.setText(String.format(Locale.getDefault(), getString(R.string.str_progress_text_format), 0));
        mPbProgress.setProgress(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.str_downloading_new_apk);
        builder.setView(downloadView);
        if(UpdateInfo.TYPE_FORCE_UPDATE == updateInfo.getUpdateType()) {
            // 强制更新，取消下载退出程序
            builder.setNegativeButton(R.string.str_exit, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reset();
                    finish(RESULT_CANCELED, null);
                }
            });
        } else {
            // 非强制更新，取消下载
            builder.setNegativeButton(R.string.str_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mMainPresenter.cancelDownloadApk();
                }
            });
        }
        mDownloadApkDialog = builder.show();
        mMainPresenter.downloadApk(updateInfo);
    }

    /**
     * 隐藏下载apk对话框
     */
    private void dismissDownloadApkDialog() {
        if(null != mDownloadApkDialog && mDownloadApkDialog.isShowing()) {
            mDownloadApkDialog.dismiss();
        }
        mDownloadApkDialog = null;
    }

    /**
     * 重启程序
     */
    public void restartApp(){
        /**开启一个新的服务，用来重启本APP*/
        Intent intent=new Intent(getApplicationContext(), RestartService.class);
        intent.putExtra(RestartService.EXTRA_PACKAGE_NAME, getPackageName());
        intent.putExtra(RestartService.EXTRA_DELAY_TIME, 1000L);
        startService(intent);
        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }
}
