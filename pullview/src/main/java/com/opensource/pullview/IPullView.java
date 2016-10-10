/*
 * Copyright (C)2016. The Android Open Source Project.
 *
 *          yinglovezhuzhu@gmail.com
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
package com.opensource.pullview;

/**
 * Usage
 *
 * @author yinglovezhuzhu@gmail.com
 */
public interface IPullView {

    public final static int RELEASE_TO_LOAD = 0x0;    //release-to-load state
    public final static int PULL_TO_LOAD = 0x1;        //pull-to-refresh state
    public final static int LOADING = 0x2;            //laoding state(refreshing or loading more)
    public final static int IDEL = 0x3;                //idel state

    /**
     * The ratio of the distance of the padding and sliding distance
     */
    public static final int OFFSET_RATIO = 3;

    public static final int ROTATE_ANIMATION_DURATION = 250;


    /**
     * The mode to load data(always use in load more data)<br/><br/>
     * <p/>
     * <p>{@link LoadMode#PULL_TO_LOAD} pull-to-loadmore<br>
     * You need to pull up to load more data<br><br>
     * <p>{@link LoadMode#AUTO_LOAD} auto-loadmore<br>
     * When you scroll to the end of the PullView, it will auto load data, if it has more data.
     */
    public static enum LoadMode {

        PULL_TO_LOAD(0), AUTO_LOAD(1);

        private int value = 0;

        LoadMode(int value) {
            this.value = value;
        }

        public static LoadMode valueOf(int value) {
            switch (value) {
                case 0:
                    return PULL_TO_LOAD;
                case 1:
                    return AUTO_LOAD;
                default:
                    return null;
            }
        }

        public int value() {
            return this.value;
        }
    }

}
