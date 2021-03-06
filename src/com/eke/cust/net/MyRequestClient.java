package com.eke.cust.net;

import android.content.Context;

import com.eke.cust.utils.MyLog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.PreferencesCookieStore;

public class MyRequestClient {

	private static String TAG = "MyRestClient";
	private static HttpUtils client;
	Context context;

	public MyRequestClient(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		client = getInstence(context);
	}

	public synchronized static HttpUtils getInstence(Context context) {
		if (client == null) {
			// 设置请求超时时间为10秒
			client = new HttpUtils(1000 * 10);
			client.configSoTimeout(1000 * 10);
			client.configResponseTextCharset("UTF-8");
			// 保存服务器端(Session)的Cookie
			PreferencesCookieStore cookieStore = new PreferencesCookieStore(
					context);
			cookieStore.clear(); // 清除原来的cookie
			client.configCookieStore(cookieStore);
		}
		return client;
	}

	public static void get(String url, RequestParams params,
			RequestCallBack<String> responseHandler) {
		client.send(HttpRequest.HttpMethod.GET, getAbsoluteUrl(url), params,
				responseHandler);
		MyLog.i(TAG, getAbsoluteUrl(url) + "?" + MyLog.getStringParams(params));
	}
	
	
	public static void getTwo(String url, RequestParams params,
			RequestCallBack<String> responseHandler) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(url+"/");
		if (params != null && params.getQueryStringParams() != null) {
			if (params.getQueryStringParams().size() > 0) {
				for (int i = 0; i < params.getQueryStringParams().size(); i++) {
					if (i == params.getQueryStringParams().size() - 1) {
						buffer.append(params.getQueryStringParams().get(i));
					} else {
						buffer.append(params.getQueryStringParams().get(i)
								+ "/");
					}
				}
			}
		}
		client.send(HttpRequest.HttpMethod.GET, getAbsoluteUrl(buffer.toString()), params,
				responseHandler);
		MyLog.i(TAG, getAbsoluteUrl(buffer.toString()));
	}

	public static void Post(String url, RequestParams params,
			RequestCallBack<String> responseHandler) {
		client.send(HttpRequest.HttpMethod.POST, getAbsoluteUrl(url), params,
				responseHandler);
		MyLog.i(TAG, getAbsoluteUrl(url) + "?" + MyLog.getStringParams(params));
	}

	private static String getAbsoluteUrl(String relativeUrl) {
			return ServerUrl.BASE_URL + relativeUrl;
	}
}
