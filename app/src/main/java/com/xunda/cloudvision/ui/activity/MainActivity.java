package com.xunda.cloudvision.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.resp.QueryHomeDataResp;
import com.xunda.cloudvision.presenter.MainPresenter;
import com.xunda.cloudvision.utils.NetworkManager;
import com.xunda.cloudvision.utils.StringUtils;
import com.xunda.cloudvision.view.IMainView;

import static android.R.attr.checked;

/**
 * 主界面Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public class MainActivity extends BaseActivity implements IMainView {

    /** 打开语音界面的RequestCode **/
    private static final int RC_VOICE_PAGE = 0x001;

//    /** 打开注册页面 **/
//    private static final int RC_ACTIVATE_PAGE = 0x002;

    private View mViewTopBar;
    private TextView mTvTopBarTime;
    private TextView mTvTopBarCity;
    private TextView mTvTopBarWeather;

    private View mViewBottomBar;
    private TextView mTvNotice;

    private CheckBox mCbVoice;
    private CheckBox mCbSetting;

    private PopupWindow mPwSettingMenu;

    private MainPresenter mMainPresenter;

    private Animation mNoticeEnterAnim;
    private Animation mNoticeExitAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPresenter = new MainPresenter(this, this);
        mNoticeEnterAnim = AnimationUtils.loadAnimation(this, R.anim.anim_main_menu_notice_enter);
        mNoticeExitAnim = AnimationUtils.loadAnimation(this, R.anim.anim_main_menu_notice_exit);

        initView();

        if(null != mMainPresenter) {
            mMainPresenter.onCreate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkManager.getInstance().updateNetworkState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mMainPresenter) {
            mMainPresenter.onDestroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_VOICE_PAGE:
                mCbVoice.setChecked(false);
                break;
//            case RC_ACTIVATE_PAGE:
//                if(RESULT_OK == resultCode) {
//
//                } else {
//                    finish();
//                }
//                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_menu_home:
                // 企业首页
                startActivity(new Intent(this, CorporateActivity.class));
                break;
            case R.id.btn_main_menu_setting_logout:
                startActivity(new Intent(this, ActivateActivity.class));
                finish();
                break;
            case R.id.btn_main_menu_setting_switch:
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
    public void onNoticeSettingsChanged(boolean disabled) {
        mViewBottomBar.setVisibility(disabled ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onWeatherSettingsChanged(boolean disabled) {
        mViewTopBar.setVisibility(disabled ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onNoticeUpdate(final String notice) {
        if(StringUtils.isEmpty(notice)) {
            return;
        }
        mNoticeExitAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTvNotice.setText(notice);
                mTvNotice.startAnimation(mNoticeEnterAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mTvNotice.startAnimation(mNoticeExitAnim);
    }

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {

    }

    @Override
    public void onQueryAdvertiseResult(QueryHomeDataResp result) {

    }

    /**
     * 初始化视图
     */
    private void initView() {

        mViewTopBar = findViewById(R.id.rl_main_top_bar);
        mTvTopBarTime = (TextView) findViewById(R.id.tv_main_top_bar_time);
        mTvTopBarCity = (TextView) findViewById(R.id.tv_main_top_bar_city);
        mTvTopBarWeather = (TextView) findViewById(R.id.tv_main_top_bar_weather);

        mViewBottomBar = findViewById(R.id.ll_main_bottom_bar);
        mTvNotice = (TextView) findViewById(R.id.tv_main_notice);

        mCbVoice = (CheckBox) findViewById(R.id.cb_main_menu_vice);
        mCbSetting = (CheckBox) findViewById(R.id.cb_main_menu_setting);

        final View menuItemView = findViewById(R.id.ll_main_menu_items);
        final ImageButton ibtnMenu = (ImageButton) findViewById(R.id.ibtn_main_menu);
        final Animation menuItemShowAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_main_menu_items_show);
        final Animation menuItemHideAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_main_menu_items_hide);

        ibtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpened = false;
                Object tag = v.getTag();
                if(null != tag && tag instanceof Boolean) {
                    isOpened = (boolean) tag;
                }
                if(isOpened) {
                    menuItemView.startAnimation(menuItemHideAnimation);
                    ibtnMenu.setImageResource(R.drawable.ic_main_menu_normal);
                    v.setTag(false);
                } else {
                    menuItemView.startAnimation(menuItemShowAnimation);
                    menuItemView.setVisibility(View.VISIBLE);
                    ibtnMenu.setImageResource(R.drawable.ic_main_menu_pressed);
                    v.setTag(true);
                }
                mCbVoice.setChecked(false);
                mCbSetting.setChecked(false);
            }
        });
        findViewById(R.id.btn_main_menu_home).setOnClickListener(this);
        menuItemShowAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ibtnMenu.setBackgroundResource(R.drawable.layer_list_bg_main_menu_on_open);
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
                menuItemView.setVisibility(View.GONE);
                ibtnMenu.setBackgroundResource(R.drawable.layer_list_bg_main_menu);
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
                            int padding = getResources().getDimensionPixelSize(R.dimen.contentPadding_level6);
                            mPwSettingMenu.showAsDropDown(mCbSetting, rect.right + padding, -(rect.bottom - rect.top));
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
        cbNoticeSettings.setChecked(!mMainPresenter.isNoticeEnabled());
        cbWeatherSettings.setChecked(!mMainPresenter.isWeatherEnabled());

        settingMenuContentView.findViewById(R.id.btn_main_menu_setting_logout).setOnClickListener(this);
        settingMenuContentView.findViewById(R.id.btn_main_menu_setting_switch).setOnClickListener(this);
    }
}
