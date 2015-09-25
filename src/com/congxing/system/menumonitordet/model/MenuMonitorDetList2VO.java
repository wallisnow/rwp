package com.congxing.system.menumonitordet.model;

import org.apache.commons.beanutils.BeanUtils;

import com.congxing.core.hibernate.MultiParameter;
import com.congxing.core.web.struts2.BaseListVO;
import com.congxing.system.menumonitorconfig.model.MenuMonitorConfigListVO;
import com.congxing.system.user.model.UserListVO;

@SuppressWarnings("serial")
public class MenuMonitorDetList2VO extends BaseListVO implements MultiParameter {
	
	private String _sk_userId;
	
	private String _sk_userName;
	
	private String _dge_beginTime;
	
	private String _dle_endTime;
	
	private String _sk_monitorUrl;
	
	private String _sk_monitorName;	
	

	public String getJoinHQL() {
		return " {0}.monitorId = {1}.monitorId and {0}.userId = {2}.userId ";
	}

	public BaseListVO[] getMultiListVO() throws Exception {
		MenuMonitorDetListVO detListVO = new MenuMonitorDetListVO();
		BeanUtils.copyProperties(detListVO, this);
		
		MenuMonitorConfigListVO configListVO = new MenuMonitorConfigListVO();
		configListVO.set_sk_monitorName(this.get_sk_monitorName());
		configListVO.set_sk_monitorUrl(this.get_sk_monitorUrl());
		
		UserListVO userListVO = new UserListVO();
		userListVO.set_sk_userName(this.get_sk_userName());
		
		return new BaseListVO[]{detListVO, configListVO, userListVO};
	}

	public String getSelectHQL() {
		return " new com.congxing.system.menumonitordet.model.MenuMonitorDet2VO({0}, {1}, {2}) ";
	}

	public String get_sk_userId() {
		return _sk_userId;
	}

	public void set_sk_userId(String skUserId) {
		_sk_userId = skUserId;
	}

	public String get_dge_beginTime() {
		return _dge_beginTime;
	}

	public void set_dge_beginTime(String _dge_beginTime) {
		this._dge_beginTime = _dge_beginTime;
	}

	public String get_sk_monitorUrl() {
		return _sk_monitorUrl;
	}

	public void set_sk_monitorUrl(String skMonitorUrl) {
		_sk_monitorUrl = skMonitorUrl;
	}

	public String get_sk_monitorName() {
		return _sk_monitorName;
	}

	public void set_sk_monitorName(String skMonitorName) {
		_sk_monitorName = skMonitorName;
	}

	public String get_sk_userName() {
		return _sk_userName;
	}

	public void set_sk_userName(String skUserName) {
		_sk_userName = skUserName;
	}

	public String get_dle_endTime() {
		return _dle_endTime;
	}

	public void set_dle_endTime(String dleEndTime) {
		_dle_endTime = dleEndTime;
	}

}
