package com.httpclient1.utils;

import javax.xml.bind.annotation.XmlRootElement;

public class Response {
	private String respCode;
	private String respDesc;
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
}
