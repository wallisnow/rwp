package com.congxing.system.role.model;

import com.congxing.core.web.struts2.BaseListVO;
import com.congxing.system.role.model.RoleVO;


@SuppressWarnings("serial")
public class RoleListVO extends BaseListVO{
	
	private String _seq_roleId;
	private String _sk_roleName;
	private String _seq_status;
	
	public String get_seq_roleId() {
		return _seq_roleId;
	}
	public void set_seq_roleId(String id) {
		_seq_roleId = id;
	}
	public String get_seq_status() {
		return _seq_status;
	}
	public void set_seq_status(String _seq_status) {
		this._seq_status = _seq_status;
	}
	public String get_sk_roleName() {
		return _sk_roleName;
	}
	public void set_sk_roleName(String name) {
		_sk_roleName = name;
	}
	
	public String getVoClassName() {
		return RoleVO.class.getName();
	}

}
