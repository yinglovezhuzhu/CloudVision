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

package com.xunda.cloudvision.model;

import java.io.File;

/**
 * 下载Model接口
 * Created by yinglovezhuzhu@gmail.com on 2016/10/8.
 */

public interface IVideoPlayerModel {

    /**
     * 下载视频文件
     */
    void downloadVideo();

    /**
     * 获取本地缓存的视频文件地址
     * @return 本地缓存的视频文件地址，没有初始化或者没有下载的情况下返回null
     */
    File getSavedVideoFile();

    void onCreate();

    void onPause();

    void onResume();

    void onDestroy();

    boolean isDownloadStopped();
}
