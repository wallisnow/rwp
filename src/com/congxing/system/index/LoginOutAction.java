package com.congxing.system.index;

import com.congxing.core.utils.Struts2Utils;
import com.congxing.core.web.constant.Constants;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LoginOutAction extends ActionSupport {
	
	public String execute() {
		Object loginObj = Struts2Utils.getSession().getAttribute(Constants.SESSION_USER);
		if(null != loginObj){
			Struts2Utils.getSession().removeAttribute(Constants.SESSION_USER);
		}
		Struts2Utils.getRequest().getSession().invalidate();
		return ActionSupport.SUCCESS;
	}

}
