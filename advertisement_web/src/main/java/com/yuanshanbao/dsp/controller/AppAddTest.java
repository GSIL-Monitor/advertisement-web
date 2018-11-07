package com.yuanshanbao.dsp.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

public class AppAddTest {
	public static final String ADD_URL = "http://dev.t.huhad.com/dsp/content.html";
	public static final String ADD_URL_CON = "http://cond.xingdk.com/dsp/content.html";
	public static final String ADD_SHOW_URL = "http://dev.t.huhad.com/dsp/ad/show.html";

	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			appadd();
		}
	}

	public static void appadd() {
		try {
			URL url = new URL(ADD_URL_CON);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			connection.connect();
			// POST请求
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			JSONObject obj = new JSONObject();
			obj.element("channel", "jytest");
			// obj.element("pId", "7AA05E254CE1ED546971EABD0ECCFD20");
			// obj.element("key", "31C977A008B924F67C014EAFFA954FA6");
			out.writeBytes(obj.toString());
			System.out.println("data=" + obj.toString());
			out.flush();
			out.close();
			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			System.out.println(sb);
			reader.close();
			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
