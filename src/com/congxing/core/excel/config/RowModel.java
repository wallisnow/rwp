package com.congxing.core.excel.config;

import java.util.ArrayList;
import java.util.List;

public class RowModel {
	
	private String id;
	
	private List<ColumnModel> columnList = new ArrayList<ColumnModel>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ColumnModel> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<ColumnModel> columnList) {
		this.columnList = columnList;
	}
	
}
