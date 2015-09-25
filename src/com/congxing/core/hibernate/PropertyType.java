package com.congxing.core.hibernate;

import java.util.Date;


/**
 * 
 * 属性前缀定义
 * S、s: 字符数据类型
 * D、d: 日期数据类型
 * L、l: 长整形数据类型
 * N、n: 整型、浮点型数据类型
 * 
 * @author Administrator
 *
 */
public enum PropertyType {
	
	s(String.class), d(Date.class), l(Long.class), n(Double.class), i(Integer.class),
	S(String.class), D(Date.class), L(Long.class), N(Double.class), I(Integer.class);

	private Class<?> clazz;

	private PropertyType(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getValue() {
		return clazz;
	}

}
