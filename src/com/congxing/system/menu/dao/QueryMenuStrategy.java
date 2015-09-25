/**  

* @Title: QueryMenuStrategy.java

* @Package com.congxing.system.menu.dao

* @Description: TODO(用一句话描述该文件做什么)

* @author LIUKANGJIN

* @date 2013-12-24 上午09:19:14

* @version V1.0  

*/

package com.congxing.system.menu.dao;

import java.util.List;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.system.menu.model.MenuVO;
import com.congxing.system.role.model.RoleOfMenuVO;
import com.congxing.system.role.model.RoleVO;
import com.congxing.system.user.model.UserVO;
import com.congxing.system.userofrole.model.UserOfRoleVO;

/**
 * <p>@Description: </p>
 *
 * <p>@author: LIUKANGJIN</p>
 *
 * <p>@date: 2013-12-24<p>
 *
 * <p>@version: V.1.0<p>
 *
 */
@SuppressWarnings("serial")
public class QueryMenuStrategy implements Strategy{
	
	private UserVO userVO;
	
	private String menuType;
	
	private String parentMenuId;
	
	public QueryMenuStrategy(UserVO userVO, String menuType, String parentMenuId){
		this.userVO = userVO;
		this.menuType = menuType;
		this.parentMenuId = parentMenuId;
	}

	/* (non-Javadoc)
	 * <p>Title: process</p>
	 * <p>Description: </p>
	 * @return
	 * @throws Exception
	 * @see com.congxing.core.hibernate.Strategy#process()
	 */
	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		HibernateDAO queryDao = DAOFactory.getInstance().createHibernateDAO(UserVO.class);
		StringBuffer sb = new StringBuffer();
		parentMenuId = parentMenuId + "%";
		if(userVO.isSuperUser()){
			sb.append(" SELECT menu FROM ");
			sb.append(MenuVO.class.getName()).append(" as menu ");
			sb.append(" WHERE ");
			sb.append(" menu.status = ? ");
			sb.append(" AND menu.menuType = ? ");//菜单类型
			sb.append(" AND menu.menuId like ? ");//菜单状态有效
			sb.append(" ORDER BY menu.menuId ");
			return (List<MenuVO>) queryDao.findByHQL(sb.toString(), new Object[]{Constants.TYPE_TRUE, menuType, parentMenuId});
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
			sb.append(" AND menu.menuId like ? ");//菜单状态有效
			sb.append(" AND role.beginTime  <= ? ");
			sb.append(" AND role.endTime > ? ");
			sb.append(" AND userofrole.beginTime <= ? ");
			sb.append(" AND userofrole.endTime > ? ");
			sb.append(" ORDER BY menu.menuId ");
			java.util.Date curDt = new java.util.Date();//当前时间
			
			return (List<MenuVO>) queryDao.findByHQL(sb.toString(), new Object[]{Constants.TYPE_TRUE, Constants.TYPE_TRUE, menuType, Constants.TYPE_TRUE, userVO.getUserId(), parentMenuId, curDt, curDt, curDt, curDt});
		}
	}

}
