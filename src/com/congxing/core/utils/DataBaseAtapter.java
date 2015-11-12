package com.congxing.core.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.congxing.core.hibernate.Page;

public class DataBaseAtapter {

	// 输出ResultSet 中的结果
	public static Page getResultSetPage(ResultSet rs) throws SQLException {
		Page page = new Page();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		// 检索此 ResultSet 对象的列的编号、类型和属性
		ResultSetMetaData rsmd = rs.getMetaData();
		// 得到当前的列数
		int colCount = rsmd.getColumnCount();
		while (rs.next()) { // while控制行数
			Map<String, Object> columns = new LinkedHashMap<String, Object>();
			for (int i = 1; i <= colCount; i++) {// for循环控制列数
				// 得到当前列的列名
				String name = null;
				if(rsmd.getColumnLabel(i)!=null){
					name = rsmd.getColumnLabel(i);
				}else{
					name = rsmd.getColumnName(i);
				}
				// 得到当前列的值
				Object value = rs.getObject(i);
				columns.put(name, value);
			}
			resultList.add(columns);
		}
		page.setDatas(resultList);
		return page;
	}

	// 输出ResultSet 中的结果
	public static void printRS(ResultSet rs) throws SQLException {
		// 检索此 ResultSet 对象的列的编号、类型和属性。
		ResultSetMetaData rsmd = rs.getMetaData();
		// 得到当前的列数
		int colCount = rsmd.getColumnCount();
		while (rs.next()) { // while控制行数
			for (int i = 1; i <= colCount; i++) {// for循环控制列数
				if (i > 1) {
					System.out.print(",");
				}
				// 得到当前列的列名
				String name = rsmd.getColumnName(i);
				// 得到当前列的值
				String value = rs.getString(i);
				System.out.print(name + "=" + value);
			}
			System.out.println();
		}
	}

}
