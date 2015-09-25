/**
 * auto-generated code
 * 2013-06-05 03:16:38
 */
package com.congxing.rulemgt.ruleset.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.congxing.rulemgt.reader.model.ReaderVO;
import com.congxing.rulemgt.storage.model.StorageVO;

/**
 * <p>
 * Title: RulesetVO <br/>
 * Description: Mapping Object for table RULESET <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-06-05
 */
public class RulesetVO implements Serializable {

	/**
	 * Determines if a de-serialized file is compatible with this class.
	 * 
	 * Maintainers must change this value if and only if the new version of this
	 * class is not compatible with old versions. See Sun docs for <a
	 * href=http://java.sun.com/products/jdk/1.1/docs/guide
	 * /serialization/spec/version.doc.html> details. </a>
	 * 
	 * Not necessary to include in first version of the class, but included here
	 * as a reminder of its importance.
	 */
	private static final long serialVersionUID = 1L;

	/** identifier field */
	private java.lang.Long rulesetId;

	private String rulesetVersion;

	/** nullable persistent field */
	private java.lang.String rulesetName;

	/** nullable persistent field */
	private java.lang.String rulesetDesc;

	private String status;

	private String versionDesc;

	private String creator;

	private Date createTime;
	
	private String rulesetType;

	public RulesetVO(Long rulesetId, String rulesetVersion, String rulesetName,
			String rulesetDesc, String status, String versionDesc,
			String creator, Date createTime,String rulesetType) {
		super();
		this.rulesetId = rulesetId;
		this.rulesetVersion = rulesetVersion;
		this.rulesetName = rulesetName;
		this.rulesetDesc = rulesetDesc;
		this.status = status;
		this.versionDesc = versionDesc;
		this.creator = creator;
		this.createTime = createTime;
		this.rulesetType = rulesetType;
	}

	public RulesetVO(Long rulesetId, String rulesetVersion) {
		super();
		this.rulesetId = rulesetId;
		this.rulesetVersion = rulesetVersion;
	}
	
	/** default constructor */
	public RulesetVO() {
	}

	public java.lang.Long getRulesetId() {
		return this.rulesetId;
	}

	public void setRulesetId(java.lang.Long rulesetId) {
		this.rulesetId = rulesetId;
	}

	public java.lang.String getRulesetName() {
		return this.rulesetName;
	}

	public void setRulesetName(java.lang.String rulesetName) {
		this.rulesetName = rulesetName;
	}

	public java.lang.String getRulesetDesc() {
		return this.rulesetDesc;
	}

	public void setRulesetDesc(java.lang.String rulesetDesc) {
		this.rulesetDesc = rulesetDesc;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getVersionDesc() {
		return versionDesc;
	}

	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}

	public String getRulesetVersion() {
		return rulesetVersion;
	}

	public void setRulesetVersion(String rulesetVersion) {
		this.rulesetVersion = rulesetVersion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRulesetType() {
		return rulesetType;
	}

	public void setRulesetType(String rulesetType) {
		this.rulesetType = rulesetType;
	}

	public String toString() {
		return new ToStringBuilder(this).append("rulesetId", getRulesetId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof RulesetVO))
			return false;
		RulesetVO castOther = (RulesetVO) other;
		return new EqualsBuilder().append(this.getRulesetId(),
				castOther.getRulesetId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getRulesetId()).toHashCode();
	}

	private List<RulesetBoVO> boDatas;

	private List<RulesetFunVO> funDatas;
	
	private List<ReaderVO> readerDatas;
	
	private List<StorageVO> storageDatas;

	public List<RulesetBoVO> getBoDatas() {
		return boDatas;
	}

	public void setBoDatas(List<RulesetBoVO> boDatas) {
		this.boDatas = boDatas;
	}

	public List<RulesetFunVO> getFunDatas() {
		return funDatas;
	}

	public void setFunDatas(List<RulesetFunVO> funDatas) {
		this.funDatas = funDatas;
	}

	public List<ReaderVO> getReaderDatas() {
		return readerDatas;
	}

	public void setReaderDatas(List<ReaderVO> readerDatas) {
		this.readerDatas = readerDatas;
	}

	public List<StorageVO> getStorageDatas() {
		return storageDatas;
	}

	public void setStorageDatas(List<StorageVO> storageDatas) {
		this.storageDatas = storageDatas;
	}

}
