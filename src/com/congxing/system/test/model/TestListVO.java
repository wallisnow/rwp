package com.congxing.system.test.model;

import com.congxing.core.web.struts2.BaseListVO;

public class TestListVO extends BaseListVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String _seq_testId;
	private String _seq_username;

	public String get_seq_username() {
		return _seq_username;
	}

	public void set_seq_username(String _seq_username) {
		this._seq_username = _seq_username;
	}

	public String get_seq_testId() {
		return _seq_testId;
	}

	public void set_seq_testId(String _seq_testId) {
		this._seq_testId = _seq_testId;
	}

}
