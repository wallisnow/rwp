package com.congxing.system.user.view;

import com.congxing.core.utils.MD5Encrypt;
import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.core.web.struts2.JsonResult;
import com.congxing.system.user.dao.ChangPwdByAdminStrategy;
import com.congxing.system.user.dao.ChangePwdBySelfStrategy;
import com.congxing.system.user.dao.UserStrategy;
import com.congxing.system.user.model.UserVO;
import com.congxing.system.user.model.UserWithRoleListVO;
import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
public class UserAction extends BaseAction {
	
	private static String defaultPassword = "123456";

	public UserAction(){
		this.voClass = UserVO.class;
		this.pkNameArray = new String[]{"userId"};
	}
	
	public String save(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String isNew = (String) paramsMap.get(Constants.PAGE_ISNEW);
			UserVO curVO = new UserVO();
			this.setParamsMapToTargetObject(paramsMap, curVO);
			
			UserStrategy strategy = new UserStrategy(curVO, this.getUserVO());
			
			if(Constants.STRING_TRUE.equals(isNew)){
				curVO.setPassword(MD5Encrypt.getMD5Password(defaultPassword));
				strategy.setOprType(Constants.OPRTYPE_ADD);
			}else{
				strategy.setOprType(Constants.OPRTYPE_MODIFY);
			}
			this.getService().doProcess(strategy);
			
			this.addInfoMessage("操作成功");
			this.setTargetObjectToParamsMap(curVO, paramsMap);
			this.setEdit(false);
			this.setNew(false);
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage("操作失败,失败原因:" +  ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	public void changePwdJson() throws Exception{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String userId = (String)paramsMap.get("userId");
			String oldPwd = (String)paramsMap.get("oldPwd");
			String newPwd = (String)paramsMap.get("newPwd");
			
			ChangePwdBySelfStrategy strategy = new ChangePwdBySelfStrategy(userId, oldPwd, newPwd);

			JsonResult result = (JsonResult) this.getService().doProcess(strategy);
			
			this.sendJsonMessage(result);
			
		}catch(Exception ex){
			ex.printStackTrace();
			this.sendJsonMessage(new JsonResult(ActionSupport.ERROR, "操作失败,失败原因:" +  ex.getMessage()));
		}
		
	}
	
	public void adminChangePwdJson() throws Exception{
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String userId = (String)paramsMap.get("userId");
			String newPwd = (String)paramsMap.get("newPwd");
			
			ChangPwdByAdminStrategy strategy = new ChangPwdByAdminStrategy(userId, newPwd, userVO);

			JsonResult result = (JsonResult) this.getService().doProcess(strategy);
			
			this.sendJsonMessage(result);
		}catch(Exception ex){
			ex.printStackTrace();
			this.sendJsonMessage(new JsonResult(ActionSupport.ERROR, "操作失败,失败原因:" +  ex.getMessage()));
		}		
	}
	
	public String adminChangePwd(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String userId = (String)paramsMap.get("userId");
			paramsMap.put("userId", userId);
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	public String selectWithRole(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			UserWithRoleListVO list2VO = new UserWithRoleListVO();
			this.setParamsMapToTargetObject(paramsMap, list2VO);
			page = this.getService().doQuery(voClass, list2VO);
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
}
