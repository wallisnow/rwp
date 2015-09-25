package com.congxing.system.role.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.Business;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.core.web.tree.TreeBuilder;
import com.congxing.system.businesslog.model.BusinessLogVO;
import com.congxing.system.menu.model.MenuVO;
import com.congxing.system.role.dao.QueryRoleOfMenuStrategy;
import com.congxing.system.role.dao.SaveRoleOfMenuStrategy;
import com.congxing.system.role.model.RoleVO;
import com.congxing.system.user.model.UserVO;
import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
public class RoleAction extends BaseAction{	
	
	public RoleAction(){
		this.voClass = RoleVO.class;
		this.pkNameArray = new String[]{"roleId"};
	}
	
	protected void setSaveEntityVO(Object entityVO, Map<String, Object> paramsMap, UserVO userVO) throws Exception {
		String curMaxRoleId = (String) this.getService().doFindMaxPropertyValue(voClass, "roleId");
		if(StringUtils.isBlank(curMaxRoleId)){
			curMaxRoleId = Constants.ROLE_ID_START;
		}
		String nextRoleId = String.valueOf(Integer.parseInt(curMaxRoleId) + 1);
		((RoleVO)entityVO).setRoleId(nextRoleId);
	}
	
	public void saveRoleJson() throws IOException{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String isNew = (String) paramsMap.get(Constants.PAGE_ISNEW);
			this.setParamsMapToTargetObject(paramsMap, this.getEntityVO());
			RoleVO roleVO = (RoleVO) this.getEntityVO();
			roleVO.setRoleName( java.net.URLDecoder.decode(roleVO.getRoleName()));
			if(Constants.STRING_TRUE.equals(isNew)){
				this.setSaveEntityVO(this.getEntityVO(), paramsMap, userVO);
				if(this.getEntityVO() instanceof Business){
					BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_ADD, (Business)this.getEntityVO());
					entityVO = this.getService().doCreate(voClass, this.getEntityVO(), BusinessLogVO.class, logVO);
				}else{
					entityVO = this.getService().doCreate(voClass, this.getEntityVO());
				}
				this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, "保存成功!"));
			}else{
				this.setUpdateEntityVO(this.getEntityVO(), paramsMap, userVO);
				if(this.getEntityVO() instanceof Business){
					BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_MODIFY, (Business)this.getEntityVO());
					entityVO = this.getService().doUpdate(voClass, this.getEntityVO(), BusinessLogVO.class, logVO);
				}else{
					entityVO = this.getService().doUpdate(voClass, this.getEntityVO());
				}
				this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, "修改成功!"));
			}
		}catch(Exception ex){
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "保存失败,失败原因:" + ex.getMessage()));
		}
	}
	
	/**
	 * @Title: rolemgr
	 * @Description: 进入角色管理界面
	 * @return
	 */
	public String rolemgr(){
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * @Title: menutree
	 * @Description: 进入角色菜单设计界面
	 * @return
	 */
	public String menutree(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
		}catch(Exception ex){
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * @Title: queryRoleMenusJson
	 * @Description: 异步获取菜单树信息
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public void queryRoleMenuPageJson() throws IOException{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String roleId = (String) paramsMap.get("roleId");
			if(!StringUtils.isNotBlank(roleId)){
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "角色信息为空"));
			}
			QueryRoleOfMenuStrategy strategy = new QueryRoleOfMenuStrategy(roleId, Constants.MENU_TYPE_PAGE);
			List<MenuVO> menuList = (List<MenuVO>) this.getService().doProcess(strategy);
			StringBuffer roleMenuXML = TreeBuilder.buildTreeXML(menuList, "menuId", "menuName", "parentMenuId", null, "chked");
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, roleMenuXML.toString()));
		}catch(Exception ex){
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	}
	
	/**
	 * @Title: queryRoleMenusJson
	 * @Description: 异步获取菜单树信息
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public void queryRoleMenuControlJson() throws IOException{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String roleId = (String) paramsMap.get("roleId");
			if(!StringUtils.isNotBlank(roleId)){
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "角色信息为空"));
			}
			String menuId = (String) paramsMap.get("menuId");
			if(!StringUtils.isNotBlank(menuId)){
				this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "菜单信息为空"));
			}
			QueryRoleOfMenuStrategy strategy = new QueryRoleOfMenuStrategy(roleId, Constants.MENU_TYPE_CONTROL, menuId);
			List<MenuVO> menuList = (List<MenuVO>) this.getService().doProcess(strategy);
			StringBuffer roleMenuXML = TreeBuilder.buildTreeXML(menuList, "menuId", "menuName", "parentMenuId", null, "chked");
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, roleMenuXML.toString()));
		}catch(Exception ex){
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.getMessage()));
		}
	}
	
	/**
	 * @Title: saveRoleOfMenusJson
	 * @Description: 保存页面择菜单项
	 * @throws IOException
	 */
	public void saveRoleOfMenusJson() throws IOException{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String addMenus = (String) paramsMap.get("addMenus");
			String delMenus = (String) paramsMap.get("delMenus");
			String roleId = (String) paramsMap.get("roleId");
			String []addMenuIds = null;
			String []delMenuIds = null;
			if(StringUtils.isNotBlank(addMenus)){
				addMenuIds = addMenus.split("\\|");
			}
			if(StringUtils.isNotBlank(delMenus)){
				delMenuIds = delMenus.split("\\|");
			}
			SaveRoleOfMenuStrategy strategy = new SaveRoleOfMenuStrategy(roleId, addMenuIds, delMenuIds, userVO);
			this.getService().doProcess(strategy);
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, "保存成功!"));
		}catch(Exception ex){
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, "保存失败,失败原因:" + ex.getMessage()));
		}
	}
	/**
	 * @Title: saveRoleOfMenusJson
	 * @Description: 保存页面择菜单项
	 * @throws IOException
	 */
	@Override
	public String save() {
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String isNew = (String) paramsMap.get(Constants.PAGE_ISNEW);
			this.setParamsMapToTargetObject(paramsMap, this.getEntityVO());
			if(Constants.STRING_TRUE.equals(isNew)){
				this.setSaveEntityVO(this.getEntityVO(), paramsMap, userVO);
				if(this.getEntityVO() instanceof Business){
					BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_ADD, (Business)this.getEntityVO());
					entityVO = this.getService().doCreate(voClass, this.getEntityVO(), BusinessLogVO.class, logVO);
				}else{
					entityVO = this.getService().doCreate(voClass, this.getEntityVO());
				}
				this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, "保存成功!"));
			}else{
				this.setUpdateEntityVO(this.getEntityVO(), paramsMap, userVO);
				if(this.getEntityVO() instanceof Business){
					BusinessLogVO logVO = BusinessLogVO.create(userVO, Constants.OPRTYPE_MODIFY, (Business)this.getEntityVO());
					entityVO = this.getService().doUpdate(voClass, this.getEntityVO(), BusinessLogVO.class, logVO);
				}else{
					entityVO = this.getService().doUpdate(voClass, this.getEntityVO());
				}
			}
			paramsMap.put("ISSUC", "TRUE");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ActionSupport.SUCCESS;
	}
	

	
	
}
