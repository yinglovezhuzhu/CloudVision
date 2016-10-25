package com.vrcvp.cloudvision.view;

/**
 * 搜索View接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public interface ISearchView extends IView {

    /**
     * 获取搜索关键字
     * @return 关键字
     */
    String getKeyword();

    /**
     * 关键字为空错误
     */
    void onKeywordEmptyError();
}
