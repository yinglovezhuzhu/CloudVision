package com.xunda.cloudvision.view;

import com.xunda.cloudvision.bean.resp.ActivateResp;

/**
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public interface IActivateView {

    public String getCodeText();

    public void onCodeEmpty();

    void onActivateResult(ActivateResp result);

}
