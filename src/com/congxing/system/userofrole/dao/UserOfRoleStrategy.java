package com.congxing.system.userofrole.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.system.user.model.UserVO;
import com.congxing.system.userofrole.model.UserOfRoleLogVO;
import com.congxing.system.userofrole.model.UserOfRoleVO;

@SuppressWarnings("serial")
public class UserOfRoleStrategy implements Strategy {
	
	private UserOfRoleVO urVO;
	
	private UserVO userVO;
	
	private String oprType = Constants.OPRTYPE_ADD;

	public UserOfRoleStrategy(UserOfRoleVO urVO, UserVO userVO) {
		this.urVO = urVO;
		this.userVO = userVO;
	}

	public Object process() throws Exception {
		HibernateDAO urDAO = DAOFactory.getInstance().createHibernateDAO(UserOfRoleVO.class);
		HibernateDAO urLogDAO = DAOFactory.getInstance().createHibernateDAO(UserOfRoleLogVO.class);
		
		UserOfRoleLogVO logVO = new UserOfRoleLogVO();
		BeanUtils.copyProperties(logVO, urVO);
		logVO.setLogId(Sequence.getSequence());
		logVO.setOprCode(userVO.getUserId());
		logVO.setOprTime(new Date());
		logVO.setOprType(oprType);
		urLogDAO.save(logVO);
		
		if(Constants.OPRTYPE_ADD.equals(oprType)){
			List<?> list = urDAO.findByHQL(" from UserOfRoleVO where userId = ? and roleId = ? ", new Object[]{urVO.getUserId(), urVO.getRoleId()});
			if(null != list && list.size()> 0){
				throw new Exception("用户角色关系已经存在");
			}
			urDAO.save(urVO);
		}else if(Constants.OPRTYPE_MODIFY.equals(oprType)){
			List<?> list = urDAO.findByHQL(" from UserOfRoleVO where userId = ? and roleId = ? and urId <> ? ", new Object[]{urVO.getUserId(), urVO.getRoleId(), urVO.getUrId()});
			if(null != list && list.size()> 0){
				throw new Exception("用户角色关系已经存在");
			}
			urDAO.saveOrUpdate(urVO);
		}else if(Constants.OPRTYPE_DELETE.equals(oprType)){
			urDAO.deleteByVO(urVO);
		}
		return null;
	}
	
	public UserOfRoleVO getUrVO() {
		return urVO;
	}
	
	public void setUrVO(UserOfRoleVO urVO) {
		this.urVO = urVO;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public String getOprType() {
		return oprType;
	}

	public void setOprType(String oprType) {
		this.oprType = oprType;
	}
	

}
