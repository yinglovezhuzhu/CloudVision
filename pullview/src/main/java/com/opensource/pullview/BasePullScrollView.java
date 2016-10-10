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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.opensource.pullview.utils.ViewUtil;

/**
 * Created by xiaoying on 14-9-15.
 */
public abstract class BasePullScrollView extends ScrollView implements IPullView {

    /**
     * The rotate up anim.
     */
    protected Animation mDownToUpAnimation;
    /**
     * The rotate down anim.
     */
    protected Animation mUpToDownAnimation;
    /**
     * The scroll layout.
     */
    protected LinearLayout mScrollLayout;
    /**
     * The content layout
     */
    protected LinearLayout mContentLayout;
    /**
     * The foot content
     */
    protected LinearLayout mFootContent;
    /**
     * The height of foot content *
     */
    protected int mFootContentHeight;
    /**
     * The top position of the scroll
     */
    protected int mTopPosition;
    /**
     * The bottom position of the scroll
     */
    protected int mBottomPosition;
    /**
     * Enable pull refresh.
     */
    protected boolean mEnablePullRefresh = false;
    /**
     * Enable over scroll
     */
    protected boolean mEnableOverScroll = true;
    /**
     * Pull refreshing.
     */
    protected boolean mRefreshing = false;
    /**
     * The listener on refresh data.
     */
    protected OnRefreshListener mOnRefreshListener = null;
    /**
     * The state of the PullView.
     */
    protected int mState = IDEL;

    /**
     * Constructor
     *
     * @param context the context
     */
    public BasePullScrollView(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * Constructor
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public BasePullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BasePullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BasePullScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 0) {
            mContentLayout.addView(child);
        } else {
            super.addView(child);
        }
    }

    @Override
    public void addView(View child, int index) {
        if (getChildCount() > 0) {
            mContentLayout.addView(child, index);
        } else {
            super.addView(child, index);
        }
    }

    @Override
    public void addView(View child, int width, int height) {
        if (getChildCount() > 0) {
            mContentLayout.addView(child, width, height);
        } else {
            super.addView(child, width, height);
        }
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            mContentLayout.addView(child, params);
        } else {
            super.addView(child, params);
        }
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            mContentLayout.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mTopPosition = t;
        mBottomPosition = t + getHeight();
    }

    private int mLastY = 0;
    private int mStartY = 0;
    private boolean mRecording = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = (int) event.getY();
                if (!mRecording) {
                    mRecording = mBottomPosition == getChildAt(0).getMeasuredHeight();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int tempY = (int) event.getY();
                if (!mRecording) {
                    mRecording = mBottomPosition == getChildAt(0).getMeasuredHeight();
                    mStartY = mRecording ? tempY : mStartY;
                }
                if (mRecording && mEnableOverScroll) {
                    int moveY = mStartY - tempY;
                    int scrollY = moveY / OFFSET_RATIO;
                    if (moveY > 0) {
                        mFootContent.setPadding(0, 0, 0, scrollY - mFootContentHeight);
                        scrollTo(0, mTopPosition - (scrollY - mFootContentHeight - mLastY));
                    }
                    mLastY = scrollY;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mRecording) {
                    mFootContent.setPadding(0, 0, 0, -mFootContentHeight);
                }
                mRecording = false;
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * Update header view by state
     */
    protected abstract void updateHeaderViewByState(int paddingTop);

    /**
     * Refresh operation
     */
    protected abstract void refresh();


    /**
     * Add header view to scroll view.
     *
     * @param headerView
     */
    protected void addHeaderView(View headerView) {
        if (null == mContentLayout || null == headerView) {
            return;
        }
        LinearLayout.LayoutParams headerLp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mScrollLayout.addView(headerView, 0, headerLp);
    }

    /**
     * Refresh complete.
     */
    public void refreshCompleted() {
        mState = IDEL;
        mRefreshing = false;
    }

    /**
     * Set Refresh Listener.
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
        mEnablePullRefresh = null != listener;
    }

    /**
     * Sets enable over scroll.
     *
     * @param enable
     */
    public void setEnableOverScroll(boolean enable) {
        this.mEnableOverScroll = enable;
    }

    /**
     * Sets Footer background color
     *
     * @param color
     */
    public void setFooterBackgroundColor(int color) {
        mFootContent.setBackgroundColor(color);
    }

    /**
     * Sets Footer background resource.
     *
     * @param resId
     */
    public void setFooterBackgroundImageResource(int resId) {
        mFootContent.setBackgroundResource(resId);
    }

    /**
     * Gets it is refreshing<br/>
     * </br><p/>If doing refresh operation, you need to use this method before {@link #refreshCompleted()}<br/>
     * to get it is refresh operation or not.
     *
     * @return
     */
    public boolean isRefreshing() {
        return mRefreshing;
    }

    /**
     * Add footer view
     *
     * @param view
     */
    public void addFooterView(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            params = generateDefaultLayoutParams();
            if (params == null) {
                throw new IllegalArgumentException("generateDefaultLayoutParams() cannot return null");
            }
        }
        addFooterView(view, params);
    }

    /**
     * Add footer view.
     *
     * @param view
     * @param lp
     */
    public void addFooterView(View view, ViewGroup.LayoutParams lp) {
        addFooterView(view, -1, lp);
    }

    /**
     * Add footer view
     *
     * @param view
     * @param index
     * @param lp
     */
    public void addFooterView(View view, int index, ViewGroup.LayoutParams lp) {
        mFootContent.addView(view, index, lp);
        ViewUtil.measureView(mFootContent);
        mFootContentHeight = mFootContent.getMeasuredHeight();
        mFootContent.setPadding(0, 0, 0, -mFootContentHeight);
    }

    /**
     * Init the View.
     *
     * @param context the context
     */
    private void init(Context context, AttributeSet attrs) {

        if(null != attrs) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullView);
            if(a.hasValue(R.styleable.PullView_overScroll)) {
                mEnableOverScroll = a.getBoolean(R.styleable.PullView_overScroll, false);
            }
            a.recycle();
        }
        mDownToUpAnimation = new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mDownToUpAnimation.setInterpolator(new LinearInterpolator());
        mDownToUpAnimation.setDuration(ROTATE_ANIMATION_DURATION);
        mDownToUpAnimation.setFillAfter(true);


        mUpToDownAnimation = new RotateAnimation(-180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mUpToDownAnimation.setInterpolator(new LinearInterpolator());
        mUpToDownAnimation.setDuration(ROTATE_ANIMATION_DURATION);
        mUpToDownAnimation.setFillAfter(true);


        mScrollLayout = new LinearLayout(context);
        mScrollLayout.setOrientation(LinearLayout.VERTICAL);

        //添加放View的容器
        mContentLayout = new LinearLayout(context);
        mContentLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams contentLp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        contentLp.weight = 1;
        mScrollLayout.addView(mContentLayout, contentLp);

        mFootContent = new LinearLayout(context);
        mFootContent.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams footLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mScrollLayout.addView(mFootContent, footLp);

        LayoutParams scrollLp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT, Gravity.TOP);
        this.addView(mScrollLayout, scrollLp);
    }
}
