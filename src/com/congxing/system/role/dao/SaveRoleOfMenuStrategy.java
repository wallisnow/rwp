package com.congxing.system.role.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.congxing.core.hibernate.DAOFactory;
import com.congxing.core.hibernate.HibernateDAO;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.system.role.model.RoleOfMenuLogVO;
import com.congxing.system.role.model.RoleOfMenuVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class SaveRoleOfMenuStrategy implements Strategy{
	
	private String roleId;
	private String[] addMenuIds;
	private String[] delMenuIds;
	private UserVO userVO;
	
	public SaveRoleOfMenuStrategy(String roleId, String[] addMenuIds, String[] delMenuIds, UserVO userVO) {
		super();
		this.roleId = roleId;
		this.addMenuIds = addMenuIds;
		this.delMenuIds = delMenuIds;
		this.userVO = userVO;
	}

	@SuppressWarnings("unchecked")
	public Object process() throws Exception {
		HibernateDAO dao = DAOFactory.getInstance().createHibernateDAO(RoleOfMenuVO.class);
		HibernateDAO logDao = DAOFactory.getInstance().createHibernateDAO(RoleOfMenuLogVO.class);
		
		StringBuffer roleofmenuHQL = new StringBuffer();
		roleofmenuHQL.append(" SELECT roleofmenu ");
		roleofmenuHQL.append(" FROM RoleOfMenuVO roleofmenu, MenuVO menu ");
		roleofmenuHQL.append(" WHERE roleofmenu.roleId = ? ");
		roleofmenuHQL.append(" AND roleofmenu.menuId = menu.menuId ");
		
		List<RoleOfMenuVO> orgList = (List<RoleOfMenuVO>)dao.findByHQL(roleofmenuHQL.toString(), roleId);
		
		Map<String, String> orgMap = new HashMap<String, String>();
		for(RoleOfMenuVO roleOfMenuVO : orgList){
			orgMap.put(roleOfMenuVO.getMenuId(), roleOfMenuVO.getRoleId());
		}
		orgList = null;
		
		
		if(null != addMenuIds && addMenuIds.length > 0){
			for(String menuId : addMenuIds){
				if(StringUtils.isNotBlank(menuId)){
					if(!orgMap.containsKey(menuId)){
						RoleOfMenuLogVO logVO = new RoleOfMenuLogVO();
						logVO.setLogId(Sequence.getSequence());
						logVO.setOprCode(userVO.getUserId());
						logVO.setOprTime(new Date());
						logVO.setOprType(Constants.OPRTYPE_ADD);
						logVO.setMenuId(menuId);
						logVO.setRoleId(roleId);
						logDao.save(logVO);
						dao.save(new RoleOfMenuVO(roleId, menuId));
					}
				}
			}
		}
		
		if(null != delMenuIds && delMenuIds.length > 0){
			for(String menuId : delMenuIds){
				if(StringUtils.isNotBlank(menuId)){
					if(orgMap.containsKey(menuId)){
						RoleOfMenuLogVO logVO = new RoleOfMenuLogVO();
						logVO.setLogId(Sequence.getSequence());
						logVO.setOprCode(userVO.getUserId());
						logVO.setOprTime(new Date());
						logVO.setOprType(Constants.OPRTYPE_DELETE);
						logVO.setMenuId(menuId);
						logVO.setRoleId(roleId);
						logDao.save(logVO);
						dao.deleteByPK(new RoleOfMenuVO(roleId, menuId));
					}
				}
			}
		}
		return null;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public String[] getAddMenuIds() {
		return addMenuIds;
	}

	public void setAddMenuIds(String[] addMenuIds) {
		this.addMenuIds = addMenuIds;
	}

	public String[] getDelMenuIds() {
		return delMenuIds;
	}

	public void setDelMenuIds(String[] delMenuIds) {
		this.delMenuIds = delMenuIds;
	}

}
