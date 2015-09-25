/**
 *
 */
package com.congxing.core.web.struts2;

import java.io.Serializable;

import com.congxing.core.web.constant.Constants;
import com.congxing.system.user.model.UserVO;
import com.opensymphony.xwork2.ActionContext;

/**
 * <p>Title: BatchFileInfo</p>
 *
 * <p>Description: BatchFileInfo</p>
 *
 * <p>Copyright: Copyright (c) Revenco</p>
 *
 * <p>Company: Sunrise Tech Ltd.</p>
 *
 * @author Lai Weilong
 *
 * @version 1.0
 *
 * @since 2011-10-21
 */
public class BatchFileInfo implements Serializable {

	private static final long serialVersionUID = -2582622506985817992L;

	private String oldFileName; // 原文件名

	private String saveAsFullPath; // 另存全路径名

	private String saveAsFileName; // 另存文件名

	private String style; // 重命名格式

	private String prefix; // 重命名前缀

	public BatchFileInfo() {
	}

	public String getOldFileName() {
		return oldFileName;
	}

	public void setOldFileName(String fileName) {
		this.oldFileName = fileName;
	}

	public String getSaveAsFullPath() {
		return saveAsFullPath;
	}

	public void setSaveAsFullPath(String saveAsFullPath) {
		this.saveAsFullPath = saveAsFullPath;
	}

	public String getSaveAsFileName() {
		return saveAsFileName;
	}

	public void setSaveAsFileName(String saveAsFileName) {
		this.saveAsFileName = saveAsFileName;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getRenameStyle() {
		return this.style;
	}

	public String getRenamePrefix() {
		String filenamePrefix = this.prefix;
		if (filenamePrefix == null) {
			UserVO user = (UserVO) ActionContext.getContext().getSession().get(Constants.SESSION_USER);
			if(user != null){
				filenamePrefix = user.getUserId();
			}
		}
		return filenamePrefix;
	}

}
