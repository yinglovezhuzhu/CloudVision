package com.vrcvp.cloudvision.http;

import com.google.gson.Gson;
import com.vrcvp.cloudvision.utils.LogUtils;
import com.vrcvp.cloudvision.utils.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网络请求
 * 
 * Create by yinglovezhuzhu@gmail.com 2016/08/19
 */
public final class HttpRequest {

	private static final String TAG = "HttpRequest";

	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final int CONNECTION_TIMEOUT = 16 * 1000;
	private static final int READ_TIMEOUT = 10 * 1000;
	private static final int HTTP_BUFFER_SIZE =  8 * 1024;
	private static final int DOWNLOAD_BUFFER_SIZE = 1024 * 1024 * 8;

	/**
	 * HTTP POST请求
	 * @param urlString URL地址
	 * @param params Map参数
	 * @return 网络请求结果 {@linkplain HttpResult}类型
	 * @throws MalformedURLException 异常
	 * @throws UnsupportedEncodingException 不支持编码异常
	 * @throws ProtocolException 协议异常
	 * @throws IOException IO异常
	 */
	public static HttpResult<String> httpPostRequest(String urlString, Map<String, Object> params)
			throws IOException {

		String paramString = getParamsString(params, true, true);

		return httpPostRequest(urlString, paramString);
	}

