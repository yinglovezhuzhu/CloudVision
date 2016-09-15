package com.xunda.cloudvision.ui.activity;

import android.os.Bundle;
import android.view.View;
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
        rgMenuItems.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

            }
        });

        final ToggleButton tBtnMainMenu = (ToggleButton) findViewById(R.id.tbtn_main_menu);
        tBtnMainMenu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                rgMenuItems.setVisibility(checked ? View.VISIBLE : View.GONE);
                rgMenuItems.check(0);
            }
        });
        findViewById(R.id.btn_main_menu_home).setOnClickListener(this);
    }
}
