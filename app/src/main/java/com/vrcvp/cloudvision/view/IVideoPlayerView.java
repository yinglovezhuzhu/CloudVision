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

package com.vrcvp.cloudvision.view;

import android.media.MediaPlayer;
import android.net.Uri;

/** 视频播放器View接口
 * Created by yinglovezhuzhu@gmail.com on 2016/10/8.
 */

public interface IVideoPlayerView {

    void setOnErrorListener(MediaPlayer.OnErrorListener listener);

    void setOnCompletionListener(MediaPlayer.OnCompletionListener listener);

    void setOnPreparedListener(MediaPlayer.OnPreparedListener listener);

    void playVideo(Uri uri, int position);

    boolean isPlaying();

    void showLoadingProgress();

    void hideLoadingProgress();
}
