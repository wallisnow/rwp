package com.congxing.core.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.SelectTag;

import com.congxing.core.web.taglib.component.CommonSelectComponent;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class CommonSelectTag extends SelectTag{
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new CommonSelectComponent(stack, req, res);
    }

}
