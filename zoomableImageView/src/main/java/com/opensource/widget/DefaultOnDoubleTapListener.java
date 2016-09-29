/*
 * Copyright (C) 2016. The Android Open Source Project.
 *
 *         yinglovezhuzhu@gmail.com
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
package com.opensource.widget;

import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Provided default implementation of GestureDetector.OnDoubleTapListener, to be overriden with custom behavior, if needed
 * <p>&nbsp;</p>
 * To be used via {@link ViewAttacher#setOnDoubleTapListener(android.view.GestureDetector.OnDoubleTapListener)}
 */
public class DefaultOnDoubleTapListener implements GestureDetector.OnDoubleTapListener {

    private ViewAttacher mViewAttacher;

    /**
     * Default constructor
     *
     * @param mViewAttacher ZoomableImageViewAttacher to bind to
     */
    public DefaultOnDoubleTapListener(ViewAttacher mViewAttacher) {
        setmViewAttacher(mViewAttacher);
    }

    /**
     * Allows to change ZoomableImageViewAttacher within range of single instance
     *
     * @param newPhotoViewAttacher ZoomableImageViewAttacher to bind to
     */
    public void setmViewAttacher(ViewAttacher newPhotoViewAttacher) {
        this.mViewAttacher = newPhotoViewAttacher;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (this.mViewAttacher == null)
            return false;

        ImageView imageView = mViewAttacher.getImageView();

        if (null != mViewAttacher.getOnPhotoTapListener()) {
            final RectF displayRect = mViewAttacher.getDisplayRect();

            if (null != displayRect) {
                final float x = e.getX(), y = e.getY();

                // Check to see if the user tapped on the photo
                if (displayRect.contains(x, y)) {

                    float xResult = (x - displayRect.left)
                            / displayRect.width();
                    float yResult = (y - displayRect.top)
                            / displayRect.height();

                    mViewAttacher.getOnPhotoTapListener().onPhotoTap(imageView, xResult, yResult);
                    return true;
                }
            }
        }
        if (null != mViewAttacher.getOnViewTapListener()) {
            mViewAttacher.getOnViewTapListener().onViewTap(imageView, e.getX(), e.getY());
        }

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent ev) {
        if (mViewAttacher == null)
            return false;

        try {
            float scale = mViewAttacher.getScale();
            float x = ev.getX();
            float y = ev.getY();

            if (scale < mViewAttacher.getMediumScale()) {
                mViewAttacher.setScale(mViewAttacher.getMediumScale(), x, y, true);
            } else if (scale >= mViewAttacher.getMediumScale() && scale < mViewAttacher.getMaximumScale()) {
                mViewAttacher.setScale(mViewAttacher.getMaximumScale(), x, y, true);
            } else {
                mViewAttacher.setScale(mViewAttacher.getMinimumScale(), x, y, true);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Can sometimes happen when getX() and getY() is called
        }

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        // Wait for the confirmed onDoubleTap() instead
        return false;
    }

}
