package com.xunda.cloudvision.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.opensource.widget.LockableViewPager;
import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.presenter.Img720ViewPresenter;
import com.xunda.cloudvision.ui.adapter.Img720ViewAdapter;
import com.xunda.cloudvision.view.IImg720ViewView;

/**
 * 720度看图
 * Created by yinglovezhuzhu@gmail.com on 2016/9/28.
 */

public class Img720ViewActivity extends BaseActivity implements IImg720ViewView {

    private Img720ViewPresenter mImg720ViewPresenter;

    private Img720ViewAdapter mAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_720_view_img);

        mImg720ViewPresenter = new Img720ViewPresenter(this);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_720_view_img_back:
                finish(RESULT_CANCELED, null);
                break;
            default:
                break;
        }
    }

    private void initView() {
        findViewById(R.id.ibtn_720_view_img_back).setOnClickListener(this);

        final LockableViewPager viewPager = (LockableViewPager) findViewById(R.id.vp_720_view_img);
        viewPager.setOffscreenPageLimit(3);
        mAdpater = new Img720ViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdpater);

        Intent intent = getIntent();
        if(null != intent) {
            final int position = intent.getIntExtra(Config.EXTRA_POSITION, 0);
            viewPager.setCurrentItem(position, false);
        }
    }
}
