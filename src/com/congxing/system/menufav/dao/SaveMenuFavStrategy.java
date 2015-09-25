package com.congxing.system.menufav.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.system.menu.model.MenuVO;
import com.congxing.system.menufav.model.MenuFavLogVO;
import com.congxing.system.menufav.model.MenuFavVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class SaveMenuFavStrategy implements Strategy {
	
	private String favMenuId;
	
	private UserVO user;
	
	public SaveMenuFavStrategy(String favMenuId, UserVO user) {
		this.favMenuId = favMenuId;
		this.user = user;
	}

	public Object process() throws Exception {
		HibernateDAO menuDAO = DAOFactory.getInstance().createHibernateDAO(MenuVO.class);
		HibernateDAO favmenuDAO = DAOFactory.getInstance().createHibernateDAO(MenuFavVO.class);
		HibernateDAO favmenuLogDAO = DAOFactory.getInstance().createHibernateDAO(MenuFavLogVO.class);
		
		/*判断收藏菜单信息是否已经存在*/
		List<?> datas = favmenuDAO.findByHQL("FROM MenuFavVO WHERE menuId = ? AND parentMenuId = ? and userId = ?", new Object[]{favMenuId, Constants.ROOT_FAVORITIES_MENU_ID, user.getUserId()});
		
		if(datas.size() > 0){
			throw new Exception("收藏菜单信息已经存在,不能重复收藏");
		}
		
		datas = menuDAO.findByHQL("FROM MenuVO WHERE menuId = ?", new Object[]{favMenuId});		
		MenuVO curMenuVO = null;		
		if(datas.size() > 0){
			curMenuVO = (MenuVO) datas.get(0);
		}
		if(null == curMenuVO){
			throw new Exception("被收藏菜单信息不存在");
		}
		
		MenuFavVO favMenuVO = new MenuFavVO();
		BeanUtils.copyProperties(favMenuVO, curMenuVO);
		favMenuVO.setSeqId(Sequence.getSequence());
		favMenuVO.setParentMenuId(Constants.ROOT_FAVORITIES_MENU_ID);
		favMenuVO.setUserId(user.getUserId());
		
		MenuFavLogVO favMenuLogVO = new MenuFavLogVO();
		BeanUtils.copyProperties(favMenuLogVO, favMenuVO);
		favMenuLogVO.setLogId(Sequence.getSequence());
		favMenuLogVO.setOprTime(new Date());
		favMenuLogVO.setOprCode(user.getUserId());
		favMenuLogVO.setOprType(Constants.OPRTYPE_ADD);

		favmenuLogDAO.save(favMenuLogVO);
		favmenuDAO.save(favMenuVO);
		
		return favMenuVO;
	}

}
