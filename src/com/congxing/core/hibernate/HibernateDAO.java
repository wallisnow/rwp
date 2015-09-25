package com.congxing.core.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.congxing.core.utils.ConvertUtils;
import com.congxing.core.utils.ReflectionUtils;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.struts2.BaseListVO;

public class HibernateDAO {

	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;
	
	protected Class<?> entityClass;

	/**
	 * HibernateDAO构造方法
	 * eg.
	 * UserDao<User, Long> userDao = new HibernateDAO<User, Long>(sessionFactory, User.class);
	 */
	public HibernateDAO(SessionFactory sessionFactory, Class<?> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 清除缓存内容
	 */
	@SuppressWarnings("deprecation")
	public void evict(){
		this.getSessionFactory().evict(entityClass);		
	}
	
	/**
	 * 从缓存中清除该对象
	 * @param id
	 */
	@SuppressWarnings("deprecation")
	public void evict(final Serializable id){
		Assert.notNull(id);
		this.getSessionFactory().evict(entityClass, id);
	}
	
	/**
	 * 保存新增或修改的对象.
	 * @param entity
	 * @return
	 */
	public Object save(final Object entity){
		Assert.notNull(entity, "save entity: {}");
		getSession().save(entity);
		return entity;
	}
	
	/**
	 * 保存新增或修改的对象.
	 * @param entity
	 * @return
	 */
	public Object saveOrUpdate(final Object entity) {
		Assert.notNull(entity, "save or update entity: {}");
		getSession().saveOrUpdate(entity);
		return entity;
	}
	
	/**
	 * 删除对象.
	 * @param entity 对象必须是session中的对象或含id属性的transient对象.
	 */
	public void deleteByVO(final Object entity) {
		Assert.notNull(entity, "delete entity: {}");
		getSession().delete(entity);
	}
	
	/**
	 * 删除对象.
	 * @param entity 对象必须是session中的对象或含pk属性的transient对象.
	 */
	public void deleteByPK(final Serializable pk) {
		Assert.notNull(pk, "delete pk: {}");
		getSession().delete(findByPK(pk));
	}
	
	/**
	 * 按id获取对象.
	 */
	public Object findByPK(final Serializable pk) {
		Assert.notNull(pk);
		return getSession().get(entityClass, pk);
	}
	
	/**
	 * 按模型进行查询
	 * @param entity
	 * @return
	 */
	public List<?> findByExample(final Object entity){
		Assert.notNull(entity);
		return getSession().createCriteria(entityClass).add(Example.create(entity)).list();
	}
	
	/**
	 *	获取全部对象. 
	 */
	public List<?> findAll() {
		return findByCriteria();
	}

	/**
	 * 按单一属性查找对象列表,匹配方式为相等.
	 */
	public List<?> findByProperty(final String propertyName, final Object value) {
		Assert.hasText(propertyName);
		Criterion criterion = Restrictions.eq(propertyName, ConvertUtils.convert(value, ReflectionUtils.getDeclaredFieldType(entityClass, propertyName)));
		return findByCriteria(criterion);
	}
	
	/**
	 * 按多属性值查找对象列表,匹配方式为相等.
	 * @param properties
	 * @return
	 */
	public List<?> findByProperties(final Map<String, Object> properties) {
		if(null == properties || properties.size() < 1) return findByCriteria();
		Criterion []criterions = new Criterion[properties.size()];
		int idx = 0;
		if(null != properties && properties.size() > 0){
			for(Map.Entry<String, Object> entry : properties.entrySet()){
				String propertyCode = entry.getKey();
				Object propertyValue = entry.getValue();
				Class<?> fieldType = ReflectionUtils.getDeclaredFieldType(entityClass, propertyCode);
				Criterion criterion = Restrictions.eq(propertyCode, ConvertUtils.convert(propertyValue, fieldType));
				criterions[idx++] = criterion;
			}
		}
		return findByCriteria(criterions);
	}
	
	/**
	 * 按多属性值查找对象列表,匹配方式为相等.
	 * @param properties
	 * @return
	 */
	public Object findFirstByProperties(final Map<String, Object> properties) {
		Criterion []criterions = null;
		if(null == properties || properties.size() < 1){
			criterions = new Criterion[0];
		}else{
			criterions = new Criterion[properties.size()];
		}
		int idx = 0;
		if(null != properties && properties.size() > 0){
			for(Map.Entry<String, Object> entry : properties.entrySet()){
				String propertyCode = entry.getKey();
				Object propertyValue = entry.getValue();
				Class<?> fieldType = ReflectionUtils.getDeclaredFieldType(entityClass, propertyCode);
				Criterion criterion = Restrictions.eq(propertyCode, ConvertUtils.convert(propertyValue, fieldType));
				criterions[idx++] = criterion;
			}
		}
		List<?> datas = createCriteria(criterions).setMaxResults(1).list();
		if(null != datas && datas.size() > 0)return datas.get(0);
		return null;
	}

	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	public Object findUniqueByProperty(final String propertyName, final Object value) {
		Assert.hasText(propertyName);
		Criterion criterion = Restrictions.eq(propertyName, ConvertUtils.convert(value, ReflectionUtils.getDeclaredFieldType(entityClass, propertyName)));
		return createCriteria(criterion).uniqueResult();
	}
	
	/**
	 * 按Criteria查询对象列表.	 * 
	 * @param criterions 数量可变的Criterion, 该方法可以不带参数
	 */
	public List<?> findByCriteria(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}
	
	/**
	 * 根据Criterion条件创建Criteria
	 * 返回对象类型不是Entity时可用此函数灵活查询
	 * @param criterions 数量可变的Criterion
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}	
	
	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 数量可变的参数
	 */
	public List<?> findByHQL(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}
	
	/**
	 * 按HQL查询第一条记录.
	 * 
	 * @param values 数量可变的参数
	 */
	public Object findFirstByHQL(final String hql, final Object... values) {
		List<?> datas = createQuery(hql, values).setMaxResults(1).list();
		if(null != datas && datas.size() > 0)return datas.get(0);
		return null;
	}

	/**
	 * 按HQL查询唯一对象.
	 */
	public Object findUniqueByHQL(final String hql, final Object... values) {
		return createQuery(hql, values).uniqueResult();
	}	
	
	/**
	 * 根据查询HQL与参数列表创建Query对象
	 * 返回对象类型不是Entity时可用此函数灵活查询
	 * @param values 数量可变的参数
	 */
	public Query createQuery(final String hql, final Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	/**
	 * 按照params进行查询
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<?> findByHQL(final String hql, final Map<String, Object> params){
		return createQuery(hql, params).list();
	}
	
	/**
	 * 按照params进行查询
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object findUniqueByHQL(final String hql, final Map<String, Object> params){
		return createQuery(hql, params).uniqueResult();
	}
	
	/**
	 * 根据查询HQL与参数列表创建Query对象
	 * 返回对象类型不是Entity时可用此函数灵活查询
	 * @param 参数Map
	 */
	public Query createQuery(final String hql, final Map<String, Object> params){
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		if(null != params && params.size() > 0){
			for(Map.Entry<String, Object> entry : params.entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query;
	}
	
	/**
	 * 查询表中propertyName字段最大值
	 * @param propertyName
	 */
	public Object findMaxValue(String propertyName) {
		Assert.hasText(propertyName, "propertyName is null");
		return createCriteria().setProjection(Projections.max(propertyName)).uniqueResult();
	}
	
	/**
	 * 单表查询
	 * @param listVO
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	public Page singleQuery(Object params, final QueryType type) throws Exception{
		Page page = new Page();
		String preFix = "";
		BaseListVO listVO = (BaseListVO)params;
		this.setPageInfo(page, listVO);
		
		StringBuffer countHQL = new StringBuffer();
		countHQL.append(" SELECT COUNT(*) FROM ");
		countHQL.append(entityClass.getName()).append(" ").append(preFix).append(" ");
		
		StringBuffer selectHQL = new StringBuffer();
		selectHQL.append(" FROM ");
		selectHQL.append(entityClass.getName()).append(" ").append(preFix).append(" ");
		
		List<Condition> conditionList = null;
		try{
			conditionList = ConditionBuilder.build(listVO, preFix);
		}catch(Exception ex){
			throw new Exception("组装查询条件出错");
		}
		
		StringBuffer conditionSQL = new StringBuffer();
		for(Condition condition : conditionList){
			conditionSQL.append(condition.getExpression()).append(" AND ");
		}
		if(conditionSQL.length() > 4){
			conditionSQL.delete(conditionSQL.length() - 4, conditionSQL.length() - 1);
			countHQL.append(" WHERE ").append(conditionSQL);
			selectHQL.append(" WHERE ").append(conditionSQL);
		}
		
		if (type == QueryType.ALL || type == QueryType.COUNT) {
			Query query = getSession().createQuery(countHQL.toString());			
			for(Condition condition : conditionList){
				for(Map.Entry<String, Object> entry : condition.getParams().entrySet()){
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
			Object countValue = query.uniqueResult();
			if(null != countValue && !"".equals(countValue)){
				page.setTotalCount(Integer.parseInt(String.valueOf(countValue)));
			}
		}
		
		if (type == QueryType.ALL || type == QueryType.DATA) {
			StringBuffer orderBySQL = new StringBuffer();
			String orderBy = listVO.getOrderBy();
			String order = listVO.getOrder();
			if(null != orderBy && !"".equals(orderBy.trim())){
				if (preFix.trim().length() > 0) {
					preFix = preFix + ".";
				}
				orderBySQL.append(" ORDER BY ");
				//orderBySQL.append(preFix + "." + orderBy + " ");
				orderBySQL.append(preFix + orderBy + " ");
				if(BaseListVO.ASC.equalsIgnoreCase(order.trim()) || BaseListVO.DESC.equalsIgnoreCase(order.trim())){
					orderBySQL.append(order);
				}
			}
			
			if(orderBySQL.length() > 0){
				selectHQL.append(orderBySQL);
			}		
			Query query = getSession().createQuery(selectHQL.toString());
			for(Condition condition : conditionList){
				for(Map.Entry<String, Object> entry : condition.getParams().entrySet()){
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
			this.setPageParameter(query, listVO);
			List<?> datas = query.list();
			page.setDatas(datas);
		}		
		return page;
	}
	
	/**
	 * 多表查询
	 * @param listVO
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	public Page multiQuery(Object params, final QueryType type) throws Exception{
		Page page = new Page();
		BaseListVO listVO = (BaseListVO)params;
		this.setPageInfo(page, listVO);
		
		MultiHQLBuilder hqlBuilder = new MultiHQLBuilder((MultiParameter)params);		
		List<Condition> conditionList = hqlBuilder.getConditionList();		
		StringBuffer countHQL = new StringBuffer();
		countHQL.append(hqlBuilder.getCountHQL());
		
		StringBuffer selectHQL = new StringBuffer();
		selectHQL.append(hqlBuilder.getSelectHQL());
		
		StringBuffer conditionSQL = new StringBuffer();
		for(Condition condition : conditionList){
			conditionSQL.append(condition.getExpression()).append(" AND ");
		}
		if(conditionSQL.length() > 4){
			conditionSQL.delete(conditionSQL.length() - 4, conditionSQL.length() - 1);
			countHQL.append(" AND ").append(conditionSQL);
			selectHQL.append(" AND ").append(conditionSQL);
		}
		
		if (type == QueryType.ALL || type == QueryType.COUNT) {
			Query query = getSession().createQuery(countHQL.toString());
			for(Condition condition : conditionList){
				for(Map.Entry<String, Object> entry : condition.getParams().entrySet()){
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
			Object countValue = query.uniqueResult();
			if(null != countValue && !"".equals(countValue)){
				page.setTotalCount(Integer.parseInt(String.valueOf(countValue)));
			}
		}
		
		if (type == QueryType.ALL || type == QueryType.DATA) {				
			Query query = getSession().createQuery(selectHQL.toString());
			for(Condition condition : conditionList){
				for(Map.Entry<String, Object> entry : condition.getParams().entrySet()){
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
			this.setPageParameter(query, listVO);
			List<?> datas = query.list();
			page.setDatas(datas);
		}		
		return page;
	}
	
	/**
	 * 选择查询种类,单表查询或多表查询
	 * @param params
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Page query(Object params, final QueryType type) throws Exception{
		if(params instanceof MultiParameter){
			return this.multiQuery(params, type);
		}
		return this.singleQuery(params, type);
	}
	
	/**
	 * 默认查询数据总数和加载指定页面数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page query(Object params) throws Exception{
		return this.query(params, QueryType.ALL);
	}
	

	/**
	 * 设置分页信息
	 * @param page
	 * @param listVO
	 */
	public void setPageInfo(Page page, BaseListVO listVO){
		int pageNo = listVO.getPageNo();
		int pageSize = listVO.getPageSize();
		if(pageNo < Constants.DEFAULT_PAGENO) pageNo = Constants.DEFAULT_PAGENO;
		if(pageSize < 0) pageSize = Constants.DEFAULT_PAGESIZE;
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		listVO.setPageNo(pageNo);
		listVO.setPageSize(pageSize);
	}
	
	/**
	 * 设置加载记录信息
	 * @param q
	 * @param page
	 * @return
	 */
	public Query setPageParameter(final Query q, final BaseListVO listVO) {
		if(!listVO.isFindAll()){
			q.setFirstResult(listVO.getFirstResult());
			q.setMaxResults(listVO.getPageSize());
		}
		return q;
	}
	
	
	public Object namedQuery(String hqlName, Map<?, ?> params){
		return null;
	}
	

	
}
