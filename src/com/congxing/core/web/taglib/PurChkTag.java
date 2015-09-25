package com.congxing.core.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.congxing.core.web.taglib.component.PurChkComponent;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class PurChkTag extends ComponentTagSupport {
	
	private String actionUrl;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new PurChkComponent(stack, req);
	}
	
	protected void populateParams() {
		super.populateParams();
		PurChkComponent component = (PurChkComponent)this.getComponent();
		component.setActionUrl(actionUrl);
    }

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
}
