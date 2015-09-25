package com.congxing.core.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.congxing.core.web.translate.TranslateUtils;

public abstract class BatchConvertUtils {
	
	public static final String TYPE_CODE2NAME = "CODE2NAME";
    public static final String TYPE_DATE = "DATE";
    public static final String TYPE_NUMBER = "NUMBER";
	
	private List<PropertyFormat> fields = new ArrayList<PropertyFormat>();
	private HashMap<String, Object> cacheMap = new HashMap<String, Object>();//要完成对单次导入的二维缓存
	
	private TranslateUtils translateUtils;
	
	public BatchConvertUtils() {
		translateUtils = SpringContextManager.getBean("translateUtils");
		register();
	}

	/**
	 * 注册字段和内容转换器
	 */
	abstract protected void register();
	
	/**
	 * 注册字段值及翻译属性
	 * @param property	属性字段
	 * @param classType	属性类型
	 * @param isNullable属性值是否可以为空
	 * @param transType	翻译类型(日期|数值|字典表)
	 * @param format	翻译串
	 */
	public void registField(String property, Class<?> classType, boolean isNullable, String transType, String format) {
		fields.add(new PropertyFormat(property, classType, isNullable, transType, format));
	}
	
	/**
	 * 注册字段值及翻译属性(主要针对String类型直接翻译)
	 * @param property	属性字段
	 * @param classType	属性类型
	 * @param isNullable属性值是否可以为空
	 */
	public void registField(String property, Class<?> classType, boolean isNullable) {
		fields.add(new PropertyFormat(property, classType, isNullable));
	}
	
	public void registField(String property, Class<?> classType, boolean isNullable, String transType) {
		fields.add(new PropertyFormat(property, classType, isNullable, transType, null));
	}
	
	
	public Object copy(Object vo, String[] values) throws Exception {
		for(int index = 0; index < values.length && index < fields.size(); index++) {
			PropertyFormat field = (PropertyFormat)fields.get(index);
			if(field != null) {
				String fieldname = field.fieldName;
				Class<?> fieldtype = field.fieldType;
				boolean isNullable = field.isNullable;
				String transType = field.transType;
				String format = field.format;
				if(isNullable){
					if(null != values[index] && !"".equals(values[index].trim())){
						try{
							this.setValue(vo, fieldname, this.buildValue(fieldtype, values[index]));
						}catch(Exception ex){
							throw new Exception("数据格式转换出错(" + fieldtype.getName() + " : " + values[index] + ")");
						}
					}else{
						continue;
					}
				}
				if(null == values[index] || "".equals(values[index].trim())){
					throw new Exception("字段(" + fieldname + ")不能为空");
				}				
				
				if(transType == null || "".equals(transType)){//直接赋值类型
					try{
						this.setValue(vo, fieldname, this.buildValue(fieldtype, values[index]));
					}catch(Exception ex){
						throw new Exception("数据格式转换出错(" + fieldtype.getName() + " : " + values[index] + ")");
					}
				}else if(BatchConvertUtils.TYPE_DATE.equals(transType)){//日期翻译类型
					try{
						this.setValue(vo, fieldname, DateFormatFactory.getInstance(format).parse(values[index]));
					}catch(Exception ex){
						throw new Exception("日期格式转换出错(" + format + " : " + values[index] + ")");
					}
				}else if(BatchConvertUtils.TYPE_NUMBER.equals(transType)){//数值翻译类型
					try{
						this.setValue(vo, fieldname, this.buildNumberValue(fieldtype, values[index]));
					}catch(Exception ex){
						throw new Exception("数值格式转换出错(" + fieldtype.getName() + " : " + values[index] + ")");
					}
				}else if(BatchConvertUtils.TYPE_CODE2NAME.equals(transType)){//数据字典翻译类型
					String key = fieldname + "~" + fieldtype.getName() + "~" + transType + "~" + values[index];
					if(!cacheMap.containsKey(key)){
						String convertValue = null;
						try{
							convertValue = translateUtils.format(format, values[index], false);
						}catch(Exception ex){
							throw new Exception(ex.getMessage());
						}
						if(StringUtils.isBlank(convertValue)){
							throw new Exception("请确认字段值是否有效(" + fieldname + " : " + values[index] + ")");
						}
						Object destValue = null;
						try{
							destValue = this.buildValue(fieldtype, convertValue);
						}catch(Exception ex){
							throw new Exception("数据格式转换出错(" + fieldtype.getName() + " : " + values[index] + ")");
						}
						
						if(null == destValue || "".equals(destValue)){
							throw new Exception("请确认字段值是否有效(" + fieldname + " : " + values[index] + ")");
						}
						cacheMap.put(key, destValue);
					}
					try{
						this.setValue(vo, fieldname, cacheMap.get(key));
					}catch(Exception ex){
						throw new Exception("数据格式转换出错(" + fieldtype.getName() + " : " + values[index] + ")");
					}
				}else{
					try{
						this.setValue(vo, fieldname, this.buildValue(fieldtype, values[index]));
					}catch(Exception ex){
						throw new Exception("数据格式转换出错(" + fieldtype.getName() + " : " + values[index] + ")");
					}
				}
			}
		}
		return vo;
	}
	