	/**
	 * HTTP POST 请求
	 * @param urlString 请求地址
	 * @param params 参数字符串
	 * @return 请求结果
	 * @throws IOException
	 */
	public static HttpResult<String> httpPostRequest(String urlString, String params) throws IOException {
		LogUtils.i(TAG, "HTTP request url: " + urlString);
		HttpResult<String> httpResult;
		HttpURLConnection connection = null;
		OutputStream os = null;
		try {
			final URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(CONNECTION_TIMEOUT);
			connection.setReadTimeout(READ_TIMEOUT);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
//			String params = getWXPayCreateOrderReqParams(data);
			LogUtils.i(TAG, "HTTP request parameter: " + params);
			if(!StringUtils.isEmpty(params)) {
				os = connection.getOutputStream();
				os.write(params.getBytes("UTF-8"));
				os.flush();
			}
			String resultString = readFromStream(connection.getInputStream());

			LogUtils.i(TAG, "HTTP request response data: " + resultString);

			httpResult = new HttpResult<>(connection.getResponseCode(),
					connection.getResponseMessage());
			httpResult.setResponseData(resultString);
		} catch (IOException e) {
			httpResult = new HttpResult<>(HttpStatus.SC_EXPECTATION_FAILED, e.getMessage());
			throw new IOException(e);
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				connection.disconnect();
			}
		}
		return httpResult;
	}

	/**
	 * HTTP POST请求
	 * @param urlString URL地址
	 * @param header 请求Header
	 * @param params Map参数
	 * @return 网络请求结果 {@linkplain HttpResult}类型
	 * @throws MalformedURLException 异常
	 * @throws UnsupportedEncodingException 不支持编码异常
	 * @throws ProtocolException 协议异常
	 * @throws IOException IO异常
	 */
	public static HttpResult<String> httpGetRequest(String urlString, Map<String, String> header,
													Map<String, Object> params) throws IOException {

		LogUtils.i(TAG, "HTTP request url: " + urlString);

		String paramString = "";
		if ((null != params) && (!params.isEmpty())) {
			paramString = getParamsString(params, true, true);
			LogUtils.i(TAG, "HTTP request parameter: " + paramString);
		}

		URL url = new URL(urlString + "?" + paramString);
		HttpURLConnection connection = null;
		HttpResult<String> httpResult;
		try {
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setConnectTimeout(CONNECTION_TIMEOUT);
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-type", "text/html");
			connection.setRequestProperty("Referer", urlString);
			connection.setRequestProperty( "Accept-Encoding", "" );
			// 设置用户代理
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; "
					+ "MSIE 8.0; Windows NT 5.2;"
					+ " Trident/4.0; .NET CLR 1.1.4322;"
					+ ".NET CLR 2.0.50727; " + ".NET CLR 3.0.04506.30;"
					+ " .NET CLR 3.0.4506.2152; " + ".NET CLR 3.5.30729)");

			if(null != header && !header.isEmpty()) {
				final Set<Map.Entry<String, String>> entrySet = header.entrySet();
				for (Map.Entry<String, String> entry : entrySet) {
					connection.addRequestProperty(entry.getKey(), entry.getValue());
				}
			}

			connection.connect();

			String resultString = readFromStream(connection.getInputStream());

			LogUtils.i(TAG, "HTTP request response data: " + resultString);

			httpResult = new HttpResult<String>(connection.getResponseCode(),
					connection.getResponseMessage());
			httpResult.setResponseData(resultString);
			return httpResult;
		} catch (EOFException e) {
			String resultString;
			if (null != connection) {
				resultString = readFromStream(connection.getErrorStream());
				httpResult = new HttpResult<String>(connection.getResponseCode(),
						connection.getResponseMessage());
				httpResult.setResponseData(resultString);
			} else {
				httpResult = new HttpResult<String>(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Internal server error:"
						+ e.getMessage());
			}
			return httpResult;
		} catch (FileNotFoundException e) {
			if (null != connection) {
				String resultString = readFromStream(connection.getErrorStream());
				httpResult = new HttpResult<String>(connection.getResponseCode(),
						connection.getResponseMessage());
				httpResult.setResponseData(resultString);
			} else {
				httpResult = new HttpResult<String>(HttpStatus.SC_NOT_FOUND, "Page not found:"
						+ e.getMessage());
			}
			return httpResult;
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
		}
	}

	/**
	 * 下载单个文件（不支持断点）
	 * @param urlString 文件url地址
	 * @param saveFolder 保存目录
	 * @return 下载结果
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws ProtocolException
	 * @throws IOException
	 */
	public static HttpResult<File> downloadFile(String urlString, File saveFolder)
			throws MalformedURLException, UnsupportedEncodingException, ProtocolException, IOException {

		LogUtils.i(TAG, "Download file url: " + urlString);

		URL url = new URL(urlString);
		HttpURLConnection connection = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		HttpResult<File> httpResult;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(CONNECTION_TIMEOUT);
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Referer", urlString);
			// 设置用户代理
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; "
					+ "MSIE 8.0; Windows NT 5.2;"
					+ " Trident/4.0; .NET CLR 1.1.4322;"
					+ ".NET CLR 2.0.50727; " + ".NET CLR 3.0.04506.30;"
					+ " .NET CLR 3.0.4506.2152; " + ".NET CLR 3.5.30729)");
			connection.connect();
			httpResult = new HttpResult<File>(connection.getResponseCode(),
					connection.getResponseMessage());
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				in = new BufferedInputStream(connection.getInputStream(), DOWNLOAD_BUFFER_SIZE);
				if(!saveFolder.exists()) {
					saveFolder.mkdirs();
				}
				final File saveFile = new File(saveFolder, getFileName(connection, urlString));
				out = new BufferedOutputStream(new FileOutputStream(saveFile), DOWNLOAD_BUFFER_SIZE);

				byte[] buff = new byte[DOWNLOAD_BUFFER_SIZE];
				int count = 0;
				while ((count = in.read(buff)) != -1) {
					out.write(buff, 0, count);
				}
				httpResult.setResponseData(saveFile);
				LogUtils.i(TAG, "Saved file path: " + saveFile.getPath());
			}
			return httpResult;
		} catch (EOFException e) {
			if (null != connection) {
				httpResult = new HttpResult<File>(connection.getResponseCode(),
						connection.getResponseMessage());
				httpResult.setResponseData(null);
			} else {
				httpResult = new HttpResult<File>(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Internal server error:"
						+ e.getMessage());
			}
			return httpResult;
		} catch (FileNotFoundException e) {
			if (null != connection) {
				httpResult = new HttpResult<File>(connection.getResponseCode(),
						connection.getResponseMessage());
				httpResult.setResponseData(null);
			} else {
				httpResult = new HttpResult<File>(HttpStatus.SC_NOT_FOUND, "File not found:"
						+ e.getMessage());
			}
			return httpResult;
		} finally {
			if(null != in) {
				in.close();
			}
			if(null != out) {
				out.close();
			}
			if (null != connection) {
				connection.disconnect();
			}
		}
	}

	/**
	 * 将Map参数转换成URL参数形式，方便写入到数据流种
	 * @param params Map参数
	 * @param encodeByURLEncoder 是否使用URLEncoder对参数值进行Encode
	 * @param includeEmptyValue 是否包含空值，如果包含，空值的参数将保留，否则不出现空值的参数
	 * @return URL参数形式字符串
	 * @throws UnsupportedEncodingException 异常
	 */
	public static String getParamsString(Map<String, Object> params, boolean encodeByURLEncoder,
										 boolean includeEmptyValue) throws UnsupportedEncodingException {
		if ((null == params) || (params.isEmpty())) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Object value;
		String stringValue;
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			if ((null != entry.getValue()) || (includeEmptyValue)) {
				value = entry.getValue();
				if(null == value) {
					stringValue = "";
				} else if(value instanceof List || value instanceof Map) {
					stringValue = new Gson().toJson(value);
				} else {
					stringValue = String.valueOf(value);
				}
				if (!StringUtils.isEmpty(stringValue) || (includeEmptyValue)) {
					if (sb.length() > 0) {
						sb.append("&");
					}
					sb.append(entry.getKey())
							.append("=")
							.append(encodeByURLEncoder ? URLEncoder.encode(stringValue, "UTF-8") : stringValue);
				}
			}
		}
