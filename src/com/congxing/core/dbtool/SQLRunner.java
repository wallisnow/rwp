package com.congxing.core.dbtool;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class SQLRunner {
	
	static Logger logger = LoggerFactory.getLogger(SQLRunner.class);

	private final static QueryRunner runner = new QueryRunner(true);

	/* 定义列处理句柄 */
	@SuppressWarnings({"unchecked" })
	private final static ColumnListHandler columnListHandler = new ColumnListHandler() {
		@Override
		protected Object handleRow(ResultSet rs) throws SQLException {
			Object obj = super.handleRow(rs);
			if (obj instanceof BigInteger || obj instanceof java.math.BigDecimal){
				return ((BigInteger) obj).longValue();
			}
			return obj;
		}
	};

	/* 定义惟一值处理句柄 */
	@SuppressWarnings("unchecked")
	private final static ScalarHandler scalarHandler = new ScalarHandler() {
		@Override
		public Object handle(ResultSet rs) throws SQLException {
			Object obj = super.handle(rs);
			if (obj instanceof BigInteger){
				return ((BigInteger) obj).longValue();
			}
			return obj;
		}
	};

	/* 定义简单对象类型列表 */
	private final static List<Class<?>> PrimitiveClasses = new ArrayList<Class<?>>() {{
			add(Long.class);
			add(Double.class);
			add(Integer.class);
			add(String.class);
			add(java.util.Date.class);
			add(java.sql.Date.class);
			add(java.sql.Timestamp.class);
		}
	};
	/**
	 * 判断当前类型是否属于简单对象类型
	 * @param cls
	 * @return
	 */
	private final static boolean _IsPrimitive(Class<?> cls) {
		return cls.isPrimitive() || PrimitiveClasses.contains(cls);
	}
	
	
	/**
	 * 普通查询封装(查询全部数据)
	 * <p>
	 * <strong>查询为JavaBean对象，采用为属性赋值方式进行设值(即JavaBean中属性必须包括setXX方法)</strong>
	 * </p>
	 * ResultSetHandler<T>可以为以下值:
	 * <pre>
	 * 1、简单类型查询 ScalarHandler<String> ScalarHandler<Integer>  ScalarHandler<Long> MapHandler BeanHandler
	 * 2、列表对象查询
	 * ScalarHandler(唯一值查询): Converts one ResultSet column into an Object.
	 * ArrayHandler(单一Array查询): Converts the first ResultSet row into an Object[].
	 * BeanHandler(单一Bean查询): Converts the first ResultSet row into a JavaBean.
	 * MapHandler(单一Map查询): Converts the first ResultSet row into a Map.
	 * ColumnListHandler(List集合查询): Converts a ResultSet into a List of Object.
	 * ArrayListHandler(List集合查询): Converts the ResultSet into a List of Object[].
	 * BeanListHandler(List集合查询): Converts a ResultSet into a List of beans.
	 * MapListHandler(List集合查询): Converts a ResultSet into a List of Map.
	 * BeanMapHandler(Map集合查询): Converts a ResultSet into a Map of beans.
	 * KeyedHandler(Map集合查询): Returns a Map of Maps,ResultSet rows are converted into Maps which are then stored in a Map under the given key.
	 * </pre>
	 * @param conn 连接对象
	 * @param sql 查询SQL
	 * @param rsh ResultSet处理句柄
	 * @param params 参数数组
	 * @return
	 * @throws SQLException
	 */
	public static <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
		logger.info("RunSQL[" + sql + "], Params[" + StringUtils.join(params, ",") + "]");
		long starttimes = System.currentTimeMillis();
		T results = runner.query(conn, sql, rsh, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		return results;
	}
	
	/**
	 * 普通查询封装(取指定页数据)
	 * <p>
	 * <strong>查询为JavaBean对象，采用为属性赋值方式进行设值(即JavaBean中属性必须包括setXX方法)</strong>
	 * </p>
	 * ResultSetHandler<T>可以为以下值:
	 * <pre>
	 * 1、简单类型查询 ScalarHandler<String> ScalarHandler<Integer>  ScalarHandler<Long> MapHandler BeanHandler
	 * 2、列表对象查询
	 * ScalarHandler(唯一值查询): Converts one ResultSet column into an Object.
	 * ArrayHandler(单一Array查询): Converts the first ResultSet row into an Object[].
	 * BeanHandler(单一Bean查询): Converts the first ResultSet row into a JavaBean.
	 * MapHandler(单一Map查询): Converts the first ResultSet row into a Map.
	 * ColumnListHandler(List集合查询：查询单列): Converts a ResultSet into a List of Object.
	 * ArrayListHandler(List集合查询): Converts a ResultSet into a List of Object[].
	 * BeanListHandler(List集合查询): Converts a ResultSet into a List of beans.
	 * MapListHandler(List集合查询): Converts a ResultSet into a List of Map.
	 * BeanMapHandler(Map集合查询): Converts a ResultSet into a Map of beans.
	 * KeyedHandler(Map集合查询): Returns a Map of Maps,ResultSet rows are converted into Maps which are then stored in a Map under the given key.
	 * </pre>
	 * @param conn 连接对象
	 * @param sql 查询SQL
	 * @param rsh ResultSet处理句柄
	 * @param page 取第page页数据,当page<=0代表查询全部记录
	 * @param size 每页数据大小,当size<=0代理查询全部记录
	 * @param params 参数数组
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh, int page, int size, Object... params) throws SQLException {
		logger.info("RunSQL[" + sql + "], Params[" + StringUtils.join(params, ",") + "], Page[" + page + "], Size[" + size + "]");
		long starttimes = System.currentTimeMillis();
		if(page <= 0 || size <= 0){
			T results = runner.query(conn, sql, rsh, params);
			logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
			return results;
		}
		int startRowNum = (page - 1) * size + 1;
		int endRowNum = page * size;
		
		String dbName = conn.getMetaData().getDatabaseProductName();
		
		T results = null;
		
		if(StringUtils.indexOfIgnoreCase(dbName, "ORACLE") >= 0){//Oracle分页方式
			String pageSQL = "select * from (select t1.*, rownum r_n from (" + sql + ") t1 where rownum <= ? ) where r_n >= ? ";
			results = runner.query(conn, pageSQL, rsh, ArrayUtils.addAll(params, new Integer[]{endRowNum, startRowNum}));
			logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
			
			if(results instanceof List<?> && rsh instanceof MapListHandler){
				for(Map<?, ?> valueMap : (List<Map<?, ?>>)results){
					valueMap.remove("r_n");
				}
			}else if(results instanceof Map && rsh instanceof KeyedHandler){
				for(Map<?, ?> valueMap : ((Map<?, Map<?, ?>>)results).values()){
					valueMap.remove("r_n");
				}
			}
			return results;
			
		}else if(StringUtils.indexOfIgnoreCase(dbName, "MYSQL") >= 0){//MySQL分页方式
			String pageSQL = sql + " LIMIT ?,? ";
			results = runner.query(conn, pageSQL, rsh, ArrayUtils.addAll(params, new Integer[]{endRowNum, startRowNum}));
			logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
			return results;
			
		}else if(StringUtils.indexOfIgnoreCase(dbName, "INFORMIX") >= 0){//Informix分页方式
			int skipNum = startRowNum - 1;
			int selectIdx = StringUtils.indexOfIgnoreCase(sql, "SELECT");
			String pageSQL = "select skip " + skipNum + " first " + size + StringUtils.substring(sql, selectIdx + 6);
			results = runner.query(conn, pageSQL, rsh, params);
			logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
			return results;
			
		}else {
			throw new SQLException("不支持关于数据据[" + dbName +"]分页过程");
		}
	}
	
	/**
	 * 对象查询,查询惟一对象(采用属性进行转换)
	 * <p>
	 * <strong>查询为JavaBean对象，采用为属性赋值方式进行设值(即JavaBean中属性必须包括setXX方法)</strong>
	 * </p>
	 * @param conn 连接对象
	 * @param beanClass 查询对象类型
	 * @param sql 查询SQL
	 * @param params SQL参数数组
	 * @return
	 * @throws SQLException 
	 */
	@SuppressWarnings({ "unchecked"})
	public static <T> T queryObject(Connection conn, String sql, Class<T> beanClass, Object... params) throws SQLException {
		logger.info("RunSQL[" + sql + "], Params[" + StringUtils.join(params, ",") + "]");
		long starttimes = System.currentTimeMillis();
		T results = (T)runner.query(conn, sql, _IsPrimitive(beanClass) ? scalarHandler : new BeanHandler(beanClass), params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		return results;
	}

	/**
	 * 列表查询,查询列表对象(返回全部数据)
	 * 支持 ColumnListHandler和BeanListHandler两种列表
	 * 
	 * @param conn 连接对象
	 * @param beanClass 查询对象类型
	 * @param sql 查询SQL
	 * @param params SQL参数数组
	 * @return
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> queryList(Connection conn, String sql, Class<T> beanClass, Object... params) throws SQLException {
		logger.info("RunSQL[" + sql + "], Params[" + StringUtils.join(params, ",") + "]");
		long starttimes = System.currentTimeMillis();
		List<T> results = (List<T>) runner.query(conn, sql, _IsPrimitive(beanClass) ? columnListHandler : new BeanListHandler(beanClass), params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		return results;
	}
	
	/**
	 * 列表分页查询,查询列表对象(支持分页和全部查询)
	 * <p>
	 * 仅支持 ColumnListHandler和BeanListHandler两种列表
	 * 
	 * @param conn 连接对象
	 * @param beanClass 查询对象类型
	 * @param sql 查询SQL
	 * @param page 取第page页数据,当page<=0代表查询全部记录
	 * @param size 每页数据大小,当size<=0代理查询全部记录
	 * @param params SQL参数数组
	 * @return
	 * @throws SQLException 
	 */
	@SuppressWarnings({ "unchecked"})
	public static <T> List<T> queryList(Connection conn, String sql, Class<T> beanClass, int page, int size, Object... params) throws SQLException {
		logger.info("RunSQL[" + sql + "], Params[" + StringUtils.join(params, ",") + "], Page[" + page + "], Size[" + size + "]");
		long starttimes = System.currentTimeMillis();
		if(page <= 0 || size <= 0){
			List<T> results = (List<T>) runner.query(conn, sql, _IsPrimitive(beanClass) ? columnListHandler : new BeanListHandler(beanClass), params);
			logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
			return results;
		}
		int startRowNum = (page - 1) * size + 1;
		int endRowNum = page * size;
		
		String dbName = conn.getMetaData().getDatabaseProductName();
		List<T> results = null;
		if(StringUtils.indexOfIgnoreCase(dbName, "ORACLE") >= 0){//Oracle分页方式
			String pageSQL = "select * from (select t1.*, rownum r_n from (" + sql + ") t1 where rownum <= ? ) where r_n >= ? ";
			results = (List<T>)runner.query(conn, pageSQL, _IsPrimitive(beanClass) ? columnListHandler : new BeanListHandler(beanClass), ArrayUtils.addAll(params, new Integer[]{endRowNum, startRowNum}));
			
		}else if(StringUtils.indexOfIgnoreCase(dbName, "MYSQL") >= 0){//MySQL分页方式
			String pageSQL = sql + " LIMIT ?,? ";
			results = (List<T>)runner.query(conn, pageSQL, _IsPrimitive(beanClass) ? columnListHandler : new BeanListHandler(beanClass), ArrayUtils.addAll(params, new Integer[]{endRowNum, startRowNum}));
			
		}else if(StringUtils.indexOfIgnoreCase(dbName, "INFORMIX") >= 0){//Informix分页方式
			int skipNum = startRowNum - 1;
			int selectIdx = StringUtils.indexOfIgnoreCase(sql, "SELECT");
			String pageSQL = "select skip " + skipNum + " first " + size + StringUtils.substring(sql, selectIdx + 6);
			results = (List<T>)runner.query(conn, pageSQL, _IsPrimitive(beanClass) ? columnListHandler : new BeanListHandler(beanClass), params);
			
		}else {
			throw new SQLException("不支持关于数据据[" + dbName +"]分页过程");
		}
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		return results;
	}
	

	/**
	 * 执行统计查询语句，语句的执行结果必须只返回一个数值
	 * 
	 * @param conn 连接对象
	 * @param sql 查询SQL
	 * @param params 参数数组
	 * @return
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public static long stat(Connection conn, String sql, Object... params) throws SQLException {
		logger.info("RunSQL[" + sql + "], Params[" + StringUtils.join(params, ",") + "]");
		long starttimes = System.currentTimeMillis();
		Number num = (Number) runner.query(conn, sql, scalarHandler, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		return (num != null) ? num.longValue() : -1;
	}	

	/**
	 * 执行INSERT/UPDATE/DELETE语句
	 * 
	 * @param conn 连接对象
	 * @param sql 查询SQL
	 * @param params 参数数组
	 * @return
	 * @throws SQLException 
	 */
	public static int execute(Connection conn, String sql, Object... params) throws SQLException {
		logger.info("RunSQL[" + sql + "], Params[" + StringUtils.join(params, ",") + "]");
		long starttimes = System.currentTimeMillis();
		int result = runner.update(conn, sql, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		return result;
	}

	/**
	 * 批量执行指定的SQL语句,包括INSERT/UPDATE/DELETE
	 * 
	 * @param conn 连接对象
	 * @param sql 查询SQL
	 * @param params 参数二维数组
	 * @return
	 * @throws SQLException 
	 */
	public static int[] executeBatch(Connection conn, String sql, Object[][] params) throws SQLException {
		logger.info("RunSQL[" + sql + "]");
		long starttimes = System.currentTimeMillis();
		int []results = runner.batch(conn, sql, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		return results;
	}
	
	/**
	 * 执行单条插入操作
	 * 
	 * @param conn 连接对象
	 * @param table 插入表名
	 * @param valueMap 值集合
	 * @return
	 * @throws SQLException
	 */
	public static int insert(Connection conn, String table, Map<String, Object> valueMap) throws SQLException {
		if (valueMap == null || valueMap.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMap.");
        }		
		Object[] params = new Object[]{};
		
		for(Iterator<String> it = valueMap.keySet().iterator(); it.hasNext(); ){
			params = ArrayUtils.add(params, valueMap.get(it.next()));
		}
		
		String insertSQL = buildInsertSQLByMap(table, valueMap);
		
		logger.info("RunSQL[" + insertSQL + "]");
		long starttimes = System.currentTimeMillis();
		int result = runner.update(conn, insertSQL, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		
		return result;
	}
	
	/**
	 * 执行批量执行插入操作
	 * 
	 * @param conn 连接对象
	 * @param table 插入表名
	 * @param valueMapList 值集合列表
	 * @return
	 * @throws SQLException
	 */
	public static int[] insertBatch(Connection conn, String table, List<Map<String, Object>> valueMapList) throws SQLException {
		if (valueMapList == null || valueMapList.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMapList.");
        }
		String insertSQL = buildInsertSQLByMap(table, valueMapList.get(0));
		
		Object[][] params = new Object[valueMapList.size()][];		
		int index = 0;
		for(Map<String, Object> valueMap : valueMapList){
			Object[] thparams = new Object[]{};
			for(Iterator<String> it = valueMap.keySet().iterator(); it.hasNext(); ){
				thparams = ArrayUtils.add(thparams, valueMap.get(it.next()));
			}
			params[index++] = thparams;
		}
		logger.info("RunSQL[" + insertSQL + "]");
		long starttimes = System.currentTimeMillis();
		int []results = runner.batch(conn, insertSQL, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		
		return results;
	}
	
	
	/**
	 * 执行单条删除操作
	 * 
	 * @param conn 连接对象
	 * @param table 删除记录表名
	 * @param conditionMap 条件集合
	 * @return
	 * @throws SQLException
	 */
	public static int delete(Connection conn, String table, Map<String, Object> conditionMap) throws SQLException {
		if (conditionMap == null || conditionMap.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty conditionMap.");
        }		
		Object[] params = new Object[]{};
		for(Iterator<String> it = conditionMap.keySet().iterator(); it.hasNext(); ){
			params = ArrayUtils.add(params, conditionMap.get(it.next()));
		}
		
		String deleteSQL = buildDeleteSQLByMap(table, conditionMap);
		
		logger.info("RunSQL[" + deleteSQL + "]");
		long starttimes = System.currentTimeMillis();
		int result = runner.update(conn, deleteSQL, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		
		return result;
	}
	
	/**
	 * 执行批量删除操作(批量删除)
	 * 
	 * @param conn 连接对象
	 * @param table 删除表名
	 * @param conditionMapList 条件集合列表(集合属性相同)
	 * @return
	 * @throws SQLException
	 */
	public static int[] deleteBatch(Connection conn, String table, List<Map<String, Object>> conditionMapList) throws SQLException {
		if (conditionMapList == null || conditionMapList.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty conditionMapList.");
        }		
		String deleteSQL = buildDeleteSQLByMap(table, conditionMapList.get(0));
		
		Object[][] params = new Object[conditionMapList.size()][];		
		int index = 0;
		for(Map<String, Object> conditionMap : conditionMapList){
			Object[] thparams = new Object[]{};
			for(Iterator<String> it = conditionMap.keySet().iterator(); it.hasNext(); ){
				thparams = ArrayUtils.add(thparams, conditionMap.get(it.next()));
			}
			params[index++] = thparams;
		}
		
		logger.info("RunSQL[" + deleteSQL + "]");
		long starttimes = System.currentTimeMillis();
		int []results = runner.batch(conn, deleteSQL, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		
		return results;
	}
	
	/**
	 * 执行单条删除操作
	 * 
	 * @param conn 数据库连接
	 * @param table 删除数据表名
	 * @param valueMap 属性集合
	 * @param keys 按Keys进行删除
	 * @return
	 * @throws SQLException
	 */
	public static int delete(Connection conn, String table, Map<String, Object> valueMap, String... keys) throws SQLException {
		if (valueMap == null || valueMap.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMap.");
        }
		if (keys == null || keys.length < 1){
			throw new SQLException("Null parameters. If parameters aren't need, pass an empty keys.");
		}
		Object[] params = new Object[]{};
		for(String key : keys){
			params = ArrayUtils.add(params, valueMap.get(key));
		}
		
		String deleteSQL = buildDeleteSQLByKeys(table, keys);
		logger.info("RunSQL[" + deleteSQL + "]");
		long starttimes = System.currentTimeMillis();
		int result = runner.update(conn, deleteSQL, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		
		return result;
	}
	
	/**
	 * 执行批量删除操作(批量删除)
	 * 
	 * @param conn 数据库连接
	 * @param table 删除记录表名
	 * @param valueMapList 属性集合列表
	 * @param keys 按Keys进行删除
	 * @return
	 * @throws SQLException
	 */
	public static int[] deleteBatch(Connection conn, String table, List<Map<String, Object>> valueMapList, String... keys) throws SQLException {
		if (valueMapList == null || valueMapList.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMapList.");
        }	
		if (keys == null || keys.length < 1){
			throw new SQLException("Null parameters. If parameters aren't need, pass an empty keys.");
		}
		
		Object[][] params = new Object[valueMapList.size()][];		
		int index = 0;
		for(Map<String, Object> valueMap : valueMapList){
			Object[] thparams = new Object[]{};
			for(String key : keys){
				thparams = ArrayUtils.add(thparams, valueMap.get(key));
			}
			params[index++] = thparams;
		}
		
		
		String deleteSQL = buildDeleteSQLByKeys(table, keys);
		logger.info("RunSQL[" + deleteSQL + "]");
		long starttimes = System.currentTimeMillis();
		int []results = runner.batch(conn, deleteSQL, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		
		return results;
	}
	
	/**
	 * 执行单条更新操作
	 * 
	 * @param conn 连接对象
	 * @param table 更新表名
	 * @param valueMap 更新属性集合
	 * @param keys 按keys字段进行更新
	 * @return
	 * @throws SQLException
	 */
	public static int update(Connection conn, String table, Map<String, Object> valueMap, String... keys) throws SQLException {
		if (valueMap == null || valueMap.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMap.");
        }
		if (keys == null || keys.length < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty keys.");
        }
		String updataSQL = buildUpdateSQLByKeys(table, valueMap, keys);
		Object[] params = new Object[]{};
		for(Iterator<String> it = valueMap.keySet().iterator(); it.hasNext(); ){
			params = ArrayUtils.add(params, valueMap.get(it.next()));
		}
		for(String key : keys){
			params = ArrayUtils.add(params, valueMap.get(key));
		}
		
		logger.info("RunSQL[" + updataSQL + "]");
		long starttimes = System.currentTimeMillis();
		int result = runner.update(conn, updataSQL, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		
		return result;
	}
	
	/**
	 * 执行批量更新操作
	 * 
	 * @param conn 连接对象
	 * @param table 更新表名
	 * @param valueMapList 更新属性集合
	 * @param keys 按keys字段进行更新
	 * @return
	 * @throws SQLException
	 */
	public static int[] updateBatch(Connection conn, String table, List<Map<String, Object>> valueMapList, String... keys) throws SQLException {
		if (valueMapList == null || valueMapList.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMapList.");
        }
		if (keys == null || keys.length < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty keys.");
        }
		
		Map<String, Object> tmpValueMap = valueMapList.get(0);
		
		String updataSQL = buildUpdateSQLByKeys(table, tmpValueMap, keys);
		String []cols = new String[]{};
		for(Iterator<String> it = tmpValueMap.keySet().iterator(); it.hasNext() ; ){
			cols = (String[]) ArrayUtils.add(cols, it.next());
		}
		cols = (String[]) ArrayUtils.addAll(cols, keys);
		
		Object[][] params = new Object[valueMapList.size()][];
		int index = 0;
		for(Map<String, Object> valueMap : valueMapList){
			Object[] thparams = new Object[]{};
			for(String col : cols){
				thparams = ArrayUtils.add(thparams, valueMap.get(col));
			}
			params[index++] = thparams;
		}
		
		logger.info("RunSQL[" + updataSQL + "]");
		long starttimes = System.currentTimeMillis();
		int []results = runner.batch(conn, updataSQL, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		
		return results;
	}
	
	
	/**
	 * 执行单条更新操作
	 * 
	 * @param conn 连接对象
	 * @param table 更新表名
	 * @param valueMap 更新属性集合
	 * @param updateCols 准备更新列
	 * @param keys 按keys字段进行更新
	 * @return
	 * @throws SQLException
	 */
	public static int update(Connection conn, String table, Map<String, Object> valueMap, String[] updateCols, String... keys) throws SQLException {
		if (valueMap == null || valueMap.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMap.");
        }
		if (updateCols == null || updateCols.length < 1){
			throw new SQLException("Null parameters. If parameters aren't need, pass an empty updateCols.");
		}
		if (keys == null || keys.length < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty keys.");
        }
		String updataSQL = buildUpdateSQLByUpdateColsAndKeys(table, updateCols, keys);
		Object[] params = new Object[]{};
		for(String updateCol : updateCols){
			params = ArrayUtils.add(params, valueMap.get(updateCol));
		}
		for(String key : keys){
			params = ArrayUtils.add(params, valueMap.get(key));
		}
		
		logger.info("RunSQL[" + updataSQL + "]");
		long starttimes = System.currentTimeMillis();
		int result = runner.update(conn, updataSQL, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		
		return result;
	}
	
	/**
	 * 执行批量更新操作
	 * 
	 * @param conn 连接对象
	 * @param table 更新表名
	 * @param valueMapList 更新属性集合
	 * @param updateCols 准备更新列
	 * @param keys 按keys字段进行更新
	 * @return
	 * @throws SQLException
	 */
	public static int[] updateBatch(Connection conn, String table, List<Map<String, Object>> valueMapList, String[] updateCols, String... keys) throws SQLException {
		if (valueMapList == null || valueMapList.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMapList.");
        }
		if (keys == null || keys.length < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty keys.");
        }
		String updataSQL = buildUpdateSQLByUpdateColsAndKeys(table, updateCols, keys);
		
		Object[][] params = new Object[valueMapList.size()][];
		int index = 0;
		for(Map<String, Object> valueMap : valueMapList){
			Object[] thparams = new Object[]{};
			for(String updateCol : updateCols){
				thparams = ArrayUtils.add(thparams, valueMap.get(updateCol));
			}
			for(String key : keys){
				thparams = ArrayUtils.add(thparams, valueMap.get(key));
			}
			params[index++] = thparams;
		}
		
		logger.info("RunSQL[" + updataSQL + "]");
		long starttimes = System.currentTimeMillis();
		int []results = runner.batch(conn, updataSQL, params);
		logger.info("RunTime [{} ms]", System.currentTimeMillis() - starttimes);
		
		return results;
	}
	
	
	/**
	 * 构造插入SQL语句
	 * 
	 * @param table 插入表名
	 * @param valueMap 按属性集合进行构造插入SQL
	 * @return
	 */
	private static String buildInsertSQLByMap(String table, Map<String, Object> valueMap){
		StringBuffer tabColName = new StringBuffer();
		tabColName.append(" INSERT INTO ").append(table).append("(");
		
		StringBuffer tabColValue = new StringBuffer();
		tabColValue.append(" VALUES (");
		
		for(Iterator<String> it = valueMap.keySet().iterator(); it.hasNext(); ){
			String colName = it.next();
			tabColName.append(colName).append(", ");
			tabColValue.append("?, ");
		}
		tabColName.delete(tabColName.length() - 2, tabColName.length());
		tabColName.append(")");
		tabColValue.delete(tabColValue.length() - 2, tabColValue.length());
		tabColValue.append(")");
		
		return tabColName.toString() + tabColValue.toString();
	}
	
	/**
	 * 构造删除SQL语句
	 * 
	 * @param table 删除记录表名
	 * @param conditionMap 按属性集合进行构造删除SQL
	 * @return
	 */
	private static String buildDeleteSQLByMap(String table, Map<String, Object> conditionMap){
		StringBuffer deleteSQL = new StringBuffer();
		deleteSQL.append(" DELETE FROM ").append(table);
		deleteSQL.append(" WHERE ");
		for(Iterator<String> it = conditionMap.keySet().iterator(); it.hasNext(); ){
			String name = it.next();
			deleteSQL.append(name).append(" = ? AND ");
		}
		deleteSQL.delete(deleteSQL.length() - 4, deleteSQL.length());
		
		return deleteSQL.toString();
	}
	
	/**
	 * 构造删除SQL语句
	 * 
	 * @param table 删除记录表名
	 * @param keys 按keys进行构造删除SQL
	 * @return
	 */
	private static String buildDeleteSQLByKeys(String table, String... keys){
		StringBuffer deleteSQL = new StringBuffer();
		deleteSQL.append(" DELETE FROM ").append(table);
		deleteSQL.append(" WHERE ");
		for(String key : keys){
			deleteSQL.append(key).append(" = ? AND ");
		}
		deleteSQL.delete(deleteSQL.length() - 4, deleteSQL.length());
		return deleteSQL.toString();
	}
	
	/**
	 * 构造更新SQL语句
	 * @param table 更新表名
	 * @param valueMap 更新值集合
	 * @param keys 按keys进行更新
	 * @return
	 */
	private static String buildUpdateSQLByKeys(String table, Map<String, Object> valueMap, String... keys){
		StringBuffer updateSQL = new StringBuffer();
		updateSQL.append(" UPDATE ");
		updateSQL.append(table);
		updateSQL.append(" SET ");
		for(Iterator<String> it = valueMap.keySet().iterator(); it.hasNext();){
			updateSQL.append(it.next()).append(" = ?, ");
		}
		updateSQL.delete(updateSQL.length() - 2, updateSQL.length());
		updateSQL.append(" WHERE ");
		for(String key : keys){
			updateSQL.append(key).append(" = ? ").append(" AND ");
		}
		updateSQL.delete(updateSQL.length() - 4, updateSQL.length());
		return updateSQL.toString();
	}
	
	/**
	 * 构造更新SQL语句
	 * @param table 更新表名
	 * @param valueMap 更新值集合
	 * @param updateCols 准备更新列
	 * @param keys 按keys进行更新
	 * @return
	 */
	private static String buildUpdateSQLByUpdateColsAndKeys(String table, String[] updateCols, String... keys){
		StringBuffer updateSQL = new StringBuffer();
		updateSQL.append(" UPDATE ");
		updateSQL.append(table);
		updateSQL.append(" SET ");
		for(String updateCol : updateCols){
			updateSQL.append(updateCol).append(" = ?, ");
		}
		updateSQL.delete(updateSQL.length() - 2, updateSQL.length());
		updateSQL.append(" WHERE ");
		for(String key : keys){
			updateSQL.append(key).append(" = ? ").append(" AND ");
		}
		updateSQL.delete(updateSQL.length() - 4, updateSQL.length());
		return updateSQL.toString();
	}
	
	public static void main(String []args) throws SQLException, Exception{
		
	}
}
