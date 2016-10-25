/*
 * Copyright (C) 2015 The Android Open Source Project.
 *
 *        yinglovezhuzhu@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vrcvp.cloudvision.ui.widget.round;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 
 * 这是一个圆角的ImageView，可以添加边框，支持双边框<br>
 * 
 * <p>大概思路：ImageView会将源图片最终转化为一个{@link Drawable}，<br>
 * 通过{@link ImageView#getDrawable()}获取该{@link Drawable}，<br>
 * 并通过{@link BitmapDrawable#getPaint()}获取其画笔。<br>
 * 通过saveLayer创建一个新图层，并在上面绘制。<br>
 * 对画笔使用{@link Paint#setXfermode(android.graphics.Xfermode)}设置PorterDuffXfermode。<br>
 * 从而将圆角效果绘制出来。<br>
 * <p>优点：能很好的兼容ImageView的scaleType<br>
 * <p>缺点：<br>
 * 1、运行速度较为缓慢，由于onDraw运行在ui线程，PorterDuffXfermode是采用SRC_IN的方式进行图像裁剪，
 * 这种裁剪方式的速度具体视图像大小质量而视，使用不当容易Anr。<br><br>
 * 2、从上面获取画笔的方式可以看出，这种方案只支持{@link BitmapDrawable}的图片类型，这个对xml中设置图片和
 * 代码中通过{@link ImageView#setImageBitmap(android.graphics.Bitmap)}、{@link ImageView#setImageResource(int)}
 * 方法设置图片时，可以完美达到效果，但是如果代码中通过{@link ImageView#setImageDrawable(Drawable)}的方式设置图片,
 * 当且仅当入参为{@link BitmapDrawable} 对象及其子类对象时，才能实现效果，否者将可能不是预期的效果，
 * （比如入参类型为{@link TransitionDrawable}（有渐变显示动画效果）时）
 * 
 * <p><font color="#FF0000">
 * 特别提示：如果图片的长宽比例和控件的长宽不一定一致是，
 * 强烈建议控件的缩放方式设置为{@link ImageView.ScaleType#CENTER_CROP},否则将可能达不到预期效果。
 * 因为只是控件显示的时候处理，并没有处理图片，也就是图片本身不是圆角的，当图片没有填满控件的时候，
 * 空白区域将会使用填充颜色填充，最终的效果可能图片不是圆角的。
 * 
 * </font>
 * 
 * @author yinglovezhuzhu@gmail.com
 * 
 * @version 1.0
 * 
 */
public class RoundImageView extends BaseRoundImageView {
	
	private final PorterDuffXfermode mModeSrcIn = new PorterDuffXfermode(Mode.SRC_IN);
	private final PorterDuffXfermode mModeDstOver = new PorterDuffXfermode(Mode.DST_OVER);

