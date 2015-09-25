package com.congxing.system.userofrole.view;

import java.util.Map;

import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.utils.PkUtils;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.sequence.Sequence;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.system.user.model.UserVO;
import com.congxing.system.userofrole.dao.UserOfRoleStrategy;
import com.congxing.system.userofrole.model.UserOfRoleList2VO;
import com.congxing.system.userofrole.model.UserOfRoleVO;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class UserOfRoleAction extends BaseAction {
	
	public UserOfRoleAction(){
		this.voClass = UserOfRoleVO.class;
		this.pkNameArray = new String[]{"urId"};
	}
	
	/**
	 * @Title: setAddEntityVO
	 * @Description: 用于doAdd方法，在新建记录前，为EntityVO设置某些恰当的初值
	 * @param entityVO
	 * @param paramsMap
	 * @param userVO
	 * @throws Exception
	 */
	protected void setAddEntityVO(Object entityVO, Map<String, Object> paramsMap, UserVO userVO) throws Exception {
		UserOfRoleVO urVO = (UserOfRoleVO)entityVO;
		urVO.setUrId(Sequence.getSequence());
		urVO.setStatus(Constants.TYPE_YES);
		this.entityVO = urVO;
	}
	
	public String save(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String isNew = (String) paramsMap.get(Constants.PAGE_ISNEW);
			
			UserOfRoleVO urVO = new UserOfRoleVO();
			this.setParamsMapToTargetObject(paramsMap, urVO);
			
			UserOfRoleStrategy strategy = new UserOfRoleStrategy(urVO, this.getUserVO());
			
			if(Constants.STRING_TRUE.equals(isNew)){
				strategy.setOprType(Constants.OPRTYPE_ADD);
			}else{
				strategy.setOprType(Constants.OPRTYPE_MODIFY);
			}
			this.getService().doProcess(strategy);
			this.addInfoMessage("操作成功");
			this.setTargetObjectToParamsMap(urVO, paramsMap);
			this.setEdit(false);
			this.setNew(false);
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage("操作失败,失败原因:" +  ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	public String delete(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			String []pkValues = this.getParamValueByParamsMap(paramsMap, "PK");
			if(null != pkValues && pkValues.length > 0)
			for (int index = 0; index < pkValues.length; index++) {
				UserOfRoleVO urVO = (UserOfRoleVO) this.getService().doFindByPK(voClass, PkUtils.getPkVO(voClass, pkNameArray, pkValues[index]));
				UserOfRoleStrategy strategy = new UserOfRoleStrategy(urVO, this.getUserVO());
				strategy.setOprType(Constants.OPRTYPE_DELETE);
				this.getService().doProcess(strategy);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	
	public String list(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			UserOfRoleList2VO list2VO = new UserOfRoleList2VO();
			this.setParamsMapToTargetObject(paramsMap, list2VO);
			page = this.getService().doQuery(voClass, list2VO);
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	public String excel(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			UserOfRoleList2VO list2VO = new UserOfRoleList2VO();
			this.setParamsMapToTargetObject(paramsMap, list2VO);
			list2VO.setPageSize(Constants.DEFAULT_MAX_EXCEL);
			list2VO.setPageNo(Constants.DEFAULT_PAGENO);
			page = this.getService().doQuery(voClass, list2VO);
		}catch(Exception ex){
			ex.printStackTrace();
			this.addErrorMessage(ex.getMessage());
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
}
