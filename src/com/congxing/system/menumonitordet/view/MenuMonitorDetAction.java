/**
 * auto-generated code
 * 2013-05-30 07:28:46
 */
package com.congxing.system.menumonitordet.view;

import com.congxing.core.utils.ParamsBuilder;
import com.congxing.core.web.constant.Constants;
import com.congxing.core.web.struts2.BaseAction;
import com.congxing.system.menumonitordet.model.MenuMonitorDetList2VO;
import com.congxing.system.menumonitordet.model.MenuMonitorDetVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * Title: MenuMonitorDetAction <br/>
 * Description: Struts main control Class for MenuMonitorDet <br/>
 * Copyright: Copyright (c) 2006 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-05-30
 */
@SuppressWarnings("serial")
public class MenuMonitorDetAction extends BaseAction {
    
    public MenuMonitorDetAction() {
        this.voClass = MenuMonitorDetVO.class;
        this.pkNameArray = new String[] { "seqId" };        
    }
    
    public String list(){
		try{
			paramsMap = ParamsBuilder.buildMapFromHttpRequest();
			MenuMonitorDetList2VO list2VO = new MenuMonitorDetList2VO();
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
			MenuMonitorDetList2VO list2VO = new MenuMonitorDetList2VO();
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

