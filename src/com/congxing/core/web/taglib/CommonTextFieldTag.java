package com.congxing.core.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.TextFieldTag;

import com.congxing.core.web.taglib.component.CommonTextFieldComponent;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class CommonTextFieldTag extends TextFieldTag{
	
	private String format;
	private String length;
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new CommonTextFieldComponent(stack, req, res);
	}
	
	protected void populateParams() {
        super.populateParams();
        CommonTextFieldComponent textField = ((CommonTextFieldComponent) component);
        textField.setFormat(format);
        textField.setLength(length);
    }

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

}
