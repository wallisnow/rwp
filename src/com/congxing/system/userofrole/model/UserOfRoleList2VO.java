package com.congxing.system.userofrole.model;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.MultiParameter;
import com.congxing.core.web.struts2.BaseListVO;
import com.congxing.system.role.model.RoleListVO;
import com.congxing.system.user.model.UserListVO;

@SuppressWarnings("serial")
public class UserOfRoleList2VO extends BaseListVO implements MultiParameter{
	
	private String _seq_userId;
	private String _seq_roleId;

	public String getJoinHQL() {
		return " {0}.userId = {1}.userId and {0}.roleId = {2}.roleId ";
	}

	public BaseListVO[] getMultiListVO() throws Exception {
		UserOfRoleListVO urlistVO = new UserOfRoleListVO();
		BeanUtils.copyProperties(urlistVO, this);
		
		return new BaseListVO[]{urlistVO, new UserListVO(), new RoleListVO()};
	}

	public String getSelectHQL() {
		return " new com.congxing.system.userofrole.model.UserOfRole2VO ({0}, {1}, {2})";
	}

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

}
