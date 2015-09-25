package com.congxing.system.menu.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.congxing.core.hibernate.QueryType;
import com.congxing.core.hibernate.Strategy;
import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.core.web.tree.AccordionXMLBuilder;
import com.congxing.core.web.tree.TreeBuilder;
import com.congxing.system.menu.dao.DeleteMenuStrategy;
import com.congxing.system.menu.dao.MenuStrategy;
import com.congxing.system.menu.dao.QueryChildMenuStrategy;
import com.congxing.system.menu.dao.QueryMenuStrategy;
import com.congxing.system.menu.model.MenuVO;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class MenuAction extends BaseAction{
	
	public MenuAction(){
		super.voClass = MenuVO.class;
		super.pkNameArray = new String[]{"seqId"};
	}
	
	public String menumgr(){
		return ActionSupport.SUCCESS;
	}
	/**
	 * 菜单管理树
	 * @return
	 */
	public String tree(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			MenuListVO listVO = new MenuListVO();
			this.setParamsMapToTargetObject(paramsMap, listVO);
			listVO.setFindAll(true);
			page = this.getService().doQuery(voClass, listVO);
			
			StringBuffer treeXML = TreeBuilder.buildTreeXML(page.getDatas(), "menuId", "menuName", "parentMenuId", "seqId", null);
			paramsMap.put("treeXML", treeXML.toString());
		}catch(Exception ex){
			ex.printStackTrace();
			this.addActionMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 增加菜单操作
	 */
	public String add(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			MenuVO menuVO = new MenuVO();
			menuVO.setSeqId(Sequence.getSequence());
			menuVO.setParentMenuId((String)paramsMap.get("menuId"));
			paramsMap.clear();
			this.setTargetObjectToParamsMap(menuVO, paramsMap);
			this.setEdit(true);
			this.setNew(true);
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 保存菜单
	 */
	public String save(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String isNew = (String) paramsMap.get(Constants.PAGE_ISNEW);
			this.setParamsMapToTargetObject(paramsMap, this.getEntityVO());
			MenuVO menuVO = (MenuVO) this.getEntityVO();
			MenuStrategy strategy = new MenuStrategy(menuVO, this.getUserVO());
			if(Constants.STRING_TRUE.equals(isNew)){
				strategy.setIsnew(true);
			}else{
				strategy.setIsnew(false);
			}
			menuVO = (MenuVO) this.getService().doProcess(strategy);
			this.setTargetObjectToParamsMap(menuVO, paramsMap);
			this.setEdit(false);
			this.setNew(false);
			this.addInfoMessage("操作成功");
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage("操作失败,失败原因:" +  ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 查询全部菜单数
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<MenuVO> getChildMenuListByParentMenuId(String menuId) throws Exception{
		MenuListVO listVO = new MenuListVO();
		listVO.setFindAll(true);
		listVO.setOrder(" parentMenuId ");
		page = this.getService().doQuery(MenuVO.class, listVO, QueryType.DATA);
		if(page.hasDatas()){
			return (List<MenuVO>) page.getDatas();
		}
		return new ArrayList<MenuVO>();
	}
	
	/**
	 * 根据父节点查询子菜单
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void queryChildMenuJson() throws IOException{
		JsonResult result = null;
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String menuId = (String) paramsMap.get("menuId");
			if(StringUtils.isBlank(menuId)){
				result = new JsonResult(ActionSupport.ERROR, "非法参数");
				this.sendJsonMessage(result);
				return;
			}
			QueryChildMenuStrategy cf = new QueryChildMenuStrategy(userVO, menuId);
			List<MenuVO> secMenuList = (List<MenuVO>) this.getService().doProcess(cf);
			StringBuffer treeXmlBuffer = TreeBuilder.buildTreeXML(secMenuList, "menuId", "menuName", "parentMenuId", null, "menuUrl");
			if(null != treeXmlBuffer && treeXmlBuffer.length() > 0){
				result = new JsonResult(ActionSupport.SUCCESS, treeXmlBuffer.toString());
			}else{
				result = new JsonResult(ActionSupport.SUCCESS, "");
			}
		}catch(Exception ex){
			result = new JsonResult(ActionSupport.ERROR, ex.getMessage());
		}
		this.sendJsonMessage(result);
	}
	
	public void deleteMenuJson() throws IOException{
		JsonResult result = null;
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String menuId = (String) paramsMap.get("menuId");
			if(StringUtils.isBlank(menuId)){
				result = new JsonResult(ActionSupport.ERROR, "非法操作");
				this.sendJsonMessage(result);
				return;
			}
			DeleteMenuStrategy delStrategy = new DeleteMenuStrategy(menuId, userVO);
			this.getService().doProcess(delStrategy);
				
			result = new JsonResult(ActionSupport.SUCCESS, "删除菜单操作成功");
		}catch(Exception ex){
			result = new JsonResult(ActionSupport.ERROR, ex.getMessage());
		}
		this.sendJsonMessage(result);
	}
	
	
	@SuppressWarnings("unchecked")
	public void menuJson() throws IOException{
		try{
			Strategy strategy = new QueryMenuStrategy(userVO, Constants.MENU_TYPE_PAGE, Constants.ROOT_MENU_ID);
			List<MenuVO> menuDatas = (List<MenuVO>) this.getService().doProcess(strategy);
			String accXML = AccordionXMLBuilder.buildXML(menuDatas, "menuId", "menuName", "parentMenuId", "seqId", "menuUrl", Constants.ROOT_MENU_ID);
			
			this.sendJsonMessage(JsonResult.create(ActionSupport.SUCCESS, accXML));
		}catch(Exception ex){
			ex.printStackTrace();
			this.sendJsonMessage(JsonResult.create(ActionSupport.ERROR, ex.toString()));
		}
	}

}
