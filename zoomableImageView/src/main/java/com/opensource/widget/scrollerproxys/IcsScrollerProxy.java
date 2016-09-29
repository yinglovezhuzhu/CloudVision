/*
 * Copyright (C) 2016. The Android Open Source Project.
 *
 *         yinglovezhuzhu@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.opensource.widget.scrollerproxys;

import android.annotation.TargetApi;
import android.content.Context;

/**
 * A scroll proxy above API level 13<br></br>
 * API level 14 {@link android.os.Build.VERSION_CODES#ICE_CREAM_SANDWICH} or older
 */
@TargetApi(14)
public class IcsScrollerProxy extends GingerScrollerProxy {

    public IcsScrollerProxy(Context context) {
        super(context);
    }

    @Override
    public boolean computeScrollOffset() {
        return mScroller.computeScrollOffset();
    }

}
