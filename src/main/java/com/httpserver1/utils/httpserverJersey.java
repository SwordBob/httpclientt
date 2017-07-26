package com.httpserver1.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

@Path("/serverJersey")
public class httpserverJersey {
	/**
	 * 模拟外部数据采集中转
	 */
	@POST
	@Path("/getZydServcieData")
	@Produces("text/plain;charset=UTF-8")
	public static String getZydServcieData(@FormParam("upsysId") String upsysid,
			@FormParam("serialId") String serialId,
			@FormParam("msgcontent") String msgcontent,
			@FormParam("mobiles") String[] mobiles){
		
				return msgcontent;
		
}
	public static void main(String[] args) {
		pushToZydInfo();
	}
	//postJson right
	public static String pushToZydInfo(){
		String url = "http://localhost:8080/serverOne/services/query"; 
		HttpClient httpClient = new HttpClient();
		// 设置连接超时时间(单位毫秒) 
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60*1000);
		// 设置读取超时时间(单位毫秒) 
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60*1000); 
		PostMethod method = new PostMethod(url);
		String info = null; 
		try { 
			String aaa = "{\"token\": \"ee32da94162d4b688af2b0241db4600a\",\"touser\":\"AB044979\",\"query\":\"qAB044979\""+ ",\"msgtype\":\"text\",\"msg\":{\"content\": \"Hello\"},\"start\":\"\",\"end\":\"2015-05-30 00:00:00\"}"; 
			RequestEntity entity = new StringRequestEntity(aaa, "application/json", "UTF-8"); 
			method.setRequestEntity(entity);
			httpClient.executeMethod(method);
			int code = method.getStatusCode(); 
			if (code == HttpStatus.SC_OK) { BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream())); 
			StringBuffer stringBuffer = new StringBuffer();
			String str = ""; 
			while ((str = reader.readLine()) != null) { stringBuffer.append(str);
			} info = stringBuffer.toString(); 
			System.out.println("bbchat 返回报文："+info); 
			}else{ 
				System.out.println("bbchat 接口返回失败 httpStatusCode="+code); 
			} 
			}
		catch (Exception ex) { 
			System.out.println("内部接口报文发送异常:" + ex.getMessage()); 
			ex.printStackTrace(); 
			} 
		finally { if (method != null) { method.releaseConnection(); 
			} 
			} 
		return info;
			}
}