	private Object buildNumberValue(Class<?> type, String value) throws Exception{
		if(StringUtils.isBlank(value)){
			throw new Exception("值为空");
		}
		value = StringUtils.trim(value);
		if (type.equals(Short.class)){
			return new Short(value);
		} else if (type.equals(Integer.class)){
			return new Integer(value);
		} else if (type.equals(Long.class)){
			return new Long(value);
		} else if (type.equals(Float.class)){
			return new Float(value);
		} else if (type.equals(Double.class)){
			return new Double(value);
		} else if (type.equals(BigDecimal.class)){
			return new BigDecimal(value);
		} else if (type.equals(BigInteger.class)){
			return new BigInteger(value);
		} else {
			return ConvertUtils.convert(value, type);
		}
	}
	
	private Object buildValue(Class<?> type, String value) throws Exception{
		if(StringUtils.isBlank(value)){
			throw new Exception("值为空");
		}
		return ConvertUtils.convert(value, type);
	}
	
	private void setValue(Object vo, String fieldname, Object value) throws Exception{
		BeanUtils.setProperty(vo, fieldname, value);
	}
	
	
	private class PropertyFormat{
		public String fieldName;//翻译字段名
		public Class<?> fieldType;//翻译字段值类型
		public boolean isNullable;//字段是否可以为空
		public String transType;//翻译类型
		public String format;//解析值格式串
		
		/**
		 * 用法举例：PropertyFormat("name", java.lang.String)
		 * @param fieldName
		 * @param fieldType
		 */
		public PropertyFormat(String fieldName, Class<?> fieldType, boolean isNullable){
			this.fieldName = fieldName;
			this.fieldType = fieldType;
			this.isNullable = isNullable;
		}
		
		/**
		 * 用法举例：
		 * 渠道字典表：	PropertyFormat("districtMan", java.util.String, "CODE2NAME", "$DISCMAN") 				默认由kind取得kindname值
		 * 				PropertyFormat("districtMan", java.util.String, "CODE2NAME", "$DISCMAN*kindname*kind")	由kindname取得kind值
		 * 类反射机制：	PropertyFormat("comid", java.util.Long, "CODE2NAME", "!com.maywide.sell.com.model.ComVO*comname*comid")	由表ComVO中comname取得comid值
		 * 类翻译机制：	PropertyFormat("partnerid", java.util.String, "CODE2NAME", "Partnercode4name")
		 * 日期类翻译：	PropertyFormat("oprtime", java.util.Date, "DATE", "yyyy-MM-dd HH:mm:ss")
		 * @param fieldName
		 * @param fieldType
		 * @param transType
		 * @param format
		 */
		public PropertyFormat(String fieldName, Class<?> fieldType, boolean isNullable, String transType, String format){
			this.fieldName = fieldName;
			this.fieldType = fieldType;
			this.isNullable = isNullable;
			this.transType = transType;
			this.format = format;
		}
	}


	public TranslateUtils getTranslateUtils() {
		return translateUtils;
	}

	public void setTranslateUtils(TranslateUtils translateUtils) {
		this.translateUtils = translateUtils;
	}
	
}
