package com.congxing.system.test.view;

import org.apache.log4j.Logger;

import com.congxing.core.web.struts2.BaseAction;
import com.congxing.system.test.model.TestVO;

public class TestAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(TestAction.class);
	
	public TestAction() {
		this.voClass = TestVO.class;
		this.pkNameArray = new String[]{"testId"};
		logger.info("test");
	}
}
