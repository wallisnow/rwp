package com.congxing.system.menufav.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.system.menufav.model.MenuFavLogVO;
import com.congxing.system.menufav.model.MenuFavVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class RemoveMenuFavStrategy implements Strategy {
	
private String favMenuId;
	
	private UserVO user;
	
	public RemoveMenuFavStrategy(String favMenuId, UserVO user) {
		this.favMenuId = favMenuId;
		this.user = user;
	}

	public Object process() throws Exception {
		HibernateDAO favmenuDAO = DAOFactory.getInstance().createHibernateDAO(MenuFavVO.class);
		HibernateDAO favmenuLogDAO = DAOFactory.getInstance().createHibernateDAO(MenuFavLogVO.class);
		
		/*判断收藏菜单信息是否已经存在*/
		List<?> datas = favmenuDAO.findByHQL("FROM MenuFavVO WHERE menuId = ? AND parentMenuId = ? and userId = ?", new Object[]{favMenuId, Constants.ROOT_FAVORITIES_MENU_ID, user.getUserId()});
		
		if(datas.size() <= 0){
			throw new Exception("收藏菜单信息不存在,删除操作失败");
		}
		MenuFavVO menuFavVO = (MenuFavVO) datas.get(0);
		
		MenuFavLogVO menuFavLogVO = new MenuFavLogVO();
		BeanUtils.copyProperties(menuFavLogVO, menuFavVO);
		menuFavLogVO.setLogId(Sequence.getSequence());
		menuFavLogVO.setOprTime(new Date());
		menuFavLogVO.setOprCode(user.getUserId());
		menuFavLogVO.setOprType(Constants.OPRTYPE_DELETE);

		favmenuLogDAO.save(menuFavLogVO);
		favmenuDAO.deleteByVO(menuFavVO);
		
		return null;
	}


}