//		Set<String> keys = params.keySet();
//		for (String key : keys) {
//			Object value = params.get(key);
//			if ((null != value) || (includeEmptyValue)) {
//                final String stringValue;
//                if(null == value) {
//                    stringValue = "";
////                } else if(value instanceof List || value instanceof Map) {
////                    stringValue = new Gson().toJson(value);
//                } else {
//                    stringValue = String.valueOf(value);
//                }
//				if (!StringUtils.isEmpty(stringValue) || (includeEmptyValue)) {
//					if (sb.length() > 0) {
//						sb.append("&");
//					}
//					sb.append(key)
//							.append("=")
//							.append(encodeByURLEncoder ? URLEncoder.encode(stringValue, "UTF-8") : stringValue);
//				}
//			}
//		}
		return sb.toString();
	}

	/**
	 * Get file name
	 * @param conn HttpConnection object
	 * @return 文件名称，如果可以获取，获取网络名称，如果不可以，自动生成一个唯一名称
	 */
	private static String getFileName(HttpURLConnection conn, String urlString) {

		String filename = urlString.substring(urlString.lastIndexOf("/") + 1);

		if (StringUtils.isEmpty(filename)) {// Get file name failed.
			for (int i = 0;; i++) { // Get file name from http header.
				String mine = conn.getHeaderField(i);
				if (mine == null)
					break; // Exit the loop when go through all http header.
				if ("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase(Locale.ENGLISH))) { // Get content-disposition header field returns, which may contain a file name
					Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase(Locale.ENGLISH)); // Using regular expressions query file name
					if (m.find()) {
						return m.group(1); // If there is compliance with the rules of the regular expression string
					}
				}
			}
			filename = UUID.randomUUID() + ".tmp";// A 16-byte binary digits generated by a unique identification number
			// (each card has a unique identification number)
			// on the card and the CPU clock as the file name
		}
		return filename;
	}

	/**
	 * 从数据流种读取字符串
	 * @param is 数据流
	 * @return 字符串
	 * @throws IOException IO异常
	 */
	public static String readFromStream(InputStream is) throws IOException {
		if (null == is) {
			return "";
		}
		ByteArrayOutputStream baos = null;
		try {
			byte[] buff = new byte[HTTP_BUFFER_SIZE];
			int dataLength = -1;
			baos = new ByteArrayOutputStream(1);
			while ((dataLength = is.read(buff)) != -1) {
				baos.write(buff, 0, dataLength);
			}
			baos.flush();

			byte[] data = baos.toByteArray();
			return new String(data, DEFAULT_CHARSET);
		} finally {
			if (null != baos) {
				try {
					baos.close();
				} catch (Exception e) {
					// do nothing
				}
			}
		}
	}

	/**
	 * Get HTTP response header field
	 *
	 * @param conn HttpURLConnection object
	 * @return HTTp response header field map.
	 */
	private static Map<String, String> getHttpResponseHeader(HttpURLConnection conn) {
		Map<String, String> header = new LinkedHashMap<String, String>();
		for (int i = 0;; i++) {
			String fieldValue = conn.getHeaderField(i);
			if (fieldValue == null) {
				break;
			}
			header.put(conn.getHeaderFieldKey(i), fieldValue);
		}
		return header;
	}

	/**
	 * Get HTTP response header field as a string
	 * @param conn HttpURLConnection object
	 * @return HTTP response header field as a string
	 */
	private static String getResponseHeader(HttpURLConnection conn) {
		Map<String, String> header = getHttpResponseHeader(conn);
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : header.entrySet()) {
			String key = entry.getKey() != null ? entry.getKey() + ":" : "";
			sb.append(key + entry.getValue());
		}
		return sb.toString();
	}


	/**
	 * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
	 *
	 * @param url 访问的服务器URL
	 * @param params    普通参数
	 * @param files     文件参数
	 * @return 服务器返回结果
	 * @throws IOException
	 */
	public static HttpResult<String> httpPostMultipleEntityRequest(String url, Map<String, String> params,
																   Map<String, File> files) throws IOException {
		LogUtils.d(TAG, "httpPostMultipleEntityRequest--url：" + url);
		final String boundary = java.util.UUID.randomUUID().toString();
		final String prefix = "--";
		final String lineEnd = "\r\n";
		final String multipartFromData = "multipart/form-data";
		HttpResult<String> httpResult;
		HttpURLConnection connection = null;
		DataOutputStream outStream = null;
		try {
			URL uri = new URL(url);
			connection = (HttpURLConnection) uri.openConnection();
			connection.setReadTimeout(READ_TIMEOUT); // 缓存的最长时间
			connection.setDoInput(true);// 允许输入
			connection.setDoOutput(true);// 允许输出
			connection.setUseCaches(false); // 不允许使用缓存
			connection.setRequestMethod("POST");
			connection.setRequestProperty("connection", "keep-alive");
			connection.setRequestProperty("Charsert", DEFAULT_CHARSET);
			connection.setRequestProperty("Content-Type", multipartFromData + ";boundary=" + boundary);

			// 首先组拼文本类型的参数
			final StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(prefix);
				sb.append(boundary);
				sb.append(lineEnd);
				sb.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"").append(lineEnd);
				sb.append("Content-Type: text/plain; charset=").append(DEFAULT_CHARSET).append(lineEnd);
				sb.append("Content-Transfer-Encoding: 8bit").append(lineEnd);
				sb.append(lineEnd);
				sb.append(entry.getValue());
				sb.append(lineEnd);
			}

			outStream = new DataOutputStream(connection.getOutputStream());
			outStream.write(sb.toString().getBytes());
			// 发送文件数据
			if (files != null) {
				final int fileCount = files.size();
				int fileIndex = 0;
				File currentFile;
				LogUtils.d(TAG, "请求中共有" + fileCount + "个文件需要上传");
				InputStream is;
				for (Map.Entry<String, File> entry : files.entrySet()) {
					currentFile = entry.getValue();
					LogUtils.e(TAG, "正在上传第" + (fileIndex + 1) + "个文件：" + currentFile.getPath());
					sb.delete(0, sb.length());
					sb.append(prefix);
					sb.append(boundary);
					sb.append(lineEnd);
					// name是post中传参的键 filename是文件的名称
					sb.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"; filename=\"").append(entry.getValue().getName()).append("\"").append(lineEnd);
					sb.append("Content-Type: application/octet-stream; charset=").append(DEFAULT_CHARSET).append(lineEnd);
					sb.append(lineEnd);
					outStream.write(sb.toString().getBytes());

					is = new FileInputStream(entry.getValue());
					byte[] buffer = new byte[HTTP_BUFFER_SIZE];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						outStream.write(buffer, 0, len);
					}

					is.close();
					outStream.write(lineEnd.getBytes());
					fileIndex++;
				}

				// 请求结束标志
				byte[] end_data = (prefix + boundary + prefix + lineEnd).getBytes();
				outStream.write(end_data);
				outStream.flush();
			}

			String resultString = readFromStream(connection.getInputStream());

			LogUtils.i(TAG, "HTTP request response data: " + resultString);

			httpResult = new HttpResult<>(connection.getResponseCode(),
					connection.getResponseMessage());
			httpResult.setResponseData(resultString);
			LogUtils.d(TAG, "httpPostMultipleEntityRequest result：------" + httpResult);
			return httpResult;
		} catch (EOFException e) {
			String resultString;
			if (null != connection) {
				resultString = readFromStream(connection.getErrorStream());
				httpResult = new HttpResult<>(connection.getResponseCode(),
						connection.getResponseMessage());
				httpResult.setResponseData(resultString);
			} else {
				httpResult = new HttpResult<>(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Internal server error:"
						+ e.getMessage());
			}
			LogUtils.d(TAG, "httpPostMultipleEntityRequest result：------" + httpResult);
			return httpResult;
		} catch (FileNotFoundException e) {
			if (null != connection) {
				String resultString = readFromStream(connection.getErrorStream());
				httpResult = new HttpResult<>(connection.getResponseCode(),
						connection.getResponseMessage());
				httpResult.setResponseData(resultString);
			} else {
				httpResult = new HttpResult<>(HttpStatus.SC_NOT_FOUND, "File not found:"
						+ e.getMessage());
			}
			LogUtils.d(TAG, "httpPostMultipleEntityRequest result：------" + httpResult);
			return httpResult;
		} finally {
			if (null != outStream) {
				try {
					outStream.close();
				} catch (Exception e) {
					// do nothing
				}
			}
			if (null != connection) {
				connection.disconnect();
			}
		}
	}

}
