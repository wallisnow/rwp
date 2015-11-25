package com.congxing.system.generaloperlog.view;

import org.apache.log4j.Logger;

import com.congxing.core.web.struts2.BaseAction;
import com.congxing.system.generaloperlog.model.GeneralOperationLogListVO;
import com.congxing.system.test.view.TestAction;

public class GeneralOperationLogAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(GeneralOperationLogAction.class);
	
	public GeneralOperationLogAction() {
		this.voClass = GeneralOperationLogListVO.class;
		this.pkNameArray = new String[]{"logId"};
		logger.info("-----double class test");
	}
	
}
