package com.jcg.vo;

public class ResponseVO {

	private String message;
	public String getMessage() {
		return message;
	}
	public Integer getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private Integer responseCode;
}
