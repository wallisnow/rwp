/**
 * auto-generated code
 * 2013-05-30 07:21:01
 */
package com.congxing.system.menufav.view;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.congxing.core.utils.JsonUtils;
import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.system.menufav.dao.RemoveMenuFavStrategy;
import com.congxing.system.menufav.dao.SaveMenuFavStrategy;
import com.congxing.system.menufav.model.MenuFavListVO;
import com.congxing.system.menufav.model.MenuFavVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * Title: MenuFavAction <br/>
 * Description: Struts main control Class for MenuFav <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
@SuppressWarnings("serial")
public class MenuFavAction extends BaseAction {
    
    public MenuFavAction() {
        this.voClass = MenuFavVO.class;
        this.pkNameArray = new String[] { "seqId" };        
    }

	
    /**
	 * 添加菜单到我的收藏
	 * @throws IOException 
	 */
	public void addMenuFavJson() throws IOException{
		JsonResult jsonResult = null;
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String favMenuId = (String) paramsMap.get("favMenuId");
			if(StringUtils.isBlank(favMenuId)){
				throw new Exception("非法进入");
			}
			
			SaveMenuFavStrategy favStrategy = new SaveMenuFavStrategy(favMenuId, userVO);
			MenuFavVO menuFavVO = (MenuFavVO) this.getService().doProcess(favStrategy);
			
			jsonResult = new JsonResult(ActionSupport.SUCCESS, JsonUtils.toJson(menuFavVO));
		}catch(Exception ex){
			jsonResult = new JsonResult(ActionSupport.ERROR, ex.getMessage());
			ex.printStackTrace();
		}
		this.sendJsonMessage(jsonResult);
	}
	
	/**
	 * 移除我的收藏
	 * @throws IOException 
	 */
	public void removeMenuFavJson() throws IOException{
		JsonResult jsonResult = null;
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String favMenuId = (String) paramsMap.get("favMenuId");
			if(StringUtils.isBlank(favMenuId)){
				throw new Exception("非法进入");
			}
			RemoveMenuFavStrategy removeStrategy = new RemoveMenuFavStrategy(favMenuId, userVO);
			this.getService().doProcess(removeStrategy);
			
			jsonResult = new JsonResult(ActionSupport.SUCCESS, "移除收藏菜单成功");
		}catch(Exception ex){
			jsonResult = new JsonResult(ActionSupport.ERROR, ex.getMessage());
			ex.printStackTrace();
		}
		this.sendJsonMessage(jsonResult);
	}
	
	public String list(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			MenuFavListVO menuFavListVO = new MenuFavListVO();
			this.setParamsMapToTargetObject(paramsMap, menuFavListVO);
			menuFavListVO.set_seq_userId(userVO.getUserId());
			page = this.getService().doQuery(voClass, menuFavListVO);
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
}

