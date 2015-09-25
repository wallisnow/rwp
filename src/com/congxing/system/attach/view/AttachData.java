package com.congxing.system.attach.view;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class AttachData implements Serializable {
	
	private String voName;
	private String pkCol;
	private String pkValue;
	private String attCol;
	private String attId;
	
	public boolean isValid(){
		boolean valid = true;
		if(StringUtils.isBlank(voName)){
			valid = false;
		}
		try{
			Class.forName(voName.trim());
		}catch(Exception ex){
			valid = false;
		}
		if(StringUtils.isBlank(pkCol)){
			valid = false;
		}
		if(StringUtils.isBlank(pkValue)){
			valid = false;
		}
		if(StringUtils.isBlank(attCol)){
			valid = false;
		}
		if(StringUtils.isBlank(attId)){
			valid = false;
		}
		return valid;
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

}
