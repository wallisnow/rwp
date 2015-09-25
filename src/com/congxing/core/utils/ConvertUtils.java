/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: ConvertUtils.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package com.congxing.core.utils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;

public class ConvertUtils {

	static {
		registerConverter();
	}

	/**
	 * 转换字符串到相应类型.
	 * 
	 * @param value 待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object convert(Object value, Class<?> toType) {
		try {
			return BeanUtilsBean.getInstance().getConvertUtils().convert(value, toType);
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 定义日期Converter的格式: yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
	 * 注意：
	 * 前台在转换过程中不需要转换异常报错
	 */
	private static void registerConverter() {
		DateConverter dc = new DateConverter(null);
		dc.setUseLocaleFormat(true);
		dc.setPatterns(new String[] { "yyyyMMdd", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyyMM"});
		BeanUtilsBean.getInstance().getConvertUtils().register(dc, java.util.Date.class);
		SqlDateConverter sdc = new SqlDateConverter(null);
		sdc.setUseLocaleFormat(true);
		sdc.setPatterns(new String[] { "yyyyMMdd", "yyyy-MM-dd", "yyyyMM"});
		BeanUtilsBean.getInstance().getConvertUtils().register(sdc, java.sql.Date.class);
	}
}
