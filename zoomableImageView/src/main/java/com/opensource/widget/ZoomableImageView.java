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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.widget.ImageView;

public class ZoomableImageView extends ImageView implements IZoomableImageView {

    private final ViewAttacher mViewAttacher;

    private ScaleType mPendingScaleType;

    public ZoomableImageView(Context context) {
        this(context, null);
    }

    public ZoomableImageView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public ZoomableImageView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        super.setScaleType(ScaleType.MATRIX);
        mViewAttacher = new ViewAttacher(this);

        if (null != mPendingScaleType) {
            setScaleType(mPendingScaleType);
            mPendingScaleType = null;
        }
    }

    @Override
    public void setRotationTo(float rotationDegree) {
        mViewAttacher.setRotationTo(rotationDegree);
    }

    @Override
    public void setRotationBy(float rotationDegree) {
        mViewAttacher.setRotationBy(rotationDegree);
    }

    @Override
    public boolean canZoom() {
        return mViewAttacher.canZoom();
    }

    @Override
    public RectF getDisplayRect() {
        return mViewAttacher.getDisplayRect();
    }

    @Override
    public Matrix getDisplayMatrix() {
        return mViewAttacher.getDrawMatrix();
    }

    @Override
    public boolean setDisplayMatrix(Matrix finalRectangle) {
        return mViewAttacher.setDisplayMatrix(finalRectangle);
    }

    @Override
    public float getMinimumScale() {
        return mViewAttacher.getMinimumScale();
    }

    @Override
    public float getMediumScale() {
        return mViewAttacher.getMediumScale();
    }

    @Override
    public float getMaximumScale() {
        return mViewAttacher.getMaximumScale();
    }

    @Override
    public float getScale() {
        return mViewAttacher.getScale();
    }

    @Override
    public ScaleType getScaleType() {
        return mViewAttacher.getScaleType();
    }

    @Override
    public void setAllowParentInterceptOnEdge(boolean allow) {
        mViewAttacher.setAllowParentInterceptOnEdge(allow);
    }

    @Override
    public void setMinimumScale(float minimumScale) {
        mViewAttacher.setMinimumScale(minimumScale);
    }

    @Override
    public void setMediumScale(float mediumScale) {
        mViewAttacher.setMediumScale(mediumScale);
    }

    @Override
    public void setMaximumScale(float maximumScale) {
        mViewAttacher.setMaximumScale(maximumScale);
    }

    @Override
    // setImageBitmap calls through to this method
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (null != mViewAttacher) {
            mViewAttacher.update();
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if (null != mViewAttacher) {
            mViewAttacher.update();
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (null != mViewAttacher) {
            mViewAttacher.update();
        }
    }

    @Override
    public void setOnMatrixChangeListener(ViewAttacher.OnMatrixChangedListener listener) {
        mViewAttacher.setOnMatrixChangeListener(listener);
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        mViewAttacher.setOnLongClickListener(l);
    }

    @Override
    public void setOnPhotoTapListener(ViewAttacher.OnPhotoTapListener listener) {
        mViewAttacher.setOnPhotoTapListener(listener);
    }

    @Override
    public ViewAttacher.OnPhotoTapListener getOnPhotoTapListener() {
        return mViewAttacher.getOnPhotoTapListener();
    }

    @Override
    public void setOnViewTapListener(ViewAttacher.OnViewTapListener listener) {
        mViewAttacher.setOnViewTapListener(listener);
    }

    @Override
    public ViewAttacher.OnViewTapListener getOnViewTapListener() {
        return mViewAttacher.getOnViewTapListener();
    }

    @Override
    public void setScale(float scale) {
        mViewAttacher.setScale(scale);
    }

    @Override
    public void setScale(float scale, boolean animate) {
        mViewAttacher.setScale(scale, animate);
    }

    @Override
    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        mViewAttacher.setScale(scale, focalX, focalY, animate);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (null != mViewAttacher) {
            mViewAttacher.setScaleType(scaleType);
        } else {
            mPendingScaleType = scaleType;
        }
    }

    @Override
    public void setZoomable(boolean zoomable) {
        mViewAttacher.setZoomable(zoomable);
    }

    @Override
    public Bitmap getVisibleRectangleBitmap() {
        return mViewAttacher.getVisibleRectangleBitmap();
    }

    @Override
    public void setZoomTransitionDuration(int milliseconds) {
        mViewAttacher.setZoomTransitionDuration(milliseconds);
    }

    @Override
    public IZoomableImageView getIPhotoViewImplementation() {
        return mViewAttacher;
    }

    @Override
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener newOnDoubleTapListener) {
        mViewAttacher.setOnDoubleTapListener(newOnDoubleTapListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        mViewAttacher.cleanup();
        super.onDetachedFromWindow();
    }

    public void cleanup() {
        if(null != mViewAttacher) {
            mViewAttacher.cleanup();
        }
    }
}