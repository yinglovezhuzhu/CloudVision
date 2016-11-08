package com.vrcvp.cloudvision.http;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.vrcvp.cloudvision.bean.resp.BaseResp;
import com.vrcvp.cloudvision.utils.BeanRefUtils;
import com.vrcvp.cloudvision.utils.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HttpAsyncTask<B extends BaseResp> {
	
	private AsyncTask<Object, Integer, B> mTask;

	public HttpAsyncTask() {

	}

	/**
	 * 执行一个异步网络POST请求
	 * @param url 请求URL地址
	 * @param reqParamBean 请求参数Bean
	 * @param resultClass 结果数据实体类Class
	 * @param callback 毁掉
	 */
	public void doPost(final String url, final Object reqParamBean,
					   final Class<? extends BaseResp> resultClass, final Callback<B> callback) {
		cancel();

		mTask = new AsyncTask<Object, Integer, B>(){
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				if(null != callback) {
					callback.onPreExecute();
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			protected B doInBackground(Object... params) {
				
				Object paramsBean = params[0];
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				if(null != paramsBean) {
					paramsMap.putAll(BeanRefUtils.getFieldValueMap(paramsBean));
				}

				BaseResp errorResult = null;
				try {
                    errorResult = (BaseResp) resultClass.newInstance();
					HttpResult<String> httpResult = HttpRequest.httpPostRequest(url, paramsMap);
					String responseData = httpResult.getResponseData();
					if (HttpStatus.SC_OK == httpResult.getResponseCode()) {
						return (B) new Gson().fromJson(responseData, resultClass);
					} else {
						errorResult.setHttpCode(httpResult.getResponseCode());
						errorResult.setMsg(httpResult.getResponseMessage());
					}
				} catch (IOException|IllegalAccessException|InstantiationException e) {
					e.printStackTrace();
                    if(null != errorResult) {
                        errorResult.setHttpCode(HttpStatus.SC_EXPECTATION_FAILED);
                        errorResult.setMsg(e.getMessage());
                    }
				}

				return (B) errorResult;
			}
			
			@Override
			protected void onPostExecute(B result) {
				super.onPostExecute(result);
                if(isCancelled()) {
                    if(null != callback) {
                        callback.onCanceled();
                    }
                    return;
                }
				if(null != callback) {
					if(isCancelled()) {
						callback.onCanceled();
						return;
					}
					callback.onResult(result);
				}
			}
			
		};
		mTask.execute(reqParamBean);
	}
	/**
	 * 执行一个异步网络POST请求
	 * @param url 请求URL地址
	 * @param reqParamBean 请求参数Bean
	 * @param resultClass 结果数据实体类Class
	 * @param callback 毁掉
	 */
	public void doGet(final String url, final Map<String, String> header, final Object reqParamBean,
					   final Class<? extends BaseResp> resultClass, final Callback<B> callback) {
		cancel();
		mTask = new AsyncTask<Object, Integer, B>(){

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				if(null != callback) {
					callback.onPreExecute();
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			protected B doInBackground(Object... params) {

				Object paramsBean = params[0];
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				if(null != paramsBean) {
					paramsMap.putAll(BeanRefUtils.getFieldValueMap(paramsBean));
				}

				BaseResp errorResult = null;
				try {
                    errorResult = (BaseResp) resultClass.newInstance();
					HttpResult<String> httpResult = HttpRequest.httpGetRequest(url, header, paramsMap);
					String responseData = httpResult.getResponseData();
					if (HttpStatus.SC_OK == httpResult.getResponseCode()) {
						return (B) new Gson().fromJson(responseData, resultClass);
					} else {
						errorResult.setHttpCode(httpResult.getResponseCode());
						errorResult.setMsg(httpResult.getResponseMessage());
					}
				} catch (IOException|IllegalAccessException|InstantiationException e) {
					e.printStackTrace();
                    if(null != errorResult) {
                        errorResult.setHttpCode(HttpStatus.SC_EXPECTATION_FAILED);
                        errorResult.setMsg(e.getMessage());
                    }
				}

				return (B) errorResult;
			}

			@Override
			protected void onPostExecute(B result) {
				super.onPostExecute(result);
                if(isCancelled()) {
                    if(null != callback) {
                        callback.onCanceled();
                    }
                    return;
                }
				if(null != callback) {
					if(isCancelled()) {
						callback.onCanceled();
						return;
					}
					callback.onResult(result);
				}
			}

		};
		mTask.execute(reqParamBean);
	}

	public void cancel() {
		if(null != mTask && !mTask.isCancelled()) {
			mTask.cancel(true);
		}
	}
	
	/**
	 * 回调
	 *
	 * @param <T> 结果数据实体类类型
	 */
	public interface Callback<T>{
		
		/**
		 * 异步执行前
		 */
		void onPreExecute();
		
		/**
		 * 取消异步任务
		 */
		void onCanceled();
		
		/**
		 * 异步任务返回结果
		 * @param result 结果数据
		 */
		void onResult(T result);
		
	}

}
