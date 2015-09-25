package com.congxing.core.datasource;

import javax.sql.DataSource;

public interface CommonDataSource {
	
	public final static String configFile = "dbconfig.properties";
	
	public final static String DS_TYPE_C3PO = "c3p0";
	
	public final static String DS_TYPE_BONECP = "bonecp";
	
	/**
	 * 构建取数据源操作
	 * @return
	 * @throws Exception
	 */
	public DataSource getDataSource();
	
	/**
	 * 关闭数据源操作
	 */
	public void close();

}
