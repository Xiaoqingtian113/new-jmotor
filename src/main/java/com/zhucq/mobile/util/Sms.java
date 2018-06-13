package com.zhucq.mobile.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class Sms {

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(getcode("172.29.150.91", "19961189923", "注册"));
	}

	public static String getcode2(String host, String tel, String yw) throws Exception {
		for (int i = 0; i < 50; i++) {
			String yzm = getcode(host, tel, yw);
			if (yzm == null) {
				Thread.sleep(5000);
			} else
				return yzm;
		}
		return "验证码获取失败";
	}

	public static String getcode(String host, String tel, String yw) {
		String loginUrl = "http://" + host + "/sms-frontal/login";
		// 需登陆后访问的 Url
		String dataUrl = "http://" + host + "/sms-frontal/MessageController/queryMsg";
		HttpClient httpClient = new HttpClient();
		// 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
		PostMethod postMethod = new PostMethod(loginUrl);
		// 设置登陆时要求的信息，用户名和密码
		NameValuePair[] data = { new NameValuePair("username", "sms_test"), new NameValuePair("password", "sms_test") };
		postMethod.setRequestBody(data);
		try {
			// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.executeMethod(postMethod);
			// 获得登陆后的 Cookie
			Cookie[] cookies = httpClient.getState().getCookies();
			StringBuffer tmpcookies = new StringBuffer();
			for (Cookie c : cookies) {
				tmpcookies.append(c.toString() + ";");
			}

			// 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
			PostMethod postMethod2 = new PostMethod(dataUrl);
			postMethod2.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			// 设置登陆时要求的信息，用户名和密码
			NameValuePair[] data2 = { new NameValuePair("phoneNumber", tel), new NameValuePair("page", "1"),
					new NameValuePair("rows", "10"), new NameValuePair("besId", yw) };
			postMethod2.setRequestBody(data2);
			httpClient.executeMethod(postMethod2);

			// String text = postMethod2.getResponseBodyAsString();
			// System.out.println(text);

			InputStream inputStream = postMethod2.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			String text = stringBuffer.toString();

			// 目标字符串 取出验证码
			String c = text;
			// 正则初始化
			Pattern p = Pattern.compile("content(.*?)/(.*?)/(.*?)([\\d+]{6})(.*?)10");
			// 匹配器初始化
			Matcher m = p.matcher(c);
			// 匹配查询
			while (m.find()) {
				return m.group(4);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
