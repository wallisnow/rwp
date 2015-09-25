package com.congxing.core.hibernate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Condition implements Serializable{
	
	private String expression;//属性表达式
	//最大提供单个字段五个查询条件
	private Map<String, Object> params = new HashMap<String, Object>(5);
	
	/**
	 * 条件构造
	 * @param expression			原始属性条件表达式
	 * @param placePropertyName		点位属性名称
	 * @param placePropertyValue	点位属性值
	 */
	public Condition(String expression, String placePropertyName, Object placePropertyValue) {
		this.expression = expression;
		this.getParams().put(placePropertyName, placePropertyValue);
	}

	public Condition(String expression, Map<String, Object> placePropertys) {
		this.expression = expression;
		this.getParams().putAll(placePropertys);
	}
	
	public Condition(String expression) {
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
