package com.congxing.system.user.model;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.MultiParameter;
import com.congxing.core.web.struts2.BaseListVO;
import com.congxing.system.userofrole.model.UserOfRoleListVO;

@SuppressWarnings("serial")
public class UserWithRoleListVO extends BaseListVO implements MultiParameter{
	
	private String _seq_roleId;
	
	private String _seq_userId;
	
	private String _sk_userId;
	
	private String[] _sin_userId;
	
	private String _sk_userName;
	
	private String _seq_loginId;

	public String getJoinHQL() {
		return " {0}.userId = {1}.userId ";
	}

	public BaseListVO[] getMultiListVO() throws Exception {
		UserListVO userlistVO = new UserListVO();
		BeanUtils.copyProperties(userlistVO, this);
		
		UserOfRoleListVO urListVO = new UserOfRoleListVO();
		urListVO.set_seq_roleId(this.get_seq_roleId());
		
		return new BaseListVO[]{userlistVO, urListVO};
	}

	public String getSelectHQL() {
		return " new com.congxing.system.user.model.UserWithRoleVO ({0}, {1})";
	}


	public String get_seq_roleId() {
		return _seq_roleId;
	}

	public void set_seq_roleId(String seqRoleId) {
		_seq_roleId = seqRoleId;
	}

	public String get_seq_userId() {
		return _seq_userId;
	}

	public void set_seq_userId(String seqUserId) {
		_seq_userId = seqUserId;
	}

	public String get_sk_userId() {
		return _sk_userId;
	}

	public void set_sk_userId(String skUserId) {
		_sk_userId = skUserId;
	}

	public String[] get_sin_userId() {
		return _sin_userId;
	}

	public void set_sin_userId(String[] sinUserId) {
		_sin_userId = sinUserId;
	}

	public String get_sk_userName() {
		return _sk_userName;
	}

	public void set_sk_userName(String skUserName) {
		_sk_userName = skUserName;
	}

	public String get_seq_loginId() {
		return _seq_loginId;
	}

	public void set_seq_loginId(String seqLoginId) {
		_seq_loginId = seqLoginId;
	}

}
