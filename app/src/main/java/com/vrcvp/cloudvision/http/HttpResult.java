package com.vrcvp.cloudvision.http;

/**
 * 网络请求结果
 * 
 * Create by yinglovezhuzhu@gmail.com 2016/08/19
 */
public class HttpResult<T> {
	private int responseCode;
	private String responseMessage;
	private T responseData;

	public HttpResult() {
	}

	public HttpResult(int responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public int getResponseCode() {
		return this.responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return this.responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public T getResponseData() {
		return (T) this.responseData;
	}

	public void setResponseData(T responseData) {
		this.responseData = responseData;
	}

	public String toString() {
		return "HttpResult{responseCode=" + this.responseCode
				+ ", responseMessage='" + this.responseMessage + '\''
				+ ", responseData='" + this.responseData + '\'' + '}';
	}
}
