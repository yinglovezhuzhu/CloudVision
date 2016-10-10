/*
 * Copyright (C)2016. The Android Open Source Project.
 *
 *          yinglovezhuzhu@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.opensource.pullview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opensource.pullview.utils.ViewUtil;

/**
 * Usage The header view.
 *
 * @author yinglovezhuzhu@gmail.com
 */
public class PullHeaderView extends LinearLayout {

    /**
     * The arrow image view.
     */
    private ImageView mArrowImageView;
    /**
     * The header progress bar.
     */
    private ProgressBar mProgress;
    /**
     * The tips textview.
     */
    private TextView mTvTitle;
    /**
     * The header time view.
     */
    private TextView mTvLabel;

    /**
     * The head content height.
     */
    int mViewHeight;

    /**
     * Instantiates a new ab list view header.
     *
     * @param context the context
     */
    public PullHeaderView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * Instantiates a new ab list view header.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public PullHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * Set arrow image visibility
     *
     * @param visibility
     */
    public void setArrowVisibility(int visibility) {
        mArrowImageView.setVisibility(visibility);
    }

    /**
     * Set progress visibility
     *
     * @param visibility
     */
    public void setProgressVisibility(int visibility) {
        mProgress.setVisibility(visibility);
    }

    /**
     * Set title text visibility.
     *
     * @param visibility
     */
    public void setTitleVisibility(int visibility) {
        mTvTitle.setVisibility(visibility);
    }

    /**
     * Set title text
     *
     * @param text
     */
    public void setTitleText(CharSequence text) {
        mTvTitle.setText(text);
    }

    /**
     * Set titme text.
     *
     * @param resid
     * @return
     */
    public void setTitleText(int resid) {
        mTvTitle.setText(resid);
    }

    /**
     * Set label text visibility<br>
     * <p> {@link android.view.View#VISIBLE} default.
     *
     * @param visibility
     */
    public void setLabelVisibility(int visibility) {
        mTvLabel.setVisibility(visibility);
    }

    /**
     * Set label text.
     *
     * @param text
     */
    public void setLabelText(CharSequence text) {
        mTvLabel.setText(text);
    }

    /**
     * Set label text.
     *
     * @param resid
     */
    public void setLabelText(int resid) {
        mTvLabel.setText(resid);
    }

    /**
     * Start animation of arrow image
     *
     * @param animation
     */
    public void startArrowAnimation(Animation animation) {
        if (null == animation) {
            mArrowImageView.clearAnimation();
            return;
        }
        if (!animation.equals(mArrowImageView.getAnimation())) {
            mArrowImageView.clearAnimation();
            mArrowImageView.startAnimation(animation);
        }
    }

    /**
     * set last refresh time.
     *
     * @param time the new refresh time
     */
    public void setRefreshTime(String time) {
        mTvLabel.setText(time);
    }


    /**
     * Set title text color
     *
     * @param color
     * @throws
     */
    public void setTitleTextColor(int color) {
        mTvTitle.setTextColor(color);
    }

    /**
     * Set label text color
     *
     * @param color
     */
    public void setLabelTextColor(int color) {
        mTvLabel.setTextColor(color);
    }

    /**
     * Inits the view.
     *
     * @param context the context
     */
    private void initView(Context context) {

        View.inflate(context, R.layout.layout_pullview_header, this);

        mArrowImageView = (ImageView) findViewById(R.id.iv_pullview_header_arrow);
        mProgress = (ProgressBar) findViewById(R.id.pb_pullview_header_progress);
        mTvTitle = (TextView) findViewById(R.id.tv_pullview_header_title);
        mTvLabel = (TextView) findViewById(R.id.tv_pullview_header_label);

        //Get height of this header view.
        ViewUtil.measureView(this);
        mViewHeight = this.getMeasuredHeight();

    }
}
