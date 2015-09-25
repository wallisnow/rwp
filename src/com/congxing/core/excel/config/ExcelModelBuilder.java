package com.congxing.core.excel.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ExcelModelBuilder{
	
	public static final String TYPE_CODE2NAME = "CODE2NAME";
    public static final String TYPE_DATE = "DATE";
    public static final String TYPE_NUMBER = "NUMBER";
    
    private static ExcelModelBuilder instance;
    
    public static ExcelModelBuilder getInstance(){
    	if(null == instance){
    		instance = new ExcelModelBuilder();
    	}
    	return instance;
    }

	private String configName = "/excelcfg.xml";
	
	private SAXReader saxReader;
	private Document doc;
	private Element root;
	
	public ExcelModelBuilder(){		
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(configName);		
		saxReader = new SAXReader();
		try {
			doc = saxReader.read(in);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		root = doc.getRootElement();
	}
	
	@SuppressWarnings("unchecked")
	public Element getModelElement(String id){
		List<Element> list = root.elements();
		for(Element element : list){
			if(element.attributeValue("id").equals(id)){
				return element;
			}
		}
		return null;
	}
	
	/**
	 * 构造行记录模型
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RowModel buildRowModel(String id) throws Exception{
		if(StringUtils.isBlank(id)){
			throw new Exception("RowModel[" + id + "] is null.");
		}
		Element rowElement = this.getModelElement(id);
		
		if(null == rowElement){
			throw new Exception("RowModel[" + id + "] is not found.");
		}
		RowModel rowModel = new RowModel();
		
		rowModel.setId(id);
		
		List<ColumnModel> columnList = this.buildColumnList(rowModel, rowElement);
		
		rowModel.setColumnList(columnList);
		
		return rowModel;
	}
	
	@SuppressWarnings("unchecked")
	private List<ColumnModel> buildColumnList(RowModel rowModel, Element rowElement) throws Exception{
		
		List<ColumnModel> columnList = new ArrayList<ColumnModel>();
		
		List<Element> list = rowElement.elements();
		
		for(Element columnElement : list){
			ColumnModel columnModel = new ColumnModel();
			
			String propertyName = columnElement.attributeValue(ConfigConstant.PROPERTY_NAME);
			String columnIndex = columnElement.attributeValue(ConfigConstant.PROPERTY_COLOUMN_INDEX);
			String titleName = columnElement.attributeValue(ConfigConstant.PROPERTY_COLOUMN_TITLE_NAME);
			String dataType = columnElement.attributeValue(ConfigConstant.PROPERTY_DATA_TYPE);
			String nullAble = columnElement.attributeValue(ConfigConstant.PROPERTY_NULLABLE);
			String transType = columnElement.attributeValue(ConfigConstant.PROPERTY_TRANSLATE_TYPE);
			String format = columnElement.attributeValue(ConfigConstant.PROPERTY_TRANSLATE_FORMAT);
			String defaultValue = columnElement.attributeValue(ConfigConstant.PROPERTY_DEFAULT_VALUE);
			
			/* 判断属性名称,属性名称必须存在,非空 */
			if(StringUtils.isBlank(propertyName)){
				throw new Exception("ColumnModel.propertyName is null.");
			}
			
			columnModel.setPropertyName(propertyName);
			
			/* 判断列序号,列序号必须存在,非空 */
			try{
				columnModel.setColumnIndex(Integer.parseInt(columnIndex));
			}catch(Exception ex){
				throw new Exception("ColumnModel.columnIndex[" + columnIndex + "] definition is not valid.");
			}
			if(StringUtils.isBlank(titleName)){
				throw new Exception("ColumnModel.titleName with columnIndex[" + columnIndex + "] is null.");
			}
			columnModel.setTitleName(titleName);
			
			/* 判断属性类型,属性类型必须有并效, 默认为String */
			if(StringUtils.isBlank(dataType)){
				dataType = "string";
			}			
			if("string".equalsIgnoreCase(dataType)){
				columnModel.setDataType(java.lang.String.class);
			}else if("integer".equalsIgnoreCase(dataType) || "int".equalsIgnoreCase(dataType)){
				columnModel.setDataType(java.lang.Integer.class);
			}else if("short".equalsIgnoreCase(dataType)){
				columnModel.setDataType(java.lang.Short.class);
			}else if("long".equalsIgnoreCase(dataType)){
				columnModel.setDataType(java.lang.Long.class);
			}else if("double".equalsIgnoreCase(dataType)){
				columnModel.setDataType(java.lang.Double.class);
			}else if("float".equalsIgnoreCase(dataType)){
				columnModel.setDataType(java.lang.Float.class);
			}else if("date".equalsIgnoreCase(dataType)){
				columnModel.setDataType(java.util.Date.class);
			}else{
				try{
					columnModel.setDataType(Class.forName(dataType));
				}catch(Exception ex){
					throw new Exception("ColumnModel.dataType[" + dataType + "] definition is not valid.");
				}
			}
			
			/* 判断属性值是否可以为空, 默认为true */
			if(StringUtils.isBlank(nullAble)){
				nullAble = "true";
			}
			try{
				columnModel.setNullAble(Boolean.valueOf(nullAble));
			}catch(Exception ex){
				throw new Exception("ColumnModel.nullAble[" + nullAble + "] definition is not valid.");
			}
			/* 当翻译类型存在时,必须判断transType是否有效 */
			if(StringUtils.isNotBlank(transType)){
				if(!(TYPE_CODE2NAME.equalsIgnoreCase(transType) || TYPE_DATE.equalsIgnoreCase(transType) || TYPE_NUMBER.equalsIgnoreCase(transType))){
					throw new Exception("ColumnModel.transType[" + transType + "] definition is not valid.");
				}
				if(StringUtils.isBlank(format) && TYPE_CODE2NAME.equalsIgnoreCase(transType)){
					throw new Exception("ColumnModel.transType[" + transType + "] with format is null.");
				}
				columnModel.setTransType(transType);
			}
			
			if(!StringUtils.isBlank(format)){
				columnModel.setFormat(format);
			}
			
			columnModel.setDefaultValue(defaultValue);
			
			columnList.add(columnModel);
		}
		
		return columnList;
	}
	
	
}
