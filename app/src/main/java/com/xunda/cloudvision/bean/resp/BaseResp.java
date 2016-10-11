package com.xunda.cloudvision.bean.resp;

/**
 * 网络请求返回数据实体类基类
 * 
 * Create by yinglovezhuzhu@gmail.com 2016/08/20
 */
public class BaseResp {
	private int httpCode;
	private String msg;

	public BaseResp() {

	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}


	@Override
	public String toString() {
		return "BaseResp [httpCode=" + httpCode + ", msg=" + msg + "]";
	}

}
