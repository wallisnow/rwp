package com.congxing.core.web.struts2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.congxing.core.web.constant.Constants;
/**
 * 
 * <p>
 * Title: BaseListVO
 * </p>
 * 
 * <p>
 * Description: BaseListVO
 * BaseListVO为查询条件封装类, 要求在BaseListVO中参数类型以String类型为准, 参数代表的具体属性类型以前缀表示
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) Revenco
 * </p>
 * 
 * <p>
 * Company: Sunrise Tech Ltd.
 * </p>
 * 
 * @author liukangjin
 * @version 1.0
 * @since 2011-9-23
 *
 */
@SuppressWarnings("serial")
public class BaseListVO implements Serializable {
	
	public static final String ASC = "asc";
	
	public static final String DESC = "desc";
	
	private String newPageSize;
	
	/**
	 * 查询页码,默认为第一页
	 */
	private int pageNo = Constants.DEFAULT_PAGENO;
	/**
	 * 查询每页记录数,默认每页查询10条记录
	 */
	private int pageSize = Constants.DEFAULT_PAGESIZE;
	/**
	 * 排序字段,默认不排序
	 */
	private String orderBy = null;
	/**
	 * 排序方向,默认为升序排列
	 */
	protected String order = ASC;
	
	/*
	 * 是否查询全部数据
	 * 默认是按分页进行查询
	 */
	private boolean findAll = false;
	
	private String voClassName;
	
	
	private Map<String, Object> queryConditions = new HashMap<String, Object>();
	
	
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getOrderBy() {
		return orderBy;
	}
	
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public String getOrder() {
		return order;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}
	
	public String getVoClassName() {
		return voClassName;
	}
	
	public void setVoClassName(String voClassName) {
		this.voClassName = voClassName;
	}
	
	public Map<String, Object> getQueryConditions() {
		return queryConditions;
	}
	
	public void setQueryConditions(Map<String, Object> queryConditions) {
		this.queryConditions = queryConditions;
	}
	
	public int getFirstResult(){
		if (pageNo < 1 || pageSize < 1)
			return -1;
		else
			return ((pageNo - 1) * pageSize);
	}
	
	public String getNewPageSize() {
		return newPageSize;
	}
	
	public void setNewPageSize(String newPageSize) {
		this.newPageSize = newPageSize;
	}
	
	public boolean isFindAll() {
		return findAll;
	}
	
	public void setFindAll(boolean findAll) {
		this.findAll = findAll;
	}
}
