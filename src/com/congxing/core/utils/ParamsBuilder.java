package com.congxing.core.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;


public class ParamsBuilder {
	
	/**
	 * 从HttpRequest中创建Map信息
	 * 
	 * <p>
	 * Map值只有两种类型：字符串或字符串数据
	 * </p>
	 * 
	 * _deq_age _sk_name _sin_xx
	 * 
	 */
	public static Map<String, Object> buildMapFromHttpRequest() {
		return ParamsBuilder.buildMapFromHttpRequest(Struts2Utils.getRequest());
	}


	/**
	 * 
	 * 从HttpRequest中创建Map信息
	 * 
	 * 考虑有数组情况,所以需要采用Map<String, Object>格式
	 * 
	 * <p>
	 * Map值只有两种类型：字符串或字符串数据
	 * </p>
	 * 
	 * _deq_age _sk_name _sin_xx
	 */
	public static Map<String, Object> buildMapFromHttpRequest(final HttpServletRequest request) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Enumeration<?> paramNames = request.getParameterNames();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] values = request.getParameterValues(paramName);
			String[] effectiveValues = getEffectiveValue(values);//去掉数组中空值信息
			if (effectiveValues.length > 1) {
				paramsMap.put(paramName, effectiveValues);
			}else if(effectiveValues.length == 1){
				paramsMap.put(paramName, effectiveValues[0]);
			}
		}
		return paramsMap;
	}
	
	/**
	 * 取页面有效值集合
	 * @param @param values
	 * @param @return
	 * @return String[]
	 * @throws
	 */
	public static String[] getEffectiveValue(String[] values){
		String []rtns = new String[]{};
		for (int i = 0; i < values.length; i++) {
			if(StringUtils.isNotBlank(values[i])){
				rtns = ArrayUtils.add(rtns, values[i]);
			}
		}
		return rtns;
	}
	
}
