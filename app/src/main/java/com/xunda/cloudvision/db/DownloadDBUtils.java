/*
 * Copyright (C) 2016. The Android Open Source Project.
 *
 *          yinglovezhuzhu@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.xunda.cloudvision.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xunda.cloudvision.bean.DownloadLog;
import com.xunda.cloudvision.utils.StringUtils;


/**
 * usage Download log database util
 * @author yinglovezhuzhu@gmail.com
 *
 */
public class DownloadDBUtils {
	
	private static final String TABLE_NAME_LOG = "download_log";
    private static final String TABLE_NAME_HISTORY = "download_history";

	private static final String _ID = "_id";
    private static final String URL = "url";
    private static final String DOWNLOADED_SIZE = "downloaded_size";
	private static final String TOTAL_SIZE = "total_size";
    private static final String SAVED_FILE = "saved_file";
    private static final String END_DOWNLOADED = "end_downloaded";
    private static final String FINISHED_TIME = "finished_time";
	
	/**
	 * Save the log of a file.
	 * @param context Context对象
	 * @param log
	 * @return 插入数据id
	 */
	public static long saveLog(Context context, DownloadLog log) {
        if(null == log || StringUtils.isEmpty(log.getUrl())) {
            return -1L;
        }
		SQLiteDatabase db = DownloadDBHelper.getWriteableDatabase(context);
		long id = -1;
		db.beginTransaction();
		try {
			ContentValues values = new ContentValues();
            values.put(URL, log.getUrl());
            values.put(DOWNLOADED_SIZE, log.getDownloadedSize());
            values.put(TOTAL_SIZE, log.getTotalSize());
            values.put(SAVED_FILE, log.getSavedFile());
            values.put(END_DOWNLOADED, log.isEndDownloaded() ? 1 : 0);
            id = db.insert(TABLE_NAME_LOG, "", values);
			// 设置事务执行的标志为成功
			db.setTransactionSuccessful();
		} catch(IllegalStateException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
		}
		return id;
	}
	
	/**
	 * Delete the log by url
	 * @param context Context
	 * @param url 下载URL
	 * @return 删除记录数
	 */
	public static int deleteLog(Context context, String url) {
		SQLiteDatabase db = DownloadDBHelper.getWriteableDatabase(context);
		int count = 0;
		try {
			db.beginTransaction();
			count = db.delete(TABLE_NAME_LOG, URL + " = ?", new String[] {url, });
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		return count;
	}
	
	/**
	 * 根据URL地址返回下载日志，如果没有返回null
	 * @param context Context
	 * @param url 下载地址
	 * @return 下载日志信息，没有返回null
	 */
	public static DownloadLog getLogByUrl(Context context, String url) {
		SQLiteDatabase db = DownloadDBHelper.getReadableDatabase(context);
		Cursor cursor = db.query(TABLE_NAME_LOG, null, URL + " = ?",
                new String[] {url, }, null, null, null);
        DownloadLog downloadLog = null;
		if(cursor != null) {
			if(cursor.moveToFirst()) {
                downloadLog = new DownloadLog();
				int idIndex = cursor.getColumnIndex(_ID);
                int urlIndex = cursor.getColumnIndex(URL);
				int downloadedSizeIndex = cursor.getColumnIndex(DOWNLOADED_SIZE);
                int totalSizeIndex = cursor.getColumnIndexOrThrow(TOTAL_SIZE);
                int savedFileIndex = cursor.getColumnIndex(SAVED_FILE);
                int endDownloadedIndex = cursor.getColumnIndex(END_DOWNLOADED);
                downloadLog.setId(cursor.getLong(idIndex));
                downloadLog.setUrl(cursor.getString(urlIndex));
                downloadLog.setDownloadedSize(cursor.getInt(downloadedSizeIndex));
                downloadLog.setTotalSize(cursor.getInt(totalSizeIndex));
                downloadLog.setSavedFile(cursor.getString(savedFileIndex));
                downloadLog.setEndDownloaded(1 == cursor.getInt(endDownloadedIndex));
			}
			cursor.close();
		}
		db.close();
		return downloadLog;
	}
	
	/**
	 * Update a log record through thread id and url
	 * @param context Context对象
	 * @param log 下载日志数据
	 */
	public static int updateLog(Context context, DownloadLog log) {
		SQLiteDatabase db = DownloadDBHelper.getWriteableDatabase(context);
		int count = 0;
		try {
			db.beginTransaction();
			ContentValues values = new ContentValues();
            values.put(URL, log.getUrl());
			values.put(DOWNLOADED_SIZE, log.getDownloadedSize());
            values.put(TOTAL_SIZE, log.getTotalSize());
            values.put(SAVED_FILE, log.getSavedFile());
            values.put(END_DOWNLOADED, log.isEndDownloaded() ? 1 : 0);
			count = db.update(TABLE_NAME_LOG, values, URL + " = ?", new String[] {log.getUrl(), });
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		return count;
	}

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Save the log of a file.
     * @param context Context对象
     * @param log
     * @return 插入数据id
     */
    public static long saveHistory(Context context, DownloadLog log) {
        if(null == log || StringUtils.isEmpty(log.getUrl())) {
            return -1L;
        }
        SQLiteDatabase db = DownloadDBHelper.getWriteableDatabase(context);
        long id = -1;
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(URL, log.getUrl());
            values.put(TOTAL_SIZE, log.getTotalSize());
            values.put(FINISHED_TIME, log.getFinishedTime());
            values.put(SAVED_FILE, log.getSavedFile());
            id = db.insert(TABLE_NAME_HISTORY, "", values);
            // 设置事务执行的标志为成功
            db.setTransactionSuccessful();
        } catch(IllegalStateException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return id;
    }

    /**
     * 根据URL返回历史记录信息，如果没有返回null
     * @param context Context
     * @param url 下载地址
     * @return 下载历史记录，没有返回null
     */
    public static DownloadLog getHistoryByUrl(Context context, String url) {
        SQLiteDatabase db = DownloadDBHelper.getReadableDatabase(context);
        Cursor cursor = db.query(TABLE_NAME_HISTORY, null, URL + " = ?",
                new String[] {url, }, null, null, null);
        DownloadLog history = null;
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                history = new DownloadLog();
                int idIndex = cursor.getColumnIndex(_ID);
                int urlIndex = cursor.getColumnIndex(URL);
                int totalSizeIndex = cursor.getColumnIndexOrThrow(TOTAL_SIZE);
                int finishedTimeIndex = cursor.getColumnIndex(FINISHED_TIME);
                int savedFileIndex = cursor.getColumnIndex(SAVED_FILE);
                history.setId(cursor.getLong(idIndex));
                history.setUrl(cursor.getString(urlIndex));
                history.setTotalSize(cursor.getInt(totalSizeIndex));
                history.setDownloadedSize(history.getTotalSize());
                history.setFinishedTime(cursor.getLong(finishedTimeIndex));
                history.setSavedFile(cursor.getString(savedFileIndex));
            }
            cursor.close();
        }
        db.close();
        return history;
    }

    /**
     * 根据URL删除历史记录
     * @param context Context
     * @param url 下载URL
     * @return 删除记录数
     */
    public static int deleteHistory(Context context, String url) {
        SQLiteDatabase db = DownloadDBHelper.getWriteableDatabase(context);
        int count = 0;
        try {
            db.beginTransaction();
            count = db.delete(TABLE_NAME_HISTORY, URL + " = ?", new String[] {url, });
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        return count;
    }
}
