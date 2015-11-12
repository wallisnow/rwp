package com.congxing.system.generalconfig.model;

public class FieldNameVO {
	private String COLUMN_NAME;
	private String COLUMN_COMMENT;

	public FieldNameVO() {
	}

	public FieldNameVO(String cOLUMN_NAME, String cOLUMN_COMMENT) {
		COLUMN_NAME = cOLUMN_NAME;
		COLUMN_COMMENT = cOLUMN_COMMENT;
	}

	public String getCOLUMN_NAME() {
		return COLUMN_NAME;
	}

	public void setCOLUMN_NAME(String cOLUMN_NAME) {
		COLUMN_NAME = cOLUMN_NAME;
	}

	public String getCOLUMN_COMMENT() {
		return COLUMN_COMMENT;
	}

	public void setCOLUMN_COMMENT(String cOLUMN_COMMENT) {
		COLUMN_COMMENT = cOLUMN_COMMENT;
	}
}
