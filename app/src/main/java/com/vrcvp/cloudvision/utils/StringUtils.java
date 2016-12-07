package com.vrcvp.cloudvision.utils;

import android.content.Context;

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



	/**
	 * 格式化HTML格式化数据
	 * @param content html原始数据
	 * @param title 标题
	 * @return 格式化后的HTML数据，以html标签开头
	 */
	public static String formatHTMLContent(String content, String title) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>")
				.append("<meta name='viewport' content='width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no'>")
				.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>")
				.append("<title>")
				.append(title)
				.append("</title></head>")
				.append("<body style='margin: 0; padding: 0; word-break: break-all; text-align:left;'>")
//                .append("<div style='font-size: 21px; line-height:32px; background-color:#f5f5f5; padding: 8px;'>")
				.append(content)
//                .append("</div>")
				.append("</body></html>");
		return sb.toString();
	}
}
