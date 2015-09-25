package com.congxing.system.userofrole.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.system.userofrole.model.UserOfRoleLogVO;
import com.congxing.system.userofrole.model.UserOfRoleVO;

@SuppressWarnings("serial")
public class DisableUserOfRoleStrategy implements Strategy {
	
	private String userId;
	
	public DisableUserOfRoleStrategy(String userId){
		this.userId = userId;
	}

	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		HibernateDAO urDao = DAOFactory.getInstance().createHibernateDAO(UserOfRoleVO.class);
		HibernateDAO urLogDao = DAOFactory.getInstance().createHibernateDAO(UserOfRoleLogVO.class);
		
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM ");
		hql.append(UserOfRoleVO.class.getName());
		hql.append(" WHERE userId = ? ");
		hql.append(" AND status = ? ");		
		
		List<UserOfRoleVO> urList = (List<UserOfRoleVO>) urDao.findByHQL(hql.toString(), new Object[]{userId, Constants.TYPE_YES});
		
		for(UserOfRoleVO urVO : urList){
			urVO.setStatus(Constants.TYPE_NO);
			
			UserOfRoleLogVO logVO = new UserOfRoleLogVO();
			logVO.setLogId(Sequence.getSequence());
			logVO.setOprCode(Constants.SYSTEM_USER_ID);
			logVO.setOprTime(new Date());
			logVO.setOprType(Constants.OPRTYPE_MODIFY);
			
			BeanUtils.copyProperties(logVO, urVO);
			
			urLogDao.save(logVO);
			urDao.saveOrUpdate(urVO);
		}
		return urList;
	}
	
	

}
