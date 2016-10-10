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
import android.graphics.drawable.Drawable;
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
public class PullFooterView extends LinearLayout {

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
     * The head content height.
     */
    int mViewHeight;

    /**
     * Instantiates a new ab list view header.
     *
     * @param context the context
     */
    public PullFooterView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * Instantiates a new ab list view header.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public PullFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * Inits the view.
     *
     * @param context the context
     */
    private void initView(Context context) {

        View.inflate(context, R.layout.layout_pullview_footer, this);

        mArrowImageView = (ImageView) findViewById(R.id.iv_pullview_footer_arrow);
        mProgress = (ProgressBar) findViewById(R.id.pb_pullview_footer_progress);
        mTvTitle = (TextView) findViewById(R.id.tv_pullview_footer_title);

        //Get height of this header view.
        ViewUtil.measureView(this);
        mViewHeight = this.getMeasuredHeight();
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
    public void setTitileVisibility(int visibility) {
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
     * Set title text
     *
     * @param resId
     */
    public void setTitleText(int resId) {
        mTvTitle.setText(resId);
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
     * Gets the header height.
     *
     * @return the header height
     */
    public int getViewHeight() {
        return mViewHeight;
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
     * Set Background color
     *
     * @param color
     * @throws
     */
    public void setBackgroundColor(int color) {
        setBackgroundColor(color);
    }

    /**
     * Get progress
     *
     * @return
     * @throws
     */
    public ProgressBar getProgress() {
        return mProgress;
    }

    /**
     * Set progress drawable
     *
     * @return
     * @throws
     */
    public void setHeaderProgressBarDrawable(Drawable indeterminateDrawable) {
        mProgress.setIndeterminateDrawable(indeterminateDrawable);
    }
}
