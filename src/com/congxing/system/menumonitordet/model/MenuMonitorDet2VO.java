package com.congxing.system.menumonitordet.model;

import java.io.Serializable;

import com.congxing.system.menumonitorconfig.model.MenuMonitorConfigVO;
import com.congxing.system.user.model.UserVO;

@SuppressWarnings("serial")
public class MenuMonitorDet2VO implements Serializable {
	
	private MenuMonitorDetVO detVO;
	
	private MenuMonitorConfigVO configVO;

	private UserVO user;
	
	public MenuMonitorDet2VO(MenuMonitorDetVO detVO, MenuMonitorConfigVO configVO, UserVO user) {
		super();
		this.detVO = detVO;
		this.configVO = configVO;
		this.user = user;
	}

	public MenuMonitorDet2VO() {
		super();
	}

	public MenuMonitorDetVO getDetVO() {
		return detVO;
	}

	public void setDetVO(MenuMonitorDetVO detVO) {
		this.detVO = detVO;
	}

	public MenuMonitorConfigVO getConfigVO() {
		return configVO;
	}

	public void setConfigVO(MenuMonitorConfigVO configVO) {
		this.configVO = configVO;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}
	
}
