package com.congxing.rulemgt.ruleset.socket;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;


public class SocketPoolFactory {

	private static Map<String, SocketPool> cache = new HashMap<String, SocketPool>();

	public static SocketPool getSocketPool(String configPrefix) throws Exception {
		try {
			if(!cache.containsKey(configPrefix)){
				Configuration config = new PropertiesConfiguration("socket.properties");
				String ipAdd = config.getString(configPrefix + ".ip", "127.0.0.1");
				int port = config.getInt(configPrefix + ".port", 9985);
				int timeOut = config.getInt(configPrefix + ".timeout", 10000);
				int initSize = config.getInt(configPrefix + ".initsize", 2);
				int maxSize = config.getInt(configPrefix + ".maxsize", 10);
				boolean waitifbusy = config.getBoolean(configPrefix + ".waitifbusy", true);
				SocketPool pool = new SocketPoolImpl(ipAdd, port, initSize, maxSize, timeOut, waitifbusy);
				cache.put(configPrefix, pool);
			}
		} catch (Exception ex) {
			throw new Exception("Drools服务器Socket连接池配置信息有误[" + ex.getMessage() + "]");
		}
		return cache.get(configPrefix);
	}

}
