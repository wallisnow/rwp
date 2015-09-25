package com.congxing.system.menu.dao;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.utils.DateFormatFactory;
import com.congxing.core.web.constant.Constants;
import com.congxing.system.menu.model.MenuVO;
import com.congxing.system.role.model.RoleOfMenuVO;
import com.congxing.system.role.model.RoleVO;
import com.congxing.system.user.model.UserVO;
import com.congxing.system.userofrole.model.UserOfRoleVO;

/**
 * 返回用户所能访问的一级菜单列表信息
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class QueryFirstMenuStrategy implements Strategy{
	
	private UserVO userVO;
	
	public QueryFirstMenuStrategy(UserVO userVO){
		this.userVO = userVO;
	}

	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		List<MenuVO> menuList = null;
		/**
		 * 用户一级菜单查询
		 */

		StringBuffer sb = new StringBuffer();
		HibernateDAO queryDao = DAOFactory.getInstance().createHibernateDAO(UserVO.class);
		if(userVO.isSuperUser()){
			sb.append(" SELECT menu FROM ");
			sb.append(MenuVO.class.getName()).append(" as menu ");
			sb.append(" WHERE ");
			sb.append(" menu.status = ? ");
			sb.append(" AND menu.parentMenuId = ? ");//菜单状态有效
			sb.append(" ORDER BY menuId ASC ");
			menuList = (List<MenuVO>) queryDao.findByHQL(sb.toString(), new Object[]{Constants.TYPE_YES, Constants.ROOT_MENU_ID});
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
			sb.append(" AND userofrole.status = ? ");//用户角色状态
			sb.append(" AND userofrole.userId = ? AND menu.parentMenuId = ? ");
			sb.append(" AND to_char(role.beginTime,'yyyy-MM-dd HH24:mi:ss')  <= ? ");
			sb.append(" AND to_char(role.endTime,'yyyy-MM-dd HH24:mi:ss') > ? ");
			sb.append(" AND to_char(userofrole.beginTime,'yyyy-MM-dd HH24:mi:ss')  <= ? ");
			sb.append(" AND to_char(userofrole.endTime,'yyyy-MM-dd HH24:mi:ss') > ? ");
			sb.append(" ORDER BY menuId ASC ");
			String dtstr = DateFormatFactory.getDefaultFormat().format(new java.util.Date());
			menuList = (List<MenuVO>) queryDao.findByHQL(sb.toString(), new Object[]{Constants.TYPE_YES, Constants.TYPE_YES, Constants.TYPE_YES, userVO.getUserId(), Constants.ROOT_MENU_ID, dtstr, dtstr, dtstr, dtstr});
		}
		
		Map<String, MenuVO> menuMap = new TreeMap<String, MenuVO>();
		
		for(MenuVO menuVO : menuList){
			menuMap.put(menuVO.getMenuId(), menuVO);
		}
		menuList.clear();
		menuList.addAll(menuMap.values());
		
		return menuList;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

}
