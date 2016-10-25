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

package com.vrcvp.cloudvision.downloader;

/**
 * Usage The listener to listen downloadVideo state.
 * @author yinglovezhuzhu@gmail.com
 *
 */
public interface DownloadListener {

    int CODE_EXCEPTION = -100;
	
	/**
	 * The callback to listen downloadVideo size
	 * @param downloadedSize  downloaded size.
	 * @param totalSize total size of downloading file.
	 */
	void onProgressUpdate(int downloadedSize, int totalSize);

    /**
	 * 下载错误
     * @param code 错误码
     * @param message 说明文字
     */
	void onError(int code, String message);
}
