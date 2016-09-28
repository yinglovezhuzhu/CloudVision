package com.xunda.cloudvision.view;

/**
 * 产品搜索View接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/28.
 */

public interface IProductSearchView {

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
