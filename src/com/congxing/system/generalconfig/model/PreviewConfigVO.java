package com.congxing.system.generalconfig.model;

import java.io.Serializable;
import java.util.Date;

public class PreviewConfigVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long generalConfigData_id;
	private Long generalConfig_id;
	private String tableName;
	private String field;
	private String fieldName;
	private String isSelectedAsKey;
	private String isSetAsHidden;
	private String rename;
	private Date creatingtime;
	private String author;
	private String mender;
	private Date modtime;
	
	
	public PreviewConfigVO() {
	}

	public PreviewConfigVO(Long generalConfigData_id, Long generalConfig_id, String tableName, String field,
			String fieldName, String isSelectedAsKey, String isSetAsHidden, String rename, Date creatingtime,
			String author, String mender, Date modtime) {
		super();
		this.generalConfigData_id = generalConfigData_id;
		this.generalConfig_id = generalConfig_id;
		this.tableName = tableName;
		this.field = field;
		this.fieldName = fieldName;
		this.isSelectedAsKey = isSelectedAsKey;
		this.isSetAsHidden = isSetAsHidden;
		this.rename = rename;
		this.creatingtime = creatingtime;
		this.author = author;
		this.mender = mender;
		this.modtime = modtime;
	}

	public Long getGeneralConfigData_id() {
		return generalConfigData_id;
	}

	public void setGeneralConfigData_id(Long generalConfigData_id) {
		this.generalConfigData_id = generalConfigData_id;
	}

	public Long getGeneralConfig_id() {
		return generalConfig_id;
	}

	public void setGeneralConfig_id(Long generalConfig_id) {
		this.generalConfig_id = generalConfig_id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Date getCreatingtime() {
		return creatingtime;
	}

	public void setCreatingtime(Date creatingtime) {
		this.creatingtime = creatingtime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMender() {
		return mender;
	}

	public void setMender(String mender) {
		this.mender = mender;
	}


	public Date getModtime() {
		return modtime;
	}

	public void setModtime(Date modtime) {
		this.modtime = modtime;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getIsSelectedAsKey() {
		return isSelectedAsKey;
	}

	public void setIsSelectedAsKey(String isSelectedAsKey) {
		this.isSelectedAsKey = isSelectedAsKey;
	}

	public String getIsSetAsHidden() {
		return isSetAsHidden;
	}

	public void setIsSetAsHidden(String isSetAsHidden) {
		this.isSetAsHidden = isSetAsHidden;
	}

	public String getRename() {
		return rename;
	}

	public void setRename(String rename) {
		this.rename = rename;
	}
	
	
}
