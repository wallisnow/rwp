package com.congxing.system.test.view;

import com.congxing.core.web.struts2.BaseAction;
import com.congxing.system.test.model.TestVO;

public class TestAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestAction() {
		this.voClass = TestVO.class;
		this.pkNameArray = new String[]{"testId"};
	}

}
