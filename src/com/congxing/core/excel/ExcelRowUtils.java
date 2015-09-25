package com.congxing.core.excel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.congxing.core.excel.config.ColumnModel;
import com.congxing.core.excel.config.RowModel;
import com.congxing.core.utils.BatchConvertUtils;
import com.congxing.core.utils.ConvertUtils;
import com.congxing.core.utils.DateFormatFactory;
import com.congxing.core.utils.SpringContextManager;
import com.congxing.core.web.translate.TranslateUtils;

public class ExcelRowUtils {

	private List<ColumnModel> columnList;
	
	private HashMap<String, Object> cacheMap = new HashMap<String, Object>();//要完成对单次导入的二维缓存
	
	private TranslateUtils translateUtils;
	
	public ExcelRowUtils(RowModel rowModel){
		columnList = rowModel.getColumnList();
		translateUtils = SpringContextManager.getBean("translateUtils");
	}
	
	public void copy(Object targetVO, Object line) throws Exception {
		Cell []cells = (Cell[])line;
		for(ColumnModel columnModel : columnList){
			String propertyName = columnModel.getPropertyName();
			Class<?> propertyType = columnModel.getDataType();
			String titleName = columnModel.getTitleName();
			boolean nullAble = columnModel.isNullAble();
			String transType = columnModel.getTransType();
			String format = columnModel.getFormat();
			String defaultValue = columnModel.getDefaultValue();
			String value = this.getCellContent(cells, columnModel);
			
			if(StringUtils.isBlank(value)){
				value = defaultValue;
			}
			if(StringUtils.isBlank(value)){
				if(nullAble){
					continue;
				}else{
					throw new Exception(titleName + "[" + propertyName + "|" + propertyType.getName() + "] is null.");
				}
			}
			this.setPropertyValue(targetVO, propertyName, propertyType, transType, format, titleName, value);
		}
	}
	
	public String getCellContent(Cell []cells, ColumnModel columnModel){
		String value = "";
		if(columnModel.getColumnIndex() <= cells.length ){
			value = cells[columnModel.getColumnIndex() - 1].getContents();
		}
		if(StringUtils.isBlank(value))return "";
		return StringUtils.trim(value);//.replace("\n", "");
	}
	
	public void setPropertyValue(Object targetVO, String propertyName, Class<?> propertyType, String transType, String format, String titleName, String value)throws Exception {
		
		if(StringUtils.isBlank(transType)){//直接赋值类型
			try{
				this.setValue(targetVO, propertyName, this.buildValue(propertyType, value));
			}catch(Exception ex){
				throw new Exception("数据格式转换出错(" + titleName + "[" + propertyName + "|" + propertyType.getName() + "] : " + value + ")");
			}
		}else if(BatchConvertUtils.TYPE_DATE.equals(transType)){//日期翻译类型
			try{
				Object dateValue = DateFormatFactory.getInstance(format).parse(value);
				if(null == dateValue){
					dateValue = ConvertUtils.convert(value, java.util.Date.class);
				}
				this.setValue(targetVO, propertyName, dateValue);
			}catch(Exception ex){
				throw new Exception("日期格式转换出错(" + titleName + "[" + propertyName + "|" + propertyType.getName() + "] : " + value + ")");
			}
		}else if(BatchConvertUtils.TYPE_NUMBER.equals(transType)){//数值翻译类型
			try{
				this.setValue(targetVO, propertyName, this.buildNumberValue(propertyType, value));
			}catch(Exception ex){
				throw new Exception("数值格式转换出错(" + titleName + "[" + propertyName + "|" + propertyType.getName() + "] : " + value + ")");
			}
		}else if(BatchConvertUtils.TYPE_CODE2NAME.equals(transType)){//数据字典翻译类型
			String key = propertyName + "~" + propertyType.getName() + "~" + transType + "~" + value;
			if(!cacheMap.containsKey(key)){
				String convertValue = null;
				try{
					convertValue = translateUtils.format(format, value, false);
				}catch(Exception ex){
					throw new Exception(ex.getMessage());
				}
				if(StringUtils.isBlank(convertValue)){
					throw new Exception("请确认字段值是否有效(" + titleName + "[" + propertyName + "|" + propertyType.getName() + "] : " + value + ")");
				}
				Object destValue = null;
				try{
					destValue = this.buildValue(propertyType, convertValue);
				}catch(Exception ex){
					throw new Exception("数据格式转换出错(" + titleName + "[" + propertyName + "|" + propertyType.getName() + "] : " + value + ")");
				}
				
				if(null == destValue || "".equals(destValue)){
					throw new Exception("请确认字段值是否有效(" + titleName + "[" + propertyName + "|" + propertyType.getName() + "] : " + value + ")");
				}
				cacheMap.put(key, destValue);
			}
			try{
				this.setValue(targetVO, propertyName, cacheMap.get(key));
			}catch(Exception ex){
				throw new Exception("数据格式转换出错(" + titleName + "[" + propertyName + "|" + propertyType.getName() + "] : " + value + ")");
			}
		}else{
			try{
				this.setValue(targetVO, propertyName, this.buildValue(propertyType, value));
			}catch(Exception ex){
				throw new Exception("数据格式转换出错(" + titleName + "[" + propertyName + "|" + propertyType.getName() + "] : " + value + ")");
			}
		}
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
	public List<ColumnModel> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<ColumnModel> columnList) {
		this.columnList = columnList;
	}
	
}
