package com.httpclient1.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;


public class testHttp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sendPost();
	}
	//postJson right
	public static void sendPost() {
		String resp= null;  
		JSONObject obj = new JSONObject(); 
		 obj.put("name", "张三");
		 obj.put("age", "18");
		 obj.put("query", "q18");
		 String query = obj.toString();
		 System.out.println("发送到URL的报文为：");
		 System.out.println(query);
		 try { 
			URL url = new URL("http://localhost:8080/serverOne/services/query"); 
		//url地址
		 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		 connection.setDoInput(true);
		connection.setDoOutput(true);
		 connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		 connection.setInstanceFollowRedirects(true);
		 connection.setRequestProperty("Content-Type","application/json");
		 connection.connect();
		 try (OutputStream os = connection.getOutputStream()) {
			 os.write(query.getBytes("UTF-8")); }
		  try (BufferedReader reader = new BufferedReader( 
				new InputStreamReader(connection.getInputStream()))) {
			 String lines;
			 StringBuffer sbf = new StringBuffer();
			while ((lines = reader.readLine()) != null) {
				 lines = new String(lines.getBytes(), "utf-8");
				 sbf.append(lines);}
			System.out.println("返回来的报文："+sbf.toString());
			 resp = sbf.toString(); } 
		connection.disconnect();} 
		catch (Exception e) {
			 e.printStackTrace();
			}finally{
				JSONObject json = (JSONObject)JSONObject.fromObject(resp);
				System.out.println(json);
			 }
		}

	}


