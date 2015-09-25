package com.congxing.core.dbtool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库连接和关闭工具类
 */
public class DBConnectionUtils {

	public DBConnectionUtils() {
		super();
	}

	/**
	 * 创建Connection
	 * @param driverClassName
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 */
	public static Connection getConnection(String driverClassName, String url,
			String user, String password) {
		Connection conn = null;
		try {
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭Connection
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	/**
	 * 关闭ResultSet
	 * @param rs
	 */
	public static void closeResultSet(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	/**
	 * 关闭Statement
	 * @param stmt
	 */
	public static void closeStatement(Statement stmt) {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}