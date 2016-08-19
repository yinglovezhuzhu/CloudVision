/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xunda.cloudvision.observer;

import java.util.ArrayList;

/**
 * Provides methods for registering or unregistering arbitrary observers in an {@link ArrayList}.
 *
 * This abstract class is intended to be subclassed and specialized to maintain
 * a registry of observers of specific types and dispatch notifications to them.
 *
 * @param T The observer type.
 */
public abstract class Observable<T> {
    /**
     * The list of observers.  An observer can be in the list at most
     * once and will never be null.
     */
    protected final ArrayList<T> mObservers = new ArrayList<T>();

    /**
     * Adds an observer to the list. The observer cannot be null and it must not already
     * be registered.
     * @param observer the observer to register
     * @throws IllegalArgumentException the observer is null
     * @throws IllegalStateException the observer is already registered
     */
    public void registerObserver(T observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized(mObservers) {
            if (mObservers.contains(observer)) {
                throw new IllegalStateException("Observer " + observer + " is already registered.");
            }
            mObservers.add(observer);
        }
    }

    /**
     * Removes a previously registered observer. The observer must not be null and it
     * must already have been registered.
     * @param observer the observer to unregister
     * @throws IllegalArgumentException the observer is null
     * @throws IllegalStateException the observer is not yet registered
     */
    public void unregisterObserver(T observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized(mObservers) {
            int index = mObservers.indexOf(observer);
            if (index == -1) {
                throw new IllegalStateException("Observer " + observer + " was not registered.");
            }
            mObservers.remove(index);
        }
    }

    /**
     * Remove all registered observers.
     */
    public void unregisterAll() {
        synchronized(mObservers) {
            mObservers.clear();
        }
    }
    
    /**
     *  是否已经注册了这个观察者
     * @param observer 观察者对象
     * @return 观察者是否已经注册， true 已注册， false 未注册
     */
    public boolean containsObserver(T observer) {
    	if(null == observer) {
    		return false;
    	}
    	synchronized(mObservers) {
            int index = mObservers.indexOf(observer);
            if (index == -1) {
                return false;
            }
            return true;
        }
    }
    
    /**
     * 返回已注册的观察者数量
     * @return 当前观察者的数量
     */
    public int size() {
    	synchronized (mObservers) {
			return mObservers.size();
		}
    }
    
    /**
     * 已注册的观察者是否为空
     * @return 观察者列表是否为空
     */
    public boolean isEmpty() {
    	synchronized (mObservers) {
			return mObservers.isEmpty();
		}
    }
}