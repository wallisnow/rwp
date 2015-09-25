package com.congxing.system.menu.dao;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.utils.DateFormatFactory;
import com.congxing.core.web.constant.Constants;
import com.congxing.system.menu.model.MenuVO;
import com.congxing.system.menufav.model.MenuFavVO;
import com.congxing.system.role.model.RoleOfMenuVO;
import com.congxing.system.role.model.RoleVO;
import com.congxing.system.user.model.UserVO;
import com.congxing.system.userofrole.model.UserOfRoleVO;

/**
 * 根据菜单ID获取所有能访问的子菜单ID
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class QueryChildMenuStrategy implements Strategy{
	
	private UserVO userVO;
	
	private String menuId;
	
	public QueryChildMenuStrategy(UserVO userVO, String menuId){
		this.userVO = userVO;
		this.menuId = menuId;
	}

	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		StringBuffer sb = new StringBuffer();
		String likeMenuId = menuId + "%";//用like进行查询
		
		if(StringUtils.equals(Constants.ROOT_FAVORITIES_MENU_ID, menuId)){//我的收藏菜单
			HibernateDAO queryDao = DAOFactory.getInstance().createHibernateDAO(MenuFavVO.class);
			sb.append(" FROM MenuFavVO ");
			sb.append(" WHERE ");
			sb.append(" status = ? ");
			sb.append(" AND menuType = ? ");
			sb.append(" AND parentMenuId = ? ");//菜单状态有效
			sb.append(" AND userId = ? ");
			
			List<MenuFavVO> favMenuList = (List<MenuFavVO>) queryDao.findByHQL(sb.toString(), new Object[]{Constants.TYPE_YES, Constants.MENU_TYPE_PAGE, menuId, userVO.getUserId()});
			
			Map<String, MenuFavVO> menuMap = new TreeMap<String, MenuFavVO>();
			
			for(MenuFavVO MenuFavVO : favMenuList){
				menuMap.put(MenuFavVO.getMenuId(), MenuFavVO);
			}
			favMenuList.clear();
			favMenuList.addAll(menuMap.values());
			
			return favMenuList;
		}
		
		
		HibernateDAO queryDao = DAOFactory.getInstance().createHibernateDAO(MenuVO.class);
		List<MenuVO> menuList = null;
		if(userVO.isSuperUser()){
			sb.append(" SELECT menu FROM ");
			sb.append(MenuVO.class.getName()).append(" as menu ");
			sb.append(" WHERE ");
			sb.append(" menu.status = ? ");
			sb.append(" AND menu.menuType = ? ");//菜单类型
			sb.append(" AND menu.menuId like ? ");//菜单状态有效
			menuList = (List<MenuVO>) queryDao.findByHQL(sb.toString(), new Object[]{Constants.TYPE_YES, Constants.MENU_TYPE_PAGE, likeMenuId});
		}else{
			sb.append(" SELECT menu FROM ");
			sb.append(UserOfRoleVO.class.getName()).append(" as userofrole, ");
			sb.append(RoleOfMenuVO.class.getName()).append(" as roleofmenu, ");
			sb.append(MenuVO.class.getName()).append(" as menu, ");
			sb.append(RoleVO.class.getName()).append(" as role ");
			sb.append(" WHERE ");
			sb.append(" userofrole.roleId = roleofmenu.roleId  ");
			sb.append(" AND roleofmenu.menuId = menu.menuId ");
			sb.append(" AND userofrole.roleId = role.roleId ");
			sb.append(" AND role.status = ? ");//角色状态有效
			sb.append(" AND menu.status = ? ");//菜单状态有效
			sb.append(" AND menu.menuType = ? ");//菜单类型
			sb.append(" AND userofrole.status = ? ");//用户角色状态
			sb.append(" AND userofrole.userId = ? ");//关于用户
			sb.append(" AND menu.menuId like ? ");
			sb.append(" AND to_char(role.beginTime,'yyyy-MM-dd HH24:mi:ss')  <= ? ");
			sb.append(" AND to_char(role.endTime,'yyyy-MM-dd HH24:mi:ss') > ? ");
			sb.append(" AND to_char(userofrole.beginTime,'yyyy-MM-dd HH24:mi:ss')  <= ? ");
			sb.append(" AND to_char(userofrole.endTime,'yyyy-MM-dd HH24:mi:ss') > ? ");
			String dtstr = DateFormatFactory.getDefaultFormat().format(new java.util.Date());
			menuList = (List<MenuVO>) queryDao.findByHQL(sb.toString(), new Object[]{Constants.TYPE_YES, Constants.TYPE_YES, Constants.MENU_TYPE_PAGE, Constants.TYPE_YES, userVO.getUserId(), likeMenuId, dtstr, dtstr, dtstr, dtstr});
		}
		Map<String, MenuVO> menuMap = new TreeMap<String, MenuVO>();
		
		for(MenuVO menuVO : menuList){
			menuMap.put(menuVO.getMenuId(), menuVO);
		}
		menuList.clear();
		menuList.addAll(menuMap.values());
		
		return menuList;
	}

}
