package com.congxing.system.menu.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.system.menu.model.MenuVO;
import com.congxing.system.role.model.RoleOfMenuVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class MenuPurChkStrategy implements Strategy {
	
	private UserVO userVO;
	
	private String currentURL;

	public MenuPurChkStrategy(UserVO userVO, String currentURL) {
		this.userVO = userVO;
		this.currentURL = currentURL;
	}

	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		if(userVO.isSuperUser()){
			return true;
		}
		
		HibernateDAO menuDao = DAOFactory.getInstance().createHibernateDAO(MenuVO.class);
		
		String thisUrl = StringUtils.substringBefore(currentURL, "?");
		String likeUrl = "%" + thisUrl + "%";
		MenuVO menuVO = (MenuVO) menuDao.findFirstByHQL(" FROM MenuVO WHERE menuType = ? and status = ? and menuUrl LIKE ? ", new Object[]{Constants.MENU_TYPE_PAGE, Constants.TYPE_YES, likeUrl});
		if(null != menuVO){
			String menuId = menuVO.getMenuId();
			List<RoleOfMenuVO> rmList = (List<RoleOfMenuVO>) menuDao.findByHQL("FROM RoleOfMenuVO WHERE menuId = ?", new Object[]{menuId});
			if(null == rmList || rmList.size() < 1){
				return Boolean.valueOf(false);
			}
			for(RoleOfMenuVO rmVO : rmList){
				if(userVO.getRoleIdList().contains(rmVO.getRoleId()))return Boolean.valueOf(true);
			}
			return Boolean.valueOf(false);
		}else{
			return Boolean.valueOf(true);
		}
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public String getCurrentURL() {
		return currentURL;
	}
	
	public void setCurrentURL(String currentURL) {
		this.currentURL = currentURL;
	}

}
