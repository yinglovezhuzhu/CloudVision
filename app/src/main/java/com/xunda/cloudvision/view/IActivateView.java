package com.xunda.cloudvision.view;

import com.xunda.cloudvision.bean.resp.ActivateResp;

/**
 * 激活View接口
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public interface IActivateView {

    public String getCodeText();

    public void onCodeEmpty();

    void onActivateResult(ActivateResp result);

}
