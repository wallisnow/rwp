/**
 * auto-generated code
 * 2013-06-07 01:16:11
 */
package com.congxing.rulemgt.ruleset.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: RuleLogVO <br/>
 * Description: Mapping Object for table RULE_LOG <br/>
 * Copyright: Copyright (c) 2013 <br/>
 * Company: Revenco Tech Ltd. <br/>
 * </p>
 * 
 * @author LIUKANGJIN
 * @version 1.0
 * @since 2013-06-07
 */
public class RuleLogVO implements Serializable {

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
	private java.lang.Long logId;

	/** nullable persistent field */
	private java.lang.String oprCode;

	/** nullable persistent field */
	private java.util.Date oprTime;

	/** nullable persistent field */
	private java.lang.String oprType;

	/** nullable persistent field */
	private java.lang.Long ruleId;

	/** nullable persistent field */
	private java.lang.Long rulesetId;
	
	/** nullable persistent field */
	private String rulesetVersion;
	
	/** nullable persistent field */
	private String ruleType;

	/** nullable persistent field */
	private java.lang.String ruleEnName;

	/** nullable persistent field */
	private java.lang.String ruleZhName;

	/** nullable persistent field */
	private java.lang.String ruleDesc;

	/** nullable persistent field */
	private java.lang.Long ruleSalience;

	/** nullable persistent field */
	private java.lang.String ruleWhenHtml;

	/** nullable persistent field */
	private java.lang.String ruleThenHtml;

	/** nullable persistent field */
	private java.lang.String ruleSemantics;

	/** nullable persistent field */
	private java.lang.String ruleCode;

	public RuleLogVO(Long logId, String oprCode, Date oprTime, String oprType,
			Long ruleId, Long rulesetId, String rulesetVersion,
			String ruleType, String ruleEnName, String ruleZhName,
			String ruleDesc, Long ruleSalience, String ruleWhenHtml,
			String ruleThenHtml, String ruleSemantics, String ruleCode) {
		super();
		this.logId = logId;
		this.oprCode = oprCode;
		this.oprTime = oprTime;
		this.oprType = oprType;
		this.ruleId = ruleId;
		this.rulesetId = rulesetId;
		this.rulesetVersion = rulesetVersion;
		this.ruleType = ruleType;
		this.ruleEnName = ruleEnName;
		this.ruleZhName = ruleZhName;
		this.ruleDesc = ruleDesc;
		this.ruleSalience = ruleSalience;
		this.ruleWhenHtml = ruleWhenHtml;
		this.ruleThenHtml = ruleThenHtml;
		this.ruleSemantics = ruleSemantics;
		this.ruleCode = ruleCode;
	}

	/** minimal constructor */
	public RuleLogVO(java.lang.Long logId) {
		this.logId = logId;
	}

	/** default constructor */
	public RuleLogVO() {
	}

	public java.lang.Long getLogId() {
		return this.logId;
	}

	public void setLogId(java.lang.Long logId) {
		this.logId = logId;
	}

	public java.lang.String getOprCode() {
		return this.oprCode;
	}

	public void setOprCode(java.lang.String oprCode) {
		this.oprCode = oprCode;
	}

	public java.util.Date getOprTime() {
		return this.oprTime;
	}

	public void setOprTime(java.util.Date oprTime) {
		this.oprTime = oprTime;
	}

	public java.lang.String getOprType() {
		return this.oprType;
	}

	public void setOprType(java.lang.String oprType) {
		this.oprType = oprType;
	}

	public java.lang.Long getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(java.lang.Long ruleId) {
		this.ruleId = ruleId;
	}

	public java.lang.Long getRulesetId() {
		return this.rulesetId;
	}

	public void setRulesetId(java.lang.Long rulesetId) {
		this.rulesetId = rulesetId;
	}

	public String getRulesetVersion() {
		return rulesetVersion;
	}

	public void setRulesetVersion(String rulesetVersion) {
		this.rulesetVersion = rulesetVersion;
	}

	public java.lang.String getRuleEnName() {
		return ruleEnName;
	}

	public void setRuleEnName(java.lang.String ruleEnName) {
		this.ruleEnName = ruleEnName;
	}

	public java.lang.String getRuleZhName() {
		return ruleZhName;
	}

	public void setRuleZhName(java.lang.String ruleZhName) {
		this.ruleZhName = ruleZhName;
	}

	public java.lang.String getRuleDesc() {
		return this.ruleDesc;
	}

	public void setRuleDesc(java.lang.String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public java.lang.Long getRuleSalience() {
		return this.ruleSalience;
	}

	public void setRuleSalience(java.lang.Long ruleSalience) {
		this.ruleSalience = ruleSalience;
	}

	public java.lang.String getRuleWhenHtml() {
		return ruleWhenHtml;
	}

	public void setRuleWhenHtml(java.lang.String ruleWhenHtml) {
		this.ruleWhenHtml = ruleWhenHtml;
	}

	public java.lang.String getRuleThenHtml() {
		return ruleThenHtml;
	}

	public void setRuleThenHtml(java.lang.String ruleThenHtml) {
		this.ruleThenHtml = ruleThenHtml;
	}

	public java.lang.String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(java.lang.String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public java.lang.String getRuleSemantics() {
		return ruleSemantics;
	}

	public void setRuleSemantics(java.lang.String ruleSemantics) {
		this.ruleSemantics = ruleSemantics;
	}

	public String toString() {
		return new ToStringBuilder(this).append("logId", getLogId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof RuleLogVO))
			return false;
		RuleLogVO castOther = (RuleLogVO) other;
		return new EqualsBuilder()
				.append(this.getLogId(), castOther.getLogId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getLogId()).toHashCode();
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
}
