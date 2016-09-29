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

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

public abstract class ScrollerProxy {

    public static ScrollerProxy getScroller(Context context) {
        final int version = VERSION.SDK_INT;
        if (version < VERSION_CODES.GINGERBREAD) {
            return new FroyoScrollerProxy(context);
        } else if (version < VERSION_CODES.ICE_CREAM_SANDWICH) {
            return new GingerScrollerProxy(context);
        } else {
            return new IcsScrollerProxy(context);
        }
    }

    public abstract boolean computeScrollOffset();

    public abstract void fling(int startX, int startY, int velocityX, int velocityY,
                               int minX, int maxX, int minY, int maxY,
                               int overX, int overY);

    public abstract void forceFinished(boolean finished);

    public abstract boolean isFinished();

    public abstract int getCurrX();

    public abstract int getCurrY();


}
