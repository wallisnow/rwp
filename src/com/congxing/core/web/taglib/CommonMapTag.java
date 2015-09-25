package com.congxing.core.web.taglib;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.congxing.core.web.taglib.component.CommonMapComponent;
import com.opensymphony.xwork2.util.ValueStack;


@SuppressWarnings("serial")
public class CommonMapTag extends ComponentTagSupport{
	
	private Map<String, Object> map;
	private String key;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new CommonMapComponent(stack);
	}
	
	protected void populateParams() {
		super.populateParams();
		CommonMapComponent mapComponent = (CommonMapComponent)this.getComponent();
		mapComponent.setMap(map);
		mapComponent.setKey(key);
    }
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
