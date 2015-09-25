package com.congxing.core.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import com.congxing.core.batch.BaseBatchTaskBean;

public class DisplayOprTag extends TagSupport {

	private static final long serialVersionUID = -3098857294751361076L;

	protected String task;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public int doStartTag() {
		return SKIP_BODY;
	}

	public int doEndTag() throws JspTagException {
		BaseBatchTaskBean taskbean = (BaseBatchTaskBean) pageContext
				.getSession().getAttribute(task);
		StringBuffer buf = new StringBuffer();
		if (taskbean.isStart_end()) {
			int percent = taskbean.getPercent();
			buf.append("<td WIDTH='10%'><span class=\"processbar\">进度:");
			buf.append(percent + "%</span></td>");
			for (int i = 5; i <= percent; i += 5) {
				buf.append("<td WIDTH='4%' BGCOLOR='#000080'>&nbsp;</td>");
			}
			for (int i = 100; i > percent; i -= 5) {
				buf.append("<td WIDTH='4%'>&nbsp;</td>");
			}
			buf.append("<td WIDTH='10%'><span class=\"processbar\">");
			if (taskbean.isRunning())
				buf.append("正在执行");
			else {
				if (taskbean.isCompleted())
					buf.append("已完成");
				else if (!taskbean.isStarted())
					buf.append("尚未开始");
				else
					buf.append("已停止");
			}
			buf.append("</span></td>");
		}
		try {
			pageContext.getOut().print(buf.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}
