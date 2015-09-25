package com.congxing.core.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.congxing.core.web.taglib.component.AttachComponnent;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class AttachTag extends ComponentTagSupport{
	
	private String voName;
	private String pkCol;
	private String pkValue;
	private String attCol;
	private String attId;
	
	private String disabledUpload;
	private String disabledDelete;
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new AttachComponnent(stack);
	}
	
	protected void populateParams() {
		super.populateParams();
		AttachComponnent attachComponnent = (AttachComponnent) this.getComponent();
		attachComponnent.setVoName(voName);
		attachComponnent.setPkCol(pkCol);
		attachComponnent.setPkValue(pkValue);
		attachComponnent.setAttCol(attCol);
		attachComponnent.setAttId(attId);
		if("true".equalsIgnoreCase(this.getDisabledDelete())){
			attachComponnent.setDisabledDelete(true);
		}
		if("true".equalsIgnoreCase(this.getDisabledUpload())){
			attachComponnent.setDisabledUpload(true);
		}
	}

	public String getVoName() {
		return voName;
	}
	public void setVoName(String voName) {
		this.voName = voName;
	}
	public String getPkCol() {
		return pkCol;
	}
	public void setPkCol(String pkCol) {
		this.pkCol = pkCol;
	}
	public String getPkValue() {
		return pkValue;
	}
	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}
	public String getAttCol() {
		return attCol;
	}
	public void setAttCol(String attCol) {
		this.attCol = attCol;
	}
	public String getAttId() {
		return attId;
	}
	public void setAttId(String attId) {
		this.attId = attId;
	}

	public String getDisabledUpload() {
		return disabledUpload;
	}

	public void setDisabledUpload(String disabledUpload) {
		this.disabledUpload = disabledUpload;
	}

	public String getDisabledDelete() {
		return disabledDelete;
	}

	public void setDisabledDelete(String disabledDelete) {
		this.disabledDelete = disabledDelete;
	}
	
}
