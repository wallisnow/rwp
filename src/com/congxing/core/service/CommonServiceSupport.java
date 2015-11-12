package com.congxing.core.service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;

import com.congxing.core.dbtool.SQLRunner;
import com.congxing.core.hibernate.AbstractReturningWork;
import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Page;
import com.congxing.core.hibernate.QueryType;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.struts2.Business;
import com.congxing.system.businesslog.model.BusinessLogVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class CommonServiceSupport implements Serializable {

	public Object doCreate(Class<?> voClass, Object vo, UserVO userVO) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		if (userVO == null) {
			return dao.save(vo);
		}
		if (vo instanceof Business) {
			BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_ADD, (Business) vo);
			return doCreate(voClass, vo, BusinessLogVO.class, logVO);
		} else {
			return dao.save(vo);
		}
	}

	public Object doUpdate(Class<?> voClass, Object vo, UserVO userVO) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		if (userVO == null) {
			return dao.saveOrUpdate(vo);
		}
		if (vo instanceof Business) {
			BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_MODIFY, (Business) vo);
			return doUpdate(voClass, vo, BusinessLogVO.class, logVO);
		} else {
			return dao.saveOrUpdate(vo);
		}
	}

	public void doRemoveByVO(Class<?> voClass, Object vo, UserVO userVO) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		if (userVO == null) {
			dao.deleteByVO(vo);
		} else {
			if (vo instanceof Business) {
				BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_MODIFY, (Business) vo);
				this.doRemoveByVO(voClass, vo, BusinessLogVO.class, logVO);
			} else {
				dao.deleteByVO(vo);
			}
		}
	}

	public void doRemoveByPK(Class<?> voClass, Serializable pk, UserVO userVO) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		if (userVO == null) {
			dao.deleteByPK(pk);
		}
		Object entityVO = this.doFindByPK(voClass, pk);
		if (entityVO instanceof Business) {
			BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_DELETE, (Business) entityVO);
			this.doRemoveByPK(voClass, pk, BusinessLogVO.class, logVO);
		} else {
			dao.deleteByPK(pk);
		}
	}

	public Object doFindByPK(Class<?> voClass, Serializable pk) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		return dao.findByPK(pk);
	}

	public List<?> doFindALL(Class<?> voClass) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		return dao.findAll();
	}

	public Page doQuery(Class<?> voClass, Object params) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		return dao.query(params);
	}

	public Page doQuery(Class<?> voClass, Object params, QueryType queryType) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		return dao.query(params, queryType);
	}

	public List<?> dofindDatas(Class<?> voClass, Object params) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		Page page = dao.query(params, QueryType.DATA);
		return page.getDatas();
	}

	public int dofindCount(Class<?> voClass, Object params) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		Page page = dao.query(params, QueryType.COUNT);
		return page.getTotalCount();
	}

	/**
	 * ======================================================================
	 * 以下方法为带日志记录日志方法
	 * ======================================================================
	 */
	public Object doCreate(Class<?> voClass, Object vo, Class<?> logClass, Object logVO) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		HibernateDAO logdao = DAOFactory.getInstance().createHibernateDAO(logClass);
		logdao.save(logVO);
		return dao.save(vo);
	}

	public void doRemoveByVO(Class<?> voClass, Object vo, Class<?> logClass, Object logVO) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		HibernateDAO logdao = DAOFactory.getInstance().createHibernateDAO(logClass);
		logdao.save(logVO);
		dao.deleteByVO(vo);
	}

	public void doRemoveByPK(Class<?> voClass, Serializable pk, Class<?> logClass, Object logVO) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		HibernateDAO logdao = DAOFactory.getInstance().createHibernateDAO(logClass);
		logdao.save(logVO);
		dao.deleteByPK(pk);
	}

	/**
	 * 带日志更新
	 * 
	 * @param voClass
	 * @param vo
	 * @param logClass
	 * @param logVO
	 * @return
	 * @throws Exception
	 */
	public Object doUpdate(Class<?> voClass, Object vo, Class<?> logClass, Object logVO) throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		HibernateDAO logdao = DAOFactory.getInstance().createHibernateDAO(logClass);
		logdao.save(logVO);
		return dao.saveOrUpdate(vo);
	}

	/**
	 * 查询接口封装
	 * 
	 * @param strategy
	 * @return
	 * @throws Exception
	 */
	public Object doProcess(Strategy strategy) throws Exception {
		return strategy.process();
	}

	/**
	 * 模型查找,需要注意此方法不能应用于主键
	 * 
	 * @param voClass
	 * @param entity
	 * @return
	 */
	public List<?> doFindByExampleVO(Class<?> voClass, Object entity) {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		return dao.findByExample(entity);
	}

	/**
	 * 属性值记录查询
	 * 
	 * @param voClass
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public List<?> doFindByProperty(Class<?> voClass, String propertyName, Object propertyValue) {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		return dao.findByProperty(propertyName, propertyValue);
	}

	public List<?> doFindByProperties(Class<?> voClass, Map<String, Object> properties) {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		return dao.findByProperties(properties);
	}

	public Object doFindFirstByProperties(Class<?> voClass, Map<String, Object> properties) {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		return dao.findFirstByProperties(properties);
	}

	/**
	 * 查找属性最大值
	 * 
	 * @param voClass
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public Object doFindMaxPropertyValue(Class<?> voClass, String propertyName) {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(voClass);
		return dao.findMaxValue(propertyName);
	}

	public List<?> doFindByHQL(final String hql, final Object... values) {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(Object.class);
		return dao.findByHQL(hql, values);
	}

	public Object doFindFirstByHQL(final String hql, final Object... values) {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(Object.class);
		return dao.findFirstByHQL(hql, values);
	}

	public Object doFindUniqueByHQL(final String hql, final Object... values) {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(Object.class);
		return dao.findUniqueByHQL(hql, values);
	}

	public List<?> doFindByHQL(final String hql, final Map<String, Object> params) {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(Object.class);
		return dao.findByHQL(hql, params);
	}

	public Object doFindUniqueByHQL(final String hql, final Map<String, Object> params) {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(Object.class);
		return dao.findUniqueByHQL(hql, params);
	}

	/**
	 * ======================================================================
	 * 以下方法为JDBC操作方法
	 * ======================================================================
	 */

	/**
	 * JDBC库表操作入口
	 * 
	 * @param work
	 * @return
	 * @throws SQLException
	 */
	public void doWork(Work work) throws SQLException {
		DAOFactory.getInstance().getSessionFactory().getCurrentSession().doWork(work);
	}

	/**
	 * JDBC库表操作入口
	 * 
	 * @param work
	 * @return
	 * @throws Exception
	 */
	public <T> T doReturningWork(ReturningWork<T> work) throws SQLException {
		return DAOFactory.getInstance().getSessionFactory().getCurrentSession().doReturningWork(work);
	}

	public <T> T doQuery(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
		return DAOFactory.getInstance().getSessionFactory().getCurrentSession()
				.doReturningWork(new AbstractReturningWork<T, T>(sql, rsh, params) {
					public T execute(Connection conn) throws SQLException {
						return SQLRunner.query(conn, this.getSql(), this.getRsh(), this.getParams());
					}
				});
	}

	public <T> T doQuery(String sql, ResultSetHandler<T> rsh, int page, int size, Object... params)
			throws SQLException {
		return DAOFactory.getInstance().getSessionFactory().getCurrentSession()
				.doReturningWork(new AbstractReturningWork<T, T>(sql, rsh, page, size, params) {
					public T execute(Connection conn) throws SQLException {
						return SQLRunner.query(conn, this.getSql(), this.getRsh(), this.getPage(), this.getSize(),
								this.getParams());
					}
				});
	}

	public <T> List<T> doQueryList(String sql, Class<T> beanClass, Object... params) throws SQLException {
		return DAOFactory.getInstance().getSessionFactory().getCurrentSession()
				.doReturningWork(new AbstractReturningWork<List<T>, T>(sql, beanClass, params) {
					public List<T> execute(Connection conn) throws SQLException {
						return SQLRunner.queryList(conn, this.getSql(), this.getBeanClass(), this.getParams());
					}
				});
	}

	public <T> List<T> doQueryList(String sql, Class<T> beanClass, int page, int size, Object... params)
			throws SQLException {
		return DAOFactory.getInstance().getSessionFactory().getCurrentSession()
				.doReturningWork(new AbstractReturningWork<List<T>, T>(sql, beanClass, page, size, params) {
					public List<T> execute(Connection conn) throws SQLException {
						return (List<T>) SQLRunner.queryList(conn, this.getSql(), this.getBeanClass(), this.getPage(),
								this.getSize(), this.getParams());
					}
				});
	}

	public long doStat(String sql, Object... params) throws SQLException {
		return DAOFactory.getInstance().getSessionFactory().getCurrentSession()
				.doReturningWork(new AbstractReturningWork<Long, Long>(sql, params) {
					public Long execute(Connection conn) throws SQLException {
						return SQLRunner.stat(conn, this.getSql(), this.getParams());
					}
				});
	}

	public int doExecute(String sql, Object... params) throws SQLException {
		return DAOFactory.getInstance().getSessionFactory().getCurrentSession()
				.doReturningWork(new AbstractReturningWork<Integer, Integer>(sql, params) {
					public Integer execute(Connection conn) throws SQLException {
						return SQLRunner.execute(conn, this.getSql(), this.getParams());
					}
				});
	}
}
