package com.congxing.core.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.congxing.core.web.taglib.component.MsgComponent;
import com.opensymphony.xwork2.util.ValueStack;


@SuppressWarnings("serial")
public class MsgTag extends ComponentTagSupport{
	
	private String key;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return new MsgComponent(stack, req);
	}
	
	protected void populateParams() {
		super.populateParams();
		MsgComponent msgComponent = (MsgComponent)this.getComponent();
		msgComponent.setKey(key);
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
