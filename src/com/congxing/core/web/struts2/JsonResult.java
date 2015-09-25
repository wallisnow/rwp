package com.congxing.core.web.struts2;

import java.io.Serializable;

import com.congxing.core.utils.JsonUtils;

@SuppressWarnings("serial")
public class JsonResult implements Serializable {
	
	/* 结果 */
	private String status;
	
	private String message;
	
	public JsonResult() {
	}

	public JsonResult(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public JsonResult(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toJson(){
		return JsonUtils.toJson(this);
	}
	
	public static JsonResult create(String status, String message){
		return new JsonResult(status, message);
	}
	
	public static void main(String []args){
		JsonResult result = new JsonResult("S", "aa");
		System.out.println(result.toJson());
		System.out.println(JsonUtils.toJson(result));
	}
	
}
