package com.congxing.core.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.congxing.core.utils.ConvertUtils;
import com.congxing.core.web.struts2.BaseListVO;

@SuppressWarnings("serial")
public class ConditionBuilder implements Serializable {
	
	/**
	 * 只考虑以"_"开始的查询条件
	 * @param @param listVO
	 * @param @param preFix
	 * @param @return
	 * @param @throws Exception
	 * @return List<Condition>
	 * @throws
	 */
	public static List<Condition> build(BaseListVO listVO, String voAilasName) throws Exception{		
		Map<?, ?> props = BeanUtils.describe(listVO);
		List<Condition> conditionList = new ArrayList<Condition>();
		for (Iterator<?> iter = props.keySet().iterator(); iter.hasNext();) {
			String pagePropertyName = (String) iter.next();
			if(!pagePropertyName.startsWith("_"))continue;//当key不是以"_"开始，代表该变更不是查询条件
			String []pagePropertyValues = BeanUtils.getArrayProperty(listVO, pagePropertyName);//BaseListVO中的值全部为String类型
			/**
			 * 先判断属性值是否有效
			 */
			if(null == pagePropertyValues || pagePropertyValues.length < 1)continue;
			Condition condition = buildCondition(voAilasName, pagePropertyName, pagePropertyValues);
			if(null != condition){
				conditionList.add(condition);
			}
		}
		return conditionList;
	}
	
	/**
	 * 按属性条件参数创建Condition,辅助函数.
	 * eg
	 * pagePropertyName = _seq_name || _sql_
	 */
	protected static Condition buildCondition(String voAilasName, String pagePropertyName, String[] pagePropertyValues) {
		/**
		 * 取得属性代表类型
		 */
		String propertyTypeName = pagePropertyName.substring(1, 2);
		/**
		 * 取属性比较类型
		 */
		String matchTypeName = pagePropertyName.substring(2, pagePropertyName.indexOf("_", 1));
		Class<?> propertyClass = null;
		try{
			propertyClass = Enum.valueOf(PropertyType.class, propertyTypeName).getValue();
		}catch (RuntimeException e) {
			throw new IllegalArgumentException("属性比较类型标识" + pagePropertyName + "没有按规则编写,无法得到属性比较类型.", e);
		}
		
		MatchType matchType = null;
		try{
			matchType = Enum.valueOf(MatchType.class, matchTypeName);
		}catch (RuntimeException e) {
			throw new IllegalArgumentException("属性比较类型标识" + pagePropertyName + "没有按规则编写,无法得到属性比较类型.", e);
		}
		/**
		 * 取属性名称
		 */
		String propertyName = pagePropertyName.substring(pagePropertyName.indexOf("_", 1) + 1);		
		
		if (StringUtils.isBlank(voAilasName)) {
			voAilasName = "";
		}
		/**
		 * HQL点位参数名称
		 * voAilasName + pagePropertyName
		 */
		String holderPropertyName = voAilasName + pagePropertyName.replaceAll("_", "");
		if (voAilasName.length() > 0) {
			voAilasName = voAilasName + ".";
		}
		/**
		 * HQL参数名称
		 * voAilasName+ "." + propertyName
		 */
		String leftPropertyName = voAilasName + propertyName;
		
		String expression = "";
		Object value = null;
		
		switch(matchType){
		case n:
			expression = leftPropertyName + " is null ";
			return new Condition(expression);
        case nn:   
        	expression = leftPropertyName + " is not null ";
        	return new Condition(expression);
        case sql:
        	expression = pagePropertyValues[0];
        	return new Condition(expression);
		case eq:
			expression = leftPropertyName + " = :" + holderPropertyName + " ";
			value = ConvertUtils.convert(pagePropertyValues[0], propertyClass);
			return new Condition(expression, holderPropertyName, value);
		case ne:
			expression = leftPropertyName + " <> :" + holderPropertyName + " ";
			value = ConvertUtils.convert(pagePropertyValues[0], propertyClass);
			return new Condition(expression, holderPropertyName, value);
        case k:
        	expression = leftPropertyName + " like :" + holderPropertyName + " ";
        	value = "%" + pagePropertyValues[0] + "%";
        	return new Condition(expression, holderPropertyName, value);
        case nk:
        	expression = leftPropertyName + " not like :" + holderPropertyName + " ";
        	value = "%" + pagePropertyValues[0] + "%";
        	return new Condition(expression, holderPropertyName, value);
        case lt:
        	expression = leftPropertyName + " < :" + holderPropertyName + " ";
        	value = ConvertUtils.convert(pagePropertyValues[0], propertyClass);
        	return new Condition(expression, holderPropertyName, value);
        case le:
        	expression = leftPropertyName + " <= :" + holderPropertyName + " ";
        	value = ConvertUtils.convert(pagePropertyValues[0], propertyClass);
        	return new Condition(expression, holderPropertyName, value);
        case gt:
        	expression = leftPropertyName + " > :" + holderPropertyName + " ";
        	value = ConvertUtils.convert(pagePropertyValues[0], propertyClass);
        	return new Condition(expression, holderPropertyName, value);
        case ge:   
        	expression = leftPropertyName + " >= :" + holderPropertyName + " ";
        	value = ConvertUtils.convert(pagePropertyValues[0], propertyClass);
        	return new Condition(expression, holderPropertyName, value);
        case in://IN模式需要测试才能使用
        	if(pagePropertyValues.length == 1){
        		pagePropertyValues = StringUtils.split(pagePropertyValues[0], ",");
        	}
    		StringBuffer inHolder = new StringBuffer();
        	Map<String, Object> inPlacePropertys = new HashMap<String, Object>();
    		for (int i = 0; i < pagePropertyValues.length; i++) {
    			inHolder.append(":").append(holderPropertyName+i).append(", ");
    			inPlacePropertys.put(holderPropertyName+i, ConvertUtils.convert(pagePropertyValues[i], propertyClass));
			}
    		inHolder.delete(inHolder.length()-2, inHolder.length());
			expression = leftPropertyName + " in (" + inHolder.toString() + ") ";
			return new Condition(expression, inPlacePropertys);
        case nin://NOT IN 模式需要测试才能使用
        	if(pagePropertyValues.length == 1){
        		pagePropertyValues = StringUtils.split(pagePropertyValues[0], ",");
        	}
    		StringBuffer notInHolder = new StringBuffer();
        	Map<String, Object> notInPlacePropertys = new HashMap<String, Object>();
    		for (int i = 0; i < pagePropertyValues.length; i++) {
    			notInHolder.append(":").append(holderPropertyName+i).append(", ");
    			notInPlacePropertys.put(holderPropertyName+i, ConvertUtils.convert(pagePropertyValues[i], propertyClass));
			}
    		notInHolder.delete(notInHolder.length()-2, notInHolder.length());
			expression = leftPropertyName + " not in (" + notInHolder.toString() + ") ";
			return new Condition(expression, notInPlacePropertys);
		}
			
		return null;
	}
	
}