	public RoundImageView(Context context) {
		super(context);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
    	if(mCornerRate <= DEFAULT_CORNER_RATE && mCornerRadius <= DEFAULT_CORNER_RADIUS) {
    		// 没有设置边角属性，直接调用原来的绘制方法
    		super.onDraw(canvas);
    		return;
    	}
    	
		Drawable drawable = this.getDrawable();
		if (drawable == null) {
			super.onDraw(canvas);
			return;
		}

		if(mViewWidth <= 0 || mViewHeight <= 0) {
			super.onDraw(canvas);
			return;
		}
		
        if (drawable instanceof BitmapDrawable) {
        	
        	int diameter = mViewWidth > mViewHeight ? mViewHeight : mViewWidth;
            Paint paint = ((BitmapDrawable) drawable).getPaint();
            paint.setAntiAlias(true);
            paint.setStyle(Style.FILL);
            
            mDrawRect.set(0, 0, mViewWidth, mViewHeight);
            
            int saveCount = canvas.saveLayerAlpha(mDrawRect, 255, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
            		| Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                    | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
      
            canvas.drawARGB(0, 0, 0, 0); 

        	float radius = 0;
        	if(mCornerRate > DEFAULT_CORNER_RATE) { // 采用比率
        		radius = ((float) diameter) / mCornerRate;
        	} else if(mCornerRadius > DEFAULT_CORNER_RADIUS) { // 采用半径值
        		float halfDiameter = ((float) diameter) / 2;
        		// 圆角半径的最大为最小边的一般，如果是正方形，最大的圆角程度是一个圆
        		radius = mCornerRadius > halfDiameter ? halfDiameter : mCornerRadius;
        	} else { // 没有设置，即没有圆角
        		super.onDraw(canvas);
        		return;
        	}
        	
            if(mBorderThickness != DEFAUTL_BORDER_THICKNESS) { // 单框
            	
            	float drawableRadius = radius - mBorderThickness;
            	paint.setColor(mFillColor);
            	mTempRect.set(mDrawRect.left + mBorderThickness, mDrawRect.top + mBorderThickness, 
            			mDrawRect.right - mBorderThickness, mDrawRect.bottom - mBorderThickness);
            	canvas.drawRoundRect(mTempRect, drawableRadius, drawableRadius, paint);  
            	paint.setXfermode(mModeSrcIn);  
            	super.onDraw(canvas);

            	paint.setXfermode(mModeDstOver);
            	paint.setColor(mBorderColor);
            	canvas.drawRoundRect(mDrawRect, radius, radius, paint);  
            } else if(mBorderInsideThickness != DEFAUTL_BORDER_THICKNESS 
            		|| mBorderOutsideThickness != DEFAUTL_BORDER_THICKNESS) { // 双框
            	
            	float drawableRadius = radius - mBorderInsideThickness - mBorderOutsideThickness;
            	
            	if(mBorderInsideThickness == DEFAUTL_BORDER_THICKNESS) { // 只有外框
                	paint.setColor(mFillColor);
                	mTempRect.set(mDrawRect.left + mBorderOutsideThickness, mDrawRect.top + mBorderOutsideThickness, 
                			mDrawRect.right - mBorderOutsideThickness, mDrawRect.bottom - mBorderOutsideThickness);
                	canvas.drawRoundRect(mTempRect, drawableRadius, drawableRadius, paint);  
                	paint.setXfermode(mModeSrcIn);  
                	super.onDraw(canvas);
                	paint.setColor(mBorderOutsideColor);
            	} else if(mBorderOutsideThickness == DEFAUTL_BORDER_THICKNESS) { // 只有内框
            		
            		paint.setColor(mFillColor);
            		mTempRect.set(mDrawRect.left + mBorderInsideThickness, mDrawRect.top + mBorderInsideThickness, 
            				mDrawRect.right - mBorderInsideThickness, mDrawRect.bottom - mBorderInsideThickness);
            		canvas.drawRoundRect(mTempRect, drawableRadius, drawableRadius, paint);  
            		paint.setXfermode(mModeSrcIn);  
            		super.onDraw(canvas);
            		paint.setColor(mBorderInsideColor);
            	} else { // 有内外框
            		
            		paint.setColor(mFillColor);
            		mTempRect.set(mDrawRect.left + mBorderInsideThickness + mBorderOutsideThickness, 
            				mDrawRect.top + mBorderInsideThickness + mBorderOutsideThickness, 
            				mDrawRect.right - mBorderInsideThickness - mBorderOutsideThickness, 
            				mDrawRect.bottom - mBorderInsideThickness - mBorderOutsideThickness);
            		canvas.drawRoundRect(mTempRect, drawableRadius, drawableRadius, paint);  
            		paint.setXfermode(mModeSrcIn);  
            		super.onDraw(canvas);
            		
            		float insideBorderRadius = radius - mBorderOutsideThickness;
            		paint.setXfermode(mModeDstOver);
            		paint.setColor(mBorderInsideColor);
            		mTempRect.set(mDrawRect.left + mBorderOutsideThickness, 
            				mDrawRect.top + mBorderOutsideThickness, 
            				mDrawRect.right - mBorderOutsideThickness, 
            				mDrawRect.bottom - mBorderOutsideThickness);
            		canvas.drawRoundRect(mTempRect, insideBorderRadius, insideBorderRadius, paint);  
            		
            		paint.setColor(mBorderOutsideColor);
            	}
            	
            	paint.setXfermode(mModeDstOver);
            	canvas.drawRoundRect(mDrawRect, radius, radius, paint);
            	
            } else { // 无框
            	paint.setColor(Color.WHITE);
            	canvas.drawRoundRect(mDrawRect, radius, radius, paint);  
            	paint.setXfermode(mModeSrcIn);  
            	super.onDraw(canvas);
            	
            	paint.setXfermode(mModeDstOver);
            	paint.setColor(Color.TRANSPARENT);
            	canvas.drawRoundRect(mDrawRect, radius, radius, paint);  
            	
            }
            canvas.restoreToCount(saveCount);
            
        } else {  
            super.onDraw(canvas);  
        }  
	}
}
