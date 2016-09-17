package com.xunda.cloudvision.ui.activity;

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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.presenter.MainPresenter;
import com.xunda.cloudvision.utils.LogUtils;
import com.xunda.cloudvision.utils.Utils;
import com.xunda.cloudvision.view.IMainView;

public class MainActivity extends BaseActivity implements IMainView {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPresenter = new MainPresenter(this);

        initView();

//        startActivity(new Intent(this, ActivateActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(null != mMainPresenter) {
            mMainPresenter.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(null != mMainPresenter) {
            mMainPresenter.onResume();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_menu_home:
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeUpdate(String time) {
        mTvTopBarTime.setText(time);
    }

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
        final ToggleButton tBtnMainMenu = (ToggleButton) findViewById(R.id.tbtn_main_menu);
        final Animation menuItemShowAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_main_menu_items_show);
        final Animation menuItemHideAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_main_menu_items_hide);

        tBtnMainMenu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked) {
                    menuItemView.startAnimation(menuItemShowAnimation);
                    menuItemView.setVisibility(View.VISIBLE);
                } else {
                    menuItemView.startAnimation(menuItemHideAnimation);
                }
                mCbVoice.setChecked(false);
                mCbSetting.setChecked(false);
            }
        });
        findViewById(R.id.btn_main_menu_home).setOnClickListener(this);
        menuItemShowAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tBtnMainMenu.setBackgroundResource(R.drawable.layer_list_bg_main_menu_on_open);
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
                tBtnMainMenu.setBackgroundResource(R.drawable.layer_list_bg_main_menu);
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
    }
}
