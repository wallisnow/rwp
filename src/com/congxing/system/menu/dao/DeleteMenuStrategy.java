package com.congxing.system.menu.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.system.menu.model.MenuLogVO;
import com.congxing.system.menu.model.MenuVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class DeleteMenuStrategy implements Strategy {

	private String menuId;
	private UserVO user;
	
	public DeleteMenuStrategy(String menuId, UserVO user) {
		this.menuId = menuId;
		this.user = user;
	}

	public Object process() throws Exception {
		HibernateDAO menuDao = DAOFactory.getInstance().createHibernateDAO(MenuVO.class);
		HibernateDAO menuLogDao = DAOFactory.getInstance().createHibernateDAO(MenuLogVO.class);
		
		List<?> datas = menuDao.findByProperty("menuId", menuId);
		if(datas.size() > 1){
			throw new Exception("菜单ID[" + menuId + "]对应菜单有多个,删除操作不成功!");
		}
		if(datas.size() < 0){
			throw new Exception("删除不存在, 操作不成功!");
		}
		
		MenuVO menuVO = (MenuVO) datas.get(0);
		
		List<?> children = menuDao.findByProperty("parentMenuId", menuId);
		if(children.size() > 0){
			throw new Exception("当前菜单有子菜单, 删除操作不成功!");
		}
		
		MenuLogVO logVO = new MenuLogVO();
		BeanUtils.copyProperties(logVO, menuVO);
		
		logVO.setLogId(Sequence.getSequence());
		logVO.setOprCode(user.getUserId());
		logVO.setOprType(Constants.OPRTYPE_DELETE);
		logVO.setOprTime(new Date());
		
		menuLogDao.save(logVO);
		menuDao.deleteByVO(menuVO);

		return null;
	}
	
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

}
