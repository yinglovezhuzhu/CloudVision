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
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.opensource.pullview.utils.DateUtil;

/**
 * Usage A custom scroll view can be pull to refresh.<br>
 * <p/>
 * <p> You can add child view use addView method.<br>
 * also you can add child view in layout xml file like this.<br>
 *
 * @author yinglovezhuzhu@gmail.com
 */
public class PullScrollView extends BasePullScrollView {

    private PullHeaderView mHeaderView;

    private String mLastRefreshTime = "";

    private int mHeaderLebelVisiblity = View.VISIBLE;

    /**
     * Constructor
     *
     * @param context the context
     */
    public PullScrollView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * Constructor
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public PullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private int mStartY = 0;
    private boolean mRecording = false;
    private boolean mIsBack = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = (int) event.getY();
                if (!mRecording) {
                    mRecording = mTopPosition == 0;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int tempY = (int) event.getY();
                if (!mRecording) {
                    mRecording = mTopPosition == 0;
                    mStartY = mRecording ? tempY : mStartY;
                }
                if (mRecording && (mEnableOverScroll || mEnablePullRefresh)) {
                    int moveY = tempY - mStartY;
                    int scrollY = moveY / OFFSET_RATIO;
                    if (mState != LOADING) {
                        // Ensure that the process of setting padding, current position has always been at the header,
                        // or if when the list exceeds the screen, then, when the push, the list will scroll at the same time
                        switch (mState) {
                            case RELEASE_TO_LOAD: // Release to load data
                                // Slide up, header part was covered, but not all be covered(Pull up to cancel)
                                if (scrollY > 0) {
                                    scrollTo(0, 0);
                                }
                                if (moveY > 0 && (scrollY < mHeaderView.mViewHeight)) {
                                    mState = PULL_TO_LOAD;
                                } else if (moveY <= 0) {
                                    // Slide to the top
                                    mState = IDEL;
                                }
                                updateHeaderViewByState(scrollY - mHeaderView.mViewHeight);
                                break;
                            case PULL_TO_LOAD:
                                // Pull down to the state can enter RELEASE_TO_REFRESH
                                if (scrollY > 0) {
                                    scrollTo(0, 0);
                                }
                                if (moveY <= 0) {
                                    mState = IDEL;
                                } else if (scrollY >= mHeaderView.mViewHeight) {
                                    mState = RELEASE_TO_LOAD;
                                    mIsBack = true;
                                }
                                updateHeaderViewByState(scrollY - mHeaderView.mViewHeight);
                                break;
                            case IDEL:
                                if (moveY > 0) {
                                    mState = PULL_TO_LOAD;
                                }
                                updateHeaderViewByState(-mHeaderView.mViewHeight);
                                break;
                            default:
                                break;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mRecording) {
                    switch (mState) {
                        case IDEL:
                            //Do nothing.
                            break;
                        case PULL_TO_LOAD:
                            //Pull to refresh.
                            mState = IDEL;
                            updateHeaderViewByState(-mHeaderView.mViewHeight);
                            break;
                        case RELEASE_TO_LOAD:
                            if (mEnablePullRefresh) {
                                //Release to refresh.
                                refresh();
                                mState = LOADING;
                                updateHeaderViewByState(0);
                            } else {
                                mState = IDEL;
                                updateHeaderViewByState(-mHeaderView.mViewHeight);
                            }
                            break;
                        default:
                            break;
                    }
                }
                mRecording = false;
                mIsBack = false;
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void updateHeaderViewByState(int paddingTop) {
        switch (mState) {
            case RELEASE_TO_LOAD:
                mHeaderView.setArrowVisibility(View.VISIBLE);
                mHeaderView.setProgressVisibility(View.GONE);
                mHeaderView.startArrowAnimation(mDownToUpAnimation);
                mHeaderView.setTitleText(R.string.pull_view_release_to_refresh);
                mHeaderView.setLabelText(getResources().getString(R.string.pull_view_refresh_time)
                        + mLastRefreshTime);
                break;
            case PULL_TO_LOAD:
                mHeaderView.setArrowVisibility(View.VISIBLE);
                mHeaderView.setProgressVisibility(View.GONE);

                if (mIsBack) {
                    mIsBack = false;
                    mHeaderView.startArrowAnimation(mUpToDownAnimation);
                }
                mHeaderView.setTitleText(R.string.pull_view_pull_to_refresh);
                mHeaderView.setLabelText(getResources().getString(
                        R.string.pull_view_refresh_time)
                        + mLastRefreshTime);
                break;
            case LOADING:
                mHeaderView.setArrowVisibility(View.GONE);
                mHeaderView.setProgressVisibility(View.VISIBLE);
                mHeaderView.startArrowAnimation(null);
                mHeaderView.setTitleText(R.string.pull_view_refreshing);
                mHeaderView.setLabelText(getResources().getString(R.string.pull_view_refresh_time)
                        + mLastRefreshTime);
                break;
            case IDEL:
                mHeaderView.setProgressVisibility(View.GONE);
                mHeaderView.startArrowAnimation(null);
                mHeaderView.setTitleText(R.string.pull_view_pull_to_refresh);
                mHeaderView.setLabelText(getResources().getString(R.string.pull_view_refresh_time)
                        + mLastRefreshTime);
                break;
            default:
                break;
        }
        mHeaderView.setVisibility(mEnablePullRefresh ? View.VISIBLE : View.INVISIBLE);
        mHeaderView.setLabelVisibility(mHeaderLebelVisiblity);
        mHeaderView.setPadding(0, paddingTop, 0, 0);
    }


    @Override
    protected void refresh() {
        if (null == mOnRefreshListener || mState == LOADING) {
            return;
        }
        mRefreshing = true;
        mOnRefreshListener.onRefresh();
    }


    @Override
    public void refreshCompleted() {
        super.refreshCompleted();
        mRecording = false;
        mIsBack = false;
        updateHeaderViewByState(-mHeaderView.mViewHeight);
        mLastRefreshTime = DateUtil.getSystemDate("yyyy-MM-dd HH:mm:ss");
        mHeaderView.setLabelText(getResources().getText(R.string.pull_view_refresh_time) + " " + mLastRefreshTime);
    }

    /**
     * Set header view label's visibility.<br>
     * <p>You can set the value of {@link android.view.View#GONE}、{@link android.view.View#VISIBLE}<br>
     *
     * @param visibility
     * @see android.view.View#GONE
     * @see android.view.View#VISIBLE
     */
    public void setHeaderLabelVisibility(int visibility) {
        mHeaderLebelVisiblity = visibility;
        if (visibility == View.INVISIBLE) {
            mHeaderView.setLabelVisibility(View.GONE);
            return;
        }
        mHeaderView.setLabelVisibility(visibility);
    }

    /**
     * Set last refresh time.
     *
     * @param time
     */
    public void setLastRefreshTime(String time) {
        this.mLastRefreshTime = time;
        mHeaderView.setLabelText(getResources().getText(R.string.pull_view_refresh_time) + " " + mLastRefreshTime);
    }


    /**
     * Init the View.
     *
     * @param context the context
     */
    private void initView(Context context) {

        // init header view
        mHeaderView = new PullHeaderView(context);

        // init header height
        mHeaderView.setGravity(Gravity.BOTTOM);
        addHeaderView(mHeaderView);

        mState = IDEL;
        updateHeaderViewByState(-mHeaderView.mViewHeight);

        mLastRefreshTime = DateUtil.getSystemDate(getResources().getString(R.string.pull_view_date_format));

    }

}
