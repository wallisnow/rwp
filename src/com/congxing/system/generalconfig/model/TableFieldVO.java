package com.congxing.system.generalconfig.model;

public class TableFieldVO {
	private String id;
	private String table;
	private String field;
	private String ptableId;

	public TableFieldVO(String id, String table, String field, String ptableId) {
		super();
		this.id = id;
		this.table = table;
		this.field = field;
		this.ptableId = ptableId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getPtableId() {
		return ptableId;
	}

	public void setPtableId(String ptableId) {
		this.ptableId = ptableId;
	}

}
