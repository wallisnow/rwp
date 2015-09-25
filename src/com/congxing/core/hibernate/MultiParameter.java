package com.congxing.core.hibernate;

import java.io.Serializable;

import com.congxing.core.web.struts2.BaseListVO;

public interface MultiParameter extends Serializable{
	
	public String getSelectHQL();
	
	public String getJoinHQL();
	
	public BaseListVO[] getMultiListVO() throws Exception;
}
