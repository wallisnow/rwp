package com.congxing.core.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;

import com.congxing.core.hibernate.Page;
import com.congxing.core.hibernate.QueryType;
import com.congxing.core.hibernate.Strategy;
import com.congxing.system.user.model.UserVO;

/**
 * 
 * <p>
 * Title: CommonService
 * </p>
 * 
 * </p>
 * 
 * @author liukangjin
 * @version 1.0
 * @since 2011-10-5
 *
 */
public interface CommonService {

	public Object doCreate(Class<?> voClass, Object vo, UserVO userVO) throws Exception;

	public Object doUpdate(Class<?> voClass, Object vo, UserVO userVO) throws Exception;

	public void doRemoveByVO(Class<?> voClass, Object vo, UserVO userVO) throws Exception;

	public void doRemoveByPK(Class<?> voClass, Serializable pk, UserVO userVO) throws Exception;

	public Object doFindByPK(Class<?> voClass, Serializable pk) throws Exception;

	public List<?> doFindALL(Class<?> voClass) throws Exception;

	public Page doQuery(Class<?> voClass, Object params) throws Exception;

	public Page doQuery(Class<?> voClass, Object params, QueryType queryType) throws Exception;

	public List<?> dofindDatas(Class<?> voClass, Object params) throws Exception;

	public int dofindCount(Class<?> voClass, Object params) throws Exception;

	/**
	 * ======================================================================
	 * 以下方法为带日志记录日志方法
	 * ======================================================================
	 */
	public Object doCreate(Class<?> voClass, Object vo, Class<?> logClass, Object logVO) throws Exception;

	public void doRemoveByVO(Class<?> voClass, Object vo, Class<?> logClass, Object logVO) throws Exception;

	public void doRemoveByPK(Class<?> voClass, Serializable pk, Class<?> logClass, Object logVO) throws Exception;

	public Object doUpdate(Class<?> voClass, Object vo, Class<?> logClass, Object logVO) throws Exception;

	/**
	 * ======================================================================
	 * doProcess 为原子业务封装方法
	 * ======================================================================
	 */
	public Object doProcess(Strategy strategy) throws Exception;

	/**
	 * ======================================================================
	 * 特殊查询
	 * ======================================================================
	 */
	public List<?> doFindByExampleVO(Class<?> voClass, Object entity);

	public List<?> doFindByProperty(Class<?> voClass, String propertyName, Object propertyValue);

	public List<?> doFindByProperties(Class<?> voClass, Map<String, Object> properties);

	public Object doFindFirstByProperties(Class<?> voClass, Map<String, Object> properties);

	public Object doFindMaxPropertyValue(Class<?> voClass, String propertyName);

	public List<?> doFindByHQL(final String hql, final Object... values);

	public Object doFindFirstByHQL(final String hql, final Object... values);

	public Object doFindUniqueByHQL(final String hql, final Object... values);

	public List<?> doFindByHQL(final String hql, final Map<String, Object> params);

	public Object doFindUniqueByHQL(final String hql, final Map<String, Object> params);

	/**
	 * ======================================================================
	 * JDBC 查询
	 * ======================================================================
	 */
	public void doWork(Work work) throws SQLException;

	public <T> T doReturningWork(ReturningWork<T> work) throws SQLException;

	public <T> T doQuery(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException;

	public <T> T doQuery(String sql, ResultSetHandler<T> rsh, int page, int size, Object... params) throws SQLException;

	public <T> List<T> doQueryList(String sql, Class<T> beanClass, Object... params) throws SQLException;

	public <T> List<T> doQueryList(String sql, Class<T> beanClass, int page, int size, Object... params)
			throws SQLException;

	public long doStat(String sql, Object... params) throws SQLException;

	public int doExecute(String sql, Object... params) throws SQLException;

}
