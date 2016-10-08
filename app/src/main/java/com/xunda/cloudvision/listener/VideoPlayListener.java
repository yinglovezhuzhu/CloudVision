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

package com.xunda.cloudvision.listener;

/**
 * 播放监听
 * Created by yinglovezhuzhu@gmail.com on 2016/8/17.
 */
public interface VideoPlayListener {

    /** 视频文件下载错误 **/
    int WHAT_DOWNLOAD_ERROR = 1;

    void onCompletion();

    void onError(int what, String msg);
}
