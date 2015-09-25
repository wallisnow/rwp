package com.congxing.system.user.model;

import java.io.Serializable;

import com.congxing.system.user.model.UserVO;
import com.congxing.system.userofrole.model.UserOfRoleVO;

public class UserWithRoleVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	private UserVO user;
	
	private UserOfRoleVO userOfRole;
	
	public UserWithRoleVO(UserVO user, UserOfRoleVO userOfRole) {
		this.user = user;
		this.userOfRole = userOfRole;
	}
	
	public UserWithRoleVO() {
		super();
	}

	public UserOfRoleVO getUserOfRole() {
		return userOfRole;
	}
	public void setUserOfRole(UserOfRoleVO userOfRole) {
		this.userOfRole = userOfRole;
	}
	public UserVO getUser() {
		return user;
	}
	public void setUser(UserVO user) {
		this.user = user;
	}
}
