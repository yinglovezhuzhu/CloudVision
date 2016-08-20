package com.xunda.cloudvision.bean.resp;

/**
 * 网络请求返回数据实体类基类
 * 
 * Create by yinglovezhuzhu@gmail.com 2016/08/20
 */
public class BaseResp<T> {
	private int code;
	private String msg;
	private T datas;

	public BaseResp() {

	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getDatas() {
		return datas;
	}

	public void setDatas(T datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		return "BaseResp [code=" + code + ", msg=" + msg + ", datas=" + datas
				+ "]";
	}

}
