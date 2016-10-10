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
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.opensource.pullview.utils.ViewUtil;

/**
 * Usage The header view.
 *
 * @author yinglovezhuzhu@gmail.com
 */
public class PullHeaderView2 extends LinearLayout {

    private static final int DEFAULT_MIN_VISIBLE_HEIGHT = 200;
    /**
     * The top view content *
     */
    private LinearLayout mTopContent;
    /**
     * The background view content *
     */
    private LinearLayout mBackgroundContent;
    /**
     * The content view content *
     */
    private LinearLayout mViewContent;
    /**
     * the state view content *
     */
    private LinearLayout mStateContent;

    /**
     * The height of top view *
     */
    private int mTopViewHeight = 0;
    /**
     * The height of background content *
     */
    private int mBackgroundHeight = 0;
    /**
     * The height of content *
     */
    private int mContentViewHeight = 0;

    /**
     * The ImageView to show background image *
     */
    ImageView mIvBackground;
    /**
     * The height of this head view content.
     */
    int mViewHeight = 0;
    /**
     * The height of state view content *
     */
    int mStateViewHeight = 0;
    /**
     * The min height visible *
     */
    int mVisibleHeight = DEFAULT_MIN_VISIBLE_HEIGHT;

    /**
     * Instantiates a new ab list view header.
     *
     * @param context the context
     */
    public PullHeaderView2(Context context) {
        super(context);
        initView(context);
    }

    /**
     * Instantiates a new ab list view header.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public PullHeaderView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * Sets state view padding
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setStateContentPadding(int left, int top, int right, int bottom) {
        mStateContent.setPadding(left, top, right, bottom);
    }

    /**
     * Sets state view visibility
     *
     * @param visibility
     */
    public void setStateContentVisibility(int visibility) {
        mStateContent.setVisibility(visibility);
    }

    /**
     * Gets state view height
     *
     * @return
     */
    public int getStateViewHeight() {
        return mStateViewHeight;
    }

    /**
     * Sets background view<br/><br/>
     * <p/>
     * If you use this method to set background view, it will replace default background ImageView.
     *
     * @param backgroundView
     */
    public void setBackgroundView(View backgroundView) {
        if (null == backgroundView) {
            return;
        }
        ViewUtil.measureView(backgroundView);
        int backgroundViewHeight = backgroundView.getMeasuredHeight();
        if (backgroundViewHeight > mBackgroundHeight) {
            mBackgroundHeight = backgroundViewHeight;
            layoutBackgroundContent(mBackgroundHeight);
        }
        mBackgroundContent.removeAllViews();
        mBackgroundContent.addView(backgroundView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ViewUtil.measureView(this);
        mViewHeight = mTopViewHeight + mBackgroundHeight;
    }

    public void setBackgroundView(int layoutId) {
        View backgroundView = null;
        try {
            backgroundView = View.inflate(getContext(), layoutId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setBackgroundView(backgroundView);
    }

    /**
     * Sets header content view.
     *
     * @param contentView
     */
    public void setContentView(View contentView) {
        if (null == contentView) {
            return;
        }
        ViewUtil.measureView(contentView);
        int contentViewHeight = contentView.getMeasuredHeight();
        if (contentViewHeight > mBackgroundHeight) {
            mBackgroundHeight = contentViewHeight;
            layoutBackgroundContent(mBackgroundHeight);
        }
        mViewContent.removeAllViews();
        mViewContent.addView(contentView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewUtil.measureView(this);
        mContentViewHeight = contentViewHeight;
        mVisibleHeight = mContentViewHeight > mVisibleHeight
                ? mContentViewHeight : mVisibleHeight; //最小可见高度为不小于内容区域高度
        mViewHeight = mTopViewHeight + mBackgroundHeight;
    }

    public void setContentView(int layoutId) {
        View contentView = null;
        try {
            contentView = View.inflate(getContext(), layoutId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(contentView);
    }

    /**
     * Sets top view
     *
     * @param layoutId
     */
    public void setTopView(int layoutId) {
        View topView = null;
        try {
            topView = View.inflate(getContext(), layoutId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTopView(topView);
    }

    /**
     * Sets top view
     *
     * @param topView
     */
    public void setTopView(View topView) {
        if (null == topView) {
            return;
        }
        ViewUtil.measureView(topView);
        int topViewHeight = topView.getMeasuredHeight();
        if (topViewHeight > mTopViewHeight) {
            mTopViewHeight = topViewHeight;
        }
        mTopContent.removeAllViews();
        mTopContent.addView(topView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewUtil.measureView(this);
        mViewHeight = mTopViewHeight + mBackgroundHeight;
    }


    /**
     * Inits the view.
     *
     * @param context the context
     */
    private void initView(Context context) {

        setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        View.inflate(context, R.layout.layout_pullview_header2, this);

        mTopContent = (LinearLayout) findViewById(R.id.ll_pull_listview_header_top);
        mBackgroundContent = (LinearLayout) findViewById(R.id.ll_pull_listview_header_bg_content);
        mViewContent = (LinearLayout) findViewById(R.id.ll_pull_listview_header_view_content);
        mStateContent = (LinearLayout) findViewById(R.id.ll_pull_listview_header_state_content);
        mIvBackground = (ImageView) findViewById(R.id.iv_pull_listview_header_bg);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mBackgroundHeight = (3 * dm.widthPixels) / 4;

        layoutBackgroundContent(mBackgroundHeight);

        ViewUtil.measureView(this);
        mViewHeight = this.getMeasuredHeight();
        mTopViewHeight = mTopContent.getMeasuredHeight();
        mContentViewHeight = mViewContent.getMeasuredHeight();
        mStateViewHeight = mStateContent.getMeasuredHeight();
        mVisibleHeight = mContentViewHeight > mVisibleHeight
                ? mContentViewHeight : mVisibleHeight; //最小可见高度为不小于内容区域高度
        mStateContent.setPadding(0, mViewHeight - mContentViewHeight - mStateViewHeight, 0, 0);
    }

    /**
     * Layout background content.
     *
     * @param height
     */
    private void layoutBackgroundContent(int height) {
        ViewGroup.LayoutParams bgContentParams = mBackgroundContent.getLayoutParams();
        if (null == bgContentParams) {
            bgContentParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        bgContentParams.height = height;
        mBackgroundContent.setLayoutParams(bgContentParams);
    }
}
