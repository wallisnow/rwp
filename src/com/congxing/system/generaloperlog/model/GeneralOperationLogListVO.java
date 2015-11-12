package com.congxing.system.generaloperlog.model;

import com.congxing.system.logindetstat.model.LoginDetStatVO;

public class GeneralOperationLogListVO {

	private String _sge_dt;
	private String _sle_dt;

	public String get_sge_dt() {
		return _sge_dt;
	}

	public void set_sge_dt(String _sge_dt) {
		this._sge_dt = _sge_dt;
	}

	public String get_sle_dt() {
		return _sle_dt;
	}

	public void set_sle_dt(String _sle_dt) {
		this._sle_dt = _sle_dt;
	}

	public String getVoClassName() {
		return LoginDetStatVO.class.getName();
	}
}
