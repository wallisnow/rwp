package com.congxing.system.role.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.system.menu.model.MenuVO;
import com.congxing.system.role.model.RoleOfMenuVO;
/**
 * 查询角色菜单列表信息
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class QueryRoleOfMenuStrategy implements Strategy{
	
	private String roleId;
	
	private String menuType;
	
	private String menuId;
	
	public QueryRoleOfMenuStrategy(String roleId, String menuType){
		this.roleId = roleId;
		this.menuType = menuType;
	}
	public QueryRoleOfMenuStrategy(String roleId, String menuType, String menuId){
		this.roleId = roleId;
		this.menuType = menuType;
		this.menuId = menuId;
	}

	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		/**首先查询角色菜单列表信息,需要保证菜单ID不是父菜单,且菜单尚未删除 **/
		StringBuffer roleofmenuHQL = new StringBuffer();
		roleofmenuHQL.append(" SELECT roleofmenu ");
		roleofmenuHQL.append(" FROM RoleOfMenuVO roleofmenu, MenuVO menu ");
		roleofmenuHQL.append(" WHERE roleofmenu.roleId = ? ");
		roleofmenuHQL.append(" AND roleofmenu.menuId = menu.menuId ");
		
		/**查询所有菜单信息列表 **/
		StringBuffer menuHQL = new StringBuffer();
		menuHQL.append(" FROM ");
		menuHQL.append(MenuVO.class.getName());
		if(StringUtils.isNotBlank(menuId)){
			menuHQL.append(" where parentMenuId = ? ");
			menuHQL.append(" and menuType = ? ");
		}else{
			menuHQL.append(" where menuType = ? ");
		}
		menuHQL.append(" order by menuId ");
		
		HibernateDAO roleOfMenuDAO = DAOFactory.getInstance().createHibernateDAO(RoleOfMenuVO.class);
		HibernateDAO menuDAO = DAOFactory.getInstance().createHibernateDAO(MenuVO.class);
		
		/**
		 * 查询用色菜单信息
		 */
		List<RoleOfMenuVO> roleOfMenuList = (List<RoleOfMenuVO>) roleOfMenuDAO.findByHQL(roleofmenuHQL.toString(), this.getRoleId());
		
		Map<String, String> roleOfMenuMap = new HashMap<String,String>();
		for(RoleOfMenuVO roleOfMenuVO : roleOfMenuList){
			roleOfMenuMap.put(roleOfMenuVO.getMenuId(), roleOfMenuVO.getRoleId());
		}
		roleOfMenuList.clear();
		roleOfMenuList = null;
		
		Object[] params = new Object[]{};
		if(StringUtils.isNotBlank(menuId)){
			params = ArrayUtils.add(params, this.getMenuId());
		}
		params = ArrayUtils.add(params, this.getMenuType());
		
		
		List<MenuVO> menuList = (List<MenuVO>) menuDAO.findByHQL(menuHQL.toString(), params);
		for(MenuVO menuVO : menuList){
			if(roleOfMenuMap.containsKey(menuVO.getMenuId())){
				menuVO.setChked(true);
			}
		}
		return menuList;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
