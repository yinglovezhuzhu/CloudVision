package com.xunda.cloudvision.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.xunda.cloudvision.R;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

//        startActivity(new Intent(this, ActivateActivity.class));
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

    private void initView() {
        final RadioGroup rgMenuItems = (RadioGroup) findViewById(R.id.rg_main_menu_items);
        final ToggleButton tBtnMainMenu = (ToggleButton) findViewById(R.id.tbtn_main_menu);
        final Animation menuItemShowAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_main_menu_items_show);
        final Animation menuItemHideAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_main_menu_items_hide);
        rgMenuItems.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

            }
        });

        tBtnMainMenu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked) {
                    rgMenuItems.startAnimation(menuItemShowAnimation);
                    rgMenuItems.setVisibility(View.VISIBLE);
                } else {
                    rgMenuItems.startAnimation(menuItemHideAnimation);
                }

                rgMenuItems.check(0);
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
                rgMenuItems.setVisibility(View.GONE);
                tBtnMainMenu.setBackgroundResource(R.drawable.layer_list_bg_main_menu);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
    }
}
