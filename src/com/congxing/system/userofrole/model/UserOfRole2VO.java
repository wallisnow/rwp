package com.congxing.system.userofrole.model;

import java.io.Serializable;

import com.congxing.system.role.model.RoleVO;
import com.congxing.system.user.model.UserVO;

public class UserOfRole2VO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private UserOfRoleVO userOfRole;
	private UserVO user;
	private RoleVO role;
	
	public UserOfRole2VO(UserOfRoleVO userOfRole, UserVO user, RoleVO role) {
		this.userOfRole = userOfRole;
		this.user = user;
		this.role = role;
	}
	
	public UserOfRole2VO() {
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
	public RoleVO getRole() {
		return role;
	}
	public void setRole(RoleVO role) {
		this.role = role;
	}
}
