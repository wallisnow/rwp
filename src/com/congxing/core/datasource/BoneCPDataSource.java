package com.congxing.core.datasource;

import javax.sql.DataSource;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoneCPDataSource implements CommonDataSource {
	
	static Logger logger = LoggerFactory.getLogger(BoneCPDataSource.class);
	
	private com.jolbox.bonecp.BoneCPDataSource ds;
	
	public BoneCPDataSource(String dbConfigName) throws Exception {
		try {
			System.getProperties().setProperty("oracle.jdbc.V8Compatible", "true");
			PropertiesConfiguration configuration = new PropertiesConfiguration(configFile);
			dbConfigName += ".";
			dbConfigName += CommonDataSource.DS_TYPE_BONECP;
			Class.forName(configuration.getString(dbConfigName + ".driver"));
			
			ds = new com.jolbox.bonecp.BoneCPDataSource();
			ds.setJdbcUrl(configuration.getString(dbConfigName + ".url"));
			ds.setUsername(configuration.getString(dbConfigName + ".username"));
			ds.setPassword(configuration.getString(dbConfigName + ".password"));
			
			ds.setPartitionCount(configuration.getInt(dbConfigName + ".partitioncount", 3));
			ds.setMinConnectionsPerPartition(configuration.getInt(dbConfigName + ".minconnectionperpartition", 3));
			ds.setMaxConnectionsPerPartition(configuration.getInt(dbConfigName + ".maxconnectionperpartition", 10));
			ds.setReleaseHelperThreads(3);
			
		} catch (ConfigurationException cfgex) {
			throw new Exception("BoneCPDataSource ConfigurationException[" + cfgex.getMessage() + "]");
		} catch (ClassNotFoundException cnfex) {
			throw new Exception("BoneCPDataSource ClassNotFoundException[" + cnfex.getMessage() + "]");
		} 
	}
	

	public DataSource getDataSource() {
		return ds;
	}


	public void close() {
		if(null != ds){
			ds.close();
		}
	}


}
