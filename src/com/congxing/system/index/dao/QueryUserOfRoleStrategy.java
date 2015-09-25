package com.congxing.system.index.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.system.user.model.UserVO;
import com.congxing.system.userofrole.model.UserOfRoleVO;

@SuppressWarnings("serial")
public class QueryUserOfRoleStrategy implements Strategy{
	
	private UserVO userVO;

	public QueryUserOfRoleStrategy(UserVO userVO) {
		this.userVO = userVO;
	}

	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" SELECT roleId FROM ");
		hql.append(UserOfRoleVO.class.getName());
		hql.append(" WHERE userId = ? ");
		hql.append(" AND status = ? ");
		hql.append(" AND beginTime <= ? ");
		hql.append(" AND endTime > ? ");
		Date dt = new java.util.Date();
		
		HibernateDAO queryDao = DAOFactory.getInstance().createHibernateDAO(UserOfRoleVO.class);
		
		List<String> roleIdList = (List<String>)queryDao.findByHQL(hql.toString(), new Object[]{userVO.getUserId(), Constants.TYPE_TRUE, dt, dt});
		if(null == roleIdList) return new ArrayList<String>();
		return roleIdList;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	
}
