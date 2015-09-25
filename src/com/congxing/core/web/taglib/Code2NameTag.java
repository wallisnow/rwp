package com.congxing.core.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.congxing.core.web.taglib.component.Code2NameComponent;
import com.opensymphony.xwork2.util.ValueStack;


@SuppressWarnings("serial")
public class Code2NameTag extends ComponentTagSupport{
	
	private String definition;
	private Object value;	
	private String params;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new Code2NameComponent(stack);
	}
	
	protected void populateParams() {
		super.populateParams();
		Code2NameComponent code2NameComponent = (Code2NameComponent)this.getComponent();
		code2NameComponent.setDefinition(definition);
		code2NameComponent.setValue(value);
		code2NameComponent.setParams(params);
    }

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
