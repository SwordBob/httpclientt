package com.httpclient1.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.protocol.HTTP;

//import com.google.gson.Gson;
@Path("/httpJsonCom")
public class httpJsonCom {

	//接收HTTPPost中的JSON: 
	@POST
	@Path("/receivePost")
	      public static String receivePost(HttpServletRequest request) throws IOException, UnsupportedEncodingException { 
	    	  // 读取请求内容
	    	  BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	  String line = null; 
	    	  StringBuilder sb = new StringBuilder(); 
	    	  while((line = br.readLine())!=null){ sb.append(line); 
	    	  } 
	    	  // 将资料解码 
	    	  String reqBody = sb.toString(); 
	    	  return URLDecoder.decode(reqBody, HTTP.UTF_8); 
	    	  }
	    	  
	      
	     // 以上摘自：http://chaico.iteye.com/blog/1954128

	      //以下为自己开发实例：

	      //接收HTTPPost中的JSON:

	// public AttendanceInfo getAttendanceInfoFromBbchat(){ // 读取请求内容 ToftContext context = ToftContext.getToftContext(); try { InputStream in = context.getRequest().getInputStream(); BufferedReader br = new BufferedReader(new InputStreamReader(in)); StringBuffer stringBuffer = new StringBuffer(); String str = ""; while ((str = br.readLine()) != null) { stringBuffer.append(str); } String info = stringBuffer.toString(); if(StringUtils.isNotBlank(info)){ Gson gson = new Gson(); AttendanceInfo attendanceInfo = gson.fromJson(info, AttendanceInfo.class); if(StringUtils.isNotBlank(attendanceInfo.getToken())){ context.getRequest().setAttribute("token", attendanceInfo.getToken()); } return attendanceInfo; } }catch (Exception e) { // TODO: handle exception log.error("bbchat 解析邦邦社区考勤json参数出现异常"); e.printStackTrace(); } return null; }
	     // HTTPPost发送JSON：

	public static String pushAttendanceInfo(){
		String url = "http://ibb.anbanggroup.com:8080/push/push"; 
		HttpClient httpClient = new HttpClient();
		// 设置连接超时时间(单位毫秒) 
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60*1000);
		// 设置读取超时时间(单位毫秒) 
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60*1000); 
		PostMethod method = new PostMethod(url);
		String info = null; 
		try { 
			String aaa = "{\"token\": \"ee32da94162d4b688af2b0241db4600a\",\"touser\":\"AB044979\""+ ",\"msgtype\":\"text\",\"msg\":{\"content\": \"Hello\"},\"start\":\"\",\"end\":\"2015-05-30 00:00:00\"}"; 
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
	
	      //HTTPPost发送参数： 
	       /** * 考勤推送接口登陆 * @return */
	public static String loginAttendancePush(){
		String url = "http://ibb.anbanggroup.com:8080/authenticate/ablogin"; 
	HttpClient httpClient = new HttpClient();
	// 设置连接超时时间(单位毫秒)
	httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60*1000); 
	// 设置读取超时时间(单位毫秒) 
	httpClient.getHttpConnectionManager().getParams().setSoTimeout(60*1000);
	PostMethod method = new PostMethod(url); 
	String info = null; 
	try {
		method.setParameter("username", "27607"); 
	method.setParameter("password", "cd55abee1c0ef6d4525a223faf00c96a193576f58ceb39b9");
	httpClient.executeMethod(method); 
	int code = method.getStatusCode(); 
	if (code == HttpStatus.SC_OK) { BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
	StringBuffer stringBuffer = new StringBuffer(); 
	String str = ""; 
	while ((str = reader.readLine()) != null) { 
		stringBuffer.append(str);
	} 
	info = stringBuffer.toString(); 
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
