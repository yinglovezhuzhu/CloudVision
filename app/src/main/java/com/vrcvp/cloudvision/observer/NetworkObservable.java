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
 *
 */

package com.vrcvp.cloudvision.observer;

import android.net.NetworkInfo;

/**
 * 网络状态被观察者
 * Created by yinglovezhuzhu@gmail.com on 2016/08/19.
 */
public class NetworkObservable extends Observable<NetworkObserver> {

    public void notifyNetworkChanged(boolean networkConnected, NetworkInfo currentNetwork,
                                     NetworkInfo lastNetwork) {
        synchronized (mObservers) {
            for(int i = mObservers.size() - 1; i >= 0; i--) {
                NetworkObserver observer = mObservers.get(i);
                observer.onNetworkStateChanged(networkConnected, currentNetwork, lastNetwork);
            }
        }
    }
}
