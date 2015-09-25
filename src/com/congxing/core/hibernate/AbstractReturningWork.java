package com.congxing.core.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;
import org.hibernate.jdbc.ReturningWork;

public abstract class AbstractReturningWork<E, T> implements ReturningWork<E> {
	
	private String sql;
	
	private Object[] params;
	
	private ResultSetHandler<T> rsh;
	
	private Class<T> beanClass;
	
	private int page;
	
	private int size;
	
	public AbstractReturningWork(String sql, Object[] params){
		this.sql = sql;
		this.params = params;
	}
	
	public AbstractReturningWork(String sql, ResultSetHandler<T> rsh, Object[] params){
		this.sql = sql;
		this.rsh = rsh;
		this.params = params;
	}
	
	public AbstractReturningWork(String sql, ResultSetHandler<T> rsh, int page, int size, Object[] params){
		this.sql = sql;
		this.rsh = rsh;
		this.page = page;
		this.size = size;
		this.params = params;
	}
	
	public AbstractReturningWork(String sql, Class<T> beanClass, Object[] params){
		this.sql = sql;
		this.beanClass = beanClass;
		this.params = params;
	}

	public AbstractReturningWork(String sql, Class<T> beanClass, int page, int size, Object[] params){
		this.sql = sql;
		this.beanClass = beanClass;
		this.page = page;
		this.size = size;
		this.params = params;
	}

	public abstract E execute(Connection conn) throws SQLException;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public ResultSetHandler<T> getRsh() {
		return rsh;
	}

	public void setRsh(ResultSetHandler<T> rsh) {
		this.rsh = rsh;
	}

	public Class<T> getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class<T> beanClass) {
		this.beanClass = beanClass;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
