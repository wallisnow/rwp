package com.congxing.core.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.congxing.core.hibernate.Page;
import com.congxing.core.web.taglib.component.PageComponent;
import com.opensymphony.xwork2.util.ValueStack;


@SuppressWarnings("serial")
public class PageTag extends ComponentTagSupport{

	private Page page;
	private String pageNumShow;
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new PageComponent(stack);
	}
	
	protected void populateParams() {
		super.populateParams();
		PageComponent pageComponent = (PageComponent)this.getComponent();
		pageComponent.setPage(page);
		pageComponent.setPageNumShow(pageNumShow);
    }

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getPageNumShow() {
		return pageNumShow;
	}

	public void setPageNumShow(String pageNumShow) {
		this.pageNumShow = pageNumShow;
	}
	
}
