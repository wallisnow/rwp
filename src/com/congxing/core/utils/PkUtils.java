package com.congxing.core.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;


@SuppressWarnings("serial")
public class PkUtils implements Serializable {
	
	private PkUtils() {
		super();
	}
	
	/**
	 * 根据提供voClass pkName pkValue返回主键
	 * @throws IllegalAccessException 
	 * @throws Exception 
	 */
	public static Serializable getPkVO(Class<?> voClass, String pkName, String pkValue) throws Exception{
		return getPkVO(voClass, pkName.split("\\|"), pkValue);
	}
	
	/**
	 * 根据提供voClass pkNames pkValue返回主键
	 * @throws IllegalAccessException 
	 * @throws Exception 
	 */
	public static Serializable getPkVO(Class<?> voClass, String[] pkNameArray, String pkValue) throws Exception{
		return getPkVO(voClass, pkNameArray, pkValue.split("\\|"));
	}
	
	/**
	 * 根据提供voClass pkNames pkValues返回主键
	 * @throws IllegalAccessException 
	 * @throws Exception 
	 */
	public static Serializable getPkVO(Class<?> voClass, String[] pkNameArray, String[] pkValues) throws Exception{
		if(null == pkValues || pkNameArray.length != pkValues.length)throw new Exception("获取主键参数错误");
		if(pkNameArray.length == 1){
			Field fidlds[] = voClass.getDeclaredFields();
			for (Field field : fidlds) {
				String propertyName = field.getName();
				if(propertyName.compareTo(pkNameArray[0]) == 0){
					return (Serializable) ConvertUtils.convert(pkValues[0], field.getType());
				}
			}
		}else if(pkNameArray.length > 1){
			Serializable pkVO =  (Serializable) voClass.newInstance();
			Field fidlds[] = pkVO.getClass().getDeclaredFields();
			for (int index = 0; index < pkNameArray.length; index++) {
				for (Field field : fidlds) {
					String propertyName = field.getName();
					if(propertyName.compareTo(pkNameArray[index]) == 0){
						BeanUtils.setProperty(pkVO, pkNameArray[index], ConvertUtils.convert(pkValues[index], field.getType()));
						break;
					}
				}
			}
			return pkVO;
		}else{
			throw new Exception("主键定义有误");
		}
		return null;
	}
	
	/**
	 * 根据提供voClass pkName pkValueMap返回主键
	 * @throws IllegalAccessException 
	 * @throws Exception 
	 */
	public static Serializable getPkVO(Class<?> voClass, String pkName, Map<String, Object> pkValueMap) throws Exception{
		return getPkVO(voClass, pkName.split("\\|"), pkValueMap);
	}
	
	/**
	 * 根据提供voClass pkNameArray pkValueMap返回主键
	 * @throws IllegalAccessException 
	 * @throws Exception 
	 */
	public static Serializable getPkVO(Class<?> voClass, String[] pkNameArray, Map<String, Object> pkValueMap) throws Exception{
		if(pkNameArray.length == 1){
			Field fidlds[] = voClass.getDeclaredFields();
			for (Field field : fidlds) {
				String propertyName = field.getName();
				if(propertyName.compareTo(pkNameArray[0]) == 0){
					if(!pkValueMap.containsKey(propertyName))throw new Exception("pkValueMap中无主键值");
					return (Serializable) ConvertUtils.convert((String)pkValueMap.get(propertyName), field.getType());
				}
			}
		}else if(pkNameArray.length > 1){
			Serializable pkVO =  (Serializable) voClass.newInstance();
			Field fidlds[] = pkVO.getClass().getDeclaredFields();			
			for (int index = 0; index < pkNameArray.length; index++) {
				for (Field field : fidlds) {
					String propertyName = field.getName();
					if(propertyName.compareTo(pkNameArray[index]) == 0){
						if(!pkValueMap.containsKey(propertyName))throw new Exception("pkValueMap中无主键值");
						BeanUtils.setProperty(pkVO, pkNameArray[index], ConvertUtils.convert((String)pkValueMap.get(propertyName), field.getType()));
						break;
					}
				}
			}
			return pkVO;
		}else{
			throw new Exception("主键定义有误");
		}
		return null;
	}

}
