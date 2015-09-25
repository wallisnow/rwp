package com.congxing.system.user.model;

import com.congxing.core.web.struts2.BaseListVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class UserListVO extends BaseListVO{
	
	private String _seq_userId;
	
	private String _sk_userId;
	
	private String[] _sin_userId;
	
	private String _sk_userName;
	
	private String _seq_loginId;

	public String get_seq_userId() {
		return _seq_userId;
	}

	public void set_seq_userId(String id) {
		_seq_userId = id;
	}

	public String[] get_sin_userId() {
		return _sin_userId;
	}

	public void set_sin_userId(String[] id) {
		_sin_userId = id;
	}

	public String get_sk_userName() {
		return _sk_userName;
	}

	public void set_sk_userName(String name) {
		_sk_userName = name;
	}

	public String get_seq_loginId() {
		return _seq_loginId;
	}

	public void set_seq_loginId(String id) {
		_seq_loginId = id;
	}

	public String get_sk_userId() {
		return _sk_userId;
	}

	public void set_sk_userId(String skUserId) {
		_sk_userId = skUserId;
	}
	
	public String getVoClassName() {
		return UserVO.class.getName();
	}

}
