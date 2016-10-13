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

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;

/**
 * An interface of ZoomableImageView.
 */
public interface IZoomableImageView {

    public static final float DEFAULT_MAX_SCALE = 8.0f;
    public static final float DEFAULT_MID_SCALE = 4.0f;
    public static final float DEFAULT_MIN_SCALE = 1.0f;
    public static final int DEFAULT_ZOOM_DURATION = 200;

    /**
     * Returns true if the ZoomableImageView is set to allow zooming of Photos.
     *
     * @return true if the ZoomableImageView allows zooming.
     */
    boolean canZoom();

    /**
     * Gets the Display Rectangle of the currently displayed Drawable. The Rectangle is relative to
     * this View and includes all scaling and translations.
     *
     * @return - RectF of Displayed Drawable
     */
    RectF getDisplayRect();

    /**
     * Sets the Display Matrix of the currently displayed Drawable. The Rectangle is considered
     * relative to this View and includes all scaling and translations.
     *
     * @param finalMatrix target matrix to set ZoomableImageView to
     * @return - true if rectangle was applied successfully
     */
    boolean setDisplayMatrix(Matrix finalMatrix);

    /**
     * Gets the Display Matrix of the currently displayed Drawable. The Rectangle is considered
     * relative to this View and includes all scaling and translations.
     *
     * @return - true if rectangle was applied successfully
     */
    Matrix getDisplayMatrix();


    /**
     * @return The current minimum scale level. What this value represents depends on the current
     * {@link android.widget.ImageView.ScaleType}.
     */
    float getMinimumScale();

    /**
     * @return The current medium scale level. What this value represents depends on the current
     * {@link android.widget.ImageView.ScaleType}.
     */
    float getMediumScale();


    /**
     * @return The current maximum scale level. What this value represents depends on the current
     * {@link android.widget.ImageView.ScaleType}.
     */
    float getMaximumScale();

    /**
     * Returns the current scale value
     *
     * @return float - current scale value
     */
    float getScale();

    /**
     * Return the current scale type in use by the ImageView.
     *
     * @return current ImageView.ScaleType
     */
    ImageView.ScaleType getScaleType();

    /**
     * Whether to allow the ImageView's parent to intercept the touch event when the photo is scroll
     * to it's horizontal edge.
     *
     * @param allow whether to allow intercepting by parent element or not
     */
    void setAllowParentInterceptOnEdge(boolean allow);

    /**
     * Sets the minimum scale level. What this value represents depends on the current {@link
     * android.widget.ImageView.ScaleType}.
     *
     * @param minimumScale minimum allowed scale
     */
    void setMinimumScale(float minimumScale);

    /**
     * Sets the medium scale level. What this value represents depends on the current {@link android.widget.ImageView.ScaleType}.
     *
     * @param mediumScale medium scale preset
     */
    void setMediumScale(float mediumScale);


    /**
     * Sets the maximum scale level. What this value represents depends on the current {@link
     * android.widget.ImageView.ScaleType}.
     *
     * @param maximumScale maximum allowed scale preset
     */
    void setMaximumScale(float maximumScale);

    /**
     * Register a callback to be invoked when the Photo displayed by this view is long-pressed.
     *
     * @param listener - Listener to be registered.
     */
    void setOnLongClickListener(View.OnLongClickListener listener);

    /**
     * Register a callback to be invoked when the Matrix has changed for this View. An example would
     * be the user panning or scaling the Photo.
     *
     * @param listener - Listener to be registered.
     */
    void setOnMatrixChangeListener(ViewAttacher.OnMatrixChangedListener listener);

    /**
     * Register a callback to be invoked when the Photo displayed by this View is tapped with a
     * single tap.
     *
     * @param listener - Listener to be registered.
     */
    void setOnPhotoTapListener(ViewAttacher.OnPhotoTapListener listener);

    /**
     * Returns a listener to be invoked when the Photo displayed by this View is tapped with a
     * single tap.
     *
     * @return ZoomableImageViewAttacher.OnPhotoTapListener currently set, may be null
     */
    ViewAttacher.OnPhotoTapListener getOnPhotoTapListener();

    /**
     * Register a callback to be invoked when the View is tapped with a single tap.
     *
     * @param listener - Listener to be registered.
     */
    void setOnViewTapListener(ViewAttacher.OnViewTapListener listener);

    /**
     * Enables rotation via ZoomableImageView internal functions.
     *
     * @param rotationDegree - Degree to rotate ZoomableImageView to, should be in range 0 to 360
     */
    void setRotationTo(float rotationDegree);

    /**
     * Enables rotation via ZoomableImageView internal functions.
     *
     * @param rotationDegree - Degree to rotate ZoomableImageView by, should be in range 0 to 360
     */
    void setRotationBy(float rotationDegree);

    /**
     * Returns a callback listener to be invoked when the View is tapped with a single tap.
     *
     * @return ZoomableImageViewAttacher.OnViewTapListener currently set, may be null
     */
    ViewAttacher.OnViewTapListener getOnViewTapListener();

    /**
     * Changes the current scale to the specified value.
     *
     * @param scale - Value to scale to
     */
    void setScale(float scale);

    /**
     * Changes the current scale to the specified value.
     *
     * @param scale   - Value to scale to
     * @param animate - Whether to animate the scale
     */
    void setScale(float scale, boolean animate);

    /**
     * Changes the current scale to the specified value, around the given focal point.
     *
     * @param scale   - Value to scale to
     * @param focalX  - X Focus Point
     * @param focalY  - Y Focus Point
     * @param animate - Whether to animate the scale
     */
    void setScale(float scale, float focalX, float focalY, boolean animate);

    /**
     * Controls how the image should be resized or moved to match the size of the ImageView. Any
     * scaling or panning will happen within the confines of this {@link
     * android.widget.ImageView.ScaleType}.
     *
     * @param scaleType - The desired scaling mode.
     */
    void setScaleType(ImageView.ScaleType scaleType);

    /**
     * Allows you to enable/disable the zoom functionality on the ImageView. When disable the
     * ImageView reverts to using the FIT_CENTER matrix.
     *
     * @param zoomable - Whether the zoom functionality is enabled.
     */
    void setZoomable(boolean zoomable);

    /**
     * Extracts currently visible area to Bitmap object, if there is no image loaded yet or the
     * ImageView is already destroyed, returns {@code null}
     *
     * @return currently visible area as bitmap or null
     */
    Bitmap getVisibleRectangleBitmap();

    /**
     * Allows to change zoom transition speed, default value is 200 (ZoomableImageViewAttacher.DEFAULT_ZOOM_DURATION).
     * Will default to 200 if provided negative value
     *
     * @param milliseconds duration of zoom interpolation
     */
    void setZoomTransitionDuration(int milliseconds);

    /**
     * Will return instance of IZoomableImageView (eg. ZoomableImageViewAttacher), can be used to provide better
     * integration
     *
     * @return IZoomableImageView implementation instance if available, null if not
     */
    IZoomableImageView getIPhotoViewImplementation();

    /**
     * Sets custom double tap listener, to intercept default given functions. To reset behavior to
     * default, you can just pass in "null" or public field of ZoomableImageViewAttacher.defaultOnDoubleTapListener
     *
     * @param newOnDoubleTapListener custom OnDoubleTapListener to be set on ImageView
     */
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener newOnDoubleTapListener);
}
