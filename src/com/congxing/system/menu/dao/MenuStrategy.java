package com.congxing.system.menu.dao;

import java.util.Date;

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
public class MenuStrategy implements Strategy{
	
	private MenuVO menuVO;
	private boolean isnew = false;
	private UserVO userVO;
	
	public MenuStrategy(MenuVO menuVO, UserVO userVO){
		this.menuVO = menuVO;
		this.userVO = userVO;
	}

	public Object process() throws Exception {
		HibernateDAO menuDAO = DAOFactory.getInstance().createHibernateDAO(MenuVO.class);
		HibernateDAO menuLogDAO = DAOFactory.getInstance().createHibernateDAO(MenuLogVO.class);
		
		String partnerMenuId = menuVO.getParentMenuId();
		MenuLogVO logVO = new MenuLogVO();
		logVO.setLogId(Sequence.getSequence());
		logVO.setOprCode(userVO.getUserId());
		logVO.setOprTime(new Date());
		logVO.setOprType(Constants.OPRTYPE_MODIFY);
		if(isnew){
			String menuId = this.getNextMenuIDByPartnerMenuId(menuDAO, partnerMenuId);
			menuVO.setMenuId(menuId);
			logVO.setOprType(Constants.OPRTYPE_ADD);
		}
		BeanUtils.copyProperties(logVO, menuVO);
		if(isnew){
			menuLogDAO.save(logVO);
			menuVO = (MenuVO) menuDAO.save(menuVO);
		}else{
			menuLogDAO.save(logVO);
			menuVO = (MenuVO) menuDAO.saveOrUpdate(menuVO);
		}
		return menuVO;
	}
	
	public String getNextMenuIDByPartnerMenuId(HibernateDAO menuDAO, String partnerMenuId){
		Object maxMenuId = menuDAO.findUniqueByHQL("select max(menuId) from MenuVO where parentMenuId =?", new Object[]{partnerMenuId});
		String menuMaxIndex = "01";
		if(null != maxMenuId){
			int maxIndex = Integer.parseInt(maxMenuId.toString().substring(partnerMenuId.length()));
			maxIndex += 1;
			if(maxIndex < 10){
				menuMaxIndex = "0" + maxIndex;
			}else{
				menuMaxIndex = String.valueOf(maxIndex);
			}
		}
		String curMenuId = partnerMenuId + menuMaxIndex;
		return curMenuId;
	}

	public MenuVO getMenuVO() {
		return menuVO;
	}

	public void setMenuVO(MenuVO menuVO) {
		this.menuVO = menuVO;
	}

	public boolean isIsnew() {
		return isnew;
	}

	public void setIsnew(boolean isnew) {
		this.isnew = isnew;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

}
