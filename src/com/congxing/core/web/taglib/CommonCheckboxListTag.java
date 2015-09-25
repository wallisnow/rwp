package com.congxing.core.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.CheckboxListTag;

import com.congxing.core.web.taglib.component.CommonCheckboxListComponent;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class CommonCheckboxListTag extends CheckboxListTag{

	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new CommonCheckboxListComponent(stack, req, res);
    }

}
