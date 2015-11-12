package com.congxing.system.generalconfig.model;

import java.util.Date;

public class GeneralConfigVO {
	private Long generalconfigId;
	private String formtitle;
	private String formname;
	private String resource;
	private String sqlstatement;
	private String author;
	private Date creatingtime;
	private String mender;
	private Date modtime;
	private String des;
	private String url;
	private String realsql;

	public GeneralConfigVO(Long generalconfigId, String formtitle, String formname, String resource,
			String sqlstatement, String author, Date creatingtime, String mender, Date modtime, String des, String url,
			String realsql) {
		super();
		this.generalconfigId = generalconfigId;
		this.formtitle = formtitle;
		this.formname = formname;
		this.resource = resource;
		this.sqlstatement = sqlstatement;
		this.author = author;
		this.creatingtime = creatingtime;
		this.mender = mender;
		this.modtime = modtime;
		this.des = des;
		this.url = url;
		this.realsql = realsql;
	}

	public GeneralConfigVO() {
	}

	public Long getGeneralconfigId() {
		return generalconfigId;
	}

	public void setGeneralconfigId(Long generalconfigId) {
		this.generalconfigId = generalconfigId;
	}

	public String getFormtitle() {
		return formtitle;
	}

	public void setFormtitle(String formtitle) {
		this.formtitle = formtitle;
	}

	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getSqlstatement() {
		return sqlstatement;
	}

	public void setSqlstatement(String sqlstatement) {
		this.sqlstatement = sqlstatement;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreatingtime() {
		return creatingtime;
	}

	public void setCreatingtime(Date creatingtime) {
		this.creatingtime = creatingtime;
	}

	public String getMender() {
		return mender;
	}

	public void setMender(String mender) {
		this.mender = mender;
	}

	public Date getModtime() {
		return modtime;
	}

	public void setModtime(Date modtime) {
		this.modtime = modtime;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRealsql() {
		return realsql;
	}

	public void setRealsql(String realsql) {
		this.realsql = realsql;
	}

}
