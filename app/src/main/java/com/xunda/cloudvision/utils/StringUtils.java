package com.xunda.cloudvision.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 字符串工具类
 * Create by yinglovezhuzhu@gmail.com in 2016/08/19
 */
public class StringUtils {

	private static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
	
	private StringUtils() {
		
	}

	/**
	 * 字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.length() == 0;
	}

	/**
	 * 格式化时间，返回yyyy.MM.dd格式时间
	 * @param timeMillis 时间毫秒
	 * @return
     */
	public static String formatTimeMillis(long timeMillis) {
		return mDateFormat.format(new Date(timeMillis));
	}

	/**
	 * 格式化时间，返回yyyy.MM.dd格式时间
	 * @param timeStamp 时间戳
	 * @return
     */
	public static String formatTimeStamp(long timeStamp) {
		return mDateFormat.format(new Date(timeStamp * 1000));
	}

	/**
	 * 从Assets文件中读取文本内容（测试）
	 * @param context
	 * @param name
	 * @return
	 * @throws IOException
     */
	public static String readStringFromAssetsFile(Context context, String name) throws IOException {
		InputStream is = null;
		ByteArrayOutputStream bos = null;
		try {
			is = context.getAssets().open(name);
			bos = new ByteArrayOutputStream();
			int count = 0;
			byte [] buff = new byte[1024 * 32];
			while ((count = is.read(buff)) != -1) {
				bos.write(buff, 0, count);
			}
			return new String(bos.toByteArray());
		} finally {
			if(null != is) {
				try {
					is.close();
				} catch (Exception e) {
					// do nothing
				}
			}
		}
	}
}
