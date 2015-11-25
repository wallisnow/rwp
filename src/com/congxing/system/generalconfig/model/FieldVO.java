package com.congxing.system.generalconfig.model;

import java.io.Serializable;

public class FieldVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String fieldId;
	private String fieldName;
	private String pFieldId;

	public FieldVO(String id, String fieldId, String fieldName, String pFieldId) {
		this.id = id;
		this.fieldId = fieldId;
		this.fieldName = fieldName;
		this.pFieldId = pFieldId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getpFieldId() {
		return pFieldId;
	}

	public void setpFieldId(String pFieldId) {
		this.pFieldId = pFieldId;
	}

}
