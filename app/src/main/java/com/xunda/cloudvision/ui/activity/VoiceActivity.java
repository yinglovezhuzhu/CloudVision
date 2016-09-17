package com.xunda.cloudvision.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.xunda.cloudvision.R;

/**
 * 语音Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/17.
 */
public class VoiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // 这里的代码让界面铺满屏幕
//        WindowManager.LayoutParams attrs = getWindow().getAttributes();
//        if(null == attrs) {
//            attrs = new WindowManager.LayoutParams();
//        }
//        attrs.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        attrs.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        getWindow().setAttributes(attrs);

        setContentView(R.layout.activity_voice);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_voice_close:
                finish(RESULT_CANCELED, null);
                break;
            default:
                break;
        }
    }

    private void initView() {
        findViewById(R.id.ibtn_voice_close).setOnClickListener(this);
    }
}
