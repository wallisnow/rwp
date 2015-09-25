package com.congxing.core.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.congxing.core.web.constant.Constants;

/**
 * 
 * <p>@Description: 封装分页和排序查询的结果</p>
 *
 * <p>@author: LIUKANGJIN</p>
 *
 * <p>@date: 2013-12-18<p>
 *
 * <p>@version: V.1.0<p>
 *
 */
@SuppressWarnings("serial")
public class Page implements Serializable {
	
	/**
	 * 页码
	 */
	private int pageNo = Constants.DEFAULT_PAGENO;

	/**
	 * 每页记录数
	 */
	private int pageSize = Constants.DEFAULT_PAGESIZE;
	
	/**
	 * 每页结果
	 */
	@SuppressWarnings("unchecked")
	private List<?> datas = new ArrayList();

	/**
	 * 总记录数
	 */
	private int totalCount = 0;

	public Page(){		
	}
	
	public List<?> getDatas() {
		return datas;
	}
	
	public void setDatas(List<?> datas) {
		this.datas = datas;
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

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
	
	public boolean hasDatas(){
		if(null == datas || datas.size() < 1){
			return false;
		}
		return true;
	}
	
	public int getFirstResult(){
		if (pageNo < 1 || pageSize < 1)
			return -1;
		else
			return ((pageNo - 1) * pageSize);
	}
	
	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		long pages = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pages++;
		}
		return pages;
	}
	
	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}
	
}
