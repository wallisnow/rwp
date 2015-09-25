package com.congxing.core.datasource;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0DataSource implements CommonDataSource {
	
	private ComboPooledDataSource dataSource;
	
	public C3P0DataSource(String dbConfigName) throws Exception {
		try {
			System.getProperties().setProperty("oracle.jdbc.V8Compatible", "true");
			PropertiesConfiguration configuration = new PropertiesConfiguration(configFile);
			dataSource = new ComboPooledDataSource();
			dbConfigName += ".";
			dbConfigName += CommonDataSource.DS_TYPE_C3PO;
			dataSource.setDriverClass(configuration.getString(dbConfigName + ".driver"));
			dataSource.setJdbcUrl(configuration.getString(dbConfigName + ".url"));
			dataSource.setUser(configuration.getString(dbConfigName + ".username"));
			dataSource.setPassword(configuration.getString(dbConfigName + ".password"));
			
			dataSource.setInitialPoolSize(configuration.getInt(dbConfigName + ".initpoolsize", 2));
			dataSource.setMinPoolSize(configuration.getInt(dbConfigName + ".minpoolsize", 2));
			dataSource.setMinPoolSize(configuration.getInt(dbConfigName + ".maxpoolsize", 10));
			dataSource.setMaxStatements(0);
		} catch (ConfigurationException cfgex) {
			throw new Exception("C3P0DataSource ConfigurationException[" + cfgex.getMessage() + "]");
		} catch (PropertyVetoException pvex){
			throw new Exception("C3P0DataSource PropertyVetoException[" + pvex.getMessage() + "]");
		}
	}
	

	public DataSource getDataSource() {
		return dataSource;
	}

	public void close(){
		if(null != dataSource){
			dataSource.close();
		}
	}

}
