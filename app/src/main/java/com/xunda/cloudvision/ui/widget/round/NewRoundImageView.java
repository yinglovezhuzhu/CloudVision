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
package com.xunda.cloudvision.ui.widget.round;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * 
 * 这是一个圆角的ImageView，可以添加边框，支持双边框<br>
 * 
 * <p>大概思路：创建以“圆角矩形”为结构的Path，并利用Path.FillType.INVERSE_WINDING反选“圆角矩形区域”。
 * 从而达到圆角边缘化的效果。<br>
 * 优点：由于不需要对ImageView的图片进行字节操作，所以速度快许多，而且在动画表现上十分平滑。<br>
 * 缺点：暂无，如果要说有，那就是在API 11及以上版本才能使用。<br>
 * 	
 * <p><font color="#FF0000">
 * 特别提示：如果在API 11或者以上版本的系统，强烈建议使用这个。<br><br>
 * 如果图片的长宽比例和控件的长宽不一定一致是，
 * 强烈建议控件的缩放方式设置为{@link ImageView.ScaleType#CENTER_CROP},否则将可能达不到预期效果。
 * 因为只是控件显示的时候处理，并没有处理图片，也就是图片本身不是圆角的，当图片没有填满控件的时候，
 * 空白区域将会使用填充颜色填充，最终的效果可能图片不是圆角的。
 * </font>
 * 
 * @author yinglovezhuzhu@gmail.com
 * 
 * @version 1.0
 * 
 */
@TargetApi(value = 11)
public class NewRoundImageView extends BaseRoundImageView {

	private final Paint mPaint = new Paint();
	
	private final Path mPath = new Path();

	public NewRoundImageView(Context context) {
		super(context);
		init();
	}

	public NewRoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public NewRoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	
    private void init() {  
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        mPath.setFillType(Path.FillType.INVERSE_WINDING);
    }  
	
	@Override
	protected void onDraw(Canvas canvas) {
    	if(mCornerRate <= DEFAULT_CORNER_RATE && mCornerRadius <= DEFAULT_CORNER_RADIUS) {
    		// 没有设置边角属性，直接调用原来的绘制方法
    		super.onDraw(canvas);
    		return;
    	}

		if(mViewWidth <= 0 || mViewHeight <= 0) {
			super.onDraw(canvas);
			return;
		}
		
		int diameter = mViewWidth > mViewHeight ? mViewHeight : mViewWidth;
		
		mDrawRect.set(0, 0, mViewWidth, mViewHeight);
		
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
		
		mPaint.setAntiAlias(true);
		
		if(mBorderThickness != DEFAUTL_BORDER_THICKNESS) { // 单框
			
			float imageRadius = radius - mBorderThickness;
			
			// 绘制边框
			drawRoundColorShape(canvas, mDrawRect, radius, mBorderColor);
			
			// 绘制填充背景
	        mTempRect.set(mDrawRect.left + mBorderThickness, mDrawRect.top + mBorderThickness, 
	        		mDrawRect.right - mBorderThickness, mDrawRect.bottom - mBorderThickness);
	        drawRoundColorShape(canvas, mTempRect, imageRadius, mFillColor);
	        
	        
	        // 保存当前layer的透明橡树到离屏缓冲区。并新创建一个透明度爲255的新layer 
	        // 绘制图片
	        int saveCount = canvas.saveLayerAlpha(0.0F, 0.0F, mViewWidth, mViewHeight, 
	        		255, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
	        super.onDraw(canvas);
	        canvas.drawPath(mPath, mPaint);
	        canvas.restoreToCount(saveCount);
			
		} else if(mBorderInsideThickness != DEFAUTL_BORDER_THICKNESS 
				|| mBorderOutsideThickness != DEFAUTL_BORDER_THICKNESS) { // 双框
			
			float imageRadius = radius - mBorderInsideThickness - mBorderOutsideThickness;
			
			if(mBorderInsideThickness == DEFAUTL_BORDER_THICKNESS) { // 只有外框
				// 绘制边框
				drawRoundColorShape(canvas, mDrawRect, radius, mBorderOutsideColor);
				
				// 绘制填充背景
		        mTempRect.set(mDrawRect.left + mBorderOutsideThickness, mDrawRect.top + mBorderOutsideThickness, 
		        		mDrawRect.right - mBorderOutsideThickness, mDrawRect.bottom - mBorderOutsideThickness);
		        drawRoundColorShape(canvas, mTempRect, imageRadius, mFillColor);
		        
		        
		        // 保存当前layer的透明橡树到离屏缓冲区。并新创建一个透明度爲255的新layer 
		        // 绘制图片
		        int saveCount = canvas.saveLayerAlpha(0.0F, 0.0F, mViewWidth, mViewHeight, 
		        		255, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
		        super.onDraw(canvas);
		        canvas.drawPath(mPath, mPaint);
		        canvas.restoreToCount(saveCount);
			} else if(mBorderOutsideThickness == DEFAUTL_BORDER_THICKNESS) { // 只有内框
				// 绘制边框
				drawRoundColorShape(canvas, mDrawRect, radius, mBorderOutsideColor);
				
				// 绘制背景填充色
				mTempRect.set(mDrawRect.left + mBorderOutsideThickness, mDrawRect.top + mBorderOutsideThickness, 
		        		mDrawRect.right - mBorderOutsideThickness, mDrawRect.bottom - mBorderOutsideThickness);
		        drawRoundColorShape(canvas, mTempRect, imageRadius, mBorderInsideColor);
		        
		        // 保存当前layer的透明橡树到离屏缓冲区。并新创建一个透明度爲255的新layer 
		        // 绘制图片
		        int saveCount = canvas.saveLayerAlpha(0.0F, 0.0F, mViewWidth, mViewHeight, 
		        		255, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
		        super.onDraw(canvas);
		        canvas.drawPath(mPath, mPaint);
		        canvas.restoreToCount(saveCount);
			} else { // 有内外框
				
				// 绘制外边框
				drawRoundColorShape(canvas, mDrawRect, radius, mBorderOutsideColor);
				
				// 绘制内边框
		        mTempRect.set(mDrawRect.left + mBorderOutsideThickness, mDrawRect.top + mBorderOutsideThickness, 
		        		mDrawRect.right - mBorderOutsideThickness, mDrawRect.bottom - mBorderOutsideThickness);
		        drawRoundColorShape(canvas, mTempRect, radius - mBorderOutsideThickness, mBorderInsideColor);
				
				// 绘制背景填充色
		        mTempRect.set(mTempRect.left + mBorderInsideThickness, mTempRect.top + mBorderInsideThickness, 
		        		mTempRect.right - mBorderInsideThickness, mTempRect.bottom - mBorderInsideThickness);
		        drawRoundColorShape(canvas, mTempRect, imageRadius, mFillColor);
		        
		        // 保存当前layer的透明橡树到离屏缓冲区。并新创建一个透明度爲255的新layer 
		        // 绘制图片
		        int saveCount = canvas.saveLayerAlpha(0.0F, 0.0F, mViewWidth, mViewHeight, 
		        		255, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
		        super.onDraw(canvas);
		        canvas.drawPath(mPath, mPaint);
		        canvas.restoreToCount(saveCount);
			}
		} else { // 无框
			// 绘制背景填充色
			drawRoundColorShape(canvas, mDrawRect, radius, mFillColor);
			
	        // 保存当前layer的透明橡树到离屏缓冲区。并新创建一个透明度爲255的新layer 
	        // 绘制图片
	        int saveCount = canvas.saveLayerAlpha(0.0F, 0.0F, mViewWidth, mViewHeight, 
	        		255, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
	        super.onDraw(canvas);
	        canvas.drawPath(mPath, mPaint);
	        canvas.restoreToCount(saveCount);
		}
	}
	
	/**
	 * 绘制颜色图形
	 * @param canvas
	 * @param rect
	 * @param radius
	 * @param color
	 */
	private void drawRoundColorShape(Canvas canvas, RectF rect, float radius, int color) {
		mPath.reset();
    	mPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
    	
        // 保存当前layer的透明橡树到离屏缓冲区。并新创建一个透明度爲255的新layer
        int saveCount = canvas.saveLayerAlpha(0.0F, 0.0F, mViewWidth, mViewHeight, 
        		255, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.drawColor(color);
        canvas.drawPath(mPath, mPaint);
        canvas.restoreToCount(saveCount);
	}
}
