package com.congxing.core.web.taglib.component;

import java.io.Writer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;

import com.congxing.core.hibernate.Page;
import com.opensymphony.xwork2.util.ValueStack;


public class PageComponent extends Component {
	
	public static final String DEFAULT_PAGE_NUM_SHOW = "10";
	
	private Page page;
	private String pageNumShow;

	public PageComponent(ValueStack stack) {
		super(stack);
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer); 
		try {
			if(null == page){
				page = new Page();
			}
			StringBuffer html = new StringBuffer();
			html.append("<div class=\"pages\">");
			html.append("<span>显示</span>");
			html.append("<select name=\"pageSize\" id=\"pageSize\" class=\"combox\" onchange=\"$('#pageNo').val(1);\">");//
			html.append("<option " + (page.getPageSize() == 20 ? "selected" : "") + " value=\"20\">20</option>");
			html.append("<option " + (page.getPageSize() == 50 ? "selected" : "") + " value=\"50\">50</option>");
			html.append("<option " + (page.getPageSize() == 100 ? "selected" : "") + " value=\"100\">100</option>");
			html.append("<option " + (page.getPageSize() == 200 ? "selected" : "") + " value=\"200\">200</option>");
			html.append("</select>");
			html.append("<span>条，共" + page.getTotalCount() + "条</span>");
			html.append("</div>");
			String pageStr = "<div class=\"pagination\" totalCount=" + page.getTotalCount() + " numPerPage="+ page.getPageSize() + " pageNumShown=" + getPageNumShow() + " currentPage=" + page.getPageNo() + "></div>";
			html.append(pageStr);
			writer.write(html.toString());
		}catch(Exception ex){
			ex.printStackTrace();
		}
        return result;
    }
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getPageNumShow() {
		try{
			if(StringUtils.isBlank(pageNumShow))return DEFAULT_PAGE_NUM_SHOW;//显示页码数量
			if(Integer.parseInt(pageNumShow.trim()) > 0)return pageNumShow;
		}catch(Exception ex){
		}
		return DEFAULT_PAGE_NUM_SHOW;
	}

	public void setPageNumShow(String pageNumShow) {
		this.pageNumShow = pageNumShow;
	}

}
