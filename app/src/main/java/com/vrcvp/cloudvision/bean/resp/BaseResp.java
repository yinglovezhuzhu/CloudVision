package com.vrcvp.cloudvision.bean.resp;

/**
 * 网络请求返回数据实体类基类
 * 
 * Create by yinglovezhuzhu@gmail.com 2016/08/20
 */
public class BaseResp<T> {
	private int httpCode;
	private String msg;
	private long timestamp;
    private T data;

	public BaseResp() {

	}

	public BaseResp(int httpCode, String msg) {
		this.httpCode = httpCode;
		this.msg = msg;
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

	public long getTimestamp() {
		return timestamp;
	}

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "BaseResp{" +
                "httpCode=" + httpCode +
                ", msg='" + msg + '\'' +
                ", timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }

}
