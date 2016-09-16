package com.xunda.cloudvision.utils;

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
}
