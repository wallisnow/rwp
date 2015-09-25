package com.congxing.system.menu.view;

import com.congxing.core.web.struts2.BaseListVO;

@SuppressWarnings("serial")
public class MenuListVO extends BaseListVO {
	
	private String _seq_menuId;
	
	private String _seq_parentMenuId;

	public String get_seq_menuId() {
		return _seq_menuId;
	}

	public void set_seq_menuId(String seqMenuId) {
		_seq_menuId = seqMenuId;
	}

	public String get_seq_parentMenuId() {
		return _seq_parentMenuId;
	}

	public void set_seq_parentMenuId(String seqParentMenuId) {
		_seq_parentMenuId = seqParentMenuId;
	}
	
}
