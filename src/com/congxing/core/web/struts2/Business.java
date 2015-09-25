package com.congxing.core.web.struts2;

import java.io.Serializable;

import com.congxing.core.utils.JsonUtils;

@SuppressWarnings("serial")
public class Business implements Serializable {
	
	public String getBusiName(){
		return this.getClass().getName();
	}
	
	public String getBusiValue(){
		return JsonUtils.toJson(this); 
	}
	
}
