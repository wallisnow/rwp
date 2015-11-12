package com.congxing.system.generaloperlog.view;

import com.congxing.core.web.struts2.BaseAction;
import com.congxing.system.generaloperlog.model.GeneralOperationLogListVO;

public class GeneralOperationLogAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GeneralOperationLogAction() {
		this.voClass = GeneralOperationLogListVO.class;
		this.pkNameArray = new String[]{"logId"};
	}
	
}
