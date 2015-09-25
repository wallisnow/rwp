package com.congxing.system.userofrole.model;

import com.congxing.core.web.struts2.BaseListVO;

@SuppressWarnings("serial")
public class UserOfRoleListVO extends BaseListVO{
	
	private String _seq_userId;
	private String _seq_roleId;
	private String _dge_beginTime;
	private String _dle_endTime;
	private String _seq_status;
	
	public String get_seq_userId() {
		return _seq_userId;
	}
	public void set_seq_userId(String seqUserId) {
		_seq_userId = seqUserId;
	}
	public String get_seq_roleId() {
		return _seq_roleId;
	}
	public void set_seq_roleId(String seqRoleId) {
		_seq_roleId = seqRoleId;
	}
	
	public String get_dge_beginTime() {
		return _dge_beginTime;
	}
	public void set_dge_beginTime(String dgeBeginTime) {
		_dge_beginTime = dgeBeginTime;
	}
	public String get_dle_endTime() {
		return _dle_endTime;
	}
	public void set_dle_endTime(String dleEndTime) {
		_dle_endTime = dleEndTime;
	}
	public String get_seq_status() {
		return _seq_status;
	}
	public void set_seq_status(String seqStatus) {
		_seq_status = seqStatus;
	}
	public String getVoClassName() {
		return UserOfRoleVO.class.getName();
	}
}
