package com.vrcvp.cloudvision.utils;

import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 日志打印工具类
 * 
 * Create by yinglovezhuzhu@gmail.com 2016/08/19
 */
public class LogUtils {
	
	private static boolean mDebugMode = true;

	public static void setDebugMode(boolean debugMode) {
		mDebugMode = debugMode;
	}

	public static boolean isDebugMode() {
		return mDebugMode;
	}

	public static void i(String tag, String msg) {
		if (mDebugMode) {
			Log.i(tag, msg.toString());
		}
	}

	public static void i(String tag, Object msg) {
		if (mDebugMode) {
			Log.i(tag, msg.toString());
		}
	}

	public static void w(String tag, String msg) {
		Log.w(tag, msg);
	}

	public static void w(String tag, Object msg) {
		Log.w(tag, msg.toString());
	}

	public static void e(String tag, String msg) {
		Log.e(tag, msg);
	}

	public static void e(String tag, Object msg) {
		Log.e(tag, msg.toString());
	}

	public static void d(String tag, String msg) {
		if (mDebugMode) {
			Log.d(tag, msg);
		}
	}

	public static void d(String tag, Object msg) {
		if (mDebugMode) {
			Log.d(tag, msg.toString());
		}
	}

	public static void v(String tag, String msg) {
		if (mDebugMode) {
			Log.v(tag, msg);
		}
	}

	public static void v(String tag, Object msg) {
		if (mDebugMode) {
			Log.v(tag, msg.toString());
		}
	}

	public static String getStackTrace(Throwable throwable) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		throwable.printStackTrace(printWriter);
		printWriter.close();
		String error = writer.toString();
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return error;
	}
}
