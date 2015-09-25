package com.congxing.system.syscode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.service.CommonServiceSupport;
import com.congxing.core.utils.ReflectionUtils;
import com.congxing.system.syscode.model.SysCodeVO;

@SuppressWarnings({ "serial", "unchecked" })
@Component
public class SysCodeServiceImpl extends CommonServiceSupport implements SysCodeService {

	/**
	 * 查询 kind <> '*' 信息
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<SysCodeVO> doFindSysCodeVOByType(String type) throws Exception {
		return DAOFactory.getInstance().getSessionFactory().getCurrentSession()
				.createCriteria(SysCodeVO.class)
					.add(Restrictions.eq("type", type))
					.add(Restrictions.ne("kind", "*")).list();
	}
	
	/**
	 * 查询 kind <> '*' 信息,并包含其他查询条件信息
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<SysCodeVO> doFindSysCodeVOByType(String type, Map<String, Object>properties) throws Exception{
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("type", type));
		criterions.add(Restrictions.ne("kind", "*"));
		if(null != properties && properties.size() > 0){
			for(Map.Entry<String, Object> entry : properties.entrySet()){
				String propertyCode = entry.getKey();
				Object propertyValue = entry.getValue();
				Class<?> fieldType = ReflectionUtils.getDeclaredFieldType(SysCodeVO.class, propertyCode);
				Criterion criterion = Restrictions.eq(propertyCode, ConvertUtils.convert(propertyValue, fieldType));
				criterions.add(criterion);
			}
		}
		Criterion []crArray = new Criterion[criterions.size()];
		crArray = criterions.toArray(crArray);
		return (List<SysCodeVO>) DAOFactory.getInstance().createHibernateDAO(SysCodeVO.class).findByCriteria(crArray);
	}
	
	/**
	 * 查询所有type信息,包括 kind = '*'
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<SysCodeVO> doFindSysCodeVOByTypeAll(String type) throws Exception {
		return DAOFactory.getInstance().getSessionFactory()
				.getCurrentSession().createCriteria(SysCodeVO.class)
					.add(Restrictions.eq("type", type)).list();
	}

	/**
	 * 根据type和kind查找SysCodeVO对象
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public SysCodeVO doFindSysCodeVOByTypeAndKind(String type, String kind) throws Exception {
		List<SysCodeVO> list = DAOFactory.getInstance().getSessionFactory()
				.getCurrentSession().createCriteria(SysCodeVO.class)
					.add(Restrictions.eq("type", type))
					.add(Restrictions.eq("kind", kind)).list();
		if(null != list && list.size() > 0) return list.get(0);
		return null;
	}

}
