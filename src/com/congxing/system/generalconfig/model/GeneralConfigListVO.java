package com.congxing.system.generalconfig.model;

import java.io.Serializable;

import com.congxing.core.web.struts2.BaseListVO;

public class GeneralConfigListVO extends BaseListVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String _seq_formtitle;
	private String _seq_formname;

	public String get_seq_formtitle() {
		return _seq_formtitle;
	}

	public void set_seq_formtitle(String _seq_formtitle) {
		this._seq_formtitle = _seq_formtitle;
	}

	public String get_seq_formname() {
		return _seq_formname;
	}

	public void set_seq_formname(String _seq_formname) {
		this._seq_formname = _seq_formname;
	}
}
